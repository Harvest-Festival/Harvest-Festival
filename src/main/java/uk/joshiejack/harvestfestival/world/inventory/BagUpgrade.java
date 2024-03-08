package uk.joshiejack.harvestfestival.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.HFData;

public class BagUpgrade {
    @SuppressWarnings("rawtypes, unchecked")
    public static boolean applyLock(CallbackInfoReturnable cir, Object defaultRet, Container container, int slot) {
        if (!HFConfig.enableBagUpgrades.get()) return false; // If bag upgrades are disabled, don't do anything
        Player player = container instanceof Inventory inventory ? inventory.player : null;
        if (player != null && !player.isCreative() && !player.getData(HFData.PLAYER_DATA).isUnlocked(slot)) {
            cir.setReturnValue(defaultRet);
            cir.cancel();
            return true;
        }

        return false;
    }

    public static boolean isUnlocked(Inventory inventory, int slot) {
        return inventory.player.isCreative() || inventory.player.getData(HFData.PLAYER_DATA).isUnlocked(slot);
    }
}
