package uk.joshiejack.harvestfestival.mixin.stacksize;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentInternals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("all")
@Mixin(FriendlyByteBuf.class)
public abstract class HFFriendlyByteBuf {
    @Shadow public abstract short readShort();
    @Shadow public abstract CompoundTag readNbt();

    @Unique
    private FriendlyByteBuf asFriendlyByteBuf() {
        return (FriendlyByteBuf) (Object) this;
    }

    @Redirect(method = "writeItem", at = @At(value = "INVOKE", target = "net/minecraft/network/FriendlyByteBuf.writeByte (I)Lnet/minecraft/network/FriendlyByteBuf;"))
    public FriendlyByteBuf writeItem(FriendlyByteBuf instance, int pValue) {
        return instance.writeShort(pValue);
    }

    @Redirect(method = "readItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;readByte()B"))
    private byte returnNothing(FriendlyByteBuf instance) {
        return 0;
    }

    @Redirect(method = "readItem", at = @At(value = "INVOKE", target = "net/neoforged/neoforge/attachment/AttachmentInternals.reconstructItemStack (Lnet/minecraft/world/item/Item;ILnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack readStackItemCount(Item item, int count, CompoundTag tag) {
        return AttachmentInternals.reconstructItemStack(item, readShort(), this.readNbt());
    }
}
