package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import uk.joshiejack.harvestfestival.HFConfig;

@Mixin(Container.class)
public interface HFContainer {
    /**
     * @author  joshiejack
     * @reason  To change the default max stack size
     */
    @Overwrite
    default int getMaxStackSize() {
        return HFConfig.maxStackSize.get();
    }
}
