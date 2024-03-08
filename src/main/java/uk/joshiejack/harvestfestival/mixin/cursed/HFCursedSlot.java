package uk.joshiejack.harvestfestival.mixin.cursed;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.world.item.tool.ToolEvents;

@Mixin(Slot.class)
public abstract class HFCursedSlot {
    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    @Final
    private int slot;

    @Inject(method = "mayPickup", at = @At(value = "HEAD"), cancellable = true)
    private void hfCursedPickup(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player.isCreative() || !HFConfig.cursedToolsUnequippable.get()) return;
        if ((slot == player.getInventory().selected || slot == 40) && ToolEvents.isCursed(getItem())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
