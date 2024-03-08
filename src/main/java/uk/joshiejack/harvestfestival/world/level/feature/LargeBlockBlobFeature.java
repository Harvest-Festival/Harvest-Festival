package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.feature.config.BlockBlobConfiguration;

public class LargeBlockBlobFeature extends Feature<BlockBlobConfiguration> {
    public LargeBlockBlobFeature(Codec<BlockBlobConfiguration> codec) {
        super(codec);
    }

    @Override
    protected void setBlock(@NotNull LevelWriter writer, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (writer instanceof LevelAccessor accessor && !accessor.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
            writer.setBlock(pos, state, 3);
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockBlobConfiguration> context) {
        BlockPos pos = context.origin();
        RandomSource randomsource = context.random();
        WorldGenLevel worldgenlevel = context.level();
        BlockState state = context.config().block().getState(randomsource, pos);

        if (randomsource.nextBoolean()) {
            pos = pos.above(randomsource.nextInt(4));
            int i = randomsource.nextInt(4) + 7;
            int j = i / 4 + randomsource.nextInt(2);

            if (j > 1 && randomsource.nextInt(60) == 0) {
                pos = pos.above(10 + randomsource.nextInt(30));
            }

            for (int k = 0; k < i; ++k) {
                float f = (1.0F - (float) k / (float) i) * (float) j;
                int l = Mth.ceil(f);

                for (int i1 = -l; i1 <= l; ++i1) {
                    float f1 = (float) Mth.abs(i1) - 0.25F;

                    for (int j1 = -l; j1 <= l; ++j1) {
                        float f2 = (float) Mth.abs(j1) - 0.25F;

                        if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f) && (i1 != -l && i1 != l && j1 != -l && j1 != l || randomsource.nextFloat() <= 0.75F)) {
                            BlockState iblockstate = worldgenlevel.getBlockState(pos.offset(i1, k, j1));
                            if (worldgenlevel.getBlockState(pos.offset(i1, k, j1)).isAir() || iblockstate.is(BlockTags.DIRT) || iblockstate.is(BlockTags.SNOW) || iblockstate.is(BlockTags.ICE)) {
                                worldgenlevel.setBlock(pos.offset(i1, k, j1), state, 2);
                            }

                            if (k != 0 && l > 1) {
                                iblockstate = worldgenlevel.getBlockState(pos.offset(i1, -k, j1));
                                if (worldgenlevel.getBlockState(pos.offset(i1, -k, j1)).isAir() || iblockstate.is(BlockTags.DIRT) || iblockstate.is(BlockTags.SNOW) || iblockstate.is(BlockTags.ICE)) {
                                    worldgenlevel.setBlock(pos.offset(i1, -k, j1), state, 2);
                                }
                            }
                        }
                    }
                }
            }

            int k1 = j - 1;

            if (k1 < 0) {
                k1 = 0;
            } else if (k1 > 1) {
                k1 = 1;
            }

            for (int l1 = -k1; l1 <= k1; ++l1) {
                for (int i2 = -k1; i2 <= k1; ++i2) {
                    BlockPos blockpos = pos.offset(l1, -1, i2);
                    int j2 = 50;

                    if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
                        j2 = randomsource.nextInt(5);
                    }

                    while (blockpos.getY() > 50) {
                        BlockState iblockstate1 = worldgenlevel.getBlockState(blockpos);
                        Block block1 = iblockstate1.getBlock();

                        if (!worldgenlevel.getBlockState(blockpos).isAir() && !iblockstate1.is(BlockTags.DIRT) && !iblockstate1.is(BlockTags.SNOW) && !iblockstate1.is(BlockTags.ICE) && block1 != state.getBlock()) {
                            break;
                        }

                        worldgenlevel.setBlock(blockpos, state, 2);

                        blockpos = blockpos.below();
                        --j2;

                        if (j2 <= 0) {
                            blockpos = blockpos.below(randomsource.nextInt(5) + 1);
                            j2 = randomsource.nextInt(5);
                        }
                    }
                }
            }
        } else {
            if (worldgenlevel.getBlockState(pos).isAir()) {
                worldgenlevel.setBlock(pos, state, 2);
            }

            for (int i = pos.getY(); i <= pos.getY() + 2; ++i) {
                int j = i - pos.getY();
                int k = 2 - j;

                for (int l = pos.getX() - k; l <= pos.getX() + k; ++l) {
                    int i1 = l - pos.getX();

                    for (int j1 = pos.getZ() - k; j1 <= pos.getZ() + k; ++j1) {
                        int k1 = j1 - pos.getZ();

                        if (Math.abs(i1) != k || Math.abs(k1) != k || randomsource.nextInt(2) != 0) {
                            BlockPos blockpos = new BlockPos(l, i, j1);
                            if (worldgenlevel.getBlockState(blockpos).isAir()) {
                                worldgenlevel.setBlock(blockpos, state, 2);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
