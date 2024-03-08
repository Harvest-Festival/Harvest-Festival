package uk.joshiejack.harvestfestival.world.collections;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.HFData;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class ObtainEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemFished(ItemFishedEvent event) {
        HFPlayerData obtained = event.getEntity().getData(HFData.PLAYER_DATA);
        event.getDrops().forEach(stack -> obtained.obtain(event.getEntity(), stack.getItem())); //Obtain all the items, Using the lowest priority so that other mods can modify the drops
    }
}
