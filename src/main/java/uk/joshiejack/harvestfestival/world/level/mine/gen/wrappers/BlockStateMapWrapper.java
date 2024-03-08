package uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

public class BlockStateMapWrapper extends DecoratorWrapper {
    private final BlockState[][][] blockStateMap;
    public BlockStateMapWrapper(BlockState[][][] blockStateMap, MineSettings settings, MineTier tier, RandomSource seed, int floor) {
        super(settings, tier, seed, floor);
        this.blockStateMap = blockStateMap;
    }

    private boolean isValid(MineSettings settings, BlockPos pos) {
        return pos.getX() > 0 && pos.getZ() > 0 && pos.getY() >= 0 &&
                pos.getX() < settings.blocksPerMine() - 1 && pos.getZ() < settings.blocksPerMine() - 1 && pos.getY() < settings.floorHeight();
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return isValid(settings, pos) && blockStateMap[pos.getX()][pos.getZ()][pos.getY()].isAir();
    }

    @Override
    public void setBlockState(BlockPos pos, BlockState state) {
        if (isValid(settings, pos)) {
            blockStateMap[pos.getX()][pos.getZ()][pos.getY()] = state;
        }
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        if (!isValid(settings, pos)) return Blocks.BEDROCK.defaultBlockState();
        else {
            return blockStateMap[pos.getX()][pos.getZ()][pos.getY()];
        }
    }

    @Override
    public boolean hasBuffer(BlockPos pos, int buffer) {
        return pos.getX() - buffer > 0 && pos.getX() + buffer < settings.blocksPerMine() - 1 && pos.getZ() - buffer > 0 && pos.getZ() + buffer < settings.blocksPerMine() - 1;
    }
}
