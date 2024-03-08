package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record CeilingBlobConfiguration(List<BlockStateProvider> ceiling,
                                       BlockStateProvider block) implements FeatureConfiguration {
    public static final Codec<CeilingBlobConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.listOf().fieldOf("ceiling").forGetter((config) -> config.ceiling),
            BlockStateProvider.CODEC.fieldOf("block").forGetter((config) -> config.block)
    ).apply(instance, CeilingBlobConfiguration::new));
}
