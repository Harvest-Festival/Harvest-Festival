package uk.joshiejack.harvestfestival.data.mine;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.harvestfestival.world.level.mine.room.CircleRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.TunnelRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.tier.*;

import java.util.ArrayList;
import java.util.List;

public class FrozenZoneFeatures {
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_FLOOR = HFFeatureUtils.createKey("frozen_grass");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_SHORT = HFFeatureUtils.createKeyFromExisting("grass_frozen_short", VegetationFeatures.PATCH_GRASS);
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> SNOW_LAYER = HFFeatureUtils.createKey("snow_layer");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> SNOW_BLOCK = HFFeatureUtils.createKey("snow_block");

    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> POWDER_SNOW_FLOOR = HFFeatureUtils.createKey("powder_snow_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> ICE_FLOOR = HFFeatureUtils.createKey("ice_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> ICY_WATER_FLOOR = HFFeatureUtils.createKey("icy_water_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> FROZEN_COBBLESTONE = HFFeatureUtils.createKey("frozen_cobblestone");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> FROZEN_COBBLE_BRICKS = HFFeatureUtils.createKey("frozen_cobble_bricks");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> ICE_SPIKE = HFFeatureUtils.createKey("ice_spike");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> SNOW_SPIKE = HFFeatureUtils.createKey("snow_spike");


    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> MINI_SPRUCE_TREE = HFFeatureUtils.createKey("mini_spruce_tree");


    public static void bootstrap(BootstapContext<PlacedFeature> ftrContext, BootstapContext<ConfiguredFeature<?, ?>> cnfContext) {
        //Frozen Zone
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, FROZEN_COBBLESTONE, HFBlocks.FROZEN_COBBLESTONE.get(), 2, 2);
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 3; i++)
            builder.add(Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, i), 1);
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, SNOW_LAYER, new WeightedStateProvider(builder), 3, 3);

        //Frostwood Glade
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, GRASS_FLOOR, Blocks.GRASS_BLOCK, HFBlocks.FROZEN_MINE_FLOOR, 16, 32, 2, 4);
        HFFeatureUtils.registerVegetationPatch(ftrContext, GRASS_SHORT, 3, 9);
        HFFeatureUtils.registerMiniTreeFeature(ftrContext, cnfContext, MINI_SPRUCE_TREE, Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, HFBlocks.FROZEN_MINE_WALL, 8);

        //Snowdrift Caverns
        HFFeatureUtils.registerSpike(ftrContext, cnfContext, SNOW_SPIKE, Blocks.SNOW_BLOCK, 3);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, POWDER_SNOW_FLOOR, Blocks.POWDER_SNOW, HFBlocks.FROZEN_MINE_FLOOR, 16, 24, 2, 3);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, SNOW_BLOCK, Blocks.SNOW_BLOCK, HFBlocks.FROZEN_MINE_FLOOR, 24, 48, 2, 3);


        //Glacial Sanctum
        HFFeatureUtils.registerSpike(ftrContext, cnfContext, ICE_SPIKE, Blocks.PACKED_ICE, 3);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, ICE_FLOOR, Blocks.PACKED_ICE, HFBlocks.FROZEN_MINE_FLOOR, 36, 64, 2, 5);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, ICY_WATER_FLOOR, HFBlocks.ICY_WATER.get(), HFBlocks.FROZEN_MINE_FLOOR, 24, 32, 2, 3);

        //Frozen Fortress
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, FROZEN_COBBLE_BRICKS, HFBlocks.FROZEN_COBBLE_BRICKS.get(), 2, 2);
    }

    public static BiomeGenerationSettings.Builder features(BiomeGenerationSettings.Builder builder) {
        return builder;
    }

    public static MobSpawnSettings frostwoodGladeMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HFEntities.FROST_SLIME.get(), 60, 3, 5));
        builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 5, 1, 2));
        return builder.build();
    }

    public static BiomeGenerationSettings frostwoodGlade(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, GRASS_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, SNOW_BLOCK.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GRASS_SHORT.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINI_SPRUCE_TREE.getLeft());
        //builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_LAYER.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FROZEN_COBBLESTONE.getLeft());
        return features(builder).build();
    }

    public static MobSpawnSettings snowdriftCavernsMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HFEntities.FROST_SLIME.get(), 60, 3, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 30, 2, 4));
        return builder.build();
    }

    public static BiomeGenerationSettings snowdriftCaverns(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, POWDER_SNOW_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, SNOW_BLOCK.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, SNOW_SPIKE.getLeft());
        //builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_LAYER.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FROZEN_COBBLESTONE.getLeft());
        return features(builder).build();
    }

    public static MobSpawnSettings glacialSanctumMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 24, 3, 5));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
        return builder.build();
    }

    public static BiomeGenerationSettings glacialSanctum(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LAKES, ICY_WATER_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.LAKES, ICE_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ICE_SPIKE.getLeft());
        //builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_LAYER.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FROZEN_COBBLESTONE.getLeft());
        return features(builder).build();
    }

    public static MobSpawnSettings frozenFortressMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HFEntities.FROST_BAT.get(), 10, 2, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 40, 3, 5));
        return builder.build();
    }

    public static BiomeGenerationSettings frozenFortress(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FROZEN_COBBLE_BRICKS.getLeft());
        return features(builder).build();
    }

    public static Holder<MineTier> buildFrozenTier(HolderGetter<Biome> biomeRegistry) {
        List<NamedRange> ranges = NamedRange.Builder.create()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.FROSTWOOD_GLADE)).min(1).max(10).maxEntities(12).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.SNOWDRIFT_CAVERNS)).min(11).max(20).maxEntities(16).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.GLACIAL_SANCTUM)).min(21).max(30).maxEntities(20).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.FROZEN_FORTRESS)).min(31).max(40).maxEntities(24).floor(HFBlocks.FROZEN_MINE_WALL.get().defaultBlockState()).wall(HFBlocks.FROZEN_MINE_BRICKS.get().defaultBlockState()).add()
                .build();
        List<RoomSettings> generators = RoomSettings.Builder.create()
                .add(CircleRoomGenerator.circleRoomGenerator(16))
                .template("harvestfestival:sprites")
                .template("harvestfestival:star")
                .template("harvestfestival:snowflake")
                .add(TunnelRoomGenerator.tunnelRoomGenerator())
                .build();
        WeightedRandomList<Loot> loots = Loot.Builder.create()
                .state(Blocks.DEAD_BUSH).max(10).cluster(8, 8).weight(32).add()
                .state(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3)).max(13).cluster(3, 1).weight(5).add()
                .state(HFBlocks.ICE_ROCK).weight(85).add()
                .state(HFBlocks.ICE_CRYSTAL).weight(48).add()
                .state(HFBlocks.FROZEN_BARREL).weight(4).add()
                .state(HFBlocks.FROZEN_BARREL).cluster(3, 8).weight(1).add()
                .state(HFBlocks.SILVER_NODE).weight(8).add()
                .state(HFBlocks.COPPER_NODE).weight(6).add()
                .state(HFBlocks.AMETHYST_NODE).weight(5).add()
                .state(HFBlocks.TOPAZ_NODE).weight(6).add()
                .state(HFBlocks.GEM_NODE).weight(0.075).add()
                .state(HFBlocks.SILVER_NODE).min(30).weight(2).add()
                //TODO: Add mint and lavender from gastronomy
                .build();
        return Holder.direct(new MineTier(
                biomeRegistry.getOrThrow(HFMineBiomes.FROSTWOOD_GLADE),
                1,
                new ResourceLocation(HarvestFestival.MODID, "textures/gui/frozen_mine_hud.png"),
                HFBlocks.FROZEN_MINE_WALL.get().defaultBlockState(),
                HFBlocks.FROZEN_MINE_FLOOR.get().defaultBlockState(),
                HFBlocks.FROZEN_MINE_LADDER.get().defaultBlockState(),
                HFBlocks.FROZEN_UPPER_PORTAL.get().defaultBlockState(),
                HFBlocks.FROZEN_LOWER_PORTAL.get().defaultBlockState(),
                HFBlocks.FROZEN_MINE_HOLE.get().defaultBlockState(),
                ranges, loots, new ArrayList<>(), generators));
    }

}