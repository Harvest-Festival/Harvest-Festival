package uk.joshiejack.harvestfestival.data.economy;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;
import uk.joshiejack.settlements.world.entity.SettlementsEntities;
import uk.joshiejack.shopaholic.Shopaholic;
import uk.joshiejack.shopaholic.api.shop.Condition;
import uk.joshiejack.shopaholic.data.shop.ShopBuilder;
import uk.joshiejack.shopaholic.world.shop.Department;
import uk.joshiejack.shopaholic.world.shop.comparator.PlayerStatusComparator;
import uk.joshiejack.shopaholic.world.shop.condition.AndCondition;
import uk.joshiejack.shopaholic.world.shop.condition.NotCondition;
import uk.joshiejack.shopaholic.world.shop.condition.OrCondition;

import java.time.DayOfWeek;
import java.util.Map;

import static java.time.DayOfWeek.*;
import static net.minecraft.network.chat.Component.translatable;
import static net.minecraft.world.entity.EntityType.CHICKEN;
import static net.minecraft.world.entity.EntityType.RABBIT;
import static net.minecraft.world.entity.EntityType.*;
import static net.minecraft.world.item.Items.EGG;
import static net.minecraft.world.item.Items.*;
import static uk.joshiejack.harvestfestival.data.economy.HFShops.ANIMAL_RANCH;
import static uk.joshiejack.harvestfestival.data.economy.HFShops.POULTRY_FARM;
import static uk.joshiejack.harvestfestival.data.economy.HFStockMechanics.*;
import static uk.joshiejack.horticulture.world.item.HorticultureItems.WATERING_CAN;
import static uk.joshiejack.husbandry.world.item.HusbandryItems.BRUSH;
import static uk.joshiejack.husbandry.world.item.HusbandryItems.*;
import static uk.joshiejack.piscary.world.item.PiscaryItems.*;
import static uk.joshiejack.shopaholic.data.ShopaholicDepartments.itemIcon;
import static uk.joshiejack.shopaholic.data.ShopaholicStockMechanics.LIMITED_ONE;
import static uk.joshiejack.shopaholic.data.shop.DepartmentBuilder.department;
import static uk.joshiejack.shopaholic.data.shop.Vendor.entityVendor;
import static uk.joshiejack.shopaholic.data.shop.comparator.ComparatorBuilder.shippedAmount;
import static uk.joshiejack.shopaholic.plugin.simplyseasons.condition.SeasonCondition.inSeason;
import static uk.joshiejack.shopaholic.world.shop.comparator.NumberComparator.number;
import static uk.joshiejack.shopaholic.world.shop.comparator.TeamStatusComparator.teamStatus;
import static uk.joshiejack.shopaholic.world.shop.condition.CompareCondition.compare;
import static uk.joshiejack.shopaholic.world.shop.condition.EntityHasNBTTagCondition.entityHasNBTTag;
import static uk.joshiejack.shopaholic.world.shop.condition.OpeningHoursCondition.openingHours;

public class HFDepartments extends AbstractPenguinRegistryProvider<Department> {
    public static final ResourceLocation ANIMAL_RANCH_TOOLS = HarvestFestival.prefix("animal_ranch_tools");
    public static final ResourceLocation ANIMAL_RANCH_ANIMALS = HarvestFestival.prefix("animal_ranch_animals");
    public static final ResourceLocation GENERAL_STORE_TOOLS = HarvestFestival.prefix("general_store_tools");
    public static final ResourceLocation GENERAL_STORE_FARMING = HarvestFestival.prefix("general_store_farming");
    public static final ResourceLocation GENERAL_STORE_GENERAL = HarvestFestival.prefix("general_store_general");
    public static final ResourceLocation TACKLE_SHOP = HarvestFestival.prefix("tackle_shop");

    public HFDepartments(PackOutput output) {
        super(output, Shopaholic.ShopaholicRegistries.DEPARTMENTS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, Department> map) {
        //Register the shops
        buildAnimalRanch(map);
//        buildBakedGoods(map);
//        buildBlacksmith(map);
//        buildCafe(map);
//        buildCarpenter(map);
//        buildClinic(map);
//        buildEngineersWorkshop(map);
//        buildFlowerMarket(map);
        buildGeneralStore(map);
//        buildMarketStall(map);
//        buildPetShop(map);
        buildPoultryFarm(map);
        buildTackleShop(map);
//        buildWitchingWares(map);
    }

    private void buildAnimalRanch(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(ANIMAL_RANCH)
                .vendor(entityVendor(SettlementsEntities.NPC.get()))
                .condition(entityHasNBTTag("NPC", "harvestfestival:jim"))
                .condition(openingHours()
                        .withHours(MONDAY, 10000, 15000)
                        .withHours(TUESDAY, 10000, 15000)
                        .withHours(WEDNESDAY, 10000, 15000)
                        .withHours(THURSDAY, 10000, 15000)
                        .withHours(FRIDAY, 10000, 15000)
                        .withHours(SATURDAY, 10000, 15000)
                        .build())
                .condition(compare(teamStatus("cow_tutorial_completed"), false, true, false, number(1)))
                .department(department(ANIMAL_RANCH_TOOLS, itemIcon(LEAD), translatable("department.harvestfestival.animal_ranch_tools"))
                        .itemListing(MIRACLE_POTION.get(), 1500, LIMITED_ONE)
                        .itemListing(NAME_TAG, 250, LIMITED_FOUR)
                        .itemListing(LEAD, 150, LIMITED_FOUR)
                        .itemListing(BRUSH, 1000, LIMITED_ONE)
                        //TODO? .listing(ListingBuilder.of("milker").addSublisting(SublistingBuilder.item(HusbandryItems.MILKER).cost(2000)).stockMechanic(HFStockMechanics.LIMITED_1))
                        .itemListing(SHEARS, 2000, LIMITED_ONE) //TODO: Has Medium Barn Condition
                )
                .department(department(ANIMAL_RANCH_ANIMALS, itemIcon(EGG), translatable("department.harvestfestival.animal_ranch_animals"))
                        .itemListing(FODDER.get(), 50)
                        .itemListing(SLOP.get(), 100) //TODO: Has Large Barn Condition
                        .itemListing(GENERIC_TREAT.get(), 10)
                        .itemListing(COW_TREAT.get(), 30)
                        .itemListing(SHEEP_TREAT.get(), 25) //TODO: Has Medium Barn Condition
                        .itemListing(PIG_TREAT.get(), 50) //TODO: Has Large Barn Condition
                        .itemListing(LLAMA_TREAT.get(), 60) //TODO: Has Large Barn Condition
                        .entityListing(COW, 3000) //TODO Has Space in Barn Condition, 4 Limit
                        .entityListing(SHEEP, 2500) //TODO Has Space in Barn Condition and Medium Barn Condition, 4 Limit
                        .entityListing(PIG, 8000) //TODO Has Space in Barn Condition and Large Barn Condition, 4 Limit
                )
                .save(map);
    }

    private void buildBakedGoods(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.BAKED_GOODS)

                .save(map);
    }

    private void buildBlacksmith(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.BLACKSMITH)

                .save(map);
    }

    private void buildCafe(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.CAFE)

                .save(map);
    }

    private void buildCarpenter(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.CARPENTER)

                .save(map);
    }

    private void buildClinic(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.CLINIC)

                .save(map);
    }

    private void buildEngineersWorkshop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.ENGINEERS_WORKSHOP)

                .save(map);
    }

    private void buildFlowerMarket(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.FLOWER_MARKET)

                .save(map);
    }

    private ItemStack seedBagOf(String crop) {
        return HFItems.SEED_BAG.get().toStack(HarvestFestival.prefix(crop));
    }

    private ItemStack seedlingOf(String crop) {
        return HFItems.SEEDLING_BAG.get().withSeedling(HarvestFestival.prefix(crop));
    }

    private ItemStack fertilizerOf(String crop) {
        return HFItems.FERTILIZER.get().withFertilizer(HarvestFestival.prefix(crop));
    }

    private void buildGeneralStore(Map<ResourceLocation, Department> map) {
        Condition IN_SPRING = inSeason("spring");
        Condition IN_SUMMER = inSeason("summer");
        Condition IN_AUTUMN = inSeason("autumn");
        Condition NOT_IN_WINTER = NotCondition.not(inSeason("winter"));
        TagKey<Item> SPRING_CROPS = ItemTags.create(HarvestFestival.prefix("spring_crops"));
        TagKey<Item> SUMMER_CROPS = ItemTags.create(HarvestFestival.prefix("summer_crops"));
        TagKey<Item> AUTUMN_CROPS = ItemTags.create(HarvestFestival.prefix("autumn_crops"));

        ShopBuilder.of(HFShops.GENERAL_STORE)
                .vendor(entityVendor(SettlementsEntities.NPC.get()))
                .condition(entityHasNBTTag("NPC", "harvestfestival:jenni"))
                //General store is open on the usual hours OR on a Wednesday if someone in the town has three hearts with jenni
                .condition(OrCondition.or(openingHours()
                                .withHours(MONDAY, 9000, 17000)
                                .withHours(TUESDAY, 9000, 17000)
                                .withHours(DayOfWeek.THURSDAY, 9000, 17000)
                                .withHours(DayOfWeek.FRIDAY, 9000, 17000)
                                .withHours(SATURDAY, 11000, 15000)
                                .build(),
                        AndCondition.and(openingHours()
                                        .withHours(DayOfWeek.WEDNESDAY, 8000, 18000)
                                        .build(),
                                compare(
                                        teamStatus("general_store_wednesday"),
                                        false, true, false, number(1))
                        ))
                )
                .department(department(GENERAL_STORE_FARMING, itemIcon(WATERING_CAN.get()), translatable("department.harvestfestival.general_store_farming"))
                        //Starters
                        .itemListing(HFItems.GRASS_STARTER.get(), 100, NOT_IN_WINTER)
                        //SPRING
                        .itemListing("turnip", seedBagOf("turnip"), 35, IN_SPRING)
                        .itemListing("potato", seedBagOf("potato"), 40, IN_SPRING)
                        .itemListing("cucumber", seedBagOf("cucumber"), 45, IN_SUMMER) //TODO Add Year Two Town Age Condition
                        .itemListing("strawberry", seedBagOf("strawberry"), 90, IN_SPRING) //TODO Add Year Three Town Age Condition
                        .itemListing("cabbage", seedBagOf("cabbage"), 165, IN_SPRING, //VVV If we have shipped more than 1000 spring crops unlock cabbage
                                compare(shippedAmount().countTag(SPRING_CROPS).build(), false, true, true, number(1000)))
                        //SUMMER
                        .itemListing("onion", seedBagOf("onion"), 40, IN_SUMMER)
                        .itemListing("pumpkin", seedBagOf("pumpkin"), 50, IN_SUMMER)
                        .itemListing("melon", seedBagOf("melon"), 30, IN_SUMMER) //TODO: Add requires 10 community points (has fishing pond (+2), mine (+2), cafe (+3), goddess pond (+3), community centre (+5))
                        .itemListing("tomato", seedBagOf("tomato"), 55, IN_SUMMER) //TODO Add Year Two Town Age Condition
                        .itemListing("corn", seedBagOf("corn"), 95, IN_SUMMER) //TODO Add Year Three Town Age Condition
                        .itemListing("pineapple", seedBagOf("pineapple"), 220, IN_SUMMER,  ///VVV If we have shipped more than 1000 summer crops unlock pineapple
                                compare(shippedAmount().countTag(SUMMER_CROPS).build(), false, true, true, number(1000)))
                        //AUTUMN
                        .itemListing("spinach", seedBagOf("spinach"), 40, IN_AUTUMN)
                        .itemListing("carrot", seedBagOf("carrot"), 55, IN_AUTUMN)
                        .itemListing("beetroot", seedBagOf("beetroot"), 60, IN_AUTUMN) //TODO: Add requires 10 community points (has fishing pond (+2), mine (+2), cafe (+3), goddess pond (+3), community centre (+5))
                        .itemListing("eggplant", seedBagOf("eggplant"), 65, IN_AUTUMN) //TODO Add Year Two Town Age Condition
                        .itemListing("green_pepper", seedBagOf("green_pepper"), 100, IN_AUTUMN) //TODO Add Year Three Town Age Condition
                        .itemListing("sweet_potato", seedBagOf("sweet_potato"), 110, IN_AUTUMN, ///VVV If we have shipped more than 1000 autumn crops unlock sweet potato
                                compare(shippedAmount().countTag(AUTUMN_CROPS).build(), false, true, true, number(1000)))
                        //TODO: All Year & Trees & Fertilizers
                        .itemListing("wheat", seedBagOf("wheat"), 15, NOT_IN_WINTER)
                        .itemListing("nether_wart", seedBagOf("nether_wart"), 55, NOT_IN_WINTER,
                                compare(PlayerStatusComparator.playerStatus("visited_nether"), false, true, false, number(1)))
                        .itemListing("orange_tree", seedlingOf("orange"), 2800, NOT_IN_WINTER)
                        .itemListing("banana_tree", seedlingOf("banana"), 2500, NOT_IN_WINTER)
                        .itemListing("apple_tree", seedlingOf("apple"), 1500, NOT_IN_WINTER)
                        .itemListing("peach_tree", seedlingOf("peach"), 3000, NOT_IN_WINTER)
                        .itemListing("basic_fertilizer", fertilizerOf("basic"), 100)
                        .itemListing("quality_fertilizer", fertilizerOf("quality"), 150) //TODO: Add year two town age condition
                        .itemListing("retention_fertilizer", fertilizerOf("retention"), 100)
                        .itemListing("aqua_guard_fertilizer", fertilizerOf("aqua_guard"), 150) //TODO: Add year two town age condition
                        .itemListing("speed_fertilizer", fertilizerOf("speed"), 100)
                        .itemListing("turbo_fertilizer", fertilizerOf("turbo"), 150) //TODO: Add year two town age condition
                )
                .department(department(GENERAL_STORE_TOOLS, itemIcon(WATERING_CAN.get()), translatable("department.harvestfestival.general_store_tools"))
                                .itemListing(HFItems.BASIC_HOE, 500, LIMITED_ONE)
                                .itemListing(SICKLE, 500, LIMITED_ONE)
                                .itemListing(WATERING_CAN.get(), 750, LIMITED_ONE)
                                .itemListing(HFItems.BASIC_AXE, 1000, LIMITED_ONE)
                                .itemListing(HFItems.BASIC_HAMMER, 1000, LIMITED_ONE)
                        //TODO: .listing(ListingBuilder.of(with).addSublisting(SublistingBuilder.item(HusbandryItems.BASIC_SHOVEL.get()).cost(1000)).stockMechanic(HFStockMechanics.LIMITED_1))

                )
                .department(department(GENERAL_STORE_GENERAL, new ItemIcon(WATERING_CAN.get().getDefaultInstance()), translatable("department.harvestfestival.general_store_general"))
                        .itemListing(HFItems.BLUE_FEATHER.get(), 10000) //TODO: Add Can_Propose Condition (Has 10 hearts with someone)
                        //TODO .itemListing(GastronomyItems.RICEBALL.get(), 100)
                        //TODO .itemListing(GastronomyItems.COOKING_OIL.get(), 50)
                        //TODO .itemListing(GastronomyItems.FLOUR.get(), 50)
                        //TODO .itemListing(GastronomyItems.CURRY_POWDER.get(), 50)
                        //TODO .itemListing(GastronomyItems.CHOCOLATE.get(), 100)
                        //TODO .itemListing(GastronomyItems.WINE.get(), 200)
                        //TODO .itemListing(GastronomyItems.SALT.get(), 25)
                        .itemListing(Items.SUGAR, 25)
                )
                .save(map);
    }

    private void buildMarketStall(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.MARKET_STALL)

                .save(map);
    }

    private void buildPetShop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.PET_SHOP)

                .save(map);
    }

    private ItemIcon icon(ItemLike item) {
        return new ItemIcon(item.asItem().getDefaultInstance());
    }

    private void buildPoultryFarm(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(POULTRY_FARM)
                .vendor(entityVendor(SettlementsEntities.NPC.get()))
                .condition(entityHasNBTTag("NPC", "harvestfestival:ashlee"))
                .condition(openingHours()
                        .withHours(MONDAY, 6000, 12000)
                        .withHours(TUESDAY, 6000, 12000)
                        .withHours(WEDNESDAY, 6000, 12000)
                        .withHours(THURSDAY, 6000, 12000)
                        .withHours(FRIDAY, 6000, 12000)
                        .withHours(SATURDAY, 6000, 12000)
                        .build())
                .department(department(POULTRY_FARM, icon(BIRD_FEED), translatable("department.harvestfestival.poultry_farm"))
                        .itemListing(BIRD_FEED.get(), 25)
                        //TODO? .itemListing(HusbandryItems.RABBIT_FOOD.get(), 50)
                        .itemListing(GENERIC_TREAT.get(), 10)
                        .itemListing(CHICKEN_TREAT.get(), 25)
                        //TODO?  .itemListing(HusbandryItems.DUCK_TREAT.get(), 20)
                        .itemListing(RABBIT_TREAT.get(), 40)
                        .entityListing(CHICKEN, 1500, LIMITED_FOUR) //Add Condition for Space in Coop
                        //TODO?. listing(ListingBuilder.of("duck").addSublisting(SublistingBuilder.entity(HusbandryEntities.DUCK.get()).cost(2000)).stockMechanic(HFStockMechanics.LIMITED_FOUR)) //Add Condition for Space in Coop and Medium Coop
                        .entityListing(RABBIT, 5000, LIMITED_FOUR) //Add Condition for Space in Coop and Large Coop
                        .itemListing(NAME_TAG, 250, LIMITED_FOUR))
                .save(map);
    }

    private void buildTackleShop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(TACKLE_SHOP)
                .vendor(entityVendor(SettlementsEntities.NPC.get()))
                .condition(entityHasNBTTag("NPC", "harvestfestival:jakob"))
                .condition(openingHours()
                        .withHours(TUESDAY, 13000, 19000)
                        .withHours(DayOfWeek.WEDNESDAY, 13000, 19000)
                        .withHours(DayOfWeek.THURSDAY, 13000, 19000)
                        .withHours(DayOfWeek.FRIDAY, 13000, 19000)
                        .build())
                .condition(compare(teamStatus("helped_yulif"), false, true, false, number(1)))
                .department(department(TACKLE_SHOP, itemIcon(FISHING_ROD), translatable("department.harvestfestival.tackle_shop"))
                                .itemListing(BAIT.get(), 5)
                                .itemListing(FISHING_ROD, 1000, LIMITED_ONE)
                                .itemListing(FISH_TRAP, 200, LIMITED_FIVE)
                                .itemListing(HATCHERY, 500, LIMITED_THREE, inSeason("summer"))
                        //TODO: Fish Trap Blueprint, One Per Player
                        //TODO: Hatchery Blueprint, One Per Player
                )


                .save(map);
    }

    private void buildWitchingWares(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.WITCHING_WARES)

                .save(map);
    }
}
