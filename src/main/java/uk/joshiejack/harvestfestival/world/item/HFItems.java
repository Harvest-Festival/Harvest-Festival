package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.block.HFFluids;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;
import uk.joshiejack.harvestfestival.world.inventory.HFBookMenu;
import uk.joshiejack.harvestfestival.world.item.tool.AxeItem;
import uk.joshiejack.harvestfestival.world.item.tool.HoeItem;
import uk.joshiejack.harvestfestival.world.item.tool.SwordItem;
import uk.joshiejack.harvestfestival.world.item.tool.*;
import uk.joshiejack.penguinlib.util.fluid.SingleFluidItemStack;
import uk.joshiejack.penguinlib.world.item.BookItem;
import uk.joshiejack.penguinlib.world.item.PenguinItem;

import java.util.function.BiConsumer;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HFItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HarvestFestival.MODID);
    public static final DeferredItem<Item> FARMING_FOR_DUMMIES = ITEMS.register("farming_for_dummies", () -> new BookItem(new Item.Properties().stacksTo(1), () -> new SimpleMenuProvider((id, inv, p) -> new HFBookMenu(id), Component.translatable("%s.item.farming_for_dummies".formatted(HarvestFestival.MODID)))));
    public static final DeferredItem<Item> ADAMANTITE = ITEMS.register("adamantite", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AGATE = ITEMS.register("agate", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ALEXANDRITE = ITEMS.register("alexandrite", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CHAROITE = ITEMS.register("charoite", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUORITE = ITEMS.register("fluorite", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> JADE = ITEMS.register("jade", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> MOONSTONE = ITEMS.register("moonstone", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> OPAL = ITEMS.register("opal", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> PERIDOT = ITEMS.register("peridot", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> PINK_DIAMOND = ITEMS.register("pink_diamond", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SAND_ROSE = ITEMS.register("sand_rose", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SAPPHIRE = ITEMS.register("sapphire", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> TOPAZ = ITEMS.register("topaz", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> COPPER_COIN = ITEMS.register("copper_coin", () -> new Item(new Item.Properties().rarity(Rarity.COMMON)));
    public static final DeferredItem<Item> SILVER_COIN = ITEMS.register("silver_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> GOLD_COIN = ITEMS.register("gold_coin", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> COPPER_ORE = ITEMS.register("copper_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_ORE = ITEMS.register("gold_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_ORE = ITEMS.register("iron_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MYSTRIL_ORE = ITEMS.register("mystril_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORICHALC = ITEMS.register("orichalc", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_ORE = ITEMS.register("silver_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MYSTRIL_INGOT = ITEMS.register("mystril_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JUNK_ORE = ITEMS.register("junk_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MYTHIC_STONE = ITEMS.register("mythic_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> ESCAPE_ROPE = ITEMS.register("escape_rope", () -> new EscapeRopeItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GODDESS_FLOWER = ITEMS.register("goddess_flower", () -> new GoddessFlowerItem(HFBlocks.GODDESS_FLOWER.get(), new Item.Properties()));
    public static final DeferredItem<Item> BAT_WING = ITEMS.register("bat_wing", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GODDESS_WATER_BUCKET = ITEMS.register("goddess_water_bucket", () -> new BucketItem(HFFluids.GODDESS_WATER_STILL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ICY_WATER_BUCKET = ITEMS.register("icy_water_bucket", () -> new BucketItem(HFFluids.ICY_WATER_STILL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BASIC_SWORD = ITEMS.register("basic_sword", () -> new SwordItem(HFTiers.STONE, new Item.Properties()));
    public static final DeferredItem<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(HFTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SwordItem(HFTiers.SILVER, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_SWORD = ITEMS.register("gold_sword", () -> new SwordItem(HFTiers.GOLD, new Item.Properties()));
    public static final DeferredItem<Item> MYSTRIL_SWORD = ITEMS.register("mystril_sword", () -> new SwordItem(HFTiers.MYSTRIL, new Item.Properties()));
    public static final DeferredItem<Item> CURSED_SWORD = ITEMS.register("cursed_sword", () -> new SwordItem(HFTiers.CURSED, new Item.Properties()));
    public static final DeferredItem<Item> BLESSED_SWORD = ITEMS.register("blessed_sword", () -> new SwordItem(HFTiers.BLESSED, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> MYTHIC_SWORD = ITEMS.register("mythic_sword", () -> new SwordItem(HFTiers.MYTHIC, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> BASIC_HAMMER = ITEMS.register("basic_hammer", () -> new HammerItem(HFTiers.STONE, new Item.Properties()));
    public static final DeferredItem<Item> COPPER_HAMMER = ITEMS.register("copper_hammer", () -> new HammerItem(HFTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<Item> SILVER_HAMMER = ITEMS.register("silver_hammer", () -> new HammerItem(HFTiers.SILVER, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_HAMMER = ITEMS.register("gold_hammer", () -> new HammerItem(HFTiers.GOLD, new Item.Properties()));
    public static final DeferredItem<Item> MYSTRIL_HAMMER = ITEMS.register("mystril_hammer", () -> new HammerItem(HFTiers.MYSTRIL, new Item.Properties()));
    public static final DeferredItem<Item> CURSED_HAMMER = ITEMS.register("cursed_hammer", () -> new HammerItem(HFTiers.CURSED, new Item.Properties()));
    public static final DeferredItem<Item> BLESSED_HAMMER = ITEMS.register("blessed_hammer", () -> new HammerItem(HFTiers.BLESSED, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> MYTHIC_HAMMER = ITEMS.register("mythic_hammer", () -> new HammerItem(HFTiers.MYTHIC, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> BASIC_AXE = ITEMS.register("basic_axe", () -> new AxeItem(HFTiers.STONE, 7F, -3.2F, new Item.Properties(), 7));
    public static final DeferredItem<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(HFTiers.COPPER, 6.5F, -3.15F, new Item.Properties(), 6));
    public static final DeferredItem<Item> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(HFTiers.SILVER, 6F, -3.1F, new Item.Properties(),5 ));
    public static final DeferredItem<Item> GOLD_AXE = ITEMS.register("gold_axe", () -> new AxeItem(HFTiers.GOLD, 5F, -3.0F, new Item.Properties(), 3));
    public static final DeferredItem<Item> MYSTRIL_AXE = ITEMS.register("mystril_axe", () -> new AxeItem(HFTiers.MYSTRIL, 5F, -3.0F, new Item.Properties(),2));
    public static final DeferredItem<Item> CURSED_AXE = ITEMS.register("cursed_axe", () -> new AxeItem(HFTiers.CURSED, 3F, -2.5F, new Item.Properties(), 1));
    public static final DeferredItem<Item> BLESSED_AXE = ITEMS.register("blessed_axe", () -> new AxeItem(HFTiers.BLESSED, 3F, -2.5F, new Item.Properties().fireResistant(), 1));
    public static final DeferredItem<Item> MYTHIC_AXE = ITEMS.register("mythic_axe", () -> new AxeItem(HFTiers.MYTHIC, 1F, -1F, new Item.Properties().fireResistant(), 0));
    public static final DeferredItem<Item> BASIC_HOE = ITEMS.register("basic_hoe", () -> new HoeItem(HFTiers.STONE, -2F, new Item.Properties()));
    public static final DeferredItem<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(HFTiers.COPPER, -1F, new Item.Properties()));
    public static final DeferredItem<Item> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(HFTiers.SILVER, -0.5F, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_HOE = ITEMS.register("gold_hoe", () -> new HoeItem(HFTiers.GOLD, 0F, new Item.Properties()));
    public static final DeferredItem<Item> MYSTRIL_HOE = ITEMS.register("mystril_hoe", () -> new HoeItem(HFTiers.MYSTRIL, 0F, new Item.Properties()));
    public static final DeferredItem<Item> CURSED_HOE = ITEMS.register("cursed_hoe", () -> new HoeItem(HFTiers.CURSED, 0F, new Item.Properties()));
    public static final DeferredItem<Item> BLESSED_HOE = ITEMS.register("blessed_hoe", () -> new HoeItem(HFTiers.BLESSED, 0F, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> MYTHIC_HOE = ITEMS.register("mythic_hoe", () -> new HoeItem(HFTiers.MYTHIC, 0F, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> COPPER_WATERING_CAN = ITEMS.register("copper_watering_can", () -> new WateringCanItem(0, 1, new Item.Properties()));
    public static final DeferredItem<Item> SILVER_WATERING_CAN = ITEMS.register("silver_watering_can", () -> new WateringCanItem(2, 1, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_WATERING_CAN = ITEMS.register("gold_watering_can", () -> new WateringCanItem(2, 2, new Item.Properties()));
    public static final DeferredItem<Item> MYSTRIL_WATERING_CAN = ITEMS.register("mystril_watering_can", () -> new WateringCanItem(2, 3, new Item.Properties()));
    public static final DeferredItem<Item> CURSED_WATERING_CAN = ITEMS.register("cursed_watering_can", () -> new WateringCanItem(5, 6, new Item.Properties()).setAsCursed());
    public static final DeferredItem<Item> BLESSED_WATERING_CAN = ITEMS.register("blessed_watering_can", () -> new WateringCanItem(5, 6, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> MYTHIC_WATERING_CAN = ITEMS.register("mythic_watering_can", () -> new WateringCanItem(11, 10, new Item.Properties().fireResistant()));

    public static final DeferredItem<Item> TEA = ITEMS.register("tea", () -> new PenguinItem(new PenguinItem.Properties().finishUsing(clearEffects()).useAction(UseAnim.DRINK).food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(HFEffects.BUZZED.get(), 500), 1F).fast().build())));
    public static final DeferredItem<Item> COFFEE = ITEMS.register("coffee", () -> new PenguinItem(new PenguinItem.Properties().finishUsing(clearEffects()).useAction(UseAnim.DRINK).food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(HFEffects.BUZZED.get(), 1500), 1F).fast().build())));
    public static final DeferredItem<Item> POWER_BERRY = ITEMS.register("power_berry", () -> new PowerBerryItem(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(999).saturationMod(999F).build()).stacksTo(1)));
    public static final DeferredItem<Item> NOTE = ITEMS.register("note", () -> new NoteItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAIL = ITEMS.register("mail", () -> new MailItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, SeedBagItem> SEED_BAG = ITEMS.register("seed_bag", () -> new SeedBagItem(new Item.Properties()));
    public static final DeferredHolder<Item, SeedlingBagItem> SEEDLING_BAG = ITEMS.register("seedling_bag", () -> new SeedlingBagItem(new Item.Properties()));
    public static final DeferredHolder<Item, FertilizerItem> FERTILIZER = ITEMS.register("fertilizer", () -> new FertilizerItem(new Item.Properties()));
    public static final DeferredItem<Item> GRASS_STARTER = ITEMS.register("grass_starter", () -> new StarterBlockItem(Blocks.SHORT_GRASS, new Item.Properties()));
    public static final DeferredItem<Item> CURSED_BOOTS = ITEMS.register("cursed_boots", () -> new SpeedBootsItem("harvestfestival:cursed", new Item.Properties().stacksTo(1), 0, -0.025D));
    public static final DeferredItem<Item> BLESSED_BOOTS = ITEMS.register("blessed_boots", () -> new SpeedBootsItem("harvestfestival:blessed", new Item.Properties().stacksTo(1), 25, 0.1D));
    public static final DeferredItem<Item> CALENDAR = ITEMS.register("calendar", () -> new CalendarItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BLUE_FEATHER = ITEMS.register("blue_feather", () -> new Item(new Item.Properties().stacksTo(1)));
    private static BiConsumer<ItemStack, LivingEntity> clearEffects() {
        return (stack, entity) -> {
            if (entity instanceof ServerPlayer player) {
                player.removeEffect(HFEffects.TIRED.get());
                player.removeEffect(HFEffects.FATIGUED.get());
                player.removeEffect(HFEffects.EXHAUSTED.get());
            }
        };
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.COPPER.getUses()), COPPER_WATERING_CAN.asItem());
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.SILVER.getUses()), SILVER_WATERING_CAN.asItem());
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.GOLD.getUses()), GOLD_WATERING_CAN.asItem());
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.MYSTRIL.getUses()), MYSTRIL_WATERING_CAN.asItem());
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.BLESSED.getUses()), CURSED_WATERING_CAN.asItem());
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.BLESSED.getUses()), BLESSED_WATERING_CAN.asItem());
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new SingleFluidItemStack(stack, Fluids.WATER, HFTiers.MYTHIC.getUses()), MYTHIC_WATERING_CAN.asItem());
    }
}
