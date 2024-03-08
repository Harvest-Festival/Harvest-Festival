package uk.joshiejack.harvestfestival.client.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.MineClientData;
import uk.joshiejack.harvestfestival.network.ElevatorButtonClickedPacket;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.penguinlib.client.PenguinClientConfig;
import uk.joshiejack.penguinlib.client.gui.widget.PenguinNumberButton;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public class ElevatorScreen extends PenguinScreen {
    private static final Component EMPTY = Component.translatable(HarvestFestival.suffix(HFBlocks.ELEVATOR.get().asItem(), "none"));
    private static final ResourceLocation FANCY_GUI = guiTexture(HarvestFestival.MODID, "elevator");
    private static final ResourceLocation MINECRAFT_GUI = guiTexture(HarvestFestival.MODID, "elevator_mc");
    private final int maximumFloor;

    public ElevatorScreen(Component component, int maximumFloor) {
        super(component, PenguinClientConfig.fancyGUI.get() ? FANCY_GUI : MINECRAFT_GUI, 227, 218);
        this.maximumFloor = maximumFloor;
    }

    @Override
    protected void initScreen(@NotNull Minecraft minecraft, @NotNull Player player) {
        int playerFloor = (MineHelper.getFloorFromPos(minecraft.level, MineClientData.getSettings(), player.getOnPos()) + 1);
        if (maximumFloor > (MineClientData.getSettings().elevatorFrequency() - 1)) {
            int y = 0;
            int i = 0;
            int index = 0;
            while (index <= 24) {
                int floor = (index * 5) + 1;
                if (floor > maximumFloor) break; //Exit early
                Button button = PenguinNumberButton.builder(Component.literal(String.valueOf(floor)), (btn) -> {
                            minecraft.setScreen(null); //Close the screen
                            PenguinNetwork.sendToServer(new ElevatorButtonClickedPacket(floor));
                        })
                        .pos(leftPos + 35 + (y * 32), topPos + 28 + (i * 22))
                        .size(28, 18).build();
                if (floor == playerFloor)
                    button.active = false;
                addRenderableWidget(button);
                y++;

                if (y > 4) {
                    y = 0;
                    i++;
                }

                index++;
            }
        }
    }

    @Override
    public void renderForeground(GuiGraphics graphics, int x, int y, float partialTicks) {
        assert minecraft != null;
        if (renderables.isEmpty())
            graphics.drawString(minecraft.font, EMPTY, leftPos + 35, topPos + 27, 16777215);
        if (!PenguinClientConfig.fancyGUI.get())
            graphics.drawString(this.font, this.title, leftPos + 135 - font.width(title), topPos + 10, 16777215);
    }
}
