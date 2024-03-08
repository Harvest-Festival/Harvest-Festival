package uk.joshiejack.harvestfestival.world.farming;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import uk.joshiejack.harvestfestival.HFRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public record Quality(double modifier, boolean appliesToCrops) {
    public static final Codec<Quality> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("modifier").forGetter(Quality::modifier),
            Codec.BOOL.optionalFieldOf("applies_to_crops", true).forGetter(Quality::appliesToCrops)
    ).apply(instance, Quality::new));

    public static final Map<Quality, ResourceLocation> SPRITES = new HashMap<>();

    @SuppressWarnings("all")
    @Nonnull
    public static Quality fromStack(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("Quality")) {
            Quality quality = HFRegistries.QUALITY.get(new ResourceLocation(stack.getTag().getString("Quality")));
            return quality == null ? HFFarming.QualityLevels.NORMAL.get() : quality;
        }

        return HFFarming.QualityLevels.NORMAL.get();
    }

    @SuppressWarnings("ConstantConditions")
    public ResourceLocation getSprite() {
        ResourceLocation key = HFRegistries.QUALITY.getKey(this);
        return SPRITES.computeIfAbsent(this, k -> new ResourceLocation(key.getNamespace(), "quality/" + key.getPath()));
    }

    @Nullable
    public ResourceLocation getModelLocation() {
        ResourceLocation id = HFRegistries.QUALITY.getKey(this);
        return id == null ? null : new ResourceLocation(id.getNamespace(), "quality/" + id.getPath());
    }

    public ResourceLocation id() {
        return HFRegistries.QUALITY.getKey(this);
    }
}
