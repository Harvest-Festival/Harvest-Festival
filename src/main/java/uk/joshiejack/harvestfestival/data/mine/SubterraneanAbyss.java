package uk.joshiejack.harvestfestival.data.mine;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.level.mine.room.*;
import uk.joshiejack.harvestfestival.world.level.mine.tier.*;

import java.util.ArrayList;
import java.util.List;

public class SubterraneanAbyss {
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> DEEPSLATE_FLOOR = HFFeatureUtils.createKey("deep_deepslate");
    public static final Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> HOLE_GEN = HFFeatureUtils.createKey("hole");

    public static void bootstrap(BootstapContext<PlacedFeature> ftrContext, BootstapContext<ConfiguredFeature<?, ?>> cnfContext) {
        HFFeatureUtils.registerFloorFeature(ftrContext, cnfContext, DEEPSLATE_FLOOR, Blocks.DEEPSLATE, HFBlocks.MINE_FLOOR, 32, 64, 2, 3);
        HFFeatureUtils.registerHoleFeature(ftrContext, cnfContext, HOLE_GEN, HFBlocks.MINE_HOLE, HFBlocks.MINE_WALL, 8, 24);
    }

    public static BiomeGenerationSettings features(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, DEEPSLATE_FLOOR.getLeft());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HOLE_GEN.getLeft());
        return builder.build();
    }

    public static MobSpawnSettings mobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 3, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 3, 5));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 3, 4));
        builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
        return builder.build();
    }

    public static Holder<MineTier> buildDeepDark(HolderGetter<Biome> biomeRegistry) {
        List<NamedRange> ranges = NamedRange.Builder.create()
                .biome(biomeRegistry.getOrThrow(HFMineBiomes.SUBTERRANEAN_ABYSS)).min(1).max(40).maxEntities(40).add()
                .build();
        List<RoomSettings> generators = RoomSettings.Builder.create()
                .add(RandomRoomLocations.randomRoomLocations())
                .add(SprawlingTunnelRoomGenerator.sprawlingTunnelRoomGenerator())
                .add(InsanityRoomGenerator.insanityRoomGenerator())
                .add(CircleRoomGenerator.circleRoomGenerator(16))
                .add(RandomPathGenerator.randomPathGenerator())
                .template("harvestfestival:sidewinder")
                .template("harvestfestival:maze")
                .template("harvestfestival:branches")
                .template("harvestfestival:ring")
                .template("harvestfestival:misc")
                .template("harvestfestival:wheel")
                .build();
        WeightedRandomList<Loot> loots = Loot.Builder.create()
                .state(HFBlocks.WEEDS).cluster(8, 8).weight(32).add()
                .state(HFBlocks.BARREL).cluster(2, 4).weight(3).add()
                .state(HFBlocks.CRATE).cluster(1, 2).weight(2).stackable().add()
                .state(HFBlocks.ROCK).weight(150).add()
                .state(HFBlocks.COPPER_NODE).weight(5).add()
                .state(HFBlocks.SILVER_NODE).weight(6).add()
                .state(HFBlocks.GOLD_NODE).weight(7).add()
                .state(HFBlocks.MYSTRIL_NODE).weight(8).add()
                .state(HFBlocks.AMETHYST_NODE).weight(3).add()
                .state(HFBlocks.TOPAZ_NODE).weight(4).add()
                .state(HFBlocks.JADE_NODE).weight(5.5).add()
                .state(HFBlocks.RUBY_NODE).weight(5).add()
                .state(HFBlocks.DIAMOND_NODE).weight(3).add()
                .state(HFBlocks.EMERALD_NODE).weight(4).add()
                .state(HFBlocks.GEM_NODE).weight(10).add()
                .build();

        List<Special> specialLoots = new ArrayList<>();
        //add mystril node to special loot
        specialLoots.add(new Special(HFBlocks.MYSTRIL_NODE.get().defaultBlockState(), 141, 4096));
        BlockState portal = HFBlocks.LOWER_PORTAL.get().defaultBlockState();
        return Holder.direct(new MineTier(
                biomeRegistry.getOrThrow(HFMineBiomes.SUBTERRANEAN_ABYSS),
                3,
                null,
                HFBlocks.MINE_WALL.get().defaultBlockState(),
                HFBlocks.MINE_FLOOR.get().defaultBlockState(),
                HFBlocks.MINE_LADDER.get().defaultBlockState(),
                portal,
                portal,
                HFBlocks.MINE_HOLE.get().defaultBlockState(),
                ranges, loots, specialLoots, generators));

    }
}
