package uk.joshiejack.harvestfestival.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.achievements.Achievement;
import uk.joshiejack.penguinlib.util.icon.SpriteIcon;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class AchievementLabel extends AbstractWidget {
    public static final ResourceLocation DEFAULT_SHADOW = new ResourceLocation(HarvestFestival.MODID, "achievements/shadowed");
    private final SpriteIcon icon;
    private final boolean completed;

    public AchievementLabel(Achievement achievement, int x, int y) {
        super(x, y, 16, 16, Component.empty());
        this.icon = new SpriteIcon(new ResourceLocation(achievement.id().getNamespace(), "achievements/" + achievement.id().getPath()),
                achievement.hasShadow() ? new ResourceLocation(achievement.id().getNamespace(), "achievements/" + achievement.id().getPath() + "_shadowed") : DEFAULT_SHADOW);
        this.setTooltip(Tooltip.create(achievement.component()));
        this.completed = achievement.completed(Minecraft.getInstance().player);
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        if (completed)
            icon.render(minecraft, graphics, getX(), getY());
        else
            icon.shadowed().render(minecraft, graphics, getX(), getY());
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput p_259858_) {}
}