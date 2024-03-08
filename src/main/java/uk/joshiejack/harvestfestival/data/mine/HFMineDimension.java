package uk.joshiejack.harvestfestival.data.mine;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

import java.util.OptionalLong;

public class HFMineDimension {
    private static final ResourceKey<DimensionType> MINE_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, HarvestFestival.prefix("mine_type"));
    private static final ResourceKey<LevelStem> MINE_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, HarvestFestival.prefix("mine"));

    static DimensionType getMineDimType() {
        return new DimensionType(
                OptionalLong.empty(),
                false,
                true,
                false,
                false,
                1.0D,
                false,
                false,
                -320,
                320,
                320,
                BlockTags.INFINIBURN_OVERWORLD,
                new ResourceLocation("overworld"),
                0F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 0), 0)
        );
    }

    public static void registerDimension(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderSet<MineTier> tiers = HolderSet.direct(EarthenZoneFeatures.buildEarthenZone(biomeRegistry),
                FrozenZoneFeatures.buildFrozenTier(biomeRegistry), HellZoneFeatures.buildHellTier(biomeRegistry),
                SubterraneanAbyss.buildDeepDark(biomeRegistry));
        MineSettings settings = new MineSettings(tiers, 10, 8, 5, 0);
        LevelStem stem = new LevelStem(dimTypes.getOrThrow(MINE_DIM_TYPE), new MineChunkGenerator(settings));
        context.register(MINE_LEVEL_STEM, stem);
    }

    public static void bootstrapDimension(BootstapContext<DimensionType> context) {
        context.register(MINE_DIM_TYPE, HFMineDimension.getMineDimType());
    }
}
