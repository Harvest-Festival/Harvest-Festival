package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class GoddessFlowerItem extends BlockItem {
    public GoddessFlowerItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return 50;
    }
}
