package uk.joshiejack.harvestfestival.world.level.ticker.growable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpreadableData(int lifespan) {
    public static final Codec<SpreadableData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("lifespan").forGetter(SpreadableData::lifespan))
            .apply(instance, SpreadableData::new));
    public static final SpreadableData NONE = new SpreadableData(90);
}
