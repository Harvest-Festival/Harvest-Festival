package uk.joshiejack.harvestfestival.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class CollectionEntryLabel extends AbstractWidget {
    private final ItemIcon icon;
    private final boolean obtained;

    public CollectionEntryLabel(ItemStack stack, int x, int y) {
        super(x, y, 16, 16, Component.empty());
        this.icon = new ItemIcon(stack);
        assert Minecraft.getInstance().player != null;
        this.obtained = Minecraft.getInstance().player.getData(HFData.PLAYER_DATA).hasObtained(stack.getItem());
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        if (obtained)
            icon.render(minecraft, graphics, getX(), getY());
        else
            icon.shadowed().render(minecraft, graphics, getX(), getY());
        if (isHovered)
            graphics.renderComponentTooltip(minecraft.font, Screen.getTooltipFromItem(minecraft, icon.stack), getX(), getY(), icon.stack);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput p_259858_) {

    }
}