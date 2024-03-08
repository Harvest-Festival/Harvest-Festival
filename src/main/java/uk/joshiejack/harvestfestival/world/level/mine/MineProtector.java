package uk.joshiejack.harvestfestival.world.level.mine;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class MineProtector {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getEntity().level().isClientSide()) return;
        if (MineHelper.isMineWorld((ServerLevel) event.getEntity().level()) && event.getPosition().get().getY() %6 == 1) event.setNewSpeed(0F);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDetonate(ExplosionEvent.Detonate event) {
        if (event.getLevel().isClientSide()) return;
        if (MineHelper.isMineWorld((ServerLevel) event.getLevel())) {
            event.getAffectedBlocks().removeIf(e -> e.getY() % 6 == 1);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHarvestBlock(PlayerEvent.HarvestCheck event) {
        if (event.getEntity().level().isClientSide()) return;
        if (MineHelper.isMineWorld((ServerLevel) event.getEntity().level()) && event.getEntity().getY() %6 == 1) event.setCanHarvest(false);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (MineHelper.isMineWorld((ServerLevel) event.getPlayer().level()) && event.getPos().getY() %6 == 1) event.setCanceled(true);
    }
}
