package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FollowPathConfiguration(BlockStateProvider block, IntProvider distance) implements FeatureConfiguration {
    public static final Codec<FollowPathConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FollowPathConfiguration::block),
            IntProvider.CODEC.fieldOf("distance").forGetter(FollowPathConfiguration::distance)
    ).apply(instance, FollowPathConfiguration::new));
}
