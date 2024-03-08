package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HoleDepthConfiguration(BlockStateProvider hole, BlockStateProvider wall, IntProvider depth) implements FeatureConfiguration {
    public static final Codec<HoleDepthConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("hole").forGetter(HoleDepthConfiguration::hole),
            BlockStateProvider.CODEC.fieldOf("wall").forGetter(HoleDepthConfiguration::wall),
            IntProvider.CODEC.fieldOf("depth").forGetter(HoleDepthConfiguration::depth)
    ).apply(instance, HoleDepthConfiguration::new));
}
