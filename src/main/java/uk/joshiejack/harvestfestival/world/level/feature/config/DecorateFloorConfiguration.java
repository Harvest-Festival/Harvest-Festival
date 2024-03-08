package uk.joshiejack.harvestfestival.world.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record DecorateFloorConfiguration(BlockStateProvider state, IntProvider numberOfBlocks) implements FeatureConfiguration {
    public static final Codec<DecorateFloorConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(DecorateFloorConfiguration::state),
            IntProvider.CODEC.fieldOf("random").forGetter(DecorateFloorConfiguration::numberOfBlocks)
    ).apply(instance, DecorateFloorConfiguration::new));
}
