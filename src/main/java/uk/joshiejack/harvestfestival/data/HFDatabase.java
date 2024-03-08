package uk.joshiejack.harvestfestival.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.collections.Collections;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.data.database.CSVUtils;
import uk.joshiejack.penguinlib.data.generator.AbstractDatabaseProvider;

import java.util.Objects;

public class HFDatabase extends AbstractDatabaseProvider {
    public HFDatabase(PackOutput output) {
        super(output, HarvestFestival.MODID);
    }

    @Override
    protected void addDatabaseEntries() {
        addAOETool(HFItems.COPPER_HAMMER, 1);
        addAOETool(HFItems.SILVER_HAMMER, 2);
        addAOETool(HFItems.GOLD_HAMMER, 3);
        addAOETool(HFItems.MYSTRIL_HAMMER, 5);
        addAOETool(HFItems.CURSED_HAMMER, 7);
        addAOETool(HFItems.BLESSED_HAMMER, 7);
        addAOETool(HFItems.MYTHIC_HAMMER, 15);
        addNutritionOverrides();
        addCollection(HarvestFestival.prefix("shipping"), 1, Collections.SHIPPING_COLLECTION, HarvestFestival.prefix("book/shipping_collection"));
        addCollection(HarvestFestival.prefix("cooking"), 4, Collections.COOKING_COLLECTION, HarvestFestival.prefix("book/cooking_collection"));
        addCollection(HarvestFestival.prefix("fishing"), 2, Collections.FISHING_COLLECTION, HarvestFestival.prefix("book/fishing_collection"));
        addCollection(HarvestFestival.prefix("mining"), 3, Collections.MINING_COLLECTION, HarvestFestival.prefix("book/mining_collection"));
        addTimeUnit("horticulture:sprinkler_start", 6000); //Water crops from 6am
        addTimeUnit("horticulture:sprinkler_finish", 6250); //Until 6:15am
    }

    private void addNutritionOverrides() {
        addNutritionOverride(Items.BAKED_POTATO, 3);
        addNutritionOverride(Items.CARROT, 2);
        addNutritionOverride(Items.APPLE, 2);
        addNutritionOverride(Items.BREAD, 3);
        addNutritionOverride(Items.COOKIE, 1);
        addNutritionOverride(Items.MELON, 1);
        addNutritionOverride(Items.COOKED_CHICKEN, 5);
        addNutritionOverride(Items.COOKED_BEEF, 6);
        addNutritionOverride(Items.COOKED_PORKCHOP, 6);
        addNutritionOverride(Items.SPIDER_EYE, 1);
        addNutritionOverride(Items.ROTTEN_FLESH, 2);
        addNutritionOverride(Items.PUMPKIN_PIE, 6);
        addNutritionOverride(Items.RABBIT, 2);
        addNutritionOverride(Items.RABBIT_STEW, 8);
        addNutritionOverride(Items.BEETROOT_SOUP, 5);
        addNutritionOverride(Items.CHORUS_FRUIT, 2);
        addUpgrade(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, 128);
    }

    protected void addCollection(ResourceLocation id, int order, TagKey<Item> tag, ResourceLocation sprite) {
        addEntry("collections", "ID,Order,Tag,Sprite,Shadow Sprite", CSVUtils.join(id.toString(), order, tag.location(), sprite.toString(), new ResourceLocation(sprite.getNamespace(), sprite.getPath() + "_shadowed").toString()));
    }


    protected void addAOETool(DeferredItem<Item> item, int area) {
        addEntry("critical_smash_tools", "Item,Area", CSVUtils.join(item.getId(), area));
    }

    protected void addNutritionOverride(Item item, int nutrition) {
        addFoodOverride(item, nutrition, -1F);
    }

    protected void addFoodOverride(Item item, int nutrition, float saturation) {
        addEntry("food_overrides", "Item,Nutrition,Saturation",
                CSVUtils.join(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).toString(), nutrition, saturation));
    }

    protected void addUpgrade(Item item, Item upgrade, int experience) {
        addEntry("tool_upgrades", "Item,Upgrade,Experience", CSVUtils.join(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).toString(), Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(upgrade)).toString(), experience));
    }
}
