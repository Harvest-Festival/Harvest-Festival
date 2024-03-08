package uk.joshiejack.harvestfestival.world.level.mine.teleport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.ITeleporter;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.network.PlayerChangedTierPacket;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.helper.TagHelper;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

public class MineTeleporter implements ITeleporter {
    private final int id; //id we're targeting
    private final int floor; //floor we're teleporting to
    private final TeleportType type; //type of teleport

    public MineTeleporter(int id, int floor, TeleportType type) {
        this.id = id;
        this.floor = floor;
        this.type = type;
    }

    public static void exitMine(ServerLevel world, Entity entity) {
        CompoundTag data = TagHelper.getOrCreateTag(entity.getPersistentData(), "MineTeleportData");
        ResourceKey<Level> targetWorld = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(data.getString("Origin")));
        ServerLevel level = world.getServer().getLevel(targetWorld);
        if (level == null) return;
        BlockPos targetPos = BlockPos.of(data.getLong("OriginPos"));
        Direction direction = Direction.from3DDataValue(data.getInt("OriginFacing"));
        entity.changeDimension(level, new OverworldTeleport(targetPos, direction));
    }

    public static void teleportToMineFloor(ServerLevel level, Entity entity, int id, int floorTarget, TeleportType portal) {
        if (level.getChunkSource().getGenerator() instanceof MineChunkGenerator generator) {
            //We will always multiply the id by 10 to get to the actual mine we want to go to
            id *= 10; //<< We're not generating mines in every 10x10 section anymore so we need to adjust the id
            Pair<BlockPos, Direction> pair = MineHelper.getOrGenerate(level, id, floorTarget, generator.settings, portal);
            if (pair != TeleportType.EMPTY) {
                Direction direction = pair.getValue().getCounterClockWise();
                if (portal == TeleportType.ELEVATOR) {
                    Set<RelativeMovement> set = EnumSet.noneOf(RelativeMovement.class);
                    direction = pair.getValue();
                    BlockPos offset = pair.getKey();
                    //Enforce the positions in front of the elevator to be air
                    level.setBlock(offset.relative(direction), level.getBlockState(offset.relative(direction)).getBlock().defaultBlockState(), 2);
                    level.setBlock(offset.relative(direction).above(), level.getBlockState(offset.relative(direction).above()).getBlock().defaultBlockState(), 2);
                    entity.teleportTo(level, (double) offset.getX() + 0.5, (double) offset.getY(), (double) offset.getZ() + 0.5, set, direction.toYRot(), 0F);
                    Holder<MineTier> tier = generator.settings.getTierByPos(offset.below());
                    if (tier != null && entity instanceof ServerPlayer player)
                        PenguinNetwork.sendToClient(player, new PlayerChangedTierPacket(tier.value().getHUD()));
                } else {
                    BlockPos offset = pair.getKey().relative(direction.getOpposite(), 2);
                    if (entity.getPortalCooldown() == 0) {
                        entity.setPortalCooldown(10);
                        entity.setYRot(pair.getValue().get2DDataValue() * 90);
                        Set<RelativeMovement> set = EnumSet.noneOf(RelativeMovement.class);
                        //Enforce the positions to be air where the player is teleporting to and the blockTag above it
                        level.setBlock(offset, level.getBlockState(offset).getBlock().defaultBlockState(), 2);
                        level.setBlock(offset.above(), level.getBlockState(offset.above()).getBlock().defaultBlockState(), 2);
                        entity.teleportTo(level, (double) offset.getX() + 0.5, (double) offset.below().getY(), (double) offset.getZ() + 0.5, set, direction.getOpposite().toYRot(), 0F);
                        Holder<MineTier> tier = generator.settings.getTierByPos(offset.below());
                        if (tier != null && entity instanceof ServerPlayer player)
                            PenguinNetwork.sendToClient(player, new PlayerChangedTierPacket(tier.value().getHUD()));
                    }
                }
            }
        }
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        CompoundTag data = TagHelper.getOrCreateTag(entity.getPersistentData(), "MineTeleportData");
        data.putString("Origin", currentWorld.dimension().location().toString());
        data.putLong("OriginPos", entity.blockPosition().asLong());
        data.putInt("OriginFacing", entity.getDirection().get3DDataValue());
        teleportToMineFloor(destWorld, entity, id, floor, type);
        entity.setPortalCooldown(20);
        return entity;
    }

    @Override
    public boolean playTeleportSound(ServerPlayer entity, ServerLevel sourceWorld, ServerLevel destWorld) {
        return false;
    }
}
