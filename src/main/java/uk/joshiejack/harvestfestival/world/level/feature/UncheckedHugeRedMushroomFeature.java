package uk.joshiejack.harvestfestival.world.level.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.HugeRedMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class UncheckedHugeRedMushroomFeature extends HugeRedMushroomFeature {
    public UncheckedHugeRedMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected void setBlock(@NotNull LevelWriter writer, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (writer instanceof LevelAccessor accessor && !accessor.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
            writer.setBlock(pos, state, 3);
        }
    }

    @Override
    protected boolean isValidPosition(@NotNull LevelAccessor accessor, @NotNull BlockPos p_65100_, int height, BlockPos.@NotNull MutableBlockPos pos, @NotNull HugeMushroomFeatureConfiguration config) {
        return true;
    }
}
