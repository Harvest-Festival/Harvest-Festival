package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;

import java.util.Map;

public class HFTVPrograms extends AbstractPenguinRegistryProvider<TVProgram> {
    public HFTVPrograms(PackOutput output) {
        super(output, HFRegistries.TV_PROGRAMS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, TVProgram> map) {
        addTVProgram(map, "adult");
        addTVProgram(map, "frying_pan");
        addTVProgram(map, "mixer");
        addTVProgram(map, "cooking_pot");
        addTVProgram(map, "power_berry");
        addTVProgram(map, "weather_clear");
        addTVProgram(map, "weather_rain");
        addTVProgram(map, "weather_snow");
        addTVProgram(map, "weather_storm");
        addTVProgram(map, "weather_blizzard");
        addTVProgram(map, "sword_art_online");
    }

    private void addTVProgram(Map<ResourceLocation, TVProgram> map, String id) {
        map.put(HarvestFestival.prefix(id), new TVProgram(HarvestFestival.prefix("tv_program/" + id)));
    }
}
