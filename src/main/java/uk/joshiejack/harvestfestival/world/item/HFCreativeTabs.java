package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.*;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.penguinlib.data.PenguinRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HFCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, HarvestFestival.MODID);

    private static final List<Class<?>> GATHERING_BLOCKS = Arrays.asList(StoneBlock.class, HFBushBlock.class, SmallBranchBlock.class, MediumBranchBlock.class, LargeBranchBlock.class, SmallStumpBlock.class, MediumStumpBlock.class, LargeStumpBlock.class);
    private static final List<DeferredItem<Item>> GATHERING_ITEMS = Arrays.asList(HFItems.BASIC_AXE, HFItems.COPPER_AXE, HFItems.SILVER_AXE, HFItems.GOLD_AXE, HFItems.MYSTRIL_AXE, HFItems.CURSED_AXE, HFItems.BLESSED_AXE, HFItems.MYTHIC_AXE);
    private static final List<DeferredItem<Item>> CHEAT_ITEMS = Arrays.asList(HFItems.NOTE, HFItems.MAIL);
    private static final List<DeferredItem<Item>> FARMING_ITEMS = Arrays.asList(HFItems.FERTILIZER);

    private static ItemStack modifyItem(Item item) {
        if (BuiltInRegistries.ITEM.getKey(item).getPath().toLowerCase(Locale.ENGLISH).contains("cursed")) {
            ItemStack stack = new ItemStack(item);
            stack.enchant(Enchantments.BINDING_CURSE, 1);
            return stack;
        } else return new ItemStack(item);
    }
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MINE = CREATIVE_MODE_TABS.register("mine", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + HarvestFestival.MODID + ".mine"))
            .icon(HFItems.ADAMANTITE::toStack)
            .displayItems((params, output) -> {
                //Add all the item found in HCItems.ITEMS that don't conform to GATHERING_BLOCKS
                HFItems.ITEMS.getEntries().stream()
                        .map(DeferredHolder::get)
                        .filter(item -> !(item instanceof BlockItem) ||
                                GATHERING_BLOCKS.stream().noneMatch(blockClass ->
                                        blockClass.isInstance(((BlockItem) item).getBlock())))
                        .filter(item -> FARMING_ITEMS.stream().noneMatch(farming -> farming.asItem() == item.asItem()))
                        .filter(item -> CHEAT_ITEMS.stream().noneMatch(cheat -> cheat.asItem() == item.asItem()))
                        .filter(item -> GATHERING_ITEMS.stream().noneMatch(gather -> gather.asItem() == item.asItem()))
                        .forEach(item -> output.accept(modifyItem(item.asItem())));
                HFEntities.SPAWN_EGGS.getEntries().forEach(egg -> output.accept(egg.get()));
            }).build());
    //Create the gathering tab and all of the branches and stumps
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GATHERING = CREATIVE_MODE_TABS.register("gathering", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + HarvestFestival.MODID + ".gathering"))
            .icon(HFItems.COPPER_AXE::toStack)
            .displayItems((params, output) -> {
                HFItems.ITEMS.getEntries().stream()
                        .map(DeferredHolder::get)
                        .filter(item -> (item instanceof BlockItem &&
                                GATHERING_BLOCKS.stream().anyMatch(blockClass ->
                                        blockClass.isInstance(((BlockItem) item).getBlock())))
                                || GATHERING_ITEMS.stream().anyMatch(gather -> gather.asItem() == item.asItem()))
                        .forEach(item -> output.accept(modifyItem(item.asItem())));
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CHEAT = CREATIVE_MODE_TABS.register("cheat", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + HarvestFestival.MODID + ".cheat"))
            .icon(HFItems.NOTE::toStack)
            .displayItems((params, output) -> {
                        //Add all notes
                        PenguinRegistries.NOTES.stream().forEach(note -> {
                            ItemStack stack = HFItems.NOTE.toStack();
                            CompoundTag tag = new CompoundTag();
                            tag.putString("Note", note.id().toString());
                            stack.setTag(tag);
                            output.accept(stack);
                        });

                        //Add all letters
                        HFRegistries.LETTERS.stream().forEach(letter -> {
                            ItemStack stack = HFItems.MAIL.toStack();
                            CompoundTag tag = new CompoundTag();
                            tag.putString("Letter", letter.id().toString());
                            stack.setTag(tag);
                            output.accept(stack);
                        });
                    }
            ).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FARMING = CREATIVE_MODE_TABS.register("farming", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + HarvestFestival.MODID + ".farming"))
            .icon(HFItems.FERTILIZER::toStack)
            .displayItems((params, output) -> {
                HFRegistries.FERTILIZER.stream().forEach(fertilizer -> {
                    ItemStack stack = HFItems.FERTILIZER.toStack();
                    CompoundTag tag = new CompoundTag();
                    tag.putString("Fertilizer", Objects.requireNonNull(HFRegistries.FERTILIZER.getKey(fertilizer)).toString());
                    stack.setTag(tag);
                    output.accept(stack);
                });

                HFRegistries.SEEDS.stream().forEach(crop -> output.accept(HFItems.SEED_BAG.get().toStack(crop)));
                HFRegistries.SEEDLINGS.stream().forEach(tree -> output.accept(HFItems.SEEDLING_BAG.get().withSeedling(tree)));
            }).build());

    @SubscribeEvent
    public static void onCreativeTabsBuilt(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == MINE.getKey()) {
            //Add all the item found in HCItems.ITEMS that don't conform to GATHERING_BLOCKS
            HFItems.ITEMS.getEntries().stream()
                    .map(DeferredHolder::get)
                    .filter(item -> !(item instanceof BlockItem) ||
                            GATHERING_BLOCKS.stream().noneMatch(blockClass ->
                                    blockClass.isInstance(((BlockItem) item).getBlock())))
                    .forEach(event::accept);
            HFEntities.SPAWN_EGGS.getEntries().forEach(egg -> event.accept(egg.get()));
        }

    }
}