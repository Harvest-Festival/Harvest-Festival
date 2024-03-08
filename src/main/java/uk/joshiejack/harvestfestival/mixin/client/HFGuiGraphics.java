package uk.joshiejack.harvestfestival.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.joshiejack.harvestfestival.client.renderer.QualityItemDecorator;

import javax.annotation.Nullable;

@Mixin(GuiGraphics.class)
public class HFGuiGraphics {
    @Shadow @Final private PoseStack pose;

    /** Add a hook to render quality for any items that have it **/
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "com/mojang/blaze3d/vertex/PoseStack.popPose ()V", shift = At.Shift.AFTER))
    private void renderItemDecorations(Font font, ItemStack stack, int x, int y, @Nullable String text, CallbackInfo ci) {
        QualityItemDecorator.render((GuiGraphics) (Object) this, pose, stack, x, y);
    }
}
