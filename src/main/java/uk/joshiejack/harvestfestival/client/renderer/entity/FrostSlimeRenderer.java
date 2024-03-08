package uk.joshiejack.harvestfestival.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.model.HFModelLayers;
import uk.joshiejack.harvestfestival.world.entity.FrostSlime;

public class FrostSlimeRenderer extends MobRenderer<FrostSlime, SlimeModel<FrostSlime>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(HarvestFestival.MODID, "textures/entity/monsters/frost_slime.png");

    public FrostSlimeRenderer(EntityRendererProvider.Context context, float shadow) {
        super(context, new SlimeModel<>(context.bakeLayer(HFModelLayers.FROST_SLIME)), shadow);
        this.addLayer(new SlimeOuterLayer<>(this, context.getModelSet()));

    }

    @Override
    protected void scale(FrostSlime slime, PoseStack stack, float slerp) {
        stack.scale(0.999F, 0.999F, 0.999F);
        stack.translate(0.0F, 0.001F, 0.0F);
        float f1 = (float) slime.getSize();
        float f2 = Mth.lerp(slerp, slime.oSquish, slime.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        stack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FrostSlime slime) {
        return TEXTURE;
    }
}
