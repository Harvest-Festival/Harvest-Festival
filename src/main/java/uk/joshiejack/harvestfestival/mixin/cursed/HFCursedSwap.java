package uk.joshiejack.harvestfestival.mixin.cursed;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.world.item.tool.ToolEvents;

/**
 * Prevents cursed tools from being swapped
 */
@Mixin(AbstractContainerMenu.class)
public class HFCursedSwap {
    @Final
    @Shadow
    public NonNullList<Slot> slots;

    @Inject(method = "doClick", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/player/Inventory.getItem (I)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER), cancellable = true)
    private void swap(int pSlotId, int pButton, ClickType pClickType, Player pPlayer, CallbackInfo ci) {
        if (pPlayer.isCreative() || !HFConfig.cursedToolsUnequippable.get()) return;
        ItemStack itemstack2 = pPlayer.getInventory().getItem(pButton);
        ItemStack itemstack7 = slots.get(pSlotId).getItem();
        if (ToolEvents.isCursed(itemstack2) || ToolEvents.isCursed(itemstack7))
            ci.cancel();
    }
}
