package uk.joshiejack.harvestfestival.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HFEntityTags extends EntityTypeTagsProvider {
    public HFEntityTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, HarvestFestival.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        // Make frost slime entity frog food itemTag
        tag(HFTags.Entities.SLIMES).add(EntityType.SLIME, EntityType.MAGMA_CUBE, HFEntities.FROST_SLIME.get());
        tag(EntityTypeTags.FROG_FOOD).add(HFEntities.FROST_SLIME.get());
        tag(HFTags.Entities.AWARDS_HIGH_COMBAT_EXPERIENCE).addTags(Tags.EntityTypes.BOSSES);
        tag(HFTags.Entities.AWARDS_LOW_COMBAT_EXPERIENCE).addTags(HFTags.Entities.SLIMES);
    }
}
