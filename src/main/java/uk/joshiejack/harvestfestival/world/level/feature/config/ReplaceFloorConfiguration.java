package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ReplaceFloorConfiguration(BlockStateProvider state, BlockStateProvider replace,
                                        IntProvider numberOfBlocks) implements FeatureConfiguration {
    public static final Codec<ReplaceFloorConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(ReplaceFloorConfiguration::state),
            BlockStateProvider.CODEC.fieldOf("replace").forGetter(ReplaceFloorConfiguration::replace),
            IntProvider.CODEC.fieldOf("number_of_blocks").forGetter(ReplaceFloorConfiguration::numberOfBlocks)
    ).apply(instance, ReplaceFloorConfiguration::new));
}
