package uk.joshiejack.harvestfestival.client.gui.screens;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.gui.components.ChannelButton;
import uk.joshiejack.harvestfestival.network.television.SetTVChannelPacket;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.television.TVChannel;
import uk.joshiejack.penguinlib.client.gui.AbstractScreen;
import uk.joshiejack.penguinlib.client.gui.PenguinFonts;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import java.util.Collections;
import java.util.List;

public class TelevisionScreen extends AbstractScreen {
    private static final ResourceLocation TEXTURE = guiTexture(HarvestFestival.MODID, "television");
    private static final Component SELECT_CHANNEL = translated(HarvestFestival.MODID, "television.select_channel");
    private final BlockPos pos;

    public TelevisionScreen(BlockPos pos) {
        super(HFBlocks.TELEVISION.get().getName(), TEXTURE, 234, 132);
        this.pos = pos;
    }

    @Override
    protected void initScreen(@NotNull Minecraft minecraft, @NotNull Player player) {
        int x = 0;
        int y = 0;
        List<TVChannel> reversed = Lists.newArrayList(HFRegistries.TV_CHANNELS.registry().values());
        System.out.println("reversed: " + reversed.size());
        Collections.reverse(reversed);
        for (TVChannel channel : reversed) {
            if (channel.isSelectable()) {
                addRenderableWidget(new ChannelButton(this, leftPos + 16 + x, topPos + 28 + y, channel));
                x += 70;

                if (x > 140) {
                    x = 0;
                    y += 54;
                }
            }
        }
    }

    public BlockPos getPosition() {
        return pos;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int x, int y, float pPartialTick) {
        int rows = renderables.size() / 3 + 1;
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, 76);
        for (int i = 0; i < rows - 1; i++) {
            graphics.blit(texture, leftPos, topPos + 76 + i * 76, 0, 22, imageWidth, 58);
        }

        graphics.blit(texture, leftPos, topPos + (rows * 67), 0, 80, imageWidth, 2);
        renderForeground(graphics, x, y, pPartialTick);
    }

    public void renderForeground(GuiGraphics graphics, int x, int y, float pPartialTick) {
        int middle = (imageWidth / 2) - PenguinFonts.FANCY.get().width(SELECT_CHANNEL) / 2;
        graphics.drawString(PenguinFonts.FANCY.get(), SELECT_CHANNEL, leftPos + middle, topPos + 8, 0xF1B81F);
    }

    @Override
    public void onClose() {
        super.onClose();
        //TODO: Delete
        PenguinNetwork.sendToServer(new SetTVChannelPacket(pos, TVChannel.OFF));
    }
}
