package uk.joshiejack.harvestfestival.world.level.ticker.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.farming.SeasonHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.penguinlib.util.helper.FakePlayerHelper;

public abstract class AbstractGrowingTreeTicker<DT extends DailyTicker<DT>> implements DailyTicker<DT> {
    protected int age;

    public AbstractGrowingTreeTicker(int age) {
        this.age = age;
    }

    protected boolean canGrow(ServerLevel world, BlockPos pos, BlockState state) {
        return SeasonHandler.isInSeason(world, pos, state);
    }

    private TreeData getData(BlockState state) {
        TreeData data = state.getBlockHolder().getData(HFData.TREE_DATA);
        return data == null ? TreeData.NONE : data;
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        TreeData data = getData(state);
        if (canGrow(level, pos, state)) {
            age++;
            if (age >= data.days()) {
                grow(level, pos, data.next());
            }
        }
    }

    protected void grow(ServerLevel world, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, state); //Soooooooooooooooooooooooooooooooooooooooooooooooo oooooooooooooooooooooooo let's get to the same length
        state.getBlock().setPlacedBy(world, pos, state, FakePlayerHelper.getFakePlayerWithPosition(world, pos), ItemStack.EMPTY); //Just cause
    }
}
