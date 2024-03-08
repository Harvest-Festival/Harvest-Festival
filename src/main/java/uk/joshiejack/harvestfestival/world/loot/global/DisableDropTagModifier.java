package uk.joshiejack.harvestfestival.world.loot.global;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public record DisableDropTagModifier(Optional<TagKey<Item>> tag) implements IGlobalLootModifier {
    public static final Codec<DisableDropTagModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.strictOptionalField(TagKey.codec(Registries.ITEM), "valid_soils").forGetter(ticker -> ticker.tag)
    ).apply(instance, DisableDropTagModifier::new));

    public static DisableDropTagModifier of(TagKey<Item> item) {
        return new DisableDropTagModifier(Optional.of(item));
    }

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (context.hasParam(LootContextParams.BLOCK_STATE)) {
            generatedLoot.removeIf(stack -> stack.is(Objects.requireNonNull(tag.get())));
        }

        return generatedLoot;
    }
}
