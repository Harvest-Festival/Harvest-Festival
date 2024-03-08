package uk.joshiejack.harvestfestival.world.level.ticker.growable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class SpreadableNotifier {
    private static final Multimap<ResourceKey<Level>, BlockPos> SPREADABLES = HashMultimap.create();

    public static void markAsSpreadable(Level world, BlockPos pos) {
        SPREADABLES.get(world.dimension()).add(pos);
    }

    @SubscribeEvent
    public static void onNotifying(BlockEvent.NeighborNotifyEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            Collection<BlockPos> set = SPREADABLES.get(level.dimension());
            if (set.contains(event.getPos())) {
                DailyTicker<?> entry = DailyTicker.get(level, event.getPos());
                if (entry instanceof SpreadableTicker) {
                    ((SpreadableTicker) entry).setStarter();
                }

                set.remove(event.getPos()); //Remove it no matter what
                level.getChunk(event.getPos()).setUnsaved(true);
            }
        }
    }
}

