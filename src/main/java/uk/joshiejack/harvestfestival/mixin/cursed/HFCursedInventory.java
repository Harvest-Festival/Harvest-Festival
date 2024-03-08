package uk.joshiejack.harvestfestival.mixin.cursed;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.world.item.tool.ToolEvents;

@Mixin(Inventory.class)
public class HFCursedInventory {
    @Shadow public int selected;
    @Final
    @Shadow
    public NonNullList<ItemStack> items;

    @Inject(method = "swapPaint", at = @At("HEAD"), cancellable = true)
    private void swapPaint(double pDirection, CallbackInfo ci) {
        if (HFConfig.cursedToolsUnequippable.get() && ToolEvents.isCursed(items.get(selected)))
            ci.cancel();
    }
}
