package uk.joshiejack.harvestfestival.data.farming;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.item.SeedlingData;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;

import java.util.Map;

public class HFSeedlingProvider extends AbstractPenguinRegistryProvider<SeedlingData> {
    public HFSeedlingProvider(PackOutput output) {
        super(output, HFRegistries.SEEDLINGS);
    }

    @Override
    public @NotNull String getName() {
        return "Seedlings";
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, SeedlingData> map) {
        map.put(HarvestFestival.prefix("oak"), SeedlingData.of(HFBlocks.OAK_SEEDLING.get(), "#B18D57"));
        map.put(HarvestFestival.prefix("spruce"), SeedlingData.of(HFBlocks.SPRUCE_SEEDLING.get(), "#826038"));
        map.put(HarvestFestival.prefix("birch"), SeedlingData.of(HFBlocks.BIRCH_SEEDLING.get(), "#D8D8D8"));
        map.put(HarvestFestival.prefix("jungle"), SeedlingData.of(HFBlocks.JUNGLE_SEEDLING.get(), "#AE8054"));
        map.put(HarvestFestival.prefix("acacia"), SeedlingData.of(HFBlocks.ACACIA_SEEDLING.get(), "#AC5A3B"));
        map.put(HarvestFestival.prefix("dark_oak"), SeedlingData.of(HFBlocks.DARK_OAK_SEEDLING.get(), "#50412C"));
        map.put(HarvestFestival.prefix("cherry"), SeedlingData.of(HFBlocks.CHERRY_SEEDLING.get(), "#E1A3C3"));
        map.put(HarvestFestival.prefix("mangrove"), SeedlingData.of(HFBlocks.MANGROVE_SEEDLING.get(), "#514C09"));
    }
}
