package uk.joshiejack.harvestfestival.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.penguinlib.client.gui.HUDRenderer;
import uk.joshiejack.penguinlib.util.helper.StringHelper;

import java.util.Objects;


public class MineHUDRenderer extends HUDRenderer.HUDRenderData {
    private static final MineHUDRenderer INSTANCE = new MineHUDRenderer();
    private static final String text = "hud." + HarvestFestival.MODID + ".mine";
    private static ResourceLocation HUD = MineTier.NO_HUD;

    public static void setHUD(ResourceLocation hud) {
        HUD = hud;
        if (hud != MineTier.NO_HUD) {
            HUDRenderer.RENDERERS.put(Objects.requireNonNull(Minecraft.getInstance().level).dimension(), INSTANCE);
        }
    }

    @Override
    public boolean isEnabled(Minecraft mc) {
        return HUD != MineTier.NO_HUD && MineClientData.SETTINGS != null && HFClientConfig.enableHUD.get();
    }

    @Override
    public ResourceLocation getTexture(Minecraft mc) {
        return HUD;
    }

    @Override
    public Component getHeader(Minecraft mc) {
        return StringHelper.format(text, (MineHelper.getFloorFromPos(Minecraft.getInstance().level, MineClientData.SETTINGS, mc.player.blockPosition()) + 1));
    }

    @Override
    public int getClockX() {
        return isEnabled(Minecraft.getInstance()) ? super.getClockX() : 8;
    }

    @Override
    public int getClockY() {
        return isEnabled(Minecraft.getInstance()) ? super.getClockY() : 5;
    }
}