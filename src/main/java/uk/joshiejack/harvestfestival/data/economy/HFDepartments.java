package uk.joshiejack.harvestfestival.data.economy;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.horticulture.world.item.HorticultureItems;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;
import uk.joshiejack.settlements.world.entity.SettlementsEntities;
import uk.joshiejack.shopaholic.Shopaholic;
import uk.joshiejack.shopaholic.api.shop.Condition;
import uk.joshiejack.shopaholic.data.ShopaholicStockMechanics;
import uk.joshiejack.shopaholic.data.shop.DepartmentBuilder;
import uk.joshiejack.shopaholic.data.shop.ShopBuilder;
import uk.joshiejack.shopaholic.data.shop.Vendor;
import uk.joshiejack.shopaholic.data.shop.comparator.ComparatorBuilder;
import uk.joshiejack.shopaholic.data.shop.listing.ListingBuilder;
import uk.joshiejack.shopaholic.data.shop.listing.SublistingBuilder;
import uk.joshiejack.shopaholic.plugin.simplyseasons.condition.SeasonCondition;
import uk.joshiejack.shopaholic.world.shop.Department;
import uk.joshiejack.shopaholic.world.shop.comparator.NumberComparator;
import uk.joshiejack.shopaholic.world.shop.comparator.PlayerStatusComparator;
import uk.joshiejack.shopaholic.world.shop.comparator.TeamStatusComparator;
import uk.joshiejack.shopaholic.world.shop.condition.*;

import java.time.DayOfWeek;
import java.util.Map;

public class HFDepartments extends AbstractPenguinRegistryProvider<Department> {
    public static final ResourceLocation ANIMAL_RANCH_TOOLS = HarvestFestival.prefix("animal_ranch_tools");
    public static final ResourceLocation ANIMAL_RANCH_ANIMALS = HarvestFestival.prefix("animal_ranch_animals");
    public static final ResourceLocation GENERAL_STORE_TOOLS = HarvestFestival.prefix("general_store_tools");
    public static final ResourceLocation GENERAL_STORE_FARMING = HarvestFestival.prefix("general_store_farming");
    public static final ResourceLocation GENERAL_STORE_GENERAL = HarvestFestival.prefix("general_store_general");

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
//        buildPoultryFarm(map);
//        buildTackleShop(map);
//        buildWitchingWares(map);
    }

    private void buildAnimalRanch(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.ANIMAL_RANCH)
                .vendor(Vendor.entity(SettlementsEntities.NPC.get()))
                .condition(EntityHasNBTTagCondition.entityHasNBTTag("NPC", "harvestfestival:jim"))
                .condition(OpeningHoursCondition.openingHours()
                        .withHours(DayOfWeek.MONDAY, 10000, 15000)
                        .withHours(DayOfWeek.TUESDAY, 10000, 15000)
                        .withHours(DayOfWeek.WEDNESDAY, 10000, 15000)
                        .withHours(DayOfWeek.THURSDAY, 10000, 15000)
                        .withHours(DayOfWeek.FRIDAY, 10000, 15000)
                        .withHours(DayOfWeek.SATURDAY, 10000, 15000)
                        .build())
                .condition(CompareCondition.compare(TeamStatusComparator.status("cow_tutorial_completed"),
                        false, true, false, NumberComparator.number(1)))
                .department(DepartmentBuilder.of(ANIMAL_RANCH_TOOLS, ItemIcon.EMPTY, Component.translatable("department.harvestfestival.animal_ranch_tools"))
                        .listing(ListingBuilder.of("miracle_potion").addSublisting(SublistingBuilder.item(HusbandryItems.MIRACLE_POTION.get()).cost(1500)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                        .listing(ListingBuilder.of("name_tag").addSublisting(SublistingBuilder.item(Items.NAME_TAG).cost(250)).stockMechanic(HFStockMechanics.LIMITED_FOUR))
                        .listing(ListingBuilder.of("lead").addSublisting(SublistingBuilder.item(Items.LEAD).cost(150)).stockMechanic(HFStockMechanics.LIMITED_FOUR))
                        .listing(ListingBuilder.of("brush").addSublisting(SublistingBuilder.item(HusbandryItems.BRUSH.get()).cost(1000)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                        //TODO? .listing(ListingBuilder.of("milker").addSublisting(SublistingBuilder.item(HusbandryItems.MILKER).cost(2000)).stockMechanic(HFStockMechanics.LIMITED_1))
                        .listing(ListingBuilder.of("shears").addSublisting(SublistingBuilder.item(Items.SHEARS).cost(2000)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE)) //TODO: Has Medium Barn Condition
                )
                .department(DepartmentBuilder.of(ANIMAL_RANCH_ANIMALS, new ItemIcon(Items.EGG.getDefaultInstance()), Component.translatable("department.harvestfestival.animal_ranch_animals"))
                        .itemListing(HusbandryItems.FODDER.get(), 50)
                        .itemListing(HusbandryItems.SLOP.get(), 100) //TODO: Has Large Barn Condition
                        .itemListing(HusbandryItems.GENERIC_TREAT.get(), 10)
                        .itemListing(HusbandryItems.COW_TREAT.get(), 30)
                        .itemListing(HusbandryItems.SHEEP_TREAT.get(), 25) //TODO: Has Medium Barn Condition
                        .itemListing(HusbandryItems.PIG_TREAT.get(), 50) //TODO: Has Large Barn Condition
                        .itemListing(HusbandryItems.LLAMA_TREAT.get(), 60) //TODO: Has Large Barn Condition
                        .entityListing(EntityType.COW, 3000) //TODO Has Space in Barn Condition, 4 Limit
                        .entityListing(EntityType.SHEEP, 2500) //TODO Has Space in Barn Condition and Medium Barn Condition, 4 Limit
                        .entityListing(EntityType.PIG, 8000) //TODO Has Space in Barn Condition and Large Barn Condition, 4 Limit
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
        Condition IN_SPRING = SeasonCondition.inSeason("spring");
        Condition IN_SUMMER = SeasonCondition.inSeason("summer");
        Condition IN_AUTUMN = SeasonCondition.inSeason("autumn");
        Condition NOT_IN_WINTER = NotCondition.not(SeasonCondition.inSeason("winter"));
        TagKey<Item> SPRING_CROPS = ItemTags.create(HarvestFestival.prefix("spring_crops"));
        TagKey<Item> SUMMER_CROPS = ItemTags.create(HarvestFestival.prefix("summer_crops"));
        TagKey<Item> AUTUMN_CROPS = ItemTags.create(HarvestFestival.prefix("autumn_crops"));

        ShopBuilder.of(HFShops.GENERAL_STORE)
                .vendor(Vendor.entity(SettlementsEntities.NPC.get()))
                .condition(EntityHasNBTTagCondition.entityHasNBTTag("NPC", "harvestfestival:jenni"))
                //General store is open on the usual hours OR on a Wednesday if someone in the town has three hearts with jenni
                .condition(OrCondition.or(OpeningHoursCondition.openingHours()
                                .withHours(DayOfWeek.MONDAY, 9000, 17000)
                                .withHours(DayOfWeek.TUESDAY, 9000, 17000)
                                .withHours(DayOfWeek.THURSDAY, 9000, 17000)
                                .withHours(DayOfWeek.FRIDAY, 9000, 17000)
                                .withHours(DayOfWeek.SATURDAY, 11000, 15000)
                                .build(),
                        AndCondition.and(OpeningHoursCondition.openingHours()
                                        .withHours(DayOfWeek.WEDNESDAY, 8000, 18000)
                                        .build(),
                                CompareCondition.compare(
                                        TeamStatusComparator.status("general_store_wednesday"),
                                        false, true, false, NumberComparator.number(1))
                        ))
                )
                .department(DepartmentBuilder.of(GENERAL_STORE_FARMING, new ItemIcon(HorticultureItems.WATERING_CAN.get().getDefaultInstance()), Component.translatable("department.harvestfestival.general_store_farming"))
                        //Starters
                        .listing(ListingBuilder.of("grass_starter").addSublisting(SublistingBuilder.item(HFItems.GRASS_STARTER.get())).condition(NotCondition.not(SeasonCondition.inSeason("winter"))))
                        //SPRING
                        .itemListing("turnip", seedBagOf("turnip"), 35, IN_SPRING)
                        .itemListing("potato", seedBagOf("potato"), 40, IN_SPRING)
                        .itemListing("cucumber", seedBagOf("cucumber"), 45, IN_SUMMER) //TODO Add Year Two Town Age Condition
                        .itemListing("strawberry", seedBagOf("strawberry"), 90, IN_SPRING) //TODO Add Year Three Town Age Condition
                        .itemListing("cabbage", seedBagOf("cabbage"), 165, IN_SPRING, //VVV If we have shipped more than 1000 spring crops unlock cabbage
                                CompareCondition.compare(ComparatorBuilder.shippedAmount().countTag(SPRING_CROPS).build(), false, true, true, NumberComparator.number(1000)))
                        //SUMMER
                        .itemListing("onion", seedBagOf("onion"), 40, IN_SUMMER)
                        .itemListing("pumpkin", seedBagOf("pumpkin"), 50, IN_SUMMER)
                        .itemListing("melon", seedBagOf("melon"), 30, IN_SUMMER) //TODO: Add requires 10 community points (has fishing pond (+2), mine (+2), cafe (+3), goddess pond (+3), community centre (+5))
                        .itemListing("tomato", seedBagOf("tomato"), 55, IN_SUMMER) //TODO Add Year Two Town Age Condition
                        .itemListing("corn", seedBagOf("corn"), 95, IN_SUMMER) //TODO Add Year Three Town Age Condition
                        .itemListing("pineapple", seedBagOf("pineapple"), 220, IN_SUMMER,  ///VVV If we have shipped more than 1000 summer crops unlock pineapple
                                CompareCondition.compare(ComparatorBuilder.shippedAmount().countTag(SUMMER_CROPS).build(), false, true, true, NumberComparator.number(1000)))
                        //AUTUMN
                        .itemListing("spinach", seedBagOf("spinach"), 40, IN_AUTUMN)
                        .itemListing("carrot", seedBagOf("carrot"), 55, IN_AUTUMN)
                        .itemListing("beetroot", seedBagOf("beetroot"), 60, IN_AUTUMN) //TODO: Add requires 10 community points (has fishing pond (+2), mine (+2), cafe (+3), goddess pond (+3), community centre (+5))
                        .itemListing("eggplant", seedBagOf("eggplant"), 65, IN_AUTUMN) //TODO Add Year Two Town Age Condition
                        .itemListing("green_pepper", seedBagOf("green_pepper"), 100, IN_AUTUMN) //TODO Add Year Three Town Age Condition
                        .itemListing("sweet_potato", seedBagOf("sweet_potato"), 110, IN_AUTUMN, ///VVV If we have shipped more than 1000 autumn crops unlock sweet potato
                                CompareCondition.compare(ComparatorBuilder.shippedAmount().countTag(AUTUMN_CROPS).build(), false, true, true, NumberComparator.number(1000)))
                        //TODO: All Year & Trees & Fertilizers
                        .itemListing("wheat", seedBagOf("wheat"), 15, NOT_IN_WINTER)
                        .itemListing("nether_wart", seedBagOf("nether_wart"), 55, NOT_IN_WINTER,
                                CompareCondition.compare(PlayerStatusComparator.status("visited_nether"), false, true, false, NumberComparator.number(1)))
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
                .department(DepartmentBuilder.of(GENERAL_STORE_TOOLS, new ItemIcon(HorticultureItems.WATERING_CAN.get().getDefaultInstance()), Component.translatable("department.harvestfestival.general_store_tools"))
                                .listing(ListingBuilder.of("hoe").addSublisting(SublistingBuilder.item(HFItems.BASIC_HOE.asItem()).cost(500)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                                .listing(ListingBuilder.of("sickle").addSublisting(SublistingBuilder.item(HusbandryItems.SICKLE.get()).cost(500)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                                .listing(ListingBuilder.of("watering_can").addSublisting(SublistingBuilder.item(HorticultureItems.WATERING_CAN.get()).cost(750)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                                .listing(ListingBuilder.of("axe").addSublisting(SublistingBuilder.item(HFItems.BASIC_AXE.get()).cost(1000)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                                .listing(ListingBuilder.of("hammer").addSublisting(SublistingBuilder.item(HFItems.BASIC_HAMMER.get()).cost(1000)).stockMechanic(ShopaholicStockMechanics.LIMITED_ONE))
                        //TODO: .listing(ListingBuilder.of("shovel").addSublisting(SublistingBuilder.item(HusbandryItems.BASIC_SHOVEL.get()).cost(1000)).stockMechanic(HFStockMechanics.LIMITED_1))

                )
                .department(DepartmentBuilder.of(GENERAL_STORE_GENERAL, new ItemIcon(HorticultureItems.WATERING_CAN.get().getDefaultInstance()), Component.translatable("department.harvestfestival.general_store_general"))
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

    private void buildPoultryFarm(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.POULTRY_FARM)

                .save(map);
    }

    private void buildTackleShop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.TACKLE_SHOP)

                .save(map);
    }

    private void buildWitchingWares(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.WITCHING_WARES)

                .save(map);
    }
}
