package uk.joshiejack.harvestfestival.mixin.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.world.item.tool.ToolEvents;

import java.util.Arrays;

@Mixin(KeyMapping.class)
public class HFCursedKeyMapping {
    @Inject(method = "isDown", at = @At(value = "HEAD"), cancellable = true)
    private void cursedIsDown(CallbackInfoReturnable<Boolean> cir) {
        harvestFestival$cancelCallback(cir);
    }
    @Inject(method = "consumeClick", at = @At(value = "HEAD"), cancellable = true)
    private void consumeClick(CallbackInfoReturnable<Boolean> cir) {
        harvestFestival$cancelCallback(cir);
    }

    @Unique
    private void harvestFestival$cancelCallback(CallbackInfoReturnable<Boolean> cir) {
        if (!HFConfig.cursedToolsUnequippable.get()) return;
        KeyMapping mapping = (KeyMapping) (Object) this;
        Minecraft minecraft = Minecraft.getInstance();
        if (mapping == minecraft.options.keyDrop || Arrays.binarySearch(minecraft.options.keyHotbarSlots, mapping) >= 0) {
            Inventory inventory = minecraft.player.getInventory();
            if (ToolEvents.isCursed(inventory.items.get(inventory.selected))) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
