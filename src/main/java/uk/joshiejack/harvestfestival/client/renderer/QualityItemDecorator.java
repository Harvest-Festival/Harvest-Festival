package uk.joshiejack.harvestfestival.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.HFClientConfig;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.farming.Quality;

public class QualityItemDecorator {
    public static void render(@NotNull GuiGraphics guiGraphics, @NotNull PoseStack pose, @NotNull ItemStack stack, int x, int y) {
        if (HFClientConfig.renderQuality.get()) {
            Quality quality = Quality.fromStack(stack);
            if (quality != HFFarming.QualityLevels.NORMAL.get()) {
                pose.translate(0F, 0F, 200F);
                guiGraphics.blitSprite(quality.getSprite(), x, y, 16, 16);
            }
        }
    }
}
