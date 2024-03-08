package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.farming.Quality;

import java.util.Objects;

public class HFQualityModels extends ModelProvider<ItemModelBuilder> {
    public HFQualityModels(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, "quality", ItemModelBuilder::new, existingFileHelper);
    }

    public ItemModelBuilder basic(Quality quality) {
        return basicItem(Objects.requireNonNull(HFRegistries.QUALITY.getKey(quality)));
    }

    public ItemModelBuilder basicItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "quality/" + item.getPath()));
    }

    @Override
    public @NotNull String getName() {
        return "Quality Models: " + modid;
    }

    @Override
    protected void registerModels() {
        basic(HFFarming.QualityLevels.ROTTEN.get());
        basic(HFFarming.QualityLevels.SILVER.get());
        basic(HFFarming.QualityLevels.GOLD.get());
        basic(HFFarming.QualityLevels.MYSTRIL.get());
    }
}
