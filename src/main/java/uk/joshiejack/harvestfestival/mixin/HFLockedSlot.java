package uk.joshiejack.harvestfestival.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.joshiejack.harvestfestival.world.inventory.BagUpgrade;

@Mixin(Slot.class)
public class HFLockedSlot {
    @Final
    @Shadow
    private int slot;

    @Shadow
    @Final
    public Container container;

    @Inject(method = "mayPickup", at = @At(value = "HEAD"), cancellable = true)
    private void lockedPickup(Player player, CallbackInfoReturnable<Boolean> cir) {
        BagUpgrade.applyLock(cir, false, container, slot);
    }

    @Inject(method = "mayPlace", at = @At(value = "HEAD"), cancellable = true)
    private void lockedPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        BagUpgrade.applyLock(cir, false, container, slot);
    }

    @Inject(method = "remove", at = @At(value = "HEAD"), cancellable = true)
    private void lockedRemove(int pAmount, CallbackInfoReturnable<ItemStack> cir) {
        BagUpgrade.applyLock(cir, ItemStack.EMPTY, container, slot);
    }
}