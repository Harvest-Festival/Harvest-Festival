package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.farming.Quality;

import java.util.List;

public class ApplySetQuality extends LootItemConditionalFunction {
    public static final Codec<ApplySetQuality> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(ExtraCodecs.strictOptionalField(LootItemConditions.CODEC.listOf(), "conditions", List.of()).forGetter(p_299114_ -> p_299114_.predicates),
                    HFRegistries.QUALITY.byNameCodec().fieldOf("quality").forGetter(p_299003_ -> p_299003_.quality)
            ).apply(instance, ApplySetQuality::new));

    private final Quality quality;

    public ApplySetQuality(List<LootItemCondition> conditions, Quality quality) {
        super(conditions);
        this.quality = quality;
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, @NotNull LootContext context) {
        ResourceLocation key = HFRegistries.QUALITY.getKey(quality);
        if (key != null) stack.getOrCreateTag().putString("Quality", key.toString());
        return stack;
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return HFLoot.APPLY_SET_QUALITY.get();
    }

    public static ApplySetQuality.Builder applyQuality(Quality quality) {
        return new ApplySetQuality.Builder(quality);
    }

    public static class Builder extends LootItemConditionalFunction.Builder<ApplySetQuality.Builder> {
        private final Quality quality;

        public Builder(Quality quality) {
            this.quality = quality;
        }

        protected ApplySetQuality.@NotNull Builder getThis() {
            return this;
        }

        @Override
        public @NotNull LootItemFunction build() {
            return new ApplySetQuality(this.getConditions(), this.quality);
        }
    }
}
