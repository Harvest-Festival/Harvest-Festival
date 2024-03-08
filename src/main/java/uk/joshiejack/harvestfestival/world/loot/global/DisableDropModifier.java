package uk.joshiejack.harvestfestival.world.loot.global;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record DisableDropModifier(List<Block> block, List<Item> item) implements IGlobalLootModifier {
    public static final Codec<DisableDropModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().listOf().fieldOf("blockTag").forGetter(DisableDropModifier::block),
            BuiltInRegistries.ITEM.byNameCodec().listOf().fieldOf("item").forGetter(DisableDropModifier::item)
    ).apply(instance, DisableDropModifier::new));

    public static DisableDropModifier of(List<Block> block, List<Item> item) {
        return new DisableDropModifier(block, item);
    }

    public static List<Block> blocks(Block... blocks) {
        return List.of(blocks);
    }

    public static List<Item> items(Item... items) {
        return List.of(items);
    }

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (context.hasParam(LootContextParams.BLOCK_STATE)) {
            Block block = Objects.requireNonNull(context.getParamOrNull(LootContextParams.BLOCK_STATE)).getBlock();
            if (this.block.contains(block)) {
                //generatedLoot.removeIf(stack -> this.item.contains(stack.getItem()));
            }
        }

        return generatedLoot;
    }
}
