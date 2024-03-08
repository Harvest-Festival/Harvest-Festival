package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record LeavesBlobConfiguration(BlockStateProvider state, BlockStateProvider wall) implements FeatureConfiguration {
    public static final Codec<LeavesBlobConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(LeavesBlobConfiguration::state),
            BlockStateProvider.CODEC.fieldOf("wall").forGetter(LeavesBlobConfiguration::wall)
    ).apply(instance, LeavesBlobConfiguration::new));
}
