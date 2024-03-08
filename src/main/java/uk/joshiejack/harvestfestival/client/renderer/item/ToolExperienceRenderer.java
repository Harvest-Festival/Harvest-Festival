package uk.joshiejack.harvestfestival.client.renderer.item;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Rarity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.tool.Upgrades;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid= HarvestFestival.MODID, value= Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToolExperienceRenderer {
    @SubscribeEvent
    public static void registerTooltipComponent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(Experience.class, (data) -> data);
    }

    @Mod.EventBusSubscriber(modid= HarvestFestival.MODID, value= Dist.CLIENT)
    public static class Events {
        @SubscribeEvent
        public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
            Upgrades.Upgrade upgrade = Upgrades.getUpgrade(event.getItemStack());
            if (upgrade != null) {
                int percentage = (int) ((event.getItemStack().getOrCreateTag().getDouble("Experience") / upgrade.experienceRequirement()) * 100);
                event.getTooltipElements().add(Either.right(Experience.INSTANCE.with(event.getItemStack().getRarity(), percentage)));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Experience implements ClientTooltipComponent, TooltipComponent {
        public static final ResourceLocation EXPERIENCE_BAR_EMPTY = new ResourceLocation(HarvestFestival.MODID, "hud/experience_bar_empty");
        public static final ResourceLocation EXPERIENCE_BAR_STANDARD = new ResourceLocation(HarvestFestival.MODID, "hud/experience_bar_standard");
        public static final ResourceLocation EXPERIENCE_BAR_SPECIAL = new ResourceLocation(HarvestFestival.MODID, "hud/experience_bar_special");
        public static final Experience INSTANCE = new Experience();

        private Rarity rarity;
        private int percentage;

        public Experience with(Rarity rarity, int percentage) {
            this.rarity = rarity;
            this.percentage = percentage;
            return this;
        }

        @Override
        public void renderImage(@Nonnull Font font, int x, int y, GuiGraphics graphics) {
            graphics.blitSprite(EXPERIENCE_BAR_EMPTY, x, y, 51, 9);
            int width = 51 * percentage / 100;
            graphics.blitSprite(rarity == Rarity.COMMON ? EXPERIENCE_BAR_STANDARD : EXPERIENCE_BAR_SPECIAL, x, y, width, 9);
            graphics.drawString(font, percentage + "%", x + 53, y + 1, 0xFFFFFF);
        }

        @Override
        public int getHeight() {
            return 11;
        }

        @Override
        public int getWidth(@Nonnull Font font) {
            return 55;
        }
    }
}
