package uk.joshiejack.harvestfestival.world.level.ticker.crop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import uk.joshiejack.harvestfestival.HFRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record CropData(@Nonnull ResourceLocation stageHandler, @Nullable TagKey<Block> validSoils, boolean requiresWater) {
    public static final Codec<CropData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("handler").forGetter(ticker -> ticker.stageHandler),
            ExtraCodecs.strictOptionalField(TagKey.codec(net.minecraft.core.registries.Registries.BLOCK), "valid_soils").forGetter(ticker -> java.util.Optional.ofNullable(ticker.validSoils)),
            Codec.BOOL.optionalFieldOf("requires_water", false).forGetter(ticker -> ticker.requiresWater)
    ).apply(instance, (handler, validSoils, requiresWater) -> new CropData(handler, validSoils.orElse(null), requiresWater)));

    public static final CropData NONE = new CropData(HFRegistries.Keys.NONE, null, false);

    public CropData(ResourceLocation handler) {
        this(handler, null, false);
    }
}
