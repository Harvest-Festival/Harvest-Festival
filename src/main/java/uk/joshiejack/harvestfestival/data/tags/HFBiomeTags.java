package uk.joshiejack.harvestfestival.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uk.joshiejack.harvestfestival.HarvestFestival;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HFBiomeTags extends BiomeTagsProvider {
    public HFBiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, HarvestFestival.MODID, helper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
//        itemTag(BiomeTags.SPAWNS_WHITE_RABBITS).add(HFMineBiomes.FROSTWOOD_GLADE, HFMineBiomes.GLACIAL_SANCTUM, HFMineBiomes.FROZEN_FORTRESS, HFMineBiomes.SNOWDRIFT_CAVERNS);
//        itemTag(BiomeTags.SPAWNS_SNOW_FOXES).add(HFMineBiomes.FROSTWOOD_GLADE, HFMineBiomes.GLACIAL_SANCTUM, HFMineBiomes.FROZEN_FORTRESS, HFMineBiomes.SNOWDRIFT_CAVERNS);
//        itemTag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).add(HFMineBiomes.FROSTWOOD_GLADE, HFMineBiomes.GLACIAL_SANCTUM, HFMineBiomes.FROZEN_FORTRESS, HFMineBiomes.SNOWDRIFT_CAVERNS);
    }
}
