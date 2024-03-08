package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.SpreadableNotifier;

import javax.annotation.Nullable;

public class StarterBlockItem extends BlockItem {
    public StarterBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(@NotNull BlockPos pPos, @NotNull Level pLevel, @Nullable Player pPlayer, @NotNull ItemStack pStack, @NotNull BlockState pState) {
        SpreadableNotifier.markAsSpreadable(pLevel, pPos);
        return super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);
    }
}
