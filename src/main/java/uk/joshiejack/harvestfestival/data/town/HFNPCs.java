package uk.joshiejack.harvestfestival.data.town;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.horticulture.world.item.HorticultureItems;
import uk.joshiejack.horticulture.world.item.HorticultureTags;
import uk.joshiejack.husbandry.world.entity.traits.food.EatsRabbitFoodTrait;
import uk.joshiejack.husbandry.world.item.HusbandryItems;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.penguinlib.util.PenguinTags;
import uk.joshiejack.piscary.world.item.PiscaryItems;
import uk.joshiejack.settlements.Settlements;
import uk.joshiejack.settlements.world.entity.npc.Age;
import uk.joshiejack.settlements.world.entity.npc.NPC;
import uk.joshiejack.settlements.world.entity.npc.NPCClass;

import java.util.List;
import java.util.Map;

public class HFNPCs extends AbstractPenguinRegistryProvider<NPC> {
    public HFNPCs(PackOutput output) {
        super(output, Settlements.Registries.NPCS);
    }

    /*
    name,occupation,class,skin,inside_color,outside_color,loot_table,script
harvestfestival:harvest_goddess,goddess,god,,0x8CEED3,0x4EC485,harvestfestival:harvest_goddess,harvestfestival:npcs/harvest_goddess
harvestfestival:yulif,builder,adult,,0x313857,0x121421,none,,,
harvestfestival:jade,villager,adult,,0x653081,0x361840,,,
harvestfestival:johan,villager,adult,,0xFFFFFF,0xC60C30,,,
harvestfestival:jenni,villager,adult,,0xDDD0AD,0xE79043,,,
harvestfestival:candice,villager,adult,,0xF65FAB,0xF21985,,,
harvestfestival:trent,villager,adult,,0xF65FAB,0xF21985,,,
harvestfestival:ashlee,villager,adult,,0xC62D2D,0x571111,,,
harvestfestival:alicia,villager,adult,,0xC62D2D,0x571111,,,
harvestfestival:jim,villager,adult,,0xDE7245,0x722B19,,,
harvestfestival:daniel,villager,adult,,0x613827,0x23150E,,,
harvestfestival:brandon,villager,dwarf,,0xC28D48,0x5F5247,,,
harvestfestival:jakob,villager,adult,,0x7396FF,0x0036D9,,,
harvestfestival:nicholas,villager,adult,,0xDD9C0C,0x292019,,,
harvestfestival:nicole,villager,adult,,0xDD9C0C,0x292019,,,
harvestfestival:nathan,villager,adult,,0xC62D2D,0x571111,,,
harvestfestival:liara,villager,adult,,0xBEC8EE,0x8091D0,,,
harvestfestival:katlin,villager,adult,,0xDDDDDD,0x777777,,,
harvestfestival:tiberius,villager,adult,,0x305A2E,0x142419,,,
harvestfestival:lily,villager,adult,,0xDFE0E2,0xBFC297,,,
harvestfestival:liam,villager,adult,,0x228C00,0x003F00,,,
harvestfestival:fenn,villager,child,,0x228C00,0x003F00,,,
harvestfestival:claudius,villager,adult,,0x663300,0x351B00,,,
harvestfestival:jared,villager,adult,,0x939594,0x4F4F51,,,
harvestfestival:damian,villager,adult,,0x939594,0x4F4F51,,,
harvestfestival:luke,villager,adult,,0x939594,0x4F4F51,,,
harvestfestival:aria,villager,child,,0xFFFFFF,0x73FFFF,,,
harvestfestival:tomas,villager,adult,,0x006666,0x00B2B20,,,
harvestfestival:jeimmi,villager,adult,,0xA8AC9A,0x3B636D,,,
harvestfestival:benjii,villager,adult,,0xFFFF99,0xB2B200,,,
harvestfestival:cloe,villager,adult,,0xFFFF99,0xB2B200,,,
harvestfestival:abii,villager,child,,0xFF99FF,0xFF20FF,,,
harvestfestival:zephyr,villager,adult,,0x300040,0x000000,,,

     */

    private NPC.ItemData awesome(TagKey<Item> tag) {
        return new NPC.ItemData(Ingredient.of(tag), HFGiftQualities.AWESOME.value());
    }

    private NPC.ItemData good(TagKey<Item> tag) {
        return new NPC.ItemData(Ingredient.of(tag), HFGiftQualities.GOOD.value());
    }

    private NPC.ItemData decent(TagKey<Item> tag) {
        return new NPC.ItemData(Ingredient.of(tag), HFGiftQualities.DECENT.value());
    }

    private NPC.ItemData dislike(TagKey<Item> tag) {
        return new NPC.ItemData(Ingredient.of(tag), HFGiftQualities.DISLIKE.value());
    }

    private NPC.ItemData bad(TagKey<Item> tag) {
        return new NPC.ItemData(Ingredient.of(tag), HFGiftQualities.BAD.value());
    }

    private NPC.ItemData terrible(TagKey<Item> tag) {
        return new NPC.ItemData(Ingredient.of(tag), HFGiftQualities.TERRIBLE.value());
    }

    private NPC.ItemData awesome(ItemLike item) {
        return new NPC.ItemData(Ingredient.of(item), HFGiftQualities.AWESOME.value());
    }

    private NPC.ItemData good(ItemLike item) {
        return new NPC.ItemData(Ingredient.of(item), HFGiftQualities.GOOD.value());
    }

    private NPC.ItemData decent(ItemLike item) {
        return new NPC.ItemData(Ingredient.of(item), HFGiftQualities.DECENT.value());
    }

    private NPC.ItemData dislike(ItemLike item) {
        return new NPC.ItemData(Ingredient.of(item), HFGiftQualities.DISLIKE.value());
    }

    private NPC.ItemData bad(ItemLike item) {
        return new NPC.ItemData(Ingredient.of(item), HFGiftQualities.BAD.value());
    }

    private NPC.ItemData terrible(ItemLike item) {
        return new NPC.ItemData(Ingredient.of(item), HFGiftQualities.TERRIBLE.value());
    }

    private NPC.CategoryData awesome(String category) {
        return new NPC.CategoryData(category, HFGiftQualities.AWESOME.value());
    }

    private NPC.CategoryData good(String category) {
        return new NPC.CategoryData(category, HFGiftQualities.GOOD.value());
    }

    private NPC.CategoryData decent(String category) {
        return new NPC.CategoryData(category, HFGiftQualities.DECENT.value());
    }

    private NPC.CategoryData dislike(String category) {
        return new NPC.CategoryData(category, HFGiftQualities.DISLIKE.value());
    }

    private NPC.CategoryData bad(String category) {
        return new NPC.CategoryData(category, HFGiftQualities.BAD.value());
    }

    private NPC.CategoryData terrible(String category) {
        return new NPC.CategoryData(category, HFGiftQualities.TERRIBLE.value());
    }


    @Override
    protected void buildRegistry(Map<ResourceLocation, NPC> map) {
        NPCClass goddess = new NPCClass(Age.ADULT, false, 1.25F, 0.25F, true, true, true, true, false, 400, true);
        NPCClass dwarf = new NPCClass(Age.ADULT, false, 0.75F, 05F, false, false, true, false, true, 0, true);
        NPCClass adult = NPCClass.ADULT;
        NPCClass child = NPCClass.CHILD;

        //Harvest Goddess
        map.put(HarvestFestival.prefix("harvest_goddess"), new NPC(
                        HarvestFestival.prefix("harvest_goddess"), //Loot Table
                        HarvestFestival.prefix("npcs/harvest_goddess"), //Custom Script
                        null,   //Custom Texture
                        "goddess", //Occupation
                        goddess, //Class
                        "#8CEED3", //Inside Color
                        "#4EC485", //Outside Color
                        List.of(awesome(HorticultureTags.STRAWBERRY),
                                good(HorticultureTags.PINEAPPLE),
                                awesome(HFItems.STRAWBERRY_JAM),
                                good(HorticultureItems.STRAWBERRY_MILK),
                                bad(Items.RABBIT_FOOT),
                                //TODO: WINE @Gastronomy
                                terrible(PiscaryItems.FISH_BONES),
                                terrible(PiscaryItems.EMPTY_CAN),
                                terrible(PiscaryItems.OLD_BOOT)
                        ), //Item Gift Overrides
                        List.of(decent("flower"),
                                decent("plant"),
                                dislike("mushroom"),
                                dislike("cooking"),
                                dislike("knowledge"),
                                dislike("gem"),
                                bad("mineral"),
                                bad("meat"),
                                bad("fish"),
                                bad("art"),
                                bad("money")
                        ) //Category Gift Overrides
                )
        );

        //Yulif
        map.put(HarvestFestival.prefix("yulif"), new NPC(
                null, null, null, "builder", adult, "#313857", "#121421",
                List.of(awesome(Items.MELON),
                        awesome(PenguinTags.CROPS_MELON),
                        good(Tags.Items.GEMS_QUARTZ),
                        good(HorticultureTags.CORN),
                        good(HorticultureTags.PINEAPPLE),
                        decent(HorticultureItems.SALAD),
                        decent(HorticultureTags.GREEN_PEPPER),
                        dislike(HorticultureItems.PICKLED_TURNIP),
                        dislike(HorticultureItems.BOILED_SPINACH),
                        dislike(HorticultureItems.BAKED_CORN),
                        dislike(HorticultureItems.TOMATO_JUICE),
                        //TODO: Gastronomy Vegetable Juice
                        //TODO: Gastronomy Vegetable Latte
                        //TODO: Gastronomy Mix Juice
                        terrible(Tags.Items.CROPS_CARROT),
                        terrible(Tags.Items.CROPS_POTATO),
                        terrible(Tags.Items.CROPS_BEETROOT)
                ),
                List.of(good("building"),
                        decent("flower"),
                        decent("mineral"),
                        dislike("monster"),
                        bad("vegetable"),
                        bad("plant")
                )
        ));

        //Jade
        map.put(HarvestFestival.prefix("jade"), new NPC(
                null, null, null, "villager", adult, "#653081", "#361840",
                List.of(//TODO: Grapes Tag new NPC.ItemData(Ingredient.of(HorticultureTags.GRAPE), HFGiftQualities.AWESOME.value()),
                        awesome(Items.ALLIUM),
                        //TODO: Gastronomy Lavender (Awesome)
                        awesome(Tags.Items.GEMS_AMETHYST),
                        good(Items.RABBIT_FOOT),
                        good(Items.FLOWER_POT),
                        terrible(Tags.Items.STONE),
                        terrible(ItemTags.LOGS)
                ),
                List.of(good("flower"),
                        good("plant"),
                        good("herb"),
                        decent("money"),
                        bad("mineral"))));

        //Johan
        map.put(HarvestFestival.prefix("johan"), new NPC(
                        null, null, null, "villager", adult, "#FFFFFF", "#C60C30",
                        List.of(awesome(Tags.Items.CROPS_POTATO),
                                awesome(Items.BAKED_POTATO),
                                //TODO Gastro Fries? awesome(HorticultureItems.FRIES),
                                good(HorticultureTags.TOMATO),
                                //good(ItemTags.WOOL),
                                //decent(Items.ALLIUM),
                                //dislike(Items.EGG),
                                dislike(HusbandryItems.BOILED_EGG),
                                //TODO dislike(GastronomyItems.SCRAMBLED_EGG),
                                //TODO dislike(HorticultureItems.EGG_OVER_RICE),
                                //TODO: ? dislike(HusbandryItems.MAYONNAISE),
                                //bad(HorticultureItems.CHOCOLATE),
                                //bad(HorticultureItems.CHOCOLATE_CAKE),
                                //bad(HorticultureItems.CHOCOLATE_COOKIES),
                                //bad(HorticultureItems.HOT_CHOCOLATE),
                                bad(HusbandryItems.HOT_MILK),
                                bad(HorticultureItems.STRAWBERRY_MILK),
                                terrible(PenguinTags.CROPS_PUMPKIN),
                                terrible(Items.PUMPKIN_PIE),
                                terrible(HorticultureItems.PUMPKIN_STEW)
                        ),
                        List.of(good("wool"),
                                decent("flower"),
                                dislike("egg"),
                                bad("milk"))
                )
        );

        //Jenni
        map.put(HarvestFestival.prefix("jenni"), new NPC(
                null, null, null, "villager", adult, "#DDD0AD", "#E79043",
                List.of(awesome(Items.CARROT_ON_A_STICK),
                        awesome(Tags.Items.CROPS_CARROT),
                        awesome(Items.GOLDEN_CARROT),
                        good(HorticultureTags.ORANGE),
                        good(HorticultureTags.PEACH),
                        good(Items.RABBIT_FOOT),
                        good(ItemTags.WOOL),
                        terrible(Items.FLINT),
                        terrible(Tags.Items.INGOTS_IRON),
                        terrible(Tags.Items.INGOTS_GOLD)
                ),
                List.of(good("art"),
                        good("wool"),
                        good("vegetable"),
                        decent("money"),
                        dislike("junk"),
                        dislike("building"),
                        bad("mineral")
                ))
        );

        //Candice
        map.put(HarvestFestival.prefix("candice"), new NPC(
                null, null, null, "villager", adult, "#F65FAB", "#F21985",
                List.of(awesome(Items.MILK_BUCKET),
                        decent(HorticultureTags.STRAWBERRY),
                        decent(HorticultureTags.TOMATO),
                        decent(HFItems.JAM_BUN),
                        //TODO GASTRO LAVENDER decent(HorticultureTags.LAVENDER),
                        //TODO GASTRO APPLE_PIE decent(Items.APPLE_PIE),
                        //TODO GASTRO SOUFFLE decent(Items.APPLE_PIE),
                        terrible(Items.GOLDEN_APPLE),
                        terrible(PenguinTags.CROPS_APPLE),
                        terrible(PenguinTags.CROPS_MELON)
                ),
                List.of(good("milk"),
                        decent("flower"),
                        bad("fruit"))));

        //Trent
        map.put(HarvestFestival.prefix("trent"), new NPC(
                null, null, null, "villager", adult, "#F65FAB", "#F21985",
                List.of(awesome(Items.SUNFLOWER),
                        awesome(HFItems.CHEESE),
                        good(Items.MILK_BUCKET),
                        good(Items.COOKED_MUTTON),
                        good(Items.COOKED_PORKCHOP),
                        good(Items.COOKED_BEEF),
                        good(HFTags.Items.TOPAZ),
                        dislike(ItemTags.BOOKSHELF_BOOKS),
                        decent(HFTags.Items.AGATE),
                        bad(Tags.Items.GEMS_EMERALD),
                        bad(HFTags.Items.FLUORITE),
                        bad(HFTags.Items.JADE),
                        bad(HFTags.Items.PERIDOT),
                        bad(HFTags.Items.SAPPHIRE),
                        terrible(Tags.Items.SLIMEBALLS),
                        terrible(HFTags.Items.ADAMANTITE),
                        terrible(PiscaryItems.PIRATE_TREASURE)
                ),
                List.of(good("milk"),
                        decent("monster"),
                        dislike("gem"),
                        bad("money")
                )));

        //Ashlee
        map.put(HarvestFestival.prefix("ashlee"), new NPC(
                null, null, null, "villager", adult, "#C62D2D", "#571111",
                List.of(awesome(HorticultureTags.BANANA),
                        awesome(HorticultureTags.CORN),
                        awesome(HorticultureItems.BAKED_CORN),
                        decent(HusbandryItems.BIRD_FEED),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.KETCHUP), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.FRIES), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.DOUGHNUT), HFGiftQualities.BAD.value()),
                        //TODO GASTRO  new NPC.ItemData(Ingredient.of(HorticultureItems.POPCORN), HFGiftQualities.BAD.value()),
                        bad(HusbandryItems.ICE_CREAM),
                        bad(EatsRabbitFoodTrait.RABBIT_FOOD),
                        terrible(Items.BEEF),
                        terrible(Items.PORKCHOP),
                        terrible(Items.RABBIT),
                        terrible(Items.MUTTON)
                ),
                List.of(good("egg"),
                        good("fruit"),
                        bad("meat"))));

        //Alicia
        //When a list starts with cat# it means it is a category, if it is in camel case, it is an itemtag e.g. gemMoonStone > HFTags.Items.MOONSTONE (using vanilla and forge tags when available)
        map.put(HarvestFestival.prefix("alicia"), new NPC(
                null, null, null, "villager", adult, "#C62D2D", "#571111",
                List.of(awesome(HFTags.Items.MOONSTONE),
                        awesome(HFBlocks.RED_MAGIC_FLOWER.asItem()),
                        awesome(HFBlocks.BLUE_MAGIC_FLOWER.asItem()),
                        good(Items.BONE),
                        good(PiscaryItems.FISH_BONES),
                        dislike(HorticultureTags.GREEN_PEPPER),
                        dislike(HorticultureTags.CORN),
                        dislike(HorticultureItems.ONION),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.POPCORN), HFGiftQualities.DISLIKE.value()),
                        decent(HorticultureItems.CORNFLAKES),
                        decent(HorticultureItems.BAKED_CORN),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.DORIA), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.NOODLES), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.CURRY_NOODLES), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.TEMPURA_NOODLES), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.THICK_FRIED_NOODLES), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.PORRIDGE), HFGiftQualities.BAD.value()),
                        terrible(HFBlocks.MOONDROP_FLOWER.asItem()),
                        terrible(HFBlocks.TOY_FLOWER.asItem()),
                        terrible(HFBlocks.PINK_CAT_FLOWER.asItem())
                ),
                List.of(awesome("magic"),
                        awesome("herb"),
                        awesome("mineral"),
                        decent("monster"),
                        decent("cooking"),
                        bad("money"),
                        bad("flower"))));

        //Jim
        map.put(HarvestFestival.prefix("jim"), new NPC(
                null, null, null, "villager", adult, "#DE7245", "#722B19",
                List.of(awesome(Items.SADDLE),
                        awesome(Items.LEAD),
                        awesome(Items.NAME_TAG),
                        good(Items.RABBIT_FOOT),
                        good(Tags.Items.CROPS_WHEAT),
                        decent(HusbandryItems.BOILED_EGG),
                        //TODO: GASTROnew NPC.ItemData(Ingredient.of(HusbandryItems.SCRAMBLED_EGG), HFGiftQualities.DECENT.value()),
                        //TODO: GASTRO new NPC.ItemData(Ingredient.of(HusbandryItems.EGG_OVER_RICE), HFGiftQualities.DECENT.value()),
                        dislike(HFItems.MAYONNAISE),
                        terrible(HFTags.Items.ALEXANDRITE),
                        terrible(HFTags.Items.PINK_DIAMOND),
                        terrible(Tags.Items.GEMS_DIAMOND),
                        terrible(Tags.Items.GEMS_EMERALD)
                ),
                List.of(good("milk"),
                        good("wool"),
                        good("meat"),
                        decent("junk"),
                        dislike("money"),
                        dislike("magic"),
                        dislike("egg"),
                        bad("fish"),
                        bad("gem")
                )));

        //Daniel
        map.put(HarvestFestival.prefix("daniel"), new NPC(
                null, null, null, "villager", adult, "#613827", "#23150E",
                List.of(awesome(Items.ENDER_PEARL),
                        awesome(Items.ENDER_EYE),
                        awesome(Items.CHORUS_FRUIT),
                        awesome(Items.DRAGON_BREATH),
                        awesome(Items.DRAGON_EGG),
                        //TODO: GASTRO new NPC.ItemData(Ingredient.of(HusbandryItems.GRILLED_FISH), HFGiftQualities.TERRIBLE.value()),
//                        //TODO: GASTRO new NPC.ItemData(Ingredient.of(HusbandryItems.FISHSTICKS), HFGiftQualities.TERRIBLE.value()),
                        terrible(Items.COOKED_COD),
                        terrible(Items.COOKED_SALMON),
                        terrible(PiscaryItems.FISH_STEW)
                ),
                List.of(awesome("mineral"),
                        decent("monster"),
                        dislike("magic"),
                        bad("fish")
                )));
        //Brandon
        map.put(HarvestFestival.prefix("brandon"), new NPC(
                null, null, null, "villager", dwarf, "#C28D48", "#5F5247",
                List.of(awesome(HFTags.Items.INGOTS_MYSTRIL),
                        awesome(HFTags.Items.MYTHIC_STONE),
                        //TODO: awesome(HorticultureItems.FRIED_RICE),
                        good(Items.TORCH),
                        good(Items.TORCHFLOWER),
                        good(Items.LADDER),
                        //TODO: decent(HorticultureItems.RICEBALL),
                        decent(HorticultureItems.PINEAPPLE_JUICE),
                        //TODO: decent(HorticultureItems.GRAPE_JUICE),
                        decent(HorticultureItems.PEACH_JUICE),
                        decent(HorticultureItems.BANANA_JUICE),
                        decent(HorticultureItems.ORANGE_JUICE),
                        decent(HorticultureItems.APPLE_JUICE),
                        //TODO: decent(HorticultureItems.FRUIT_JUICE),
                        //TODO: decent(HorticultureItems.FRUIT_SANDWICH),
                        decent(HorticultureItems.PICKLED_CUCUMBER),
                        //TODO: decent(HorticultureItems.PANCAKE),
                        terrible(Items.NETHER_STAR),
                        //TODO: terrible(HorticultureItems.WINE),
                        terrible(Items.DANDELION),
                        terrible(ItemTags.SAPLINGS)
                ),
                List.of(awesome("mineral"),
                        awesome("mushroom"),
                        dislike("art"),
                        dislike("plant"),
                        dislike("flower"),
                        dislike("fruit"),
                        bad("fish")
                )));

        //Jakob
        map.put(HarvestFestival.prefix("jakob"), new NPC(
                null, null, null, "villager", adult, "#7396FF", "#0036D9",
                List.of(awesome(PiscaryItems.MANTA_RAY),
                        awesome(PiscaryItems.ELECTRIC_RAY),
                        awesome(PiscaryItems.STINGRAY),
                        good(Tags.Items.GEMS_PRISMARINE),
                        good(Tags.Items.DUSTS_PRISMARINE),
                        //TODO dislike(HorticultureItems.SANDWICH),
                        //TODO dislike(HorticultureItems.HERB_SANDWICH),
                        //TODO  dislike(HorticultureItems.FRUIT_SANDWICH),
                        bad(HorticultureItems.HAPPY_EGGPLANT),
                        bad(HorticultureTags.EGGPLANT),
                        terrible(HFItems.ORICHALC),
                        terrible(HFTags.Items.MOONSTONE)
                ),
                List.of(good("fish"),
                        decent("flower"),
                        dislike("knowledge"),
                        bad("mineral"),
                        terrible("junk")
                )));

        //Nicholas
        map.put(HarvestFestival.prefix("nicholas"), new NPC(
                null, null, null, "villager", adult, "#DD9C0C", "#292019",
                List.of(awesome(PenguinTags.CROPS_APPLE),
                        awesome(Items.GOLDEN_APPLE),
                        awesome(Items.HONEY_BOTTLE),
                        awesome(Items.HONEYCOMB),
                        decent(HusbandryItems.BOILED_EGG),
                        //TODO decent(HusbandryItems.SCRAMBLED_EGG),
                        bad(Items.PUFFERFISH),
                        bad(HFItems.MAYONNAISE),
                        terrible(Items.SKELETON_SKULL),
                        terrible(Items.BONE)
                ),
                List.of(good("herb"),
                        good("fruit"),
                        good("vegetable"),
                        dislike("egg")
                )));

        //Nicole
        map.put(HarvestFestival.prefix("nicole"), new NPC(
                null, null, null, "villager", adult, "#DD9C0C", "#292019",
                List.of(awesome(Items.MUSHROOM_STEW),
                        //Gastro Matsutake awesome(HFItems.NATURE_SALAD),
                        good(Tags.Items.DUSTS_REDSTONE),
                        //TODO dislike(HorticultureItems.APPLE_PIE),
                        //TODO dislike(HorticultureItems.APPLE_SOUFFLE),
                        //TODO dislike(HorticultureItems.APPLE_JUICE),
                        dislike(HFItems.APPLE_JAM),
                        bad(PenguinTags.CROPS_APPLE),
                        bad(Items.GOLDEN_APPLE),
                        terrible(Items.CHICKEN),
                        terrible(Items.BEETROOT_SOUP),
                        terrible(Tags.Items.CROPS_BEETROOT)
                ),
                List.of(good("mushroom"),
                        good("herb"),
                        good("vegetable"),
                        good("knowledge"),
                        good("fruit"),
                        decent("plant"),
                        decent("machine"),
                        decent("building"),
                        bad("meat")
                )));
        
    }
}
