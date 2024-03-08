package uk.joshiejack.harvestfestival.data.mine;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.level.HFLevel;
import uk.joshiejack.harvestfestival.world.level.mine.room.CircleRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.TunnelRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.tier.*;

import java.util.ArrayList;
import java.util.List;

public class EarthenZoneFeatures {
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> COBBLESTONE = HFFeatureUtils.createKey("cobblestone");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> COBBLESTONE_LESS = HFFeatureUtils.createKeyFromExisting("cobblestone_lesser", COBBLESTONE.getRight());

    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> DARK_OAK_FLOOR = HFFeatureUtils.createKey("dark_oak_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_FLOOR = HFFeatureUtils.createKey("earthen_grass");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_EARTH_SHORT = HFFeatureUtils.createKeyFromExisting("grass_earth_short", VegetationFeatures.PATCH_GRASS);
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_EARTH_TALL = HFFeatureUtils.createKeyFromExisting("grass_earth_tall", VegetationFeatures.PATCH_TALL_GRASS);

    //Mossy Caverns
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> MINI_OAK_TREE = HFFeatureUtils.createKey("mini_oak_tree");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> OAK_BUSHES = HFFeatureUtils.createKey("oak_bushes");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> OAK_TREES = HFFeatureUtils.createKey("oak_trees");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> OAK_LEAVES = HFFeatureUtils.createKey("oak_leaves");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> PINK_PETALS = HFFeatureUtils.createKey("pink_petals");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> MOSS_CARPET = HFFeatureUtils.createKey("moss_carpet");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> VINES = HFFeatureUtils.createKeyFromExisting("vines", VegetationFeatures.VINES);

    //Mycelium Hollows
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> MINI_DARK_OAK_TREE = HFFeatureUtils.createKey("mini_dark_oak_tree");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> DARK_FOREST_TREES = HFFeatureUtils.createKey("dark_forest_trees");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> DARK_OAK_BUSHES = HFFeatureUtils.createKey("dark_oak_bushes");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> DARK_OAK_LEAVES = HFFeatureUtils.createKey("dark_oak_leaves");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> HUGE_BROWN_MUSHROOM = HFFeatureUtils.createKey("huge_brown_mushroom");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> HUGE_RED_MUSHROOM = HFFeatureUtils.createKey("huge_red_mushroom");

    //Silted Depths
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> COARSE_DIRT_FLOOR = HFFeatureUtils.createKey("coarse_dirt_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> WATER_FLOOR = HFFeatureUtils.createKey("water_floor");


    //Deep Cave Features
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> DEEPSLATE_FLOOR = HFFeatureUtils.createKey("earthen_deepslate_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> TUFF_FLOOR = HFFeatureUtils.createKey("earthen_tuff_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> BASALT_FLOOR = HFFeatureUtils.createKey("earthen_basalt_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> BLACKSTONE_FLOOR = HFFeatureUtils.createKey("earthen_blackstone_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> SMOOTH_BASALT_FLOOR = HFFeatureUtils.createKey("earthen_smooth_basalt_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> MOSSY_BLOCK = HFFeatureUtils.createKey("mossy_block");



    public static void bootstrap(BootstapContext<PlacedFeature> ftrContext, BootstapContext<ConfiguredFeature<?, ?>> cnfContext) {
        //Earthen Zone
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, COBBLESTONE, HFBlocks.COBBLESTONE.get(), 2, 2);
        HFFeatureUtils.registerVegetationPatch(ftrContext, GRASS_EARTH_SHORT, 5, 10);
        HFFeatureUtils.registerRareVegetationPatch(ftrContext, GRASS_EARTH_TALL, 5);
        HFFeatureUtils.registerCountVegetationPatch(ftrContext, VINES, 256);
        HFFeatureUtils.registerExistingFeature(ftrContext, COBBLESTONE_LESS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), BiomeFilter.biome());

        //Mossy Caverns
        HFFeatureUtils.registerSpike(ftrContext, cnfContext, MOSSY_BLOCK, Blocks.MOSSY_COBBLESTONE, 2);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, DARK_OAK_FLOOR, Blocks.DARK_OAK_PLANKS, HFBlocks.MINE_FLOOR, 6, 12, 1, 2);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, COARSE_DIRT_FLOOR, Blocks.COARSE_DIRT, HFBlocks.MINE_FLOOR, 6, 16, 2, 5);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, GRASS_FLOOR, Blocks.GRASS_BLOCK, HFBlocks.MINE_FLOOR, 24, 36, 3, 5);
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, PINK_PETALS, Blocks.PINK_PETALS, 2, 1);
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, MOSS_CARPET, Blocks.MOSS_CARPET, 1, 1);
        HFFeatureUtils.registerLeavesFeature(ftrContext, cnfContext, OAK_LEAVES, Blocks.OAK_LEAVES, HFBlocks.MINE_WALL, 256);
        HFFeatureUtils.registerMiniTreeFeature(ftrContext, cnfContext, MINI_OAK_TREE, Blocks.OAK_LEAVES, Blocks.OAK_LOG, HFBlocks.MINE_WALL, 8);
        HFFeatureUtils.registerFeatures(ftrContext, cnfContext, OAK_TREES, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(2, 3, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 1),
                new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))
                .build(), CountPlacement.of(5), InSquarePlacement.spread(), BiomeFilter.biome());

        HFFeatureUtils.registerFeatures(ftrContext, cnfContext, OAK_BUSHES, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0))
                .build(), CountPlacement.of(7), InSquarePlacement.spread(), BiomeFilter.biome());

        //Mycelium Hollows
        HFFeatureUtils.registerMiniTreeFeature(ftrContext, cnfContext, MINI_DARK_OAK_TREE, Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, HFBlocks.MINE_WALL, 6);
        HFFeatureUtils.registerLeavesFeature(ftrContext, cnfContext, DARK_OAK_LEAVES, Blocks.DARK_OAK_LEAVES, HFBlocks.MINE_WALL, 256);
        HFFeatureUtils.registerFeatures(ftrContext, cnfContext, HUGE_BROWN_MUSHROOM, HFLevel.HUGE_BROWN_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.TRUE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)),
                        BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.FALSE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), 3),
                CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome());

        HFFeatureUtils.registerFeatures(ftrContext, cnfContext, HUGE_RED_MUSHROOM, HFLevel.HUGE_RED_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(Blocks.RED_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.TRUE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)),
                        BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.FALSE).setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), 3),
                CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome());

        HFFeatureUtils.registerFeatures(ftrContext, cnfContext, DARK_FOREST_TREES, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new DarkOakTrunkPlacer(2, 3, 0), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))
                .build(), CountPlacement.of(3), InSquarePlacement.spread(), BiomeFilter.biome());

        HFFeatureUtils.registerFeatures(ftrContext, cnfContext, DARK_OAK_BUSHES, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))
                .build(), CountPlacement.of(5), InSquarePlacement.spread(), BiomeFilter.biome());

        //Silted Depths
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, WATER_FLOOR, Blocks.WATER, HFBlocks.MINE_FLOOR, 24, 32, 2, 2);

        //Rock Caves
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, DEEPSLATE_FLOOR, Blocks.DEEPSLATE, HFBlocks.MINE_FLOOR, 24, 48, 3, 5);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, TUFF_FLOOR, Blocks.TUFF, HFBlocks.MINE_WALL, 16, 32, 2, 4);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, BASALT_FLOOR, Blocks.BASALT, HFBlocks.MINE_WALL, 16, 32, 2, 4);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, BLACKSTONE_FLOOR, Blocks.BLACKSTONE, HFBlocks.MINE_WALL, 16, 32, 2, 4);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, SMOOTH_BASALT_FLOOR, Blocks.SMOOTH_BASALT, HFBlocks.MINE_WALL, 16, 32, 3, 5);
    }

    public static BiomeGenerationSettings.Builder features(BiomeGenerationSettings.Builder builder) {
        return builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, DARK_OAK_FLOOR.getLeft());
    }

    public static MobSpawnSettings mossyCavernsMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 3, 5));
        return builder.build();
    }

    public static BiomeGenerationSettings mossyCaverns(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINI_OAK_TREE.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MOSS_CARPET.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, OAK_LEAVES.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GRASS_EARTH_SHORT.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GRASS_EARTH_TALL.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, OAK_TREES.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, OAK_BUSHES.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VINES.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PINK_PETALS.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, GRASS_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, COBBLESTONE.getLeft());
        return features(builder).build();
    }

    public static MobSpawnSettings myceliumHollowsMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 24, 3, 5));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.MOOSHROOM, 1, 1, 2));
        return builder.build();
    }

    public static BiomeGenerationSettings myceliumHollows(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINI_DARK_OAK_TREE.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MOSS_CARPET.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DARK_OAK_LEAVES.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GRASS_EARTH_SHORT.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GRASS_EARTH_TALL.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HUGE_RED_MUSHROOM.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HUGE_BROWN_MUSHROOM.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DARK_FOREST_TREES.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DARK_OAK_BUSHES.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, GRASS_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, COBBLESTONE.getLeft());
        return features(builder).build();
    }

    public static MobSpawnSettings siltedDepthsMobs(MobSpawnSettings.Builder builder) {
        //Slimes and cave spiders
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 3, 2, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 2, 3, 5));
        return builder.build();
    }


    public static BiomeGenerationSettings siltedDepths(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MOSS_CARPET.getLeft());
        builder.addFeature(GenerationStep.Decoration.LAKES, WATER_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, COARSE_DIRT_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, COBBLESTONE_LESS.getLeft());
        return features(builder).build();
    }


    public static MobSpawnSettings rockCavesMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 50, 2, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 45, 3, 5));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 3, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 50, 2, 3));
        builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 5, 1, 2));
        return builder.build();
    }

    public static BiomeGenerationSettings rockCaves(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, COARSE_DIRT_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DEEPSLATE_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TUFF_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, BASALT_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, BLACKSTONE_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SMOOTH_BASALT_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, COBBLESTONE_LESS.getLeft());
        return features(builder).build();
    }

    public static Holder<MineTier> buildEarthenZone(HolderGetter<Biome> biomeRegistry) {
        List<NamedRange> namedRanges = NamedRange.Builder.create()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.MOSSY_CAVERNS)).min(1).max(10).lootChanceMin(0.006F).lootChanceMax(0.175F).lootChanceDivisor(840).maxEntities(8).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.MYCELIUM_HOLLOWS)).min(11).max(20).lootChanceMin(0.0055F).lootChanceMax(0.2F).lootChanceDivisor(880).maxEntities(10).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.SILTED_DEPTHS)).min(21).max(30).lootChanceMin(0.00525F).lootChanceMax(0.225F).lootChanceDivisor(920).maxEntities(12).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.ROCK_CAVES)).min(31).max(40).lootChanceMin(0.005F).lootChanceMax(0.25F).lootChanceDivisor(960).maxEntities(16).floor(HFBlocks.MINE_WALL.get().defaultBlockState()).add()
                .build();

        List<RoomSettings> generators = RoomSettings.Builder.create()
                .add(TunnelRoomGenerator.tunnelRoomGenerator())
                .add(CircleRoomGenerator.circleRoomGenerator())
                .template("harvestfestival:hexagon")
                .build();

        //Loots
        WeightedRandomList<Loot> loots = Loot.Builder.create()
                .state(Blocks.BROWN_MUSHROOM).min(10).max(30).cluster(10, 3).weight(12).add()
                .state(Blocks.RED_MUSHROOM).min(20).max(30).cluster(20, 3).weight(10).add()
                .state(HFBlocks.WEEDS).max(30).cluster(8, 8).weight(32).add()
                .state(HFBlocks.BARREL).max(40).cluster(4, 3).weight(4).add()
                .state(HFBlocks.CRATE).max(40).cluster(2, 2).weight(1).stackable().add()
                .state(HFBlocks.SMALL_DARK_OAK_BRANCH).max(20).weight(6).add()
                .state(HFBlocks.MEDIUM_DARK_OAK_BRANCH).min(3).max(20).weight(5).add()
                .state(HFBlocks.SMALL_DARK_OAK_STUMP).min(5).max(20).weight(4).add()
                .state(HFBlocks.LARGE_DARK_OAK_BRANCH).min(6).max(20).weight(3).add()
                .state(HFBlocks.MEDIUM_DARK_OAK_STUMP).min(7).max(20).weight(2).add()
                .state(HFBlocks.LARGE_DARK_OAK_STUMP).min(8).max(20).weight(1).add()
                .state(HFBlocks.SMALL_STONES).min(21).max(31).weight(6).add()
                .state(HFBlocks.MEDIUM_STONES).min(22).max(32).weight(5).add()
                .state(HFBlocks.SMALL_BOULDER).min(23).max(33).weight(4).add()
                .state(HFBlocks.LARGE_STONES).min(24).max(34).weight(3).add()
                .state(HFBlocks.MEDIUM_BOULDER).min(26).max(36).weight(2).add()
                .state(HFBlocks.LARGE_BOULDER).min(28).max(38).weight(1).add()
                .state(HFBlocks.ROCK).weight(100).add()
                .state(HFBlocks.COPPER_NODE).min(6).max(40).weight(8).add()
                .state(HFBlocks.AMETHYST_NODE).min(11).max(40).weight(6).add()
                .state(HFBlocks.COPPER_NODE).min(31).max(40).weight(2).add()
                .state(HFBlocks.DWARFEN_SHROOM).min(26).max(30).weight(1).add()
                .build();

        List<Special> specialLoots = new ArrayList<>();
        return Holder.direct(new MineTier(
                biomeRegistry.getOrThrow(HFMineBiomes.MOSSY_CAVERNS),
                0,
                null,
                HFBlocks.MINE_WALL.get().defaultBlockState(),
                HFBlocks.MINE_FLOOR.get().defaultBlockState(),
                HFBlocks.MINE_LADDER.get().defaultBlockState(),
                HFBlocks.UPPER_PORTAL.get().defaultBlockState(),
                HFBlocks.LOWER_PORTAL.get().defaultBlockState(),
                HFBlocks.MINE_HOLE.get().defaultBlockState(),
                namedRanges, loots, specialLoots, generators));
    }
}
