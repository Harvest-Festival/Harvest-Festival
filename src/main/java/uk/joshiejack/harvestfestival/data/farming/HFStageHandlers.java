package uk.joshiejack.harvestfestival.data.farming;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.world.level.ticker.HFDailyTickTypes;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.AlwaysGrowStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.NumberBasedStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.PatternBasedStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;

import java.util.List;
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
        handlers.put(HFDailyTickTypes.MELON, new PatternBasedStageHandler(true, true, true, false, true, true, true, false, true, true));
        handlers.put(HFDailyTickTypes.TORCH_FLOWER, new PatternBasedStageHandler(false, false, true, false, false, true));
        handlers.put(HFDailyTickTypes.TEXTURE_3_DAYS_5, new PatternBasedStageHandler(false, false, true, false, true));
        handlers.put(HFDailyTickTypes.ONION, new PatternBasedStageHandler(false, false, false, true, false, false, false, true));
        handlers.put(HFDailyTickTypes.TEXTURE_4_DAYS_9 , new PatternBasedStageHandler(false, false, true, false, false, true, false, false, true));
        handlers.put(HFDailyTickTypes.TEXTURE_4_DAYS_10, new PatternBasedStageHandler(false, false, true, false, false, false, true, false, false, true));
        handlers.put(HFDailyTickTypes.CABBAGE, new PatternBasedStageHandler(false, false, false, false, true, false, false, false, false, true, false, false, false, false, true));
        handlers.put(HFDailyTickTypes.TOMATO, new NumberBasedStageHandler(4, List.of(2, 4, 6, 9)));
        handlers.put(HFDailyTickTypes.PINEAPPLE, new NumberBasedStageHandler(4, List.of(5, 10, 15, 20)));
        handlers.put(HFDailyTickTypes.CORN, new NumberBasedStageHandler(4, List.of(3, 8, 12, 14)));
        handlers.put(HFDailyTickTypes.SWEET_POTATO, new NumberBasedStageHandler(2, List.of(3, 5)));
        handlers.put(HFDailyTickTypes.GREEN_PEPPER, new NumberBasedStageHandler(4, List.of(1, 3, 4, 7)));
    }
}
