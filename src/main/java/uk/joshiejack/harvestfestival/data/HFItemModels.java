package uk.joshiejack.harvestfestival.data;

import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.world.item.AbstractWateringCanItem;
import uk.joshiejack.penguinlib.world.item.PenguinItem;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HFItemModels extends ItemModelProvider {
    public HFItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, HarvestFestival.MODID, existingFileHelper);
    }

    private static final Set<DeferredItem<Item>> GEMS = Sets.newHashSet(
            HFItems.ADAMANTITE, HFItems.AGATE, HFItems.ALEXANDRITE, HFItems.CHAROITE, HFItems.FLUORITE, HFItems.JADE, HFItems.MOONSTONE,
            HFItems.OPAL, HFItems.PERIDOT, HFItems.PINK_DIAMOND, HFItems.RUBY, HFItems.SAND_ROSE, HFItems.SAPPHIRE, HFItems.TOPAZ
    );

    private static final Set<DeferredItem<Item>> MATERIALS = Sets.newHashSet(
            HFItems.JUNK_ORE, HFItems.COPPER_ORE, HFItems.SILVER_ORE, HFItems.GOLD_ORE, HFItems.MYSTRIL_ORE, HFItems.SILVER_INGOT, HFItems.MYSTRIL_INGOT, HFItems.ORICHALC, HFItems.IRON_ORE,
            HFItems.MYTHIC_STONE
    );

    private static final Set<DeferredItem<Item>> MINING = Sets.newHashSet(
            HFItems.COPPER_COIN, HFItems.SILVER_COIN, HFItems.GOLD_COIN, HFItems.BAT_WING, HFItems.ESCAPE_ROPE
    );

    private static String subdir(Item item) {
        if (MINING.stream().map(DeferredItem::asItem).collect(Collectors.toSet()).contains(item)) return "mining/";
        if (GEMS.stream().map(DeferredItem::asItem).collect(Collectors.toSet()).contains(item)) return "mining/gems/";
        if (MATERIALS.stream().map(DeferredItem::asItem).collect(Collectors.toSet()).contains(item)) return "mining/materials/";
        return item instanceof PenguinItem pi && pi.getUseAnimation(new ItemStack(item)) == UseAnim.DRINK ? "drinks/" : "";
    }

    @Override
    protected void registerModels() {
        //HF
        HFEntities.SPAWN_EGGS.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(item -> item instanceof SpawnEggItem)
                .forEach(item -> {
                    String path = BuiltInRegistries.ITEM.getKey(item).getPath();
                    getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/template_spawn_egg")));
                });

        List<Item> EXCEPTIONS = Arrays.asList(HFItems.SEED_BAG.get(), HFItems.SEEDLING_BAG.get(), HFItems.GRASS_STARTER.get());

        HFItems.ITEMS.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(item -> !EXCEPTIONS.contains(item))
                .forEach(item -> {
                    String path = BuiltInRegistries.ITEM.getKey(item).getPath();
                    if (item instanceof BlockItem)
                        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
                    else if (!(item instanceof TieredItem) && !(item instanceof AbstractWateringCanItem)) {
                        getBuilder(item.toString())
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", modLoc("item/" + subdir(item) + path));
                    }
                });

        singleTexture("grass_starter", mcLoc("item/generated"), "layer0", modLoc("item/farming/grass_starter"));

        getBuilder("mine_wall").parent(new ModelFile.UncheckedModelFile(modLoc("block/mine_wall_gems_1")));
        getBuilder("elevator").parent(new ModelFile.UncheckedModelFile(modLoc("block/elevator")));
        getBuilder("mine_ladder").parent(new ModelFile.UncheckedModelFile(modLoc("block/ladder")));
        getBuilder("upper_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/upper_portal_tm")));
        getBuilder("lower_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/lower_portal_tm")));
        getBuilder("mine_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/mine_portal_tm")));

        getBuilder("mine_floor").parent(new ModelFile.UncheckedModelFile(modLoc("block/mine_floor_bones_1")));
        getBuilder("frozen_mine_floor").parent(new ModelFile.UncheckedModelFile(modLoc("block/frozen_mine_floor_bones_1")));
        getBuilder("hell_mine_floor").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_mine_floor_bones_1")));

        //Fix the mine wall blocks to use the correct models (mine_wall_gems_1) (same for frozen and hell variantts and also make rock use rock_1_t1)
        getBuilder("frozen_mine_wall").parent(new ModelFile.UncheckedModelFile(modLoc("block/frozen_mine_wall_gems_1")));
        getBuilder("hell_mine_wall").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_mine_wall_gems_1")));
        getBuilder("rock").parent(new ModelFile.UncheckedModelFile(modLoc("block/rock_1_t1")));
        getBuilder("ice_rock").parent(new ModelFile.UncheckedModelFile(modLoc("block/ice_rock_1_t1")));
        getBuilder("ice_crystal").parent(new ModelFile.UncheckedModelFile(modLoc("block/ice_crystal_1_t1")));
        getBuilder("hell_rock").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_rock_1_t1")));

        getBuilder("frozen_upper_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/frozen_upper_portal_tm")));
        getBuilder("frozen_lower_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/frozen_lower_portal_tm")));
        getBuilder("hell_upper_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_upper_portal_tm")));
        getBuilder("hell_lower_portal").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_lower_portal_tm")));
        //cobblestone, frozen_cobblestone, frozen_cobble_bricks, hell_cobblestone, hell_cobble_bricks
        getBuilder("cobblestone").parent(new ModelFile.UncheckedModelFile(modLoc("block/cobblestone_standard")));
        getBuilder("frozen_cobblestone").parent(new ModelFile.UncheckedModelFile(modLoc("block/frozen_cobblestone")));
        getBuilder("frozen_cobble_bricks").parent(new ModelFile.UncheckedModelFile(modLoc("block/frozen_cobble_bricks")));
        getBuilder("hell_cobblestone").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_cobblestone")));
        getBuilder("hell_cobble_bricks").parent(new ModelFile.UncheckedModelFile(modLoc("block/hell_cobble_bricks")));

        singleTexture("dwarfen_shroom", mcLoc("item/generated"), "layer0", modLoc("block/dwarfen_shroom"));
        singleTexture("goddess_flower", mcLoc("item/generated"), "layer0", modLoc("block/goddess_flower"));
        singleTexture("blue_magic_flower", mcLoc("item/generated"), "layer0", modLoc("block/blue_magic_flower"));
        singleTexture("moondrop_flower", mcLoc("item/generated"), "layer0", modLoc("block/moondrop_flower"));
        singleTexture("pink_cat_flower", mcLoc("item/generated"), "layer0", modLoc("block/pink_cat_flower"));
        singleTexture("red_magic_flower", mcLoc("item/generated"), "layer0", modLoc("block/red_magic_flower"));
        singleTexture("toy_flower", mcLoc("item/generated"), "layer0", modLoc("block/toy_flower"));
        singleTexture("weeds", mcLoc("item/generated"), "layer0", modLoc("block/weeds_1"));
        singleTexture("wild_grapes", mcLoc("item/generated"), "layer0", modLoc("block/wild_grapes"));
        singleTexture("toadstool", mcLoc("item/generated"), "layer0", modLoc("block/toadstool"));

        //Textures for the swords are in the tools folder of the items
        singleTexture("basic_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/basic_sword"));
        singleTexture("copper_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/copper_sword"));
        singleTexture("silver_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/silver_sword"));
        singleTexture("gold_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/gold_sword"));
        singleTexture("mystril_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mystril_sword"));
        singleTexture("cursed_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/cursed_sword"));
        singleTexture("blessed_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/blessed_sword"));
        singleTexture("mythic_sword", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mythic_sword"));

        //Textures for the hammers are in the tools folder of the items
        singleTexture("basic_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/basic_hammer"));
        singleTexture("copper_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/copper_hammer"));
        singleTexture("silver_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/silver_hammer"));
        singleTexture("gold_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/gold_hammer"));
        singleTexture("mystril_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mystril_hammer"));
        singleTexture("cursed_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/cursed_hammer"));
        singleTexture("blessed_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/blessed_hammer"));
        singleTexture("mythic_hammer", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mythic_hammer"));

        //Textures for the axes are in the tools folder of the items
        singleTexture("basic_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/basic_axe"));
        singleTexture("copper_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/copper_axe"));
        singleTexture("silver_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/silver_axe"));
        singleTexture("gold_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/gold_axe"));
        singleTexture("mystril_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mystril_axe"));
        singleTexture("cursed_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/cursed_axe"));
        singleTexture("blessed_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/blessed_axe"));
        singleTexture("mythic_axe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mythic_axe"));

        //Textures for the hoes are in the tools folder of the items
        singleTexture("basic_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/basic_hoe"));
        singleTexture("copper_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/copper_hoe"));
        singleTexture("silver_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/silver_hoe"));
        singleTexture("gold_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/gold_hoe"));
        singleTexture("mystril_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mystril_hoe"));
        singleTexture("cursed_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/cursed_hoe"));
        singleTexture("blessed_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/blessed_hoe"));
        singleTexture("mythic_hoe", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mythic_hoe"));

        //Textures for the sickles are in the tools folder of the items
        singleTexture("basic_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/basic_sickle"));
        singleTexture("copper_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/copper_sickle"));
        singleTexture("silver_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/silver_sickle"));
        singleTexture("gold_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/gold_sickle"));
        singleTexture("mystril_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mystril_sickle"));
        singleTexture("cursed_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/cursed_sickle"));
        singleTexture("blessed_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/blessed_sickle"));
        singleTexture("mythic_sickle", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mythic_sickle"));

        //Textures for the watering cans are in the tools folder of the items
        singleTexture("basic_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/basic_watering_can"));
        singleTexture("copper_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/copper_watering_can"));
        singleTexture("silver_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/silver_watering_can"));
        singleTexture("gold_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/gold_watering_can"));
        singleTexture("mystril_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mystril_watering_can"));
        singleTexture("cursed_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/cursed_watering_can"));
        singleTexture("blessed_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/blessed_watering_can"));
        singleTexture("mythic_watering_can", mcLoc("item/handheld"), "layer0", modLoc("item/tools/mythic_watering_can"));

        //Textures for the fishing rods are in the tools folder of the items
        singleTexture("basic_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/basic_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/basic_fishing_rod_cast")));
        singleTexture("basic_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/basic_fishing_rod_cast"));
        singleTexture("copper_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/copper_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/copper_fishing_rod_cast")));
        singleTexture("copper_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/copper_fishing_rod_cast"));
        singleTexture("silver_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/silver_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/silver_fishing_rod_cast")));
        singleTexture("silver_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/silver_fishing_rod_cast"));
        singleTexture("gold_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/gold_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/gold_fishing_rod_cast")));
        singleTexture("gold_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/gold_fishing_rod_cast"));
        singleTexture("mystril_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/mystril_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/mystril_fishing_rod_cast")));
        singleTexture("mystril_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/mystril_fishing_rod_cast"));
        singleTexture("cursed_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/cursed_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/cursed_fishing_rod_cast")));
        singleTexture("cursed_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/cursed_fishing_rod_cast"));
        singleTexture("blessed_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/blessed_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/blessed_fishing_rod_cast")));
        singleTexture("blessed_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/blessed_fishing_rod_cast"));
        singleTexture("mythic_fishing_rod", mcLoc("item/handheld_rod"), "layer0", modLoc("item/tools/mythic_fishing_rod_uncast")).override().predicate(mcLoc("cast"), 1).model(new ModelFile.UncheckedModelFile(modLoc("item/tools/mythic_fishing_rod_cast")));
        singleTexture("mythic_fishing_rod_cast", mcLoc("item/fishing_rod"), "layer0", modLoc("item/tools/mythic_fishing_rod_cast"));
        singleTexture("note", mcLoc("item/generated"), "layer0", modLoc("item/note")).override().predicate(modLoc("special"), 1).model(new ModelFile.UncheckedModelFile(modLoc("note_special")));
        singleTexture("note_special", mcLoc("item/generated"), "layer0", modLoc("item/note_special"));

        //Fertilizers
        singleTexture("fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/none_fertilizer"))
                .override()
                .predicate(modLoc("fertilizer"), HFFarming.Fertilizers.BONE_MEAL.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/bone_meal_fertilizer")))
                .end().override().predicate(modLoc("fertilizer"), HFFarming.Fertilizers.BASIC.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/basic_fertilizer")))
                .end().override().predicate(modLoc("fertilizer"), HFFarming.Fertilizers.QUALITY.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/quality_fertilizer")))
                .end().override().predicate(modLoc("fertilizer"), HFFarming.Fertilizers.SPEED.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/speed_fertilizer")))
                .end().override().predicate(modLoc("fertilizer"), HFFarming.Fertilizers.TURBO.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/turbo_fertilizer")))
                .end().override().predicate(modLoc("fertilizer"), HFFarming.Fertilizers.RETENTION.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/retention_fertilizer")))
                .end().override().predicate(modLoc("fertilizer"), HFFarming.Fertilizers.AQUA_GUARD.value().itemModelIndex()).model(new ModelFile.UncheckedModelFile(modLoc("item/aqua_guard_fertilizer")));
        singleTexture("bone_meal_fertilizer", mcLoc("item/generated"), "layer0", mcLoc("item/bone_meal"));
        singleTexture("basic_fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/basic_fertilizer"));
        singleTexture("quality_fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/quality_fertilizer"));
        singleTexture("speed_fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/speed_fertilizer"));
        singleTexture("turbo_fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/turbo_fertilizer"));
        singleTexture("retention_fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/retention_fertilizer"));
        singleTexture("aqua_guard_fertilizer", mcLoc("item/generated"), "layer0", modLoc("item/farming/aqua_guard_fertilizer"));

    }
}