package uk.joshiejack.harvestfestival.world.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import uk.joshiejack.penguinlib.world.inventory.AbstractBookMenu;

import javax.annotation.Nonnull;

public class HFBookMenu extends AbstractBookMenu {
    public HFBookMenu(int windowID) {
        super(HFMenus.BOOK.get(), windowID);
    }

    @Override
    public @Nonnull ItemStack quickMoveStack(@Nonnull Player player, int id) {
        return ItemStack.EMPTY;
    }
}

