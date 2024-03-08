package uk.joshiejack.harvestfestival.data.mine;

import com.google.common.collect.Lists;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.HFLevel;
import uk.joshiejack.harvestfestival.world.level.feature.config.*;

import javax.annotation.Nullable;

public class HFFeatureUtils {
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerFeatures(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                                                                                 Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, F feature, FC configuration, PlacementModifier... modifiers) {
        if (cnfContext != null)
            net.minecraft.data.worldgen.features.FeatureUtils.register(cnfContext, key.getRight(), feature, configuration);
        if (ftrContext != null) {
            HolderGetter<ConfiguredFeature<?, ?>> ftr = ftrContext.lookup(Registries.CONFIGURED_FEATURE);
            PlacementUtils.register(ftrContext, key.getLeft(), ftr.getOrThrow(key.getRight()), modifiers);
        }
    }

    public static void registerMiniTreeFeature(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                        Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, Block leaves, Block logs, DeferredBlock<Block> replace, int rarity) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.MINI_TREE.get(), new MiniTreeConfiguration(BlockStateProvider.simple(leaves), BlockStateProvider.simple(logs), BlockStateProvider.simple(replace.get())),
                CountPlacement.of(rarity), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerFloorFeature(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                            Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, Block floor, DeferredBlock<Block> replace, int min, int max, int noiseBelow, int noiseAbove) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.REPLACE_FLOOR.get(), new ReplaceFloorConfiguration(BlockStateProvider.simple(floor), BlockStateProvider.simple(replace.get()), UniformInt.of(min, max)),
                NoiseThresholdCountPlacement.of(-0.8, noiseBelow, noiseAbove), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerHoleFeature(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                            Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, DeferredBlock<Block> hole, DeferredBlock<Block> replace, int depth, int random) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.HOLE.get(), new HoleDepthConfiguration(BlockStateProvider.simple(hole.get()), BlockStateProvider.simple(replace.get()), ConstantInt.of(depth)),
                RarityFilter.onAverageOnceEvery(random), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerFlooring(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                        Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, Block floor, int radius, int count) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.DECORATE_FLOOR.get(), new DecorateFloorConfiguration(BlockStateProvider.simple(floor), ConstantInt.of(radius)),
                CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerFlooring(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                        Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, BlockStateProvider provider, int radius, int count) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.DECORATE_FLOOR.get(), new DecorateFloorConfiguration(provider, ConstantInt.of(radius)),
                CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerCeilingFeature(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                              Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, Block block, DeferredBlock<Block> ceiling, int noiseMin, int noiseMax) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.BLOB_MODIFIABLE.get(), new CeilingBlobConfiguration(Lists.newArrayList(BlockStateProvider.simple(ceiling.get())), BlockStateProvider.simple(block)),
                NoiseThresholdCountPlacement.of(-0.8, noiseMin, noiseMax), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerLeavesFeature(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                             Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, Block leaves, DeferredBlock<Block> replace, int count) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.LEAVES_BLOB.get(), new LeavesBlobConfiguration(BlockStateProvider.simple(leaves), BlockStateProvider.simple(replace.get())),
                CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static void registerCountVegetationPatch(@Nullable BootstapContext<PlacedFeature> ftrContext,
                                                   Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, int count) {
        if (ftrContext != null) {
            HolderGetter<ConfiguredFeature<?, ?>> ftr = ftrContext.lookup(Registries.CONFIGURED_FEATURE);
            PlacementUtils.register(ftrContext, key.getLeft(), ftr.getOrThrow(key.getRight()), CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
        }
    }

    public static void registerRareVegetationPatch(@Nullable BootstapContext<PlacedFeature> ftrContext,
                                               Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, int rarity) {
        if (ftrContext != null) {
            HolderGetter<ConfiguredFeature<?, ?>> ftr = ftrContext.lookup(Registries.CONFIGURED_FEATURE);
            PlacementUtils.register(ftrContext, key.getLeft(), ftr.getOrThrow(key.getRight()), RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), BiomeFilter.biome());
        }
    }

    public static void registerExistingFeature(@Nullable BootstapContext<PlacedFeature> ftrContext,
                                               Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, PlacementModifier... modifiers) {
        if (ftrContext != null) {
            HolderGetter<ConfiguredFeature<?, ?>> ftr = ftrContext.lookup(Registries.CONFIGURED_FEATURE);
            PlacementUtils.register(ftrContext, key.getLeft(), ftr.getOrThrow(key.getRight()), modifiers);
        }
    }

    public static void registerVegetationPatch(@Nullable BootstapContext<PlacedFeature> ftrContext,
                                               Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, int noiseMin, int noiseMax) {
        if (ftrContext != null) {
            HolderGetter<ConfiguredFeature<?, ?>> ftr = ftrContext.lookup(Registries.CONFIGURED_FEATURE);
            PlacementUtils.register(ftrContext, key.getLeft(), ftr.getOrThrow(key.getRight()), NoiseThresholdCountPlacement.of(-0.8, noiseMin, noiseMax), InSquarePlacement.spread(), BiomeFilter.biome());
        }
    }

    public static void registerSpike(@Nullable BootstapContext<PlacedFeature> ftrContext, @Nullable BootstapContext<ConfiguredFeature<?, ?>> cnfContext,
                                     Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> key, Block floor, int count) {
        registerFeatures(ftrContext, cnfContext, key, HFLevel.BLOCK_BLOB.get(), new BlockBlobConfiguration(BlockStateProvider.simple(floor)),
                CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    public static Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> createKey(String path) {
        String resource = new ResourceLocation(HarvestFestival.MODID, path).toString();
        ResourceKey<PlacedFeature> feature = PlacementUtils.createKey(resource);
        ResourceKey<ConfiguredFeature<?, ?>> configured = FeatureUtils.createKey(resource);
        return Pair.of(feature, configured);
    }

    public static Pair<ResourceKey<PlacedFeature>, ResourceKey<ConfiguredFeature<?, ?>>> createKeyFromExisting(String path, ResourceKey<ConfiguredFeature<?, ?>> key) {
        String resource = new ResourceLocation(HarvestFestival.MODID, path).toString();
        ResourceKey<PlacedFeature> feature = PlacementUtils.createKey(resource);
        return Pair.of(feature, key);
    }
}
