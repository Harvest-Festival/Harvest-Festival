package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import uk.joshiejack.harvestfestival.HFConfig;

@Mixin(ServerGamePacketListenerImpl.class)
public class HFCreativeOverride {
    @ModifyConstant(method = "handleSetCreativeModeSlot", constant = @Constant(intValue = 64))
    private int increaseStackLimit(int value) {
        return HFConfig.maxStackSize.get();
    }
}
