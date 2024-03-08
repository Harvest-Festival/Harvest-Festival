package uk.joshiejack.harvestfestival.world.item.tool;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HoeItem extends net.minecraft.world.item.HoeItem {
    private final HFTiers tier;
    public HoeItem(HFTiers tier, float speed, Properties properties) {
        super(tier, (int) -tier.getAttackDamageBonus(), speed, properties);
        this.tier = tier;
    }

    //TODO: Make use of AreaOfEffect to make hoes function correctly
    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return tier == HFTiers.CURSED ? !pStack.isEnchanted() : super.isFoil(pStack);
    }
}
