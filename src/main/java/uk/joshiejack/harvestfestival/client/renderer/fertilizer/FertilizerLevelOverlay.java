package uk.joshiejack.harvestfestival.client.renderer.fertilizer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.network.request.RequestFertilizerDataPacket;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, value = Dist.CLIENT)
public class FertilizerLevelOverlay {
    private static final Multimap<Fertilizer, BlockPos> fertilized = HashMultimap.create();
    public static final LoadingCache<Fertilizer, FertilizerRenderer> RENDERING_CACHE = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES)
            .<Fertilizer, FertilizerRenderer>removalListener(entry -> entry.getValue().close()).build(new CacheLoader<>() {
        @Override
        public @NotNull FertilizerRenderer load(@NotNull Fertilizer fertilizer) {
            return new FertilizerRenderer(fertilizer, new FertilizerBlockGetter(fertilized.get(fertilizer)));
        }
    });
    public static void addBlock(Fertilizer fertilizer, BlockPos pos) {
        fertilized.get(fertilizer).add(pos);
        RENDERING_CACHE.invalidate(fertilizer);
    }

    public static void removeBlock(Fertilizer fertilizer, BlockPos pos) {
        fertilized.get(fertilizer).remove(pos);
        RENDERING_CACHE.invalidate(fertilizer);
    }

    public static boolean isFertilized(BlockPos pos) {
        return fertilized.values().contains(pos);
    }

    public static void addRenderData(List<Pair<Fertilizer, BlockPos>> data, RequestFertilizerDataPacket.RequestType type) {
        data.forEach(pair -> {
            Fertilizer fertilizer = pair.getLeft();
            BlockPos pos = pair.getRight();
            if (pair.getLeft() == HFFarming.Fertilizers.NONE.get() || type == RequestFertilizerDataPacket.RequestType.UNLOAD)
                fertilized.get(fertilizer).remove(pos);
            else
                fertilized.get(fertilizer).add(pos);
        });
    }

    @SubscribeEvent
    public static void renderBuildingPreviews(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS || fertilized.isEmpty()) return;
        PoseStack matrixStack = event.getPoseStack();
        Minecraft mc = Minecraft.getInstance();
        Vec3 view = mc.gameRenderer.getMainCamera().getPosition();
        matrixStack.pushPose();
        matrixStack.translate(-view.x(), -view.y(), -view.z());
        for (Fertilizer fertilizer : fertilized.keySet()) {
            RENDERING_CACHE.getUnchecked(fertilizer).draw(BlockPos.ZERO, event.getFrustum(), event.getPoseStack());
        }

        matrixStack.popPose();
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {

    }
}
