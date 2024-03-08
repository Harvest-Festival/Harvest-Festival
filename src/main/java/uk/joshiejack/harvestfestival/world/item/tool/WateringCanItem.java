package uk.joshiejack.harvestfestival.world.item.tool;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.penguinlib.world.item.AbstractWateringCanItem;

import java.util.Set;

public class WateringCanItem extends AbstractWateringCanItem {
    private boolean isCursed;
    private final int width;
    private final int depth;

    public WateringCanItem(int width, int depth, Properties properties) {
        super(properties);
        this.width = width;
        this.depth = depth;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return isCursed ? !pStack.isEnchanted() : super.isFoil(pStack);
    }

    public WateringCanItem setAsCursed() {
        this.isCursed = true;
        return this;
    }
    
    @Override
    public Set<BlockPos> getPositions(Player player, Level level, BlockPos pos) {
        Set<BlockPos> set = Sets.newHashSet();
        Direction front = player.getDirection();
        for (int w = -depth; w <= depth; w++) {
            for (int h = -1; h <= 0; h++) {
                for (int d = 0; d <= width; d++) {
                    set.add(front == Direction.EAST || front == Direction.WEST ?
                            pos.offset(((front == Direction.WEST) ? d : -d), h, w) :
                            pos.offset(w, h, ((front == Direction.NORTH) ? d : -d)));
                }
            }
        }

        return set;
    }
}
