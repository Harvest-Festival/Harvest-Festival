package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BlockBlobConfiguration(BlockStateProvider block) implements FeatureConfiguration {
    public static final Codec<BlockBlobConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(BlockBlobConfiguration::block)
    ).apply(instance, BlockBlobConfiguration::new));
}
