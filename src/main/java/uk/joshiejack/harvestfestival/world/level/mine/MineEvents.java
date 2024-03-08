package uk.joshiejack.harvestfestival.world.level.mine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.network.PlayerChangedTierPacket;
import uk.joshiejack.harvestfestival.network.PlayerEnteredMinePacket;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import java.util.Objects;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class MineEvents {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = Objects.requireNonNull(event.getEntity().level().getServer()).getLevel(event.getEntity().level().dimension());
            if (level != null && level.getChunkSource().getGenerator() instanceof MineChunkGenerator generator) {
                PenguinNetwork.sendToClient(player,
                        new PlayerEnteredMinePacket(generator.settings),
                        new PlayerChangedTierPacket(Objects.requireNonNull(generator.settings.getTierByPos(player.blockPosition())).value().getHUD()));
            }
        }
    }

    /* Send the client the settings data about this mine */
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = Objects.requireNonNull(event.getEntity().level().getServer()).getLevel(event.getTo());
            if (level != null && level.getChunkSource().getGenerator() instanceof MineChunkGenerator generator) {
                PenguinNetwork.sendToClient(player, new PlayerEnteredMinePacket(generator.settings));
            }

            //Opposite, We are exiting a mine
            level = Objects.requireNonNull(event.getEntity().level().getServer()).getLevel(event.getFrom());
            if (level != null && level.getChunkSource().getGenerator() instanceof MineChunkGenerator generator) {
                PenguinNetwork.sendToClient(player, new PlayerChangedTierPacket(MineTier.NO_HUD));
            }
        }
    }

    @SubscribeEvent
    public static void reduceEntityCount(MobSpawnEvent.PositionCheck event) {
        if (!(event.getLevel().getChunkSource() instanceof ServerChunkCache))
            return;
        if (!MobSpawnType.isSpawner(event.getSpawnType()) && ((ServerChunkCache) event.getLevel().getChunkSource()).getGenerator()
                instanceof MineChunkGenerator generator) {
            BlockPos target = event.getEntity().blockPosition();
            Holder<MineTier> tier = generator.settings.getTierByPos(target);
            if (tier != null) {
                int floor = MineHelper.getRelativeFloor(generator.settings, target.getY());
                int entityCount = event.getLevel().getEntities(null, event.getEntity().getBoundingBox()
                        .inflate(generator.settings.blocksPerMine() / 8D, generator.settings.floorHeight() / 2D, generator.settings.blocksPerMine() / 8D)).size();
                if (entityCount > tier.value().getMaxEntities(floor))
                    event.setResult(Event.Result.DENY);
            }
        }
    }

    //Allows mobs to spawn in the mine based on the tier no matter standard rules
    @SubscribeEvent
    public static void onCheckSpawn(MobSpawnEvent.SpawnPlacementCheck event) {
        if (!(event.getLevel().getChunkSource() instanceof ServerChunkCache))
            return;
        if (!MobSpawnType.isSpawner(event.getSpawnType()) && ((ServerChunkCache) event.getLevel().getChunkSource()).getGenerator()
                instanceof MineChunkGenerator generator) {
            BlockPos target = event.getPos();
            Holder<MineTier> tier = generator.settings.getTierByPos(target);
            if (tier != null) {
                int id = MineHelper.getMineIDFromPos(generator.settings, target);
                if (id % 10 != 0 || target.getY() >= event.getLevel().getMaxBuildHeight() - 1)
                    event.setResult(Event.Result.DENY); //Prevent mobs from spawning above the world and in mine ids that are excluded
                else {
                    int floor = MineHelper.getRelativeFloor(generator.settings, target.getY());
                    ServerLevel level = event.getLevel().getLevel();
                    //We we need to check if a player is in this section of the mine
                    //If they are, we allow the mob to spawn
                    for (ServerPlayer player: level.players()) {
                        int playerID = MineHelper.getMineIDFromPos(generator.settings, player.blockPosition());
                        if (playerID == id) { //If the player and the monster spawn are in the same tier of the mine allow them to spawn
                            if (MineHelper.getTierFromPos(generator.settings, player.blockPosition()) == MineHelper.getTierFromPos(generator.settings, target)) {
                                event.setResult(Event.Result.ALLOW);
                                return;
                            }
                        }
                    }

                    event.setResult(Event.Result.DENY); //Don't allow them to spawn if no players are in the same tier
                }
            }
        }
    }
}