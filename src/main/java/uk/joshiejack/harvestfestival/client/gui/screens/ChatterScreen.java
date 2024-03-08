package uk.joshiejack.harvestfestival.client.gui.screens;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.client.gui.SimpleChatter;

@SuppressWarnings("ConstantConditions")
public class ChatterScreen extends PenguinScreen {
    private static final ResourceLocation TEXTURE = guiTexture(HarvestFestival.MODID, "chatterbox");
    private final SimpleChatter script;
    private Window window;

    public ChatterScreen(ResourceLocation texture, Component text) {
        super(Component.empty(), texture, 256, 256);
        this.script = new SimpleChatter(text).withSpeed(0.2F);
    }

    public ChatterScreen(Component text) {
        this(TEXTURE, text);
    }

    @Override
    protected void initScreen(@NotNull Minecraft minecraft, @NotNull Player player) {
        this.window = minecraft.getWindow();
    }

    @Override
    public void renderTransparentBackground(@NotNull GuiGraphics pGuiGraphics) {}
    @Override
    public void renderBackground(GuiGraphics graphics, int x, int y, float pPartialTick) {
        graphics.blit(TEXTURE, leftPos, window.getGuiScaledHeight() - 101, 0, 140, 256, 71);
        renderForeground(graphics, x, y, pPartialTick);
    }

    @Override
    public void tick() {
        script.update(font);
       // script.update(font);
    }

    @Override
    public void renderForeground(GuiGraphics graphics, int x, int y, float pPartialTick) {
        script.draw(graphics, font, leftPos + 20, window.getGuiScaledHeight() - 87, 0x382A22);
    }

    @Override
    public boolean mouseClicked(double x, double y, int mouseButton) {
        if (getFocused() == null && script.mouseClicked(mouseButton)) {
            minecraft.player.closeContainer();
            return true;
        }

        return super.mouseClicked(x, y, mouseButton);
    }
}
