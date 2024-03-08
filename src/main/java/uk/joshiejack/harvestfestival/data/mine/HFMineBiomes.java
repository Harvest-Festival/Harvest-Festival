package uk.joshiejack.harvestfestival.data.mine;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import uk.joshiejack.harvestfestival.HarvestFestival;

public class HFMineBiomes {
    public static final ResourceKey<Biome> MOSSY_CAVERNS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("mossy_caverns"));
    public static final ResourceKey<Biome> MYCELIUM_HOLLOWS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("mycelium_hollows"));
    public static final ResourceKey<Biome> SILTED_DEPTHS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("silted_depths"));
    public static final ResourceKey<Biome> ROCK_CAVES = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("rock_caves"));
    public static final ResourceKey<Biome> FROSTWOOD_GLADE = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("frostwood_glade"));
    public static final ResourceKey<Biome> SNOWDRIFT_CAVERNS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("snowdrift_caverns"));
    public static final ResourceKey<Biome> GLACIAL_SANCTUM = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("glacial_sanctum"));
    public static final ResourceKey<Biome> FROZEN_FORTRESS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("frozen_fortress"));
    public static final ResourceKey<Biome> INFERNAL_FOREST = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("infernal_forest"));
    public static final ResourceKey<Biome> SCORCHLANDS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("scorchlands"));
    public static final ResourceKey<Biome> RADIANT_ABYSS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("radiant_abyss"));
    public static final ResourceKey<Biome> BRIMSTONE_KEEP = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("brimstone_keep"));

    public static final ResourceKey<Biome> SUBTERRANEAN_ABYSS = ResourceKey.create(Registries.BIOME, HarvestFestival.prefix("subterranean_abyss"));

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        //Register the Overgrown, Frozen, Hellish and Deep Caves Biomes
        HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredFeature<?, ?>> featureGetter = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carver = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(MOSSY_CAVERNS, overgrownWithDefaults().mobSpawnSettings(EarthenZoneFeatures.mossyCavernsMobs(new MobSpawnSettings.Builder())).generationSettings(EarthenZoneFeatures.mossyCaverns(defaultBiomeGen(placedGetter, carver))).build());
        context.register(MYCELIUM_HOLLOWS, overgrownWithDefaults().mobSpawnSettings(EarthenZoneFeatures.myceliumHollowsMobs(new MobSpawnSettings.Builder())).generationSettings(EarthenZoneFeatures.myceliumHollows(defaultBiomeGen(placedGetter, carver))).build());
        context.register(SILTED_DEPTHS, overgrownWithDefaults().mobSpawnSettings(EarthenZoneFeatures.siltedDepthsMobs(new MobSpawnSettings.Builder())).generationSettings(EarthenZoneFeatures.siltedDepths(defaultBiomeGen(placedGetter, carver))).build());
        context.register(ROCK_CAVES, overgrownWithDefaults().mobSpawnSettings(EarthenZoneFeatures.rockCavesMobs(new MobSpawnSettings.Builder())).generationSettings(EarthenZoneFeatures.rockCaves(defaultBiomeGen(placedGetter, carver))).build());
        context.register(FROSTWOOD_GLADE, frozenWithDefaults().mobSpawnSettings(FrozenZoneFeatures.frostwoodGladeMobs(new MobSpawnSettings.Builder())).generationSettings(FrozenZoneFeatures.frostwoodGlade(defaultBiomeGen(placedGetter, carver))).build());
        context.register(SNOWDRIFT_CAVERNS, frozenWithDefaults().mobSpawnSettings(FrozenZoneFeatures.snowdriftCavernsMobs(new MobSpawnSettings.Builder())).generationSettings(FrozenZoneFeatures.snowdriftCaverns(defaultBiomeGen(placedGetter, carver))).build());
        context.register(GLACIAL_SANCTUM, frozenWithDefaults().mobSpawnSettings(FrozenZoneFeatures.glacialSanctumMobs(new MobSpawnSettings.Builder())).generationSettings(FrozenZoneFeatures.glacialSanctum(defaultBiomeGen(placedGetter, carver))).build());
        context.register(FROZEN_FORTRESS, frozenWithDefaults().mobSpawnSettings(FrozenZoneFeatures.frozenFortressMobs(new MobSpawnSettings.Builder())).generationSettings(FrozenZoneFeatures.frozenFortress(defaultBiomeGen(placedGetter, carver))).build());
        context.register(INFERNAL_FOREST, hellWithDefaults().mobSpawnSettings(HellZoneFeatures.infernalForestMobs(new MobSpawnSettings.Builder())).generationSettings(HellZoneFeatures.infernalForest(defaultBiomeGen(placedGetter, carver))).build());
        context.register(SCORCHLANDS, hellWithDefaults().mobSpawnSettings(HellZoneFeatures.scorchlandsMobs(new MobSpawnSettings.Builder())).generationSettings(HellZoneFeatures.scorchlands(defaultBiomeGen(placedGetter, carver))).build());
        context.register(RADIANT_ABYSS, hellWithDefaults().mobSpawnSettings(HellZoneFeatures.radiantAbyssMobs(new MobSpawnSettings.Builder())).generationSettings(HellZoneFeatures.radiantAbyss(defaultBiomeGen(placedGetter, carver))).build());
        context.register(BRIMSTONE_KEEP, hellWithDefaults().mobSpawnSettings(HellZoneFeatures.brimstoneKeepMobs(new MobSpawnSettings.Builder())).generationSettings(HellZoneFeatures.brimstoneKeep(defaultBiomeGen(placedGetter, carver))).build());
        context.register(SUBTERRANEAN_ABYSS, deepWithDefaults().mobSpawnSettings(SubterraneanAbyss.mobs(new MobSpawnSettings.Builder())).generationSettings(SubterraneanAbyss.features(defaultBiomeGen(placedGetter, carver))).build());
    }

    private static Biome.BiomeBuilder overgrownWithDefaults() {
        return biomeWithDefaults().temperature(0.2F).specialEffects(defaultSpecialEffects().build());
    }

    private static Biome.BiomeBuilder frozenWithDefaults() {
        return biomeWithDefaults().temperature(-0.5F).specialEffects(defaultSpecialEffects().build());
    }

    private static Biome.BiomeBuilder hellWithDefaults() {
        return biomeWithDefaults().temperature(2F).specialEffects(defaultSpecialEffects().grassColorOverride(0xFF2E1F33).foliageColorOverride(0xFF2E1F33).build());
    }

    private static Biome.BiomeBuilder deepWithDefaults() {
        return biomeWithDefaults().temperature(0.2F).specialEffects(defaultSpecialEffects().build());
    }

    private static Biome.BiomeBuilder biomeWithDefaults() {
        return new Biome.BiomeBuilder().mobSpawnSettings(new MobSpawnSettings.Builder().build()).downfall(0F).hasPrecipitation(false);
    }

    private static BiomeSpecialEffects.Builder defaultSpecialEffects() {
        return new BiomeSpecialEffects.Builder()
                .fogColor(12638463)
                .waterColor(4159204)
                .waterFogColor(329011)
                .skyColor(0);
    }

    private static BiomeGenerationSettings.Builder defaultBiomeGen(HolderGetter<PlacedFeature> placedGetter, HolderGetter<ConfiguredWorldCarver<?>> carver) {
        return new BiomeGenerationSettings.Builder(placedGetter, carver);
    }
}
