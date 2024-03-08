package uk.joshiejack.harvestfestival.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.data.mine.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HFDatapackEntries extends DatapackBuiltinEntriesProvider {
    public HFDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, new RegistrySetBuilder()
                .add(Registries.DIMENSION_TYPE, HFMineDimension::bootstrapDimension)
                .add(Registries.BIOME, HFMineBiomes::bootstrapBiomes)
                .add(Registries.LEVEL_STEM, HFMineDimension::registerDimension)
                .add(Registries.CONFIGURED_FEATURE, (ctx) -> {
                    EarthenZoneFeatures.bootstrap(null, ctx);
                    FrozenZoneFeatures.bootstrap(null, ctx);
                    HellZoneFeatures.bootstrap(null, ctx);
                    SubterraneanAbyss.bootstrap(null, ctx);
                })
                .add(Registries.PLACED_FEATURE, (ctx) -> {
                    EarthenZoneFeatures.bootstrap(ctx, null);
                    FrozenZoneFeatures.bootstrap(ctx, null);
                    HellZoneFeatures.bootstrap(ctx, null);
                    SubterraneanAbyss.bootstrap(ctx, null);
                }), Set.of("minecraft", HarvestFestival.MODID));
    }
}