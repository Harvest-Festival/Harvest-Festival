package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class HFItemStack {
    @Redirect(method = "save", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putByte(Ljava/lang/String;B)V"))
    private void saveAsInt(CompoundTag tag, String key, byte value) {
        int count = ((ItemStack) (Object) this).getCount();
        tag.putByte("Count", (byte) Math.min(count, Byte.MAX_VALUE));
        if (count > Byte.MAX_VALUE)
            tag.putInt("ExtraCount", count);
    }

    @Redirect(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/ItemStack;count:I", opcode = Opcodes.PUTFIELD))
    private void reloadFromInt(ItemStack instance, int value, CompoundTag tag) {
        if (tag.contains("ExtraCount"))
            instance.setCount(tag.getInt("ExtraCount"));
        else
            instance.setCount(tag.getByte("Count"));
    }
}
