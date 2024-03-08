package uk.joshiejack.harvestfestival.client.model;

import net.minecraft.client.model.SlimeModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HFLayerDefinitions {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HFModelLayers.FROST_SLIME, SlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(HFModelLayers.FROST_SLIME_OUTER, SlimeModel::createOuterBodyLayer);
    }
}
