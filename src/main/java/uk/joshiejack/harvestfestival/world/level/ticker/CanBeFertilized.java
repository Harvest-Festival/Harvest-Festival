package uk.joshiejack.harvestfestival.world.level.ticker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;

public interface CanBeFertilized {
    Fertilizer fertilizer();

    boolean fertilize(Level level, BlockPos clickedPos, BlockState blockState, Fertilizer fertilizer);
}