package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.world.Containers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import uk.joshiejack.harvestfestival.HFConfig;

@Mixin(Containers.class)
public class HFContainers {
    @ModifyConstant(method = "dropItemStack", constant = { @Constant(intValue = 21), @Constant(intValue = 10) })
    private static int reworkDropScaling(int value) {
        return Math.max(value, (value / 64) * HFConfig.maxStackSize.get());
    }
}