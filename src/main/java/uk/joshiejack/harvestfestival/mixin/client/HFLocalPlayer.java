package uk.joshiejack.harvestfestival.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class HFLocalPlayer {
    @Inject(method = "hasEnoughFoodToStartSprinting", at = @At("HEAD"), cancellable = true)
    private void hasPlentyOfFood(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}