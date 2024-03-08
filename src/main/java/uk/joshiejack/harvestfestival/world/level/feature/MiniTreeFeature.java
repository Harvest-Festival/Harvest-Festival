package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import uk.joshiejack.harvestfestival.world.level.feature.config.MiniTreeConfiguration;

public class MiniTreeFeature extends Feature<MiniTreeConfiguration> {
    public MiniTreeFeature(Codec<MiniTreeConfiguration> config) {
        super(config);
    }

    public boolean isNextTo(WorldGenLevel world, BlockPos target, BlockState state) {
        return world.getBlockState(target.east()) == state || world.getBlockState(target.west()) == state || world.getBlockState(target.north()) == state || world.getBlockState(target.south()) == state;
    }

    @Override
    public boolean place(FeaturePlaceContext<MiniTreeConfiguration> context) {
        BlockState leaves = context.config().leaves().getState(context.random(), context.origin());
        BlockState logs = context.config().log().getState(context.random(), context.origin());
        BlockState wall = context.config().wall().getState(context.random(), context.origin());
        BlockPos pos = context.origin();
        BlockPos log = context.origin();
        RandomSource rand = context.random();
        WorldGenLevel world = context.level();

        int l = 10;
        while (world.isEmptyBlock(log) && l > 0) {
            world.setBlock(log, logs, 2);
            log = log.above();
            l--;
        }

        for (int y = rand.nextInt(3); y < 10; y++) {
            for (int i = -4 + rand.nextInt(3); i <= 4 - rand.nextInt(3); i++) {
                for (int j = -4 + rand.nextInt(3); j <= 4 - rand.nextInt(3); j++) {
                    BlockPos target = pos.offset(i, y, j);
                    if (world.getBlockState(target).isAir() && isNextTo(world, target, Blocks.AIR.defaultBlockState()) && (isNextTo(world, target, wall)
                            || isNextTo(world, target, logs))) {
                        world.setBlock(target, leaves, 2);
                        if (leaves.hasProperty(LeavesBlock.PERSISTENT)) {
                            world.setBlock(target, leaves.setValue(LeavesBlock.PERSISTENT, true), 2);
                        }

                        if (rand.nextInt(8) == 0) {
                            BlockPos blockpos3 = target.west();
                            BlockPos blockpos4 = target.east();
                            BlockPos blockpos1 = target.north();
                            BlockPos blockpos2 = target.south();
                            if (rand.nextInt(4) == 0 && world.getBlockState(blockpos3).isAir()) {
                                addHangingVine(world, blockpos3, VineBlock.EAST);
                            }

                            if (rand.nextInt(4) == 0 && world.getBlockState(blockpos4).isAir()) {
                                addHangingVine(world, blockpos4, VineBlock.WEST);
                            }

                            if (rand.nextInt(4) == 0 && world.getBlockState(blockpos1).isAir()) {
                                addHangingVine(world, blockpos1, VineBlock.SOUTH);
                            }

                            if (rand.nextInt(4) == 0 && world.getBlockState(blockpos2).isAir()) {
                                addHangingVine(world, blockpos2, VineBlock.NORTH);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private void addHangingVine(WorldGenLevel level, BlockPos pos, BooleanProperty prop) {
        BlockState iblockstate = Blocks.VINE.defaultBlockState().setValue(prop, Boolean.TRUE);
        int i = 4;
        for (BlockPos blockpos = pos.below(); level.getBlockState(blockpos).isAir() && i > 0; --i) {
            level.setBlock(blockpos, iblockstate, 2);
            blockpos = blockpos.below();
        }
    }
}
