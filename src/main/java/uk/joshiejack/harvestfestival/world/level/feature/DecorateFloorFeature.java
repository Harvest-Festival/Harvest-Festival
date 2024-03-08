package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import uk.joshiejack.harvestfestival.world.level.feature.config.DecorateFloorConfiguration;

public class DecorateFloorFeature extends Feature<DecorateFloorConfiguration> {
    public DecorateFloorFeature(Codec<DecorateFloorConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<DecorateFloorConfiguration> context) {
        RandomSource rand = context.random();
        BlockPos blockpos = context.origin();
        WorldGenLevel world = context.level();
        DecorateFloorConfiguration config = context.config();
        BlockState blockstate = config.state().getState(rand, blockpos);
        int numberOfBlocks = config.numberOfBlocks().sample(rand);
        for (int i = -numberOfBlocks + rand.nextInt(numberOfBlocks); i <= numberOfBlocks - rand.nextInt(numberOfBlocks); i++) {
            for (int j = -numberOfBlocks + rand.nextInt(numberOfBlocks); j <= numberOfBlocks - rand.nextInt(numberOfBlocks); j++) {
                BlockPos target = blockpos.offset(i, 0, j);
                if (world.getBlockState(target).isAir() && world.getBlockState(target.below()).isSolid()
                        && !world.getBlockState(target.below()).is(BlockTags.CLIMBABLE)) {
                    world.setBlock(target, blockstate, 2);
                }
            }
        }

        return true;
    }

}
