package uk.joshiejack.harvestfestival.client.renderer.entity;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.model.entity.HellBatModel;
import uk.joshiejack.harvestfestival.world.entity.HellBat;


public class HellBatRenderer extends MobRenderer<HellBat, HellBatModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(HarvestFestival.MODID, "textures/entity/monsters/hell_bat.png");

    public HellBatRenderer(EntityRendererProvider.Context context) {
        super(context, new HellBatModel(context.bakeLayer(ModelLayers.BAT)), 0.25F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HellBat bat) {
        return TEXTURE;
    }
}
