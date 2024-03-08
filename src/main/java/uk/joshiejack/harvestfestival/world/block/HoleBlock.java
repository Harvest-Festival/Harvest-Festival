package uk.joshiejack.harvestfestival.world.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("deprecation")
public class HoleBlock extends Block {
    public static final MapCodec<HoleBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("dimension", Level.OVERWORLD).forGetter(hole -> hole.dimension)
    ).apply(instance, HoleBlock::new));

    protected final ResourceKey<Level> dimension;

    public HoleBlock(Properties properties, ResourceKey<Level> dimension) {
        super(properties);
        this.dimension = dimension;
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    public void updateEntityAfterFallOn(@NotNull BlockGetter p_56406_, @NotNull Entity entity) {
        entity.resetFallDistance();
        if (entity instanceof Player player && entity.level() instanceof ServerLevel level) {
            if (MineHelper.isMineWorld(level)) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50));
                MineSettings settings = ((MineChunkGenerator) level.getChunkSource().getGenerator()).settings;
                int floor = MineHelper.getFloorFromPos(entity.level(), settings, entity.blockPosition()) + 1;
                int target = floor + HFConfig.holeMinDistance.get() + player.getRandom().nextInt(HFConfig.holeBlockExtraDistance.get() * settings.floorsPerMine(entity.level().getHeight()));
                int mineID = MineHelper.getMineIDFromPos(settings, entity.blockPosition());
                ChunkPos originChunk = MineHelper.getChunkTarget(level, settings, mineID, target);
                int rows = settings.blocksPerMine();
                int cols = settings.blocksPerMine();
                //Find air on the floor
                int relativeFloor = ((target - 1) % settings.floorsPerMine(entity.level().getHeight())) + 1;
                BlockPos origin = new BlockPos(originChunk.getMinBlockX(), -(relativeFloor * settings.floorHeight()) + 2, originChunk.getMinBlockZ());
                // Gather all the positions
                List<BlockPos> positions = new ArrayList<>();
                for (int x = 1; x < rows - 1; x++)
                    for (int z = 1; z < cols - 1; z++)
                        positions.add(origin.offset(x, 0, z));
                Collections.shuffle(positions);
                for (BlockPos pos : positions) {
                    if (level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above())) {
                        entity.teleportTo(level, (double) pos.getX() + 0.5, (double) pos.getY() + 2, (double) pos.getZ() + 0.5,
                                EnumSet.noneOf(RelativeMovement.class), Direction.Plane.HORIZONTAL.getRandomDirection(((Player) entity).getRandom()).toYRot(), 0F);
                        player.hurt(level.damageSources().fall(), 5F);
                        break;
                    }
                }
            }
        }
    }
}
