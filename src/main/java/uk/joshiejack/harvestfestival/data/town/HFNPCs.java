package uk.joshiejack.harvestfestival.data.town;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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
                        List.of(new NPC.ItemData(Ingredient.of(HorticultureTags.STRAWBERRY), HFGiftQualities.AWESOME.value()),
                                new NPC.ItemData(Ingredient.of(HorticultureTags.PINEAPPLE), HFGiftQualities.GOOD.value()),
                                new NPC.ItemData(Ingredient.of(HFItems.STRAWBERRY_JAM), HFGiftQualities.DECENT.value()),
                                new NPC.ItemData(Ingredient.of(HorticultureItems.STRAWBERRY_MILK), HFGiftQualities.DECENT.value()),
                                new NPC.ItemData(Ingredient.of(Items.RABBIT_FOOT), HFGiftQualities.BAD.value()),
                                //TODO: WINE @Gastronomy
                                new NPC.ItemData(Ingredient.of(PiscaryItems.FISH_BONES), HFGiftQualities.TERRIBLE.value()),
                                new NPC.ItemData(Ingredient.of(PiscaryItems.EMPTY_CAN), HFGiftQualities.TERRIBLE.value()),
                                new NPC.ItemData(Ingredient.of(PiscaryItems.OLD_BOOT), HFGiftQualities.TERRIBLE.value())
                        ), //Item Gift Overrides
                        List.of(new NPC.CategoryData("flower", HFGiftQualities.DECENT.value()),
                                new NPC.CategoryData("plant", HFGiftQualities.DECENT.value()),
                                new NPC.CategoryData("mushroom", HFGiftQualities.DISLIKE.value()),
                                new NPC.CategoryData("cooking", HFGiftQualities.DISLIKE.value()),
                                new NPC.CategoryData("knowledge", HFGiftQualities.DISLIKE.value()),
                                new NPC.CategoryData("gem", HFGiftQualities.DISLIKE.value()),
                                new NPC.CategoryData("mineral", HFGiftQualities.BAD.value()),
                                new NPC.CategoryData("meat", HFGiftQualities.BAD.value()),
                                new NPC.CategoryData("fish", HFGiftQualities.BAD.value()),
                                new NPC.CategoryData("art", HFGiftQualities.BAD.value()),
                                new NPC.CategoryData("money", HFGiftQualities.BAD.value())
                        ) //Category Gift Overrides
                )
        );

        //Yulif
        map.put(HarvestFestival.prefix("yulif"), new NPC(
                null, null, null, "builder", adult, "#313857", "#121421",
                List.of(new NPC.ItemData(Ingredient.of(Items.MELON), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(PenguinTags.CROPS_MELON), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.GEMS_QUARTZ), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.CORN), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.PINEAPPLE), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.SALAD), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.GREEN_PEPPER), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.PICKLED_TURNIP), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.BOILED_SPINACH), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.BAKED_CORN), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.TOMATO_JUICE), HFGiftQualities.DISLIKE.value()),
                        //TODO: Gastronomy Vegetable Juice
                        //TODO: Gastronomy Vegetable Latte
                        //TODO: Gastronomy Mix Juice
                        new NPC.ItemData(Ingredient.of(Tags.Items.CROPS_CARROT), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.CROPS_POTATO), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.CROPS_BEETROOT), HFGiftQualities.TERRIBLE.value())
                ),
                List.of(new NPC.CategoryData("building", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("flower", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("mineral", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("monster", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("vegetable", HFGiftQualities.BAD.value()),
                        new NPC.CategoryData("plant", HFGiftQualities.BAD.value())
                )
        ));

        //Jade
        map.put(HarvestFestival.prefix("jade"), new NPC(
                null, null, null, "villager", adult, "#653081", "#361840",
                List.of(//TODO: Grapes Tag new NPC.ItemData(Ingredient.of(HorticultureTags.GRAPE), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.ALLIUM), HFGiftQualities.AWESOME.value()),
                        //TODO: Gastronomy Lavender (Awesome)
                        new NPC.ItemData(Ingredient.of(Tags.Items.GEMS_AMETHYST), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.RABBIT_FOOT), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.FLOWER_POT), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.STONE), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(ItemTags.LOGS), HFGiftQualities.TERRIBLE.value())
                ),
                List.of(new NPC.CategoryData("flower", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("plant", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("herb", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("money", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("mineral", HFGiftQualities.BAD.value()))));

        //Johan
        map.put(HarvestFestival.prefix("johan"), new NPC(
                        null, null, null, "villager", adult, "#FFFFFF", "#C60C30",
                        List.of(new NPC.ItemData(Ingredient.of(Tags.Items.CROPS_POTATO), HFGiftQualities.AWESOME.value()),
                                new NPC.ItemData(Ingredient.of(Items.BAKED_POTATO), HFGiftQualities.AWESOME.value()),
                                //TODO Gastro Fries? new NPC.ItemData(Ingredient.of(HorticultureItems.FRIES), HFGiftQualities.AWESOME.value()),
                                new NPC.ItemData(Ingredient.of(HorticultureTags.TOMATO), HFGiftQualities.GOOD.value()),
                                //new NPC.ItemData(Ingredient.of(ItemTags.WOOL), HFGiftQualities.GOOD.value()),
                                //new NPC.ItemData(Ingredient.of(Items.ALLIUM), HFGiftQualities.DECENT.value()),
                                //new NPC.ItemData(Ingredient.of(Items.EGG), HFGiftQualities.DISLIKE.value()),
                                new NPC.ItemData(Ingredient.of(HusbandryItems.BOILED_EGG), HFGiftQualities.DISLIKE.value()),
                                //TODO new NPC.ItemData(Ingredient.of(GastronomyItems.SCRAMBLED_EGG), HFGiftQualities.DISLIKE.value()),
                                //TODO new NPC.ItemData(Ingredient.of(HorticultureItems.EGG_OVER_RICE), HFGiftQualities.DISLIKE.value()),
                                //TODO: ? new NPC.ItemData(Ingredient.of(HusbandryItems.MAYONNAISE), HFGiftQualities.DISLIKE.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.CHOCOLATE), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.CHOCOLATE_CAKE), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.CHOCOLATE_COOKIES), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.HOT_CHOCOLATE), HFGiftQualities.BAD.value()),
                                new NPC.ItemData(Ingredient.of(HusbandryItems.HOT_MILK), HFGiftQualities.BAD.value()),
                                new NPC.ItemData(Ingredient.of(HorticultureItems.STRAWBERRY_MILK), HFGiftQualities.BAD.value()),
                                new NPC.ItemData(Ingredient.of(PenguinTags.CROPS_PUMPKIN), HFGiftQualities.TERRIBLE.value()),
                                new NPC.ItemData(Ingredient.of(Items.PUMPKIN_PIE), HFGiftQualities.TERRIBLE.value()),
                                new NPC.ItemData(Ingredient.of(HorticultureItems.PUMPKIN_STEW), HFGiftQualities.TERRIBLE.value())
                        ),
                        List.of(new NPC.CategoryData("wool", HFGiftQualities.GOOD.value()),
                                new NPC.CategoryData("flower", HFGiftQualities.DECENT.value()),
                                new NPC.CategoryData("egg", HFGiftQualities.DISLIKE.value()),
                                new NPC.CategoryData("milk", HFGiftQualities.BAD.value()))
                )
        );

        //Jenni
        map.put(HarvestFestival.prefix("jenni"), new NPC(
                null, null, null, "villager", adult, "#DDD0AD", "#E79043",
                List.of(new NPC.ItemData(Ingredient.of(Items.CARROT_ON_A_STICK), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.CROPS_CARROT), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.GOLDEN_CARROT), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.ORANGE), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.PEACH), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.RABBIT_FOOT), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(ItemTags.WOOL), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.FLINT), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.INGOTS_IRON), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.INGOTS_GOLD), HFGiftQualities.TERRIBLE.value())
                ),
                List.of(new NPC.CategoryData("art", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("wool", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("vegetable", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("money", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("junk", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("building", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("mineral", HFGiftQualities.BAD.value()))
        ));

        //Candice
        map.put(HarvestFestival.prefix("candice"), new NPC(
                null, null, null, "villager", adult, "#F65FAB", "#F21985",
                List.of(new NPC.ItemData(Ingredient.of(Items.MILK_BUCKET), HFGiftQualities.AWESOME.value()),
//  TODO: SIZED?                      new NPC.ItemData(Ingredient.of(HorticultureItems.MILK), HFGiftQualities.AWESOME.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.MILK), HFGiftQualities.AWESOME.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.MILK), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.STRAWBERRY), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.TOMATO), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HFItems.JAM_BUN), HFGiftQualities.DECENT.value()),
                        //TODO GASTRO LAVENDER new NPC.ItemData(Ingredient.of(HorticultureTags.LAVENDER), HFGiftQualities.DISLIKE.value()),
                        //TODO GASTRO  APPLE PIE new NPC.ItemData(Ingredient.of(HorticultureItems.APPLE_JAM), HFGiftQualities.BAD.value()),
                        //TODO: GASTRO SOUFFLE new NPC.ItemData(Ingredient.of(Items.APPLE_PIE), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(Items.GOLDEN_APPLE), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(PenguinTags.CROPS_APPLE), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(PenguinTags.CROPS_MELON), HFGiftQualities.TERRIBLE.value())
                ),
                List.of(new NPC.CategoryData("milk", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("flower", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("fruit", HFGiftQualities.BAD.value()))));

        //Trent
        map.put(HarvestFestival.prefix("trent"), new NPC(
                null, null, null, "villager", adult, "#F65FAB", "#F21985",
                List.of(new NPC.ItemData(Ingredient.of(Items.SUNFLOWER), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HFItems.CHEESE), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.MILK_BUCKET), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.COOKED_MUTTON), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.COOKED_PORKCHOP), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.COOKED_BEEF), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.TOPAZ), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(ItemTags.BOOKSHELF_BOOKS), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.AGATE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.GEMS_EMERALD), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.FLUORITE), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.JADE), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.PERIDOT), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.SAPPHIRE), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.SLIMEBALLS), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.ADAMANTITE), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(PiscaryItems.PIRATE_TREASURE), HFGiftQualities.TERRIBLE.value())),
                List.of(new NPC.CategoryData("milk", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("monster", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("gem", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("money", HFGiftQualities.BAD.value())
                )));

        //Ashlee
        map.put(HarvestFestival.prefix("ashlee"), new NPC(
                null, null, null, "villager", adult, "#C62D2D", "#571111",
                List.of(new NPC.ItemData(Ingredient.of(HorticultureTags.BANANA), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.CORN), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.BAKED_CORN), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HusbandryItems.BIRD_FEED), HFGiftQualities.DECENT.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.KETCHUP), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.FRIES), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.DOUGHNUT), HFGiftQualities.BAD.value()),
                        //TODO GASTRO  new NPC.ItemData(Ingredient.of(HorticultureItems.POPCORN), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(HusbandryItems.ICE_CREAM), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(EatsRabbitFoodTrait.RABBIT_FOOD), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(Items.BEEF), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Items.PORKCHOP), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Items.RABBIT), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Items.MUTTON), HFGiftQualities.TERRIBLE.value())
                ),
                List.of(new NPC.CategoryData("egg", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("fruit", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("meat", HFGiftQualities.BAD.value()))));

        //Alicia
        //When a list starts with cat# it means it is a category, if it is in camel case, it is an itemtag e.g. gemMoonStone > HFTags.Items.MOONSTONE (using vanilla and forge tags when available)
        map.put(HarvestFestival.prefix("alicia"), new NPC(
                null, null, null, "villager", adult, "#C62D2D", "#571111",
                List.of(new NPC.ItemData(Ingredient.of(HFTags.Items.MOONSTONE), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HFBlocks.RED_MAGIC_FLOWER.asItem()), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HFBlocks.BLUE_MAGIC_FLOWER.asItem()), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.BONE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(PiscaryItems.FISH_BONES), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.GREEN_PEPPER), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.CORN), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.ONION), HFGiftQualities.DISLIKE.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.POPCORN), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.CORNFLAKES), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.BAKED_CORN), HFGiftQualities.DISLIKE.value()),
//TODO GASTRO
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.DORIA), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.NOODLES), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.CURRY_NOODLES), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.TEMPURA_NOODLES), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.THICK_FRIED_NOODLES), HFGiftQualities.BAD.value()),
//                        new NPC.ItemData(Ingredient.of(HorticultureItems.PORRIDGE), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(HFBlocks.MOONDROP_FLOWER.asItem()), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(HFBlocks.TOY_FLOWER.asItem()), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(HFBlocks.PINK_CAT_FLOWER.asItem()), HFGiftQualities.TERRIBLE.value())),
                List.of(new NPC.CategoryData("magic", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("herb", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("mineral", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("monster", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("cooking", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("money", HFGiftQualities.BAD.value()),
                        new NPC.CategoryData("flower", HFGiftQualities.BAD.value()))));

        //Jim
        map.put(HarvestFestival.prefix("jim"), new NPC(
                null, null, null, "villager", adult, "#DE7245", "#722B19",
                List.of(new NPC.ItemData(Ingredient.of(Items.SADDLE), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.LEAD), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.NAME_TAG), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.RABBIT_FOOT), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.CROPS_WHEAT), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(HusbandryItems.BOILED_EGG), HFGiftQualities.DECENT.value()),
                        //TODO: GASTROnew NPC.ItemData(Ingredient.of(HusbandryItems.SCRAMBLED_EGG), HFGiftQualities.DECENT.value()),
                        //TODO: GASTRO new NPC.ItemData(Ingredient.of(HusbandryItems.EGG_OVER_RICE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HFItems.MAYONNAISE), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.ALEXANDRITE), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.PINK_DIAMOND), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.GEMS_DIAMOND), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Tags.Items.GEMS_EMERALD), HFGiftQualities.TERRIBLE.value())
                ),
                List.of(new NPC.CategoryData("milk", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("wool", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("meat", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("junk", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("money", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("magic", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("egg", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("fish", HFGiftQualities.BAD.value()),
                        new NPC.CategoryData("gem", HFGiftQualities.BAD.value())
                )));

        //Daniel
        map.put(HarvestFestival.prefix("daniel"), new NPC(
                null, null, null, "villager", adult, "#613827", "#23150E",
                List.of(new NPC.ItemData(Ingredient.of(Items.ENDER_PEARL), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.ENDER_EYE), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.CHORUS_FRUIT), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.DRAGON_BREATH), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.DRAGON_EGG), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.COOKED_COD), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(Items.COOKED_SALMON), HFGiftQualities.TERRIBLE.value()),
                        //TODO: GASTRO new NPC.ItemData(Ingredient.of(HusbandryItems.GRILLED_FISH), HFGiftQualities.TERRIBLE.value()),
                        //TODO: GASTRO new NPC.ItemData(Ingredient.of(HusbandryItems.FISHSTICKS), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(PiscaryItems.FISH_STEW), HFGiftQualities.TERRIBLE.value())),
                List.of(new NPC.CategoryData("mineral", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("monster", HFGiftQualities.DECENT.value()),
                        new NPC.CategoryData("magic", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("fish", HFGiftQualities.BAD.value())
                )));

        //Brandon
        map.put(HarvestFestival.prefix("brandon"), new NPC(
                null, null, null, "villager", dwarf, "#C28D48", "#5F5247",
                List.of(new NPC.ItemData(Ingredient.of(HFTags.Items.INGOTS_MYSTRIL), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HFTags.Items.MYTHIC_STONE), HFGiftQualities.AWESOME.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.FRIED_RICE), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(Items.TORCH), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.TORCHFLOWER), HFGiftQualities.GOOD.value()),
                        new NPC.ItemData(Ingredient.of(Items.LADDER), HFGiftQualities.GOOD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.RICEBALL), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.PINEAPPLE_JUICE), HFGiftQualities.DECENT.value()),
                        //TODO new NPC.ItemData(Ingredient.of(HorticultureItems.GRAPE_JUICE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.PEACH_JUICE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.BANANA_JUICE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.ORANGE_JUICE), HFGiftQualities.DECENT.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.APPLE_JUICE), HFGiftQualities.DECENT.value()),
                        //TODO GASTRO  new NPC.ItemData(Ingredient.of(HorticultureItems.FRUIT_JUICE), HFGiftQualities.DECENT.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.FRUIT_SANDWICH), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureItems.PICKLED_CUCUMBER), HFGiftQualities.DISLIKE.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.PANCAKE), HFGiftQualities.DISLIKE.value()),
                        new NPC.ItemData(Ingredient.of(Items.NETHER_STAR), HFGiftQualities.BAD.value()),
                        //TODO GASTRO new NPC.ItemData(Ingredient.of(HorticultureItems.WINE), HFGiftQualities.BAD.value()),
                        new NPC.ItemData(Ingredient.of(Items.DANDELION), HFGiftQualities.TERRIBLE.value()),
                        new NPC.ItemData(Ingredient.of(ItemTags.SAPLINGS), HFGiftQualities.TERRIBLE.value())),
                List.of(new NPC.CategoryData("mineral", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("mushroom", HFGiftQualities.GOOD.value()),
                        new NPC.CategoryData("art", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("plant", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("flower", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("fruit", HFGiftQualities.DISLIKE.value()),
                        new NPC.CategoryData("fish", HFGiftQualities.BAD.value())
                )));
    }
}
