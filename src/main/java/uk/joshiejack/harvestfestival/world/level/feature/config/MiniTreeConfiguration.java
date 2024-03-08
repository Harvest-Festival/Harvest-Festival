package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MiniTreeConfiguration(BlockStateProvider leaves, BlockStateProvider log, BlockStateProvider wall) implements FeatureConfiguration {
    public static final Codec<MiniTreeConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("leaves").forGetter(MiniTreeConfiguration::leaves),
            BlockStateProvider.CODEC.fieldOf("log").forGetter(MiniTreeConfiguration::log),
            BlockStateProvider.CODEC.fieldOf("wall").forGetter(MiniTreeConfiguration::wall)
    ).apply(instance, MiniTreeConfiguration::new));
}
