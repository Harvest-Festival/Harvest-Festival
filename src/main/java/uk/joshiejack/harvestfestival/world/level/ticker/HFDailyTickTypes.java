package uk.joshiejack.harvestfestival.world.level.ticker;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.CropTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.SoilTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.AlwaysGrowStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.NumberBasedStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.PatternBasedStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.data.QualityRetainingBlock;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.GrowthTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.SpreadableTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.JuvenileTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.SaplingTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.SeedlingTicker;

public class HFDailyTickTypes {
    public static final DeferredRegister<Codec<? extends DailyTicker<?>>> DAILY_TICKER_TYPES = DeferredRegister.create(HFRegistries.Keys.TICKER_TYPE, HarvestFestival.MODID);
    public static final DeferredRegister<Codec<? extends StageHandler<?>>> STAGE_HANDLER_TYPES = DeferredRegister.create(HFRegistries.Keys.STAGE_HANDLER, HarvestFestival.MODID);

    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<SoilTicker>>> SOIL = DAILY_TICKER_TYPES.register("soil", () -> SoilTicker.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<CropTicker>>> CROP = DAILY_TICKER_TYPES.register("crop", () -> CropTicker.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<QualityRetainingBlock>>> QUALITY_RETAINING = DAILY_TICKER_TYPES.register("quality_retaining", () -> QualityRetainingBlock.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<SeedlingTicker>>> SEEDLING = DAILY_TICKER_TYPES.register("seedling", () -> SeedlingTicker.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<SaplingTicker>>> SAPLING = DAILY_TICKER_TYPES.register("sapling", () -> SaplingTicker.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<JuvenileTicker>>> JUVENILE = DAILY_TICKER_TYPES.register("juvenile", () -> JuvenileTicker.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<SpreadableTicker>>> SPREADABLE = DAILY_TICKER_TYPES.register("spreadable", () -> SpreadableTicker.CODEC);
    public static final DeferredHolder<Codec<? extends DailyTicker<?>>, Codec<? extends DailyTicker<GrowthTicker>>> GROWTH = DAILY_TICKER_TYPES.register("growth", () -> GrowthTicker.CODEC);

    public static final DeferredHolder<Codec<? extends StageHandler<?>>, Codec<? extends StageHandler<AlwaysGrowStageHandler>>> ALWAYS = STAGE_HANDLER_TYPES.register("always", () -> AlwaysGrowStageHandler.CODEC);
    public static final DeferredHolder<Codec<? extends StageHandler<?>>, Codec<? extends StageHandler<NumberBasedStageHandler>>> NUMBERED = STAGE_HANDLER_TYPES.register("numbered", () -> NumberBasedStageHandler.CODEC);
    public static final DeferredHolder<Codec<? extends StageHandler<?>>, Codec<? extends StageHandler<PatternBasedStageHandler>>> PATTERN = STAGE_HANDLER_TYPES.register("pattern", () -> PatternBasedStageHandler.CODEC);

    public static final ResourceLocation FOUR_DAYS = new ResourceLocation(HarvestFestival.MODID, "four_days");
    public static final ResourceLocation EIGHT_DAYS = new ResourceLocation(HarvestFestival.MODID, "eight_days");
    public static final ResourceLocation WHEAT = new ResourceLocation(HarvestFestival.MODID, "wheat");
    public static final ResourceLocation PUMPKIN = new ResourceLocation(HarvestFestival.MODID, "pumpkin");
    public static final ResourceLocation MELON = new ResourceLocation(HarvestFestival.MODID, "melon");
    public static final ResourceLocation TORCH_FLOWER = new ResourceLocation(HarvestFestival.MODID, "torch_flower");
    public static void register(IEventBus eventBus) {
        DAILY_TICKER_TYPES.register(eventBus);
        STAGE_HANDLER_TYPES.register(eventBus);
    }
}
