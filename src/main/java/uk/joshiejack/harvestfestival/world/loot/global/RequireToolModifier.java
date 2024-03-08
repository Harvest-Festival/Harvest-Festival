package uk.joshiejack.harvestfestival.world.loot.global;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RequireToolModifier(TagKey<Block> blockTag, TagKey<Item> itemTag) implements IGlobalLootModifier {
    public static final Codec<RequireToolModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TagKey.codec(Registries.BLOCK).fieldOf("block_tag").forGetter(RequireToolModifier::blockTag),
            TagKey.codec(Registries.ITEM).fieldOf("item_tag").forGetter(RequireToolModifier::itemTag)
    ).apply(instance, RequireToolModifier::new));

    public static RequireToolModifier of(TagKey<Item> tag, TagKey<Block> block) {
        return new RequireToolModifier(block, tag);
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
            if (context.hasParam(LootContextParams.TOOL) && context.getParam(LootContextParams.BLOCK_STATE).is(blockTag)) {
                ItemStack tool = context.getParam(LootContextParams.TOOL);
                if (!tool.is(itemTag))
                    generatedLoot.clear();
            }
        }

        return generatedLoot;
    }
}
