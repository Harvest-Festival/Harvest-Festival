package uk.joshiejack.harvestfestival.world.level.ticker.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public abstract class UpdateProcessor {
    protected final ServerLevel level;
    protected final LinkedList<Pair<BlockPos, DailyTicker<?>>> queue;

    public UpdateProcessor(ServerLevel level, LinkedList<Pair<BlockPos, DailyTicker<?>>> list) {
        this.level = level;
        this.queue = list;
    }

    protected static boolean isWrongTypeForState(DailyTicker<?> type, DailyTicker<?> type2) {
        if (type == null || type2 == null) return true;
        return !Objects.equals(HFRegistries.TICKER_TYPE.getKey(type.codec()), HFRegistries.TICKER_TYPE.getKey(type2.codec()));
    }

    public void tick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase != TickEvent.Phase.END) return;
        //Go through each phase and process the tickers
        int i;
        for (i = 0; i < HFConfig.tickersPerMinecraftTick.get(); i++) {
            if (queue.isEmpty()) break; //If the queue is empty, then we should skip this phase
            Pair<BlockPos, DailyTicker<?>> pair = queue.poll();
            BlockState state = level.getBlockState(pair.getLeft());
            //Check if it is still valid
            if (state.isAir() || isWrongTypeForState(pair.getRight(), state.getBlockHolder().getData(HFData.TICKERS))) {
                continue;
            }

            process(level, pair.getLeft(), level.getBlockState(pair.getLeft()), pair.getRight());
            //pair.getRight().tick(level, pair.getLeft(), level.getBlockState(pair.getLeft()));
        }

        if (!queue.isEmpty()) {
            return;
        }

        NeoForge.EVENT_BUS.unregister(this);
    }

    protected abstract void process(ServerLevel level, BlockPos pos, BlockState state, DailyTicker<?> ticker);

    public static void process(ServerLevel world, BiConsumer<ServerLevel, LinkedList<Pair<BlockPos, DailyTicker<?>>>> consumer) {
        //Split this up so that we don't process all the entries at once
        consumer.accept(world, world.getChunkSource().chunkMap.visibleChunkMap.values().stream()
                .filter(holder -> holder.getFullChunk() != null && holder.getFullChunk().hasData(HFData.DAILY_TICKER_DATA))
                .map(e -> {
                    e.getFullChunk().setUnsaved(true); //Mark the chunk as unsaved
                    return e.getFullChunk().getData(HFData.DAILY_TICKER_DATA).getSorted();
                })
                .flatMap(List::stream)
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll));

    }
}
