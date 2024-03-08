package uk.joshiejack.harvestfestival.world.level.ticker;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.EventPriority;
import uk.joshiejack.harvestfestival.HFData;

import javax.annotation.Nullable;

public interface DailyTicker<DT extends DailyTicker<DT>> {

    @Nullable
    static DailyTicker<?> get(Level world, BlockPos pos) {
        return DailyTickData.getDataForChunk(world.getChunkAt(pos)).get(pos);
    }

    Codec<DT> codec();

    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    DT createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state);

    default void onStateChanged(ServerLevel world, BlockPos pos, DailyTicker<?> oldTicker, BlockState newState) {}

    default void onWeatherChanged(ServerLevel world, BlockPos pos, BlockState state) {}

    default @Nullable CustomPacketPayload onRemoved(BlockPos pos) {
        return null;
    }

    default void onAdded(ServerLevel world, BlockPos pos) {}

    void tick(ServerLevel world, BlockPos pos, BlockState state);

    static boolean has(BlockState state) {
        return state.getBlockHolder().getData(HFData.TICKERS) != null;
    };
}
