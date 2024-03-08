package uk.joshiejack.harvestfestival.data.farming;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.world.level.ticker.HFDailyTickTypes;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.AlwaysGrowStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.PatternBasedStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;

import java.util.Map;

public class HFStageHandlers extends AbstractStageProvider {
    public HFStageHandlers(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildStages(Map<ResourceLocation, StageHandler<?>> handlers) {
        handlers.put(HFDailyTickTypes.FOUR_DAYS, new AlwaysGrowStageHandler(3));
        handlers.put(HFDailyTickTypes.EIGHT_DAYS, new AlwaysGrowStageHandler(7));
        handlers.put(HFDailyTickTypes.WHEAT, new PatternBasedStageHandler(false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, true, false, false, false, true, false, false, true, false, false, false, true));
        handlers.put(HFDailyTickTypes.PUMPKIN, new PatternBasedStageHandler(true, true, true, false, true, false, true, false, true, false, false, true, false, true));
        handlers.put(HFDailyTickTypes.MELON, new PatternBasedStageHandler(true,true,true,false,true,true,true,false,true,true));
        handlers.put(HFDailyTickTypes.TORCH_FLOWER, new PatternBasedStageHandler(false, false, true, false, false, true));
    }
}
