package uk.joshiejack.harvestfestival.data;

import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import uk.joshiejack.harvestfestival.HarvestFestival;

import java.util.concurrent.CompletableFuture;

public class HFSpriteSourceProvider extends SpriteSourceProvider {
    public HFSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        super(output, lookupProvider, HarvestFestival.MODID, fileHelper);
    }

    @Override
    protected void gather() {
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new DirectoryLister("fertilizer", "fertilizer/"));
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new DirectoryLister("quality", "quality/"));
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new DirectoryLister("tv_program", "tv_program/"));
    }
}
