package uk.joshiejack.harvestfestival.client.renderer.fertilizer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import uk.joshiejack.harvestfestival.client.model.ModelCache;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.penguinlib.client.level.ghost.GhostBlockRenderer;

import java.util.Collection;

public class FertilizerRenderer extends GhostBlockRenderer<Collection<BlockPos>, FertilizerBlockGetter> {
    private final Fertilizer fertilizer;

    public FertilizerRenderer(Fertilizer fertilizer, FertilizerBlockGetter blockAccess) {
        super(blockAccess);
        this.fertilizer = fertilizer;
    }

    @Override
    protected BakedModel getBlockModel(BlockRenderDispatcher blockRenderer, BlockState state) {
        BakedModel model = ModelCache.FERTILIZER_MODELS.get(fertilizer);
        return ModelCache.FERTILIZER_MODELS.get(fertilizer);
       //return fertilizer == HFFarming.AQUA_GUARD.get() ? blockRenderer.getBlockModel(Blocks.DIAMOND_BLOCK.defaultBlockState()) : blockRenderer.getBlockModel(Blocks.FARMLAND.defaultBlockState());
    }

    @Override
    protected void setupRenderer(SectionBufferBuilderPack sectionBuffer, BlockRenderDispatcher blockRenderer, PoseStack poseStack, RandomSource randomSource) {
        blockGetter.getData().forEach(pos -> addRender(sectionBuffer, blockRenderer, poseStack, Blocks.FARMLAND.defaultBlockState(), pos, randomSource));
    }
}
