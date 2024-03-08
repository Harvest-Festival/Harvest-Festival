package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import uk.joshiejack.harvestfestival.HFConfig;

@Mixin(ItemEntity.class)
public class HFItemEntity {
    @ModifyConstant(method = "merge(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", constant = @Constant(intValue = 64))
    private static int getMergeLimit(int val) {
        return Math.max(HFConfig.maxStackSize.get(), 64);
    }
}
