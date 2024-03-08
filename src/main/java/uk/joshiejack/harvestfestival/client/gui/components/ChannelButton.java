package uk.joshiejack.harvestfestival.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.client.gui.screens.TelevisionScreen;
import uk.joshiejack.harvestfestival.network.television.SetTVChannelPacket;
import uk.joshiejack.harvestfestival.world.television.TVChannel;
import uk.joshiejack.penguinlib.client.gui.widget.AbstractButton;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import javax.annotation.Nonnull;

public class ChannelButton extends AbstractButton<TelevisionScreen> {
    private final ResourceLocation screenshot;

    public ChannelButton(TelevisionScreen screen, int x, int y, TVChannel channel) {
        super(screen, x, y, 62, 46, Component.empty(), (btn) -> {
            PenguinNetwork.sendToServer(new SetTVChannelPacket(screen.getPosition(), channel));
            Minecraft.getInstance().setScreen(null); //Close this screen
        });

        this.screenshot = new ResourceLocation(channel.id().getNamespace(), "tv_channel/" + channel.id().getPath());
        this.setTooltip(Tooltip.create(channel.getName()));
    }

    @Override
    public void renderButton(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, boolean hovered) {
        if (hovered) {
            graphics.fill(getX(), getY(), getX() + width, getY() + height, 0x80FFFFFF);
        }

        graphics.blitSprite(screenshot, getX(), getY(), width, height);
    }
}