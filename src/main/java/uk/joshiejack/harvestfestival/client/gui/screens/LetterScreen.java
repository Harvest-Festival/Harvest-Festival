package uk.joshiejack.harvestfestival.client.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.network.mail.LetterButtonClickedPacket;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.penguinlib.client.gui.PenguinFonts;
import uk.joshiejack.penguinlib.client.gui.widget.PenguinButton;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public class LetterScreen extends PenguinScreen { //TODO: Fix this
    public static final ResourceLocation TEXTURE = guiTexture(HarvestFestival.MODID, "letter");
    private final AbstractLetter<?> letter;

    public LetterScreen(AbstractLetter<?> letter) {
        super(Component.empty(), TEXTURE, 166, 200);
        this.letter = letter;
    }

    protected Button button(LetterButtonClickedPacket.Action action) {
        return PenguinButton.penguinBuilder(Component.empty(), (btn) -> {
                    assert minecraft != null;
                    minecraft.setScreen(null); //Close the screen
                    PenguinNetwork.sendToServer(new LetterButtonClickedPacket(letter, action));
                })
                .sprites(HarvestFestival.MODID, action.name().toLowerCase())
                .pos(leftPos + 140, topPos + 165)
                .tooltip(Tooltip.create(Component.translatable("gui." + HarvestFestival.MODID + ".letter." + action.name().toLowerCase())))
                .size(action.width(), 16)
                .build();
    }

    @Override
    public void initScreen(@NotNull Minecraft minecraft, @NotNull Player player) {
        addRenderableWidget(button(LetterButtonClickedPacket.Action.ACCEPT));
        if (letter.isRejectable())
            addRenderableWidget(button(LetterButtonClickedPacket.Action.REJECT));
    }

    @Override
    public void renderForeground(GuiGraphics graphics, int x, int y, float partialTicks) {
        letter.renderLetter(graphics, PenguinFonts.UNICODE.get(), leftPos, topPos, x, y);
    }

    @Override
    public void onClose() {
        super.onClose();
        assert minecraft != null;
        letter.onClosed(minecraft.player);
    }
}
