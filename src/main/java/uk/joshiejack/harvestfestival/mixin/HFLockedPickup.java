package uk.joshiejack.harvestfestival.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.inventory.BagUpgrade;

@Mixin(Inventory.class)
public abstract class HFLockedPickup implements Container, Nameable {
    @Final
    @Shadow
    public Player player;

    @SuppressWarnings("unchecked")
    @WrapOperation(method = "getFreeSlot", at = @At(value = "INVOKE", target = "net/minecraft/core/NonNullList.get (I)Ljava/lang/Object;"))
    private<E> E onAdd(NonNullList<ItemStack> instance, int pIndex, Operation<E> original) {
        if(BagUpgrade.isUnlocked(player.getInventory(), pIndex))
            return original.call(instance, pIndex);
        else
            return (E) new ItemStack(Items.BARRIER);
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack pStack) {
        return player.isCreative() || player.getData(HFData.PLAYER_DATA).isUnlocked(slot);
    }
}
