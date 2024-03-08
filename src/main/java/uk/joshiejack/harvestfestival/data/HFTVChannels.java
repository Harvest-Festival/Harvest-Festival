package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.television.TVChannel;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;

import java.util.Map;

public class HFTVChannels extends AbstractPenguinRegistryProvider<TVChannel> {
    public HFTVChannels(PackOutput output) {
        super(output, HFRegistries.TV_CHANNELS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, TVChannel> map) {
        addTVChannel(map, "weather", true);
        addTVChannel(map, "cooking", true);
        addTVChannel(map, "farming", true);
        addTVChannel(map, "fishing", true);
        addTVChannel(map, "adult", false);
    }

    private void addTVChannel(Map<ResourceLocation, TVChannel> map, String id, boolean selectable) {
        map.put(HarvestFestival.prefix(id), new TVChannel(HarvestFestival.prefix("tv_channels/" + id), selectable));
    }
}
