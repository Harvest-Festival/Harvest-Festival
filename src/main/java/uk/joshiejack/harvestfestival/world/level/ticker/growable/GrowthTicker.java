package uk.joshiejack.harvestfestival.world.level.ticker.growable;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.farming.SeasonHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

public class GrowthTicker implements DailyTicker<GrowthTicker> { //TODO: Season Data
    public static final Codec<GrowthTicker> CODEC = Codec.unit(GrowthTicker::new);

    public GrowthTicker() {}

    private GrowthData getData(BlockState state) {
        GrowthData data = state.getBlockHolder().getData(HFData.GROWTH_DATA);
        return data == null ? GrowthData.NONE : data;
    }

    @Override
    public Codec<GrowthTicker> codec() {
        return CODEC;
    }

    @Override
    public GrowthTicker createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new GrowthTicker();
    }

    protected boolean canGrow(ServerLevel level, BlockPos pos, BlockState state) {
        return SeasonHandler.isInSeason(level, pos, state);
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        if (canGrow(level, pos, state))
            state.tick(level, pos, level.random);
    }
}
