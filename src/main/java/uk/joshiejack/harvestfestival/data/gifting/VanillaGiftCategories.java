package uk.joshiejack.harvestfestival.data.gifting;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import uk.joshiejack.penguinlib.util.PenguinTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class VanillaGiftCategories extends AbstractGiftCategories{
    public VanillaGiftCategories(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super("minecraft", packOutput, lookupProvider);
    }

    public List<Object> art() {
        return List.of(
                ItemTags.MUSIC_DISCS,
                Items.ITEM_FRAME,
                Tags.Items.DYES,
                Items.PAINTING
        );
    }

    public List<Object> building() {
        return List.of(
                Items.NETHER_BRICK,
                Tags.Items.INGOTS_BRICK,
                Items.CLAY_BALL,
                ItemTags.SIGNS,
                Items.FLOWER_POT,
                Items.CARVED_PUMPKIN,
                ItemTags.LOGS,
                Tags.Items.ORES,
                Tags.Items.GLASS,
                Tags.Items.GLASS_PANES);
    }

    public List<Object> cooking() {
        return List.of(
                Items.MUSHROOM_STEW,
                Items.BREAD,
                Items.COOKED_COD,
                Items.CAKE,
                Items.COOKIE,
                Items.BAKED_POTATO,
                Items.PUMPKIN_PIE,
                Items.BEETROOT_SOUP,
                Items.RABBIT_STEW);
    }

    public List<Object> egg() {
        return List.of(
                Items.EGG,
                Items.TURTLE_EGG);
    }

    public List<Object> fish() {
        return List.of(PenguinTags.RAW_FISHES);
    }

    public List<Object> flower() {
        return List.of(
                Items.POPPY,
                Items.DANDELION,
                Items.BLUE_ORCHID,
                Items.ALLIUM,
                Items.AZURE_BLUET,
                Items.RED_TULIP,
                Items.ORANGE_TULIP,
                Items.WHITE_TULIP,
                Items.PINK_TULIP,
                Items.OXEYE_DAISY,
                Items.CORNFLOWER,
                Items.LILY_OF_THE_VALLEY,
                Items.WITHER_ROSE,
                Items.SUNFLOWER,
                Items.LILAC,
                Items.ROSE_BUSH,
                Items.PEONY);
    }

    public List<Object> fruit() {
        return List.of(
                PenguinTags.CROPS_APPLE,
                PenguinTags.CROPS_MELON,
                Items.SWEET_BERRIES,
                Items.CHORUS_FRUIT,
                Items.POPPED_CHORUS_FRUIT,
                Items.GOLDEN_APPLE,
                Items.GLISTERING_MELON_SLICE);
    }

    public List<Object> gem() {
        return List.of(
                Tags.Items.GEMS_DIAMOND,
                Tags.Items.GEMS_EMERALD,
                Tags.Items.GEMS_AMETHYST,
                Tags.Items.GEMS_LAPIS,
                Items.RABBIT_FOOT);
    }

    public List<Object> junk() {
        return List.of(
                Items.POISONOUS_POTATO,
                Items.SUGAR,
                Items.GLASS_BOTTLE,
                Items.CARROT_ON_A_STICK,
                Items.FIREWORK_ROCKET,
                Items.FIREWORK_STAR,
                Items.LEAD,
                Items.NAME_TAG,
                Items.SADDLE,
                Items.SNOWBALL,
                Items.STICK,
                Items.BOWL,
                Items.DEAD_BUSH,
                Items.FEATHER,
                Items.LEATHER,
                Items.RABBIT_HIDE);
    }

    public List<Object> knowledge() {
        return List.of(
                Items.BOOK,
                Items.WRITTEN_BOOK,
                Items.WRITABLE_BOOK,
                Items.KNOWLEDGE_BOOK,
                Items.MAP,
                Items.COMPASS,
                Items.CLOCK,
                Items.SPYGLASS,
                Items.INK_SAC,
                Items.PAPER);
    }

    public List<Object> machine() {
        return List.of(
                Tags.Items.STORAGE_BLOCKS_REDSTONE,
                Items.REDSTONE_TORCH,
                Items.REPEATER,
                Items.COMPARATOR,
                Items.PISTON,
                Items.STICKY_PISTON);
    }

    public List<Object> magic() {
        return List.of(
                Items.POTION,
                Items.LINGERING_POTION,
                Items.SPLASH_POTION,
                Items.EXPERIENCE_BOTTLE,
                Items.ENCHANTED_BOOK,
                Items.NETHER_STAR,
                Items.DRAGON_BREATH,
                Items.TOTEM_OF_UNDYING);
    }

    public List<Object> meat() {
        return List.of(
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP,
                Items.BEEF,
                Items.COOKED_BEEF,
                Items.CHICKEN,
                Items.COOKED_CHICKEN,
                Items.MUTTON,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT,
                Items.RABBIT);
    }

    public List<Object> milk() {
        return List.of(Items.MILK_BUCKET);
    }

    public List<Object> mineral() {
        return List.of(
                Items.COAL,
                Tags.Items.INGOTS_IRON,
                Tags.Items.INGOTS_GOLD,
                Tags.Items.INGOTS_COPPER,
                Items.FLINT,
                Tags.Items.DUSTS_REDSTONE,
                Tags.Items.DUSTS_GLOWSTONE,
                Tags.Items.NUGGETS_GOLD,
                Tags.Items.NUGGETS_IRON,
                Tags.Items.GEMS_QUARTZ,
                Tags.Items.GEMS_PRISMARINE,
                Tags.Items.DUSTS_PRISMARINE,
                Tags.Items.ORES_EMERALD,
                Tags.Items.ORES_DIAMOND,
                Tags.Items.ORES_REDSTONE,
                Tags.Items.ORES_IRON,
                Tags.Items.ORES_GOLD,
                Tags.Items.ORES_LAPIS,
                Tags.Items.ORES_COPPER);
    }

    public List<Object> monster() {
        return List.of(
                Items.ROTTEN_FLESH,
                Items.ENDER_PEARL,
                Items.BLAZE_ROD,
                Items.GHAST_TEAR,
                Items.SPIDER_EYE,
                Items.FERMENTED_SPIDER_EYE,
                Items.BLAZE_POWDER,
                Items.MAGMA_CREAM,
                Tags.Items.SLIMEBALLS,
                Items.ENDER_EYE,
                Items.FIRE_CHARGE,
                Tags.Items.HEADS,
                Items.GUNPOWDER,
                Items.BONE,
                Items.BONE_MEAL,
                Items.SHULKER_SHELL
                );
    }

    public List<Object> mushroom() {
        return List.of(
                Items.RED_MUSHROOM,
                Items.BROWN_MUSHROOM,
                Items.CRIMSON_FUNGUS,
                Items.WARPED_FUNGUS);
    }

    public List<Object> plant() {
        return List.of(
                Tags.Items.CROPS_WHEAT,
                ItemTags.VILLAGER_PLANTABLE_SEEDS,
                Items.LILY_PAD,
                Items.TALL_GRASS,
                Items.SHORT_GRASS,
                Items.CACTUS,
                Items.FERN,
                Items.LARGE_FERN,
                Items.COCOA_BEANS,
                Items.VINE,
                ItemTags.SAPLINGS);
    }

    public List<Object> vegetable() {
        return List.of(
                Items.BEETROOT,
                Items.CARROT,
                Items.GOLDEN_CARROT,
                Items.POTATO,
                Items.PUMPKIN);
    }

    public List<Object> wool() {
        return List.of(
                ItemTags.WOOL,
                Items.STRING,
                ItemTags.WOOL_CARPETS,
                ItemTags.BANNERS
        );
    }
}
