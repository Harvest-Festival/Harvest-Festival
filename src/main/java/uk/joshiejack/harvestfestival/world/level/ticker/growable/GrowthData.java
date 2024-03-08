package uk.joshiejack.harvestfestival.world.level.ticker.growable;

import com.mojang.serialization.Codec;

public record GrowthData() {
    public static final Codec<GrowthData> CODEC = Codec.unit(GrowthData::new);
    public static final GrowthData NONE = new GrowthData();
}
