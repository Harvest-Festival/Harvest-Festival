package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.penguinlib.util.helper.MathHelper;

@Mixin(Item.class)
public class HFItemProperties {
    @Shadow @Final private int maxStackSize;

    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
    private void getMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        //Scale the maximum stack size based on 64
        cir.setReturnValue(MathHelper.convertRange(1, 64, 1, HFConfig.maxStackSize.get(), maxStackSize));
    }
}
