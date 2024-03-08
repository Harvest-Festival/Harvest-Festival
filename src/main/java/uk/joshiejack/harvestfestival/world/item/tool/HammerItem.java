package uk.joshiejack.harvestfestival.world.item.tool;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import org.jetbrains.annotations.NotNull;

public class HammerItem extends PickaxeItem {
    private final HFTiers tier;
    public HammerItem(HFTiers tier, Properties properties) {

        super(tier, 2, -3F, properties);
        this.tier = tier;
    }

    //TODO: Make use of AreaOfEffect to make hammers function correctly
    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return tier == HFTiers.CURSED ? !pStack.isEnchanted() : super.isFoil(pStack);
    }
}
