package uk.joshiejack.harvestfestival.client.renderer.entity;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.model.entity.FrostBatModel;
import uk.joshiejack.harvestfestival.world.entity.FrostBat;


public class FrostBatRenderer extends MobRenderer<FrostBat, FrostBatModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(HarvestFestival.MODID, "textures/entity/monsters/frost_bat.png");

    public FrostBatRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostBatModel(context.bakeLayer(ModelLayers.BAT)), 0.25F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FrostBat bat) {
        return TEXTURE;
    }
}
