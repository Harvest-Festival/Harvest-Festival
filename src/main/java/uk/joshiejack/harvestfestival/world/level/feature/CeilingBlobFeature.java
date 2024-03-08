package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import uk.joshiejack.harvestfestival.world.level.feature.config.CeilingBlobConfiguration;

public class CeilingBlobFeature extends Feature<CeilingBlobConfiguration> {
    public CeilingBlobFeature(Codec<CeilingBlobConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CeilingBlobConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        RandomSource randomsource = context.random();
        if (!worldgenlevel.isEmptyBlock(blockpos)) {
            return false;
        } else {
            BlockState blockstate = worldgenlevel.getBlockState(blockpos.above());
            while (blockstate.isAir()) {
                blockpos = blockpos.above();
                blockstate = worldgenlevel.getBlockState(blockpos);
            }

            final BlockPos finalBlockpos = blockpos;
            final BlockState finalBlockstate = blockstate;
            if (context.config().ceiling().stream().noneMatch((state) -> state.getState(randomsource, finalBlockpos) == finalBlockstate)) {
                return false;
            } else {
                worldgenlevel.setBlock(blockpos, context.config().block().getState(randomsource, blockpos), 2);
                for (int i = 0; i < 1500; ++i) {
                    BlockPos blockpos1 = blockpos.offset(
                            randomsource.nextInt(8) - randomsource.nextInt(8), -randomsource.nextInt(12), randomsource.nextInt(8) - randomsource.nextInt(8)
                    );
                    if (worldgenlevel.getBlockState(blockpos1).isAir()) {
                        int j = 0;

                        for (Direction direction : Direction.values()) {
                            if (worldgenlevel.getBlockState(blockpos1.relative(direction)).is(context.config().block().getState(randomsource, blockpos).getBlock())) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            worldgenlevel.setBlock(blockpos1, context.config().block().getState(randomsource, blockpos), 2);
                        }
                    }
                }

                return true;
            }
        }
    }

}
