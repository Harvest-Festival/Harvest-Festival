package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import uk.joshiejack.harvestfestival.world.level.feature.config.HoleDepthConfiguration;

import java.util.stream.IntStream;

public class HoleFeature extends Feature<HoleDepthConfiguration> {
    public HoleFeature(Codec<HoleDepthConfiguration> config) {
        super(config);
    }

    private static boolean isWall(LevelAccessor world, BlockPos pos, BlockState wall) {
        return world.getBlockState(pos) == wall;
    }

    @Override
    public boolean place(FeaturePlaceContext<HoleDepthConfiguration> context) {
        RandomSource rand = context.random();
        BlockPos blockpos = context.origin().offset(8 - rand.nextInt(16), 0, 8 - rand.nextInt(16));
        WorldGenLevel world = context.level();
        attemptToGenerateHole(world, blockpos, context.config().hole().getState(rand, blockpos), context.config().wall().getState(rand, blockpos), context.config().depth().getMinValue());
        return true;
    }

    public static void attemptToGenerateHole(LevelAccessor world, BlockPos pos, BlockState hole, BlockState wall, int depth) {
        if (world.isEmptyBlock(pos) && IntStream.of(2, depth).allMatch(i -> {
            for (Direction dir: Direction.Plane.HORIZONTAL) {
                if (!isWall(world, pos.below(i).relative(dir), wall)) return false;
            }

            return isWall(world, pos.below(i), wall);
        })) {
            int i;
            for (i = 0; i < depth; i++) {
                world.setBlock(pos.below(i), Blocks.AIR.defaultBlockState(), 2);
            }

            world.setBlock(pos.below(i), hole, 2);
        }
    }
}
