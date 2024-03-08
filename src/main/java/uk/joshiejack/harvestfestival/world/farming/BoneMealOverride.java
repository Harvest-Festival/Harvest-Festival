package uk.joshiejack.harvestfestival.world.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.renderer.fertilizer.FertilizerLevelOverlay;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.level.ticker.CanBeFertilized;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class BoneMealOverride {
    /**
     * Here we check if the block has the ability to be fertilized
     * If so we then allow the bonemeal to be used on it
     * Applying our own fertilizer effect instead of the vanilla method
     *
     * If the block is a daily ticker, we cancel the event
     */
    @SubscribeEvent
    public static void onBoneMealApplied(BonemealEvent event) {
        if (!isFertilized(event.getLevel(), event.getPos())){
            event.setResult(Event.Result.ALLOW);
            if (event.getLevel() instanceof ServerLevel level) {
                DailyTicker<?> ticker = DailyTicker.get(level, event.getPos());
                if (ticker instanceof CanBeFertilized canBeFertilized && canBeFertilized.fertilizer() == HFFarming.Fertilizers.NONE.get())
                    canBeFertilized.fertilize(level, event.getPos(), event.getBlock(), HFFarming.Fertilizers.BONE_MEAL.get());
            }
        } else if (DailyTicker.has(event.getBlock())) {
            event.setCanceled(true);
        }
    }

    private static boolean isFertilized(Level level, BlockPos pos) {
        return level.isClientSide ? FertilizerLevelOverlay.isFertilized(pos) : level.getBlockState(pos).getBlockHolder().getData(HFData.TICKERS)
                instanceof CanBeFertilized canBeFertilized && canBeFertilized.fertilizer() != HFFarming.Fertilizers.NONE.get();
    }
}