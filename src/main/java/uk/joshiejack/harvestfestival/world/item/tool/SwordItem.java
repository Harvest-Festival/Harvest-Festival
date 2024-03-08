package uk.joshiejack.harvestfestival.world.item.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SwordItem extends net.minecraft.world.item.SwordItem {
    private final HFTiers tier;

    public SwordItem(HFTiers tier, Properties properties) {
        super(tier, 3, -2.4F, properties);
        this.tier = tier;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return tier == HFTiers.CURSED ? !pStack.isEnchanted() : super.isFoil(pStack);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            return state.is(BlockTags.SWORD_EFFICIENT) ? tier.getSpeed() : 1.0F;
        }
    }
}