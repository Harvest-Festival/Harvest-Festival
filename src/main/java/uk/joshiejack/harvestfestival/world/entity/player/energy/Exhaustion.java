package uk.joshiejack.harvestfestival.world.entity.player.energy;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.HFTags;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class Exhaustion {
    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().hasEffect(HFEffects.EXHAUSTED.get()) && event.getItemStack().is(HFTags.Items.REQUIRES_ENERGY))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity().hasEffect(HFEffects.EXHAUSTED.get())  && !event.getItemStack().getItem().isEdible()
                && event.getItemStack().is(HFTags.Items.REQUIRES_ENERGY))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity().hasEffect(HFEffects.EXHAUSTED.get()) && event.getItemStack().is(HFTags.Items.REQUIRES_ENERGY))
            event.setCanceled(true);
    }
}
