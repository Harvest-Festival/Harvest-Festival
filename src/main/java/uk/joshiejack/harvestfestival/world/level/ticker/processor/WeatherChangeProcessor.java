package uk.joshiejack.harvestfestival.world.level.ticker.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

import java.util.LinkedList;

public class WeatherChangeProcessor extends UpdateProcessor {
    public WeatherChangeProcessor(ServerLevel level, LinkedList<Pair<BlockPos, DailyTicker<?>>> list) {
        super(level, list);
    }

    @SubscribeEvent
    @Override
    public void tick(TickEvent.LevelTickEvent event) {
        super.tick(event);
    }

    @Override
    protected void process(ServerLevel level, BlockPos pos, BlockState state, DailyTicker<?> ticker) {
        ticker.onWeatherChanged(level, pos, state);
    }
}
