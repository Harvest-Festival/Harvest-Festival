package uk.joshiejack.harvestfestival.world.entity.player.energy;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.entity.player.SleepingTimeCheckEvent;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class Sleep {
    @SubscribeEvent
    public static void onSleepTimeCheck(SleepingTimeCheckEvent event) {
        if (HFConfig.sleepAnytime.get())
            event.setResult(Event.Result.ALLOW);
    }

    @SubscribeEvent
    public static void onWakeup(PlayerWakeUpEvent event) {
        event.getEntity().getPersistentData().putLong("LastSlept", event.getEntity().level().getGameTime()); //Update the last sleeping timer
        EnergyUse.updateEffectInstances(event.getEntity());
        if (HFConfig.sleepRestoresEnergy.get()) {
            Level world = event.getEntity().level();
            if (!world.isClientSide && event.getEntity().getFoodData() instanceof EnergyData data && world.getDayTime() % 24000 == 0) {
                boolean fatigued = event.getEntity().hasEffect(HFEffects.FATIGUED.get());
                boolean exhausted = event.getEntity().hasEffect(HFEffects.EXHAUSTED.get());
                data.setEnergyLevel((int) (data.getMaxEnergy() * (fatigued ? 0.75 : exhausted ? 0.5 : 1)));
            }
        }
    }
}
