package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import uk.joshiejack.harvestfestival.world.level.feature.config.ReplaceFloorConfiguration;

public class ReplaceFloorFeature extends Feature<ReplaceFloorConfiguration> {
    public ReplaceFloorFeature(Codec<ReplaceFloorConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<ReplaceFloorConfiguration> context) {
        BlockState snow = Blocks.GRASS_BLOCK.defaultBlockState().setValue(GrassBlock.SNOWY, true);
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin().below();
        WorldGenLevel worldgenlevel = context.level();
        ReplaceFloorConfiguration config = context.config();
        BlockState blockstate = config.state().getState(randomsource, blockpos);
        BlockState replaceState = config.replace().getState(randomsource, blockpos);
        if(worldgenlevel.getBlockState(blockpos) == replaceState && worldgenlevel.getBlockState(blockpos.above()) != replaceState
                && !worldgenlevel.getBlockState(blockpos).is(BlockTags.CLIMBABLE))
            worldgenlevel.setBlock(blockpos, blockstate, 2);
        final int numberOfBlocks = config.numberOfBlocks().sample(randomsource);
        int blocksToPlace = numberOfBlocks;
        for (int i = 0; i < 150 && blocksToPlace > 0; i++) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(randomsource);
            BlockPos blockpos1 = blockpos.relative(direction);
            if (blockpos1.distSqr(context.origin()) < numberOfBlocks && worldgenlevel.getBlockState(blockpos1) == replaceState
                    && worldgenlevel.getBlockState(blockpos1.above()) != replaceState && !worldgenlevel.getBlockState(blockpos1).is(BlockTags.CLIMBABLE)) {
                blockpos = blockpos1;

                if (blockstate == snow) {
                    if (worldgenlevel.getBlockState(blockpos.above()).isAir()) {
                        worldgenlevel.setBlock(blockpos, blockstate, 2);
                        worldgenlevel.setBlock(blockpos.above(), Blocks.SNOW.defaultBlockState(), 2);
                    } else worldgenlevel.setBlock(blockpos, Blocks.SNOW_BLOCK.defaultBlockState(), 2);
                } else worldgenlevel.setBlock(blockpos, blockstate, 2);

                blocksToPlace--;
            }
        }

        return false;
    }

}
