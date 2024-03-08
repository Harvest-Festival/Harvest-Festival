package uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

import javax.annotation.Nullable;

public class LevelChunkSectionWrapper extends DecoratorWrapper {
    private final LevelChunkSection storage;
    private final @Nullable LevelChunkSection below;
    public final WorldGenLevel level;
    public final int relativeFloor;

    public LevelChunkSectionWrapper(WorldGenLevel level, LevelChunkSection storage, @Nullable LevelChunkSection below, MineSettings settings, MineTier tier, RandomSource random, int floor, int relativeFloor) {
        super(settings, tier, random, floor);
        this.level = level;
        this.relativeFloor = relativeFloor;
        this.storage = storage;
        this.below = below;
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return pos.getY() < 0 ? below != null && below.getBlockState(pos.getX(), pos.getY() + 16, pos.getZ()).isAir() :
                storage.getBlockState(pos.getX(), pos.getY(), pos.getZ()).isAir();
    }

    @Override
    public void setBlockState(BlockPos pos, BlockState state) {
        if (pos.getY() < 0) {
            if (below != null)
                below.setBlockState(pos.getX(), pos.getY() + 16, pos.getZ(), state);
        } else storage.setBlockState(pos.getX(), pos.getY(), pos.getZ(), state);
    }

    @Override
    public @NotNull BlockState getBlockState(BlockPos pos) {
        if (pos.getX() < 0 || pos.getX() > 15 || pos.getZ() < 0 || pos.getZ() > 15) return Blocks.AIR.defaultBlockState();
        if (pos.getY() < 0) {
            return below != null ? below.getBlockState(pos.getX(), pos.getY() + 16, pos.getZ()) : Blocks.AIR.defaultBlockState();
        } return storage.getBlockState(pos.getX(), pos.getY(), pos.getZ());
    }
}
