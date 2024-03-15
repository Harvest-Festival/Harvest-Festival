package uk.joshiejack.harvestfestival.data.economy;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.util.PenguinTags;
import uk.joshiejack.shopaholic.world.shipping.ShippingRegistry;

import java.util.concurrent.CompletableFuture;

public class HFShipping extends DataMapProvider {
    public HFShipping(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public @NotNull String getName() {
        return "Shipping Data Maps";
    }

    private void add(Builder<Long, Item> builder, TagKey<Item> tag, int value) {
        builder.add(tag, (long) value, false);
    }

    private void add(Builder<Long, Item> builder, ItemLike item, int value) {
        builder.add(item.asItem().builtInRegistryHolder(), (long) value, false);
    }

    @Override
    protected void gather() {
        final var shippingData = builder(ShippingRegistry.SELL_VALUE);
        registerVanilla(shippingData);
        registerHarvestFestival(shippingData);
    }

    private void registerHarvestFestival(Builder<Long, Item> shippingData) {
        //Flowers
        add(shippingData, HFBlocks.BLUE_MAGIC_FLOWER.asItem(), 80);
        add(shippingData, HFBlocks.MOONDROP_FLOWER.asItem(), 60);
        add(shippingData, HFBlocks.PINK_CAT_FLOWER.asItem(), 70);
        add(shippingData, HFBlocks.RED_MAGIC_FLOWER.asItem(), 200);
        add(shippingData, HFBlocks.TOY_FLOWER.asItem(), 130);
        add(shippingData, HFBlocks.WEEDS.asItem(), 1);
        add(shippingData, HFBlocks.TOADSTOOL.asItem(), 100);
        add(shippingData, HFBlocks.DWARFEN_SHROOM.asItem(), 250);

        //Coins
        add(shippingData, HFItems.COPPER_COIN, 10);
        add(shippingData, HFItems.SILVER_COIN, 100);
        add(shippingData, HFItems.GOLD_COIN, 1000);

        //Materials
        add(shippingData, HFItems.JUNK_ORE, 1);
        add(shippingData, HFItems.MYTHIC_STONE, 20000);
        add(shippingData, Tags.Items.RAW_MATERIALS_COPPER, 5);
        //TODO: Rename to RAW_MATERIALS_SILVER AND ADD A TAG
        add(shippingData, HFItems.SILVER_ORE, 15);
        add(shippingData, Tags.Items.RAW_MATERIALS_GOLD, 25);
        add(shippingData, HFItems.MYSTRIL_ORE, 40);
        add(shippingData, HFItems.ORICHALC, 50);
        add(shippingData, HFTags.Items.INGOTS_SILVER, 150);
        add(shippingData, HFTags.Items.INGOTS_MYSTRIL, 500);
        //Gems
        add(shippingData, HFItems.ADAMANTITE, 100);
        add(shippingData, HFItems.AGATE, 110);
        add(shippingData, HFItems.ALEXANDRITE, 10000);
        add(shippingData, HFItems.CHAROITE, 80);
        add(shippingData, HFItems.FLUORITE, 40);
        add(shippingData, HFItems.MOONSTONE, 50);
        add(shippingData, HFItems.PERIDOT, 70);
        add(shippingData, HFItems.PINK_DIAMOND, 10000);
        add(shippingData, HFItems.RUBY, 140);
        add(shippingData, HFItems.SAND_ROSE, 60);
        add(shippingData, HFItems.SAPPHIRE, 200);
        add(shippingData, HFItems.TOPAZ, 130);
        add(shippingData, HFItems.JADE, 150);
        //Food
        add(shippingData, HFItems.APPLE_JAM, 130);
        add(shippingData, HFItems.JAM_BUN, 240);
        add(shippingData, HFItems.MARMALADE, 260);
        add(shippingData, HFItems.GRAPE_JAM, 260);
        add(shippingData, HFItems.STRAWBERRY_JAM, 65);
        add(shippingData, HFBlocks.WILD_GRAPES.asItem(), 50);
    }

    private void registerVanilla(Builder<Long, Item> builder) {
        //Farming
        add(builder, PenguinTags.CROPS_PUMPKIN, 220);
        add(builder, Items.MELON, 180);
        add(builder, Items.CACTUS, 50);
        add(builder, Items.SUGAR_CANE, 30);
        add(builder, PenguinTags.CROPS_APPLE, 100);
        add(builder, PenguinTags.CROPS_MELON, 20);
        add(builder, Items.POISONOUS_POTATO, 1);
        add(builder, Tags.Items.CROPS_POTATO, 80);
        add(builder, Tags.Items.CROPS_CARROT, 160);
        add(builder, Items.CHORUS_FRUIT, 20);
        add(builder, Tags.Items.CROPS_BEETROOT, 120);
        add(builder, Tags.Items.CROPS_WHEAT, 100);
        add(builder, Items.EGG, 60);
        add(builder, Items.FEATHER, 125);
        add(builder, Items.CHICKEN, 40);
        add(builder, Items.PORKCHOP, 60);
        add(builder, Items.MILK_BUCKET, 460);
        add(builder, Items.BEEF, 60);
        add(builder, Items.LEATHER, 150);
        add(builder, Items.RABBIT, 40);
        add(builder, Items.RABBIT_FOOT, 400);
        add(builder, Items.RABBIT_HIDE, 120);
        add(builder, Items.MUTTON, 80);
        add(builder, ItemTags.WOOL, 250);
        add(builder, Items.COCOA_BEANS, 25);
        add(builder, Items.NETHER_WART, 10);

        //Mining
        add(builder, Tags.Items.COBBLESTONE, 1);
        add(builder, Tags.Items.STONE, 2);
        add(builder, Items.GRANITE, 3);
        add(builder, Items.DIORITE, 3);
        add(builder, Items.ANDESITE, 3);
        add(builder, Tags.Items.INGOTS_COPPER, 50);
        add(builder, Tags.Items.INGOTS_GOLD, 250);
        add(builder, Tags.Items.INGOTS_IRON, 120);
        add(builder, Tags.Items.GEMS_DIAMOND, 750);
        add(builder, Tags.Items.GEMS_EMERALD, 250);
        add(builder, Items.COAL, 15);
        add(builder, Tags.Items.GEMS_LAPIS, 45);
        add(builder, Tags.Items.DUSTS_REDSTONE, 20);
        add(builder, Tags.Items.GEMS_QUARTZ, 35);
        add(builder, Items.FLINT, 5);
        add(builder, Tags.Items.DUSTS_GLOWSTONE, 10);
        add(builder, Tags.Items.NUGGETS_GOLD, 25);
        add(builder, Tags.Items.NUGGETS_IRON, 10);

        //Lumberjack
        add(builder, Items.STICK, 1);
        add(builder, ItemTags.LOGS, 2);
        add(builder, ItemTags.SAPLINGS, 5);
        add(builder, Items.CHARCOAL, 5);

        //Fishing
        add(builder, Items.COD, 10);
        add(builder, Items.SALMON, 30);
        add(builder, Items.TROPICAL_FISH, 50);
        add(builder, Items.PUFFERFISH, 100);

        //Hunter
        add(builder, Items.STRING, 5);
        add(builder, Items.GUNPOWDER, 25);
        add(builder, Items.SLIME_BALL, 5);
        add(builder, Items.INK_SAC, 3);
        add(builder, Items.BONE, 5);
        add(builder, Items.ROTTEN_FLESH, 5);
        add(builder, Items.ENDER_PEARL, 15);
        add(builder, Items.BLAZE_ROD, 10);
        add(builder, Items.GHAST_TEAR, 30);
        add(builder, Items.SPIDER_EYE, 10);
        add(builder, Items.MAGMA_CREAM, 10);
        add(builder, Items.PRISMARINE_CRYSTALS, 30);
        add(builder, Items.PRISMARINE_SHARD, 50);
        add(builder, Items.ARROW, 10);
        add(builder, Items.TIPPED_ARROW, 15);
        add(builder, Items.SNOWBALL, 3);
        add(builder, Items.SHULKER_SHELL, 25);
        add(builder, Items.MUSIC_DISC_13, 500);
        add(builder, Items.MUSIC_DISC_CAT, 500);
        add(builder, Items.MUSIC_DISC_BLOCKS, 500);
        add(builder, Items.MUSIC_DISC_CHIRP, 500);
        add(builder, Items.MUSIC_DISC_FAR, 500);
        add(builder, Items.MUSIC_DISC_MALL, 500);
        add(builder, Items.MUSIC_DISC_MELLOHI, 500);
        add(builder, Items.MUSIC_DISC_STAL, 500);
        add(builder, Items.MUSIC_DISC_STRAD, 500);
        add(builder, Items.MUSIC_DISC_WARD, 500);
        add(builder, Items.MUSIC_DISC_11, 500);
        add(builder, Items.MUSIC_DISC_WAIT, 500);
        add(builder, Items.TOTEM_OF_UNDYING, 3000);
        add(builder, Items.NETHER_STAR, 5000);
        add(builder, Items.DRAGON_EGG, 50000);

        //Gathering
        add(builder, Items.CLAY_BALL, 5);
        add(builder, Items.DEAD_BUSH, 1);
        add(builder, Items.FERN, 1);
        add(builder, Items.BROWN_MUSHROOM, 15);
        add(builder, Items.RED_MUSHROOM, 20);
        add(builder, Items.DANDELION, 10);
        add(builder, Items.POPPY, 25);
        add(builder, Items.BLUE_ORCHID, 30);
        add(builder, Items.ALLIUM, 20);
        add(builder, Items.AZURE_BLUET, 15);
        add(builder, Items.RED_TULIP, 15);
        add(builder, Items.ORANGE_TULIP, 15);
        add(builder, Items.WHITE_TULIP, 15);
        add(builder, Items.PINK_TULIP, 15);
        add(builder, Items.OXEYE_DAISY, 15);
        add(builder, Items.SUNFLOWER, 45);
        add(builder, Items.LILAC, 40);
        add(builder, Items.ROSE_BUSH, 35);
        add(builder, Items.PEONY, 30);
        add(builder, Items.LILY_PAD, 10);

        //Cooking
        add(builder, Items.BREAD, 60);
        add(builder, Items.COOKED_PORKCHOP, 70);
        add(builder, Items.GOLDEN_APPLE, 2500);
        add(builder, Items.ENCHANTED_GOLDEN_APPLE, 25000);
        add(builder, Items.SUGAR, 5);
        add(builder, Items.COOKIE, 40);
        add(builder, Items.COOKED_BEEF, 70);
        add(builder, Items.COOKED_CHICKEN, 45);
        add(builder, Items.CAKE, 235);
        add(builder, Items.BAKED_POTATO, 90);
        add(builder, Items.GOLDEN_CARROT, 400);
        add(builder, Items.PUMPKIN_PIE, 315);
        add(builder, Items.COOKED_RABBIT, 45);
        add(builder, Items.RABBIT_STEW, 360);
        add(builder, Items.COOKED_MUTTON, 90);
        add(builder, Items.POPPED_CHORUS_FRUIT, 20);
        add(builder, Items.BEETROOT_SOUP, 320);
        add(builder, Items.COOKED_COD, 15);
        add(builder, Items.COOKED_SALMON, 35);
        add(builder, Items.MUSHROOM_STEW, 80);
    }
}
