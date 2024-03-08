package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import uk.joshiejack.harvestfestival.world.level.feature.config.LeavesBlobConfiguration;

public class LeavesBlobFeature extends Feature<LeavesBlobConfiguration> {
    public LeavesBlobFeature(Codec<LeavesBlobConfiguration> config) {
        super(config);
    }

    public boolean isNextTo(WorldGenLevel world, BlockPos target, BlockState state) {
        return world.getBlockState(target.east()) == state || world.getBlockState(target.west()) == state || world.getBlockState(target.north()) == state || world.getBlockState(target.south()) == state;
    }

    public boolean isNextTo(WorldGenLevel world, BlockPos target, TagKey<Block> tag) {
        return world.getBlockState(target.east()).is(tag) || world.getBlockState(target.west()).is(tag) || world.getBlockState(target.north()).is(tag) || world.getBlockState(target.south()).is(tag);
    }

    @Override
    public boolean place(FeaturePlaceContext<LeavesBlobConfiguration> context) {
        BlockState state = context.config().state().getState(context.random(), context.origin());
        BlockState wall = context.config().wall().getState(context.random(), context.origin());
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        WorldGenLevel world = context.level();
        for (int y = rand.nextInt(3); y < 6; y++) {
            for (int i = -2 + rand.nextInt(3); i <= 2 - rand.nextInt(3); i++) {
                for (int j = -2 + rand.nextInt(3); j <= 2 - rand.nextInt(3); j++) {
                    BlockPos target = pos.offset(i, y, j);
                    if (world.getBlockState(target).isAir() && isNextTo(world, target, Blocks.AIR.defaultBlockState()) && (isNextTo(world, target, wall)
                            || isNextTo(world, target, BlockTags.LOGS))) {
                        world.setBlock(target, state, 2);
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
