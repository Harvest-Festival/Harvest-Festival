package uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

public abstract class DecoratorWrapper {
    public final MineSettings settings;
    public final MineTier tier;
    public final int floor;
    public final RandomSource rand;

    public DecoratorWrapper(MineSettings settings, MineTier tier, RandomSource rand, int floor) {
        this.settings = settings;
        this.tier = tier;
        this.rand = rand;
        this.floor = Math.max(1, floor);
    }

    public abstract boolean isAirBlock(BlockPos pos);

    public abstract void setBlockState(BlockPos pos, BlockState state);

    public boolean hasBuffer(BlockPos pos, int buffer) {
        return pos.getX() - buffer >= 0 && pos.getX() + buffer <= 15 && pos.getZ() - buffer >= 0 && pos.getZ() + buffer <= 15;
    }

    public abstract BlockState getBlockState(BlockPos pos);

}