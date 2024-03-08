package uk.joshiejack.harvestfestival.world.entity.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.entity.player.energy.ReplaceFood;
import uk.joshiejack.penguinlib.util.helper.PlayerHelper;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class ServerConnect {
    @SubscribeEvent
    public static void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CompoundTag tag = PlayerHelper.getPenguinStatuses(player);
            if (!tag.contains("Birthday"))
                tag.putLong("Birthday", player.level().getDayTime());
            Objects.requireNonNull(player.level().getServer()).submit(() -> {
                ReplaceFood.replaceFoodStats(player);
            });
        }
    }
}
