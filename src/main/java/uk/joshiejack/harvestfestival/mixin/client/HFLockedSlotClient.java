package uk.joshiejack.harvestfestival.mixin.client;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.joshiejack.harvestfestival.world.inventory.BagUpgrade;

@Mixin(Slot.class)
public class HFLockedSlotClient {
    @Final
    @Shadow
    private int slot;

    @Shadow
    @Final
    public Container container;

    @Inject(method = "isActive", at = @At("HEAD"), cancellable = true)
    private void lockedSlotActive(CallbackInfoReturnable<Boolean> callback) {
        BagUpgrade.applyLock(callback, false, container, slot);
    }
}
