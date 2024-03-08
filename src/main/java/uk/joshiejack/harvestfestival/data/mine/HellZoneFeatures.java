package uk.joshiejack.harvestfestival.data.mine;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.harvestfestival.world.level.mine.room.MultiTunnelRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.TunnelRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.tier.Loot;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.harvestfestival.world.level.mine.tier.NamedRange;
import uk.joshiejack.harvestfestival.world.level.mine.tier.RoomSettings;

import java.util.ArrayList;
import java.util.List;

public class HellZoneFeatures {
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GLOWSTONE = HFFeatureUtils.createKey("glowstone");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> LAVA_FLOOR = HFFeatureUtils.createKey("lava_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> HELL_COBBLESTONE = HFFeatureUtils.createKey("hell_cobblestone");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> HELL_COBBLE_BRICKS = HFFeatureUtils.createKey("hell_cobble_bricks");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> CRIMSON_NYLIUM = HFFeatureUtils.createKey("crimson_nylium");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> WARPED_TREE = HFFeatureUtils.createKey("warped_tree");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> NETHER_BRICKS = HFFeatureUtils.createKey("nether_bricks");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_HELL_FLOOR = HFFeatureUtils.createKey("grass_hell_floor");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> GRASS_HELL_SHORT = HFFeatureUtils.createKeyFromExisting("grass_hell_short", VegetationFeatures.PATCH_GRASS);


    public static void bootstrap(BootstapContext<PlacedFeature> ftrContext, BootstapContext<ConfiguredFeature<?, ?>> cnfContext) {
        //Hell Zone
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, HELL_COBBLESTONE, HFBlocks.HELL_COBBLESTONE.get(), 2, 2);

        //Infernal Forest
        //Nylium floor
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, CRIMSON_NYLIUM, Blocks.CRIMSON_NYLIUM, HFBlocks.HELL_MINE_FLOOR, 32, 48, 4, 8);
        HFFeatureUtils.registerMiniTreeFeature(ftrContext, cnfContext, WARPED_TREE, Blocks.OAK_LEAVES, Blocks.WARPED_STEM, HFBlocks.HELL_MINE_WALL, 8);
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, GRASS_HELL_FLOOR, Blocks.GRASS_BLOCK, HFBlocks.HELL_MINE_FLOOR, 24, 32, 2, 2);
        HFFeatureUtils.registerVegetationPatch(ftrContext, GRASS_HELL_SHORT, 5, 10);

        //Scorchlands
        HFFeatureUtils.registerFloorFeature(ftrContext,cnfContext, LAVA_FLOOR, Blocks.LAVA, HFBlocks.HELL_MINE_FLOOR, 32, 64, 2, 3);


        //Radiant Abyss
        HFFeatureUtils.registerCeilingFeature(ftrContext, cnfContext, GLOWSTONE, Blocks.GLOWSTONE, HFBlocks.HELL_MINE_WALL, 2, 3);

        //Brimstone Keep
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, NETHER_BRICKS, Blocks.NETHER_BRICKS, HFBlocks.HELL_MINE_FLOOR, 24, 32, 2, 2);
        HFFeatureUtils.registerFlooring(ftrContext, cnfContext, HELL_COBBLE_BRICKS, HFBlocks.HELL_COBBLE_BRICKS.get(), 2, 2);
    }

    public static MobSpawnSettings infernalForestMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 50, 3, 5));
        return builder.build();
    }

    public static BiomeGenerationSettings infernalForest(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, CRIMSON_NYLIUM.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, GRASS_HELL_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HELL_COBBLESTONE.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GRASS_HELL_SHORT.getLeft());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WARPED_TREE.getLeft());
        return builder.build();
    }



    public static MobSpawnSettings scorchlandsMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 6, 2, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 7, 3, 5));
        return builder.build();
    }

    public static BiomeGenerationSettings scorchlands(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HELL_COBBLESTONE.getLeft());
        builder.addFeature(GenerationStep.Decoration.LAKES, LAVA_FLOOR.getLeft());
        return builder.build();
    }

    public static MobSpawnSettings radiantAbyssMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 50, 2, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 100, 3, 5));
        return builder.build();
    }

    public static BiomeGenerationSettings radiantAbyss(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HELL_COBBLESTONE.getLeft());
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, GLOWSTONE.getLeft());
        builder.addFeature(GenerationStep.Decoration.LAKES, LAVA_FLOOR.getLeft());
        return builder.build();
    }

    public static MobSpawnSettings brimstoneKeepMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 5, 2, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 30, 3, 5));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 15, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HFEntities.HELL_BAT.get(), 25, 2, 3));
        return builder.build();
    }

    public static BiomeGenerationSettings brimstoneKeep(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_BRICKS.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HELL_COBBLE_BRICKS.getLeft());
        return builder.build();
    }

    public static Holder<MineTier> buildHellTier(HolderGetter<Biome> biomeRegistry) {
        List<NamedRange> ranges = NamedRange.Builder.create()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.INFERNAL_FOREST)).min(1).max(10).maxEntities(26).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.SCORCHLANDS)).min(11).max(20).maxEntities(28).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.RADIANT_ABYSS)).min(21).max(30).maxEntities(30).add()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.BRIMSTONE_KEEP)).min(31).max(40).maxEntities(32).wall(HFBlocks.HELL_MINE_BRICKS.get().defaultBlockState()).add()
                .build();

        List<RoomSettings> generators = RoomSettings.Builder.create()
                .add(MultiTunnelRoomGenerator.multiTunnelRoomGenerator())
                .add(TunnelRoomGenerator.tunnelRoomGenerator())
                .template("harvestfestival:spiral")
                .template("harvestfestival:hexagon")
                .template("harvestfestival:triangle")
                .build();

        WeightedRandomList<Loot> loots = Loot.Builder.create()
                .state(Blocks.WARPED_FUNGUS).min(3).max(10).weight(3).add()
                .state(Blocks.CRIMSON_FUNGUS).min(3).min(10).weight(5).add()
                .state(HFBlocks.SMALL_WARPED_BRANCH).min(1).max(10).weight(6).add()
                .state(HFBlocks.MEDIUM_WARPED_BRANCH).min(3).max(10).weight(5).add()
                .state(HFBlocks.SMALL_WARPED_STUMP).min(5).max(10).weight(4).add()
                .state(HFBlocks.LARGE_WARPED_BRANCH).min(6).max(10).weight(3).add()
                .state(HFBlocks.MEDIUM_WARPED_STUMP).min(7).max(10).weight(2).add()
                .state(HFBlocks.LARGE_WARPED_STUMP).min(8).max(10).weight(1).add()
                .state(HFBlocks.SMALL_CRIMSON_BRANCH).min(1).max(10).weight(6).add()
                .state(HFBlocks.MEDIUM_CRIMSON_BRANCH).min(3).max(10).weight(5).add()
                .state(HFBlocks.SMALL_CRIMSON_STUMP).min(5).max(10).weight(4).add()
                .state(HFBlocks.LARGE_CRIMSON_BRANCH).min(6).max(10).weight(3).add()
                .state(HFBlocks.MEDIUM_CRIMSON_STUMP).min(7).max(10).weight(2).add()
                .state(HFBlocks.LARGE_CRIMSON_STUMP).min(8).max(10).weight(1).add()
                .state(HFBlocks.WEEDS).max(30).cluster(8, 8).weight(32).add()
                .state(HFBlocks.HELL_ROCK).weight(144).add()
                .state(HFBlocks.HELL_CHEST).cluster(2, 2).weight(4).add()
                .state(HFBlocks.GOLD_NODE).weight(8).add()
                .state(HFBlocks.GOLD_NODE).weight(8).add()
                .state(HFBlocks.SILVER_NODE).weight(7).add()
                .state(HFBlocks.COPPER_NODE).weight(6).add()
                .state(HFBlocks.AMETHYST_NODE).weight(4).add()
                .state(HFBlocks.TOPAZ_NODE).weight(5).add()
                .state(HFBlocks.JADE_NODE).weight(5.5).add()
                .state(HFBlocks.RUBY_NODE).weight(6).add()
                .state(HFBlocks.GEM_NODE).weight(2.5).add()
                .state(HFBlocks.SILVER_NODE).min(30).weight(2).add()
                .build();

        return Holder.direct(new MineTier(
                biomeRegistry.getOrThrow(HFMineBiomes.INFERNAL_FOREST),
                2,
                new ResourceLocation(HarvestFestival.MODID, "textures/gui/hell_mine_hud.png"),
                HFBlocks.HELL_MINE_WALL.get().defaultBlockState(),
                HFBlocks.HELL_MINE_FLOOR.get().defaultBlockState(),
                HFBlocks.HELL_MINE_LADDER.get().defaultBlockState(),
                HFBlocks.HELL_UPPER_PORTAL.get().defaultBlockState(),
                HFBlocks.HELL_LOWER_PORTAL.get().defaultBlockState(),
                HFBlocks.HELL_MINE_HOLE.get().defaultBlockState(),
                ranges, loots, new ArrayList<>(), generators));
    }


}