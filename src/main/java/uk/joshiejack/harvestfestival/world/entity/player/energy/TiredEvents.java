package uk.joshiejack.harvestfestival.world.entity.player.energy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.HFClientConfig;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, value = Dist.CLIENT)
public class TiredEvents {
    private static float FADE_IN_OUT;
    private static boolean INCREASE = true;
    private static boolean BLINK = false;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void renderTiredness(RenderGuiOverlayEvent.Pre event) {
        if (HFClientConfig.blinkMaximum.get() == 0) return;
        // if (event.getOverlay().id().equals()) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (!player.isCreative() && player.hasEffect(HFEffects.TIRED.get())) {
            if (player.level().getGameTime() % 200 == 0) {
                BLINK = true;
                FADE_IN_OUT = 0.025F;
                player.playSound(HarvestFestival.HFSounds.YAWN.get());
            }

            if (BLINK) {
                int color = FastColor.ARGB32.color((int) FADE_IN_OUT, 0, 0, 0);
                //FADE_IN_OUT = 0F;
                if (BLINK) {
                    if (INCREASE) {
                        FADE_IN_OUT += 0.01F;
                    } else FADE_IN_OUT -= 0.01F;


                    if (FADE_IN_OUT > HFClientConfig.blinkMaximum.get() || FADE_IN_OUT < 0.025F) {
                        INCREASE = !INCREASE;
                        if (FADE_IN_OUT <= 0.025F) {
                            FADE_IN_OUT = 0F;
                            BLINK = false;
                        }
                    }
                }

                event.getGuiGraphics().fill(0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), color);
            }
        }
        //}
    }
}
