package uk.joshiejack.harvestfestival.client.model;

import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.model.block.BakedTVModel;
import uk.joshiejack.harvestfestival.client.model.block.FertilizerBakedModel;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelCache {
    public static final ResourceLocation FERTILIZER = new ResourceLocation(HarvestFestival.MODID, "extra/fertilizer");
    public static final ResourceLocation BASE_NORTH = new ResourceLocation(HarvestFestival.MODID, "extra/television_base_north");
    public static final ResourceLocation BASE_EAST = new ResourceLocation(HarvestFestival.MODID, "extra/television_base_east");
    public static final ResourceLocation BASE_WEST = new ResourceLocation(HarvestFestival.MODID, "extra/television_base_west");
    public static final ResourceLocation BASE_SOUTH = new ResourceLocation(HarvestFestival.MODID, "extra/television_base_south");
    public static final ResourceLocation SCREEN_NORTH = new ResourceLocation(HarvestFestival.MODID, "extra/television_screen_north");
    public static final ResourceLocation SCREEN_EAST = new ResourceLocation(HarvestFestival.MODID, "extra/television_screen_east");
    public static final ResourceLocation SCREEN_WEST = new ResourceLocation(HarvestFestival.MODID, "extra/television_screen_west");
    public static final ResourceLocation SCREEN_SOUTH = new ResourceLocation(HarvestFestival.MODID, "extra/television_screen_south");
    public static final Map<Fertilizer, BakedModel> FERTILIZER_MODELS = new HashMap<>();
    public static final Map<Direction, BakedModel> TELEVISION_BASE = new HashMap<>();
    public static final Map<Direction, BakedModel> TELEVISION_SCREEN = new HashMap<>();


    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(FERTILIZER);
        event.register(SCREEN_EAST);
        event.register(SCREEN_NORTH);
        event.register(SCREEN_SOUTH);
        event.register(SCREEN_WEST);
        event.register(BASE_EAST);
        event.register(BASE_NORTH);
        event.register(BASE_SOUTH);
        event.register(BASE_WEST);
        HFRegistries.QUALITY.stream()
                .filter(quality -> quality.getModelLocation() != null)
                .forEach(quality -> event.register(quality.getModelLocation()));
    }

    @SubscribeEvent
    public static void onBaking(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> registry = event.getModels();
        for (BlockState state: HFBlocks.TELEVISION.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation mrl = BlockModelShaper.stateToModelLocation(state);
            BakedModel existing = registry.get(mrl);
            if (!(existing instanceof BakedTVModel)) {
                registry.put(mrl, new BakedTVModel(existing));
            }
        }
    }

    @SubscribeEvent
    public static void onBakingCompleted(ModelEvent.BakingCompleted event) {
        BakedModel FERTILIZER_MODEL = event.getModels().get(FERTILIZER);
        HFRegistries.FERTILIZER.stream().forEach(fertilizer ->
                FERTILIZER_MODELS.put(fertilizer, new FertilizerBakedModel(FERTILIZER_MODEL, fertilizer)));
        TELEVISION_BASE.put(Direction.NORTH, event.getModels().get(BASE_NORTH));
        TELEVISION_BASE.put(Direction.EAST, event.getModels().get(BASE_EAST));
        TELEVISION_BASE.put(Direction.WEST, event.getModels().get(BASE_WEST));
        TELEVISION_BASE.put(Direction.SOUTH, event.getModels().get(BASE_SOUTH));
        TELEVISION_SCREEN.put(Direction.NORTH, event.getModels().get(SCREEN_NORTH));
        TELEVISION_SCREEN.put(Direction.EAST, event.getModels().get(SCREEN_EAST));
        TELEVISION_SCREEN.put(Direction.WEST, event.getModels().get(SCREEN_WEST));
        TELEVISION_SCREEN.put(Direction.SOUTH, event.getModels().get(SCREEN_SOUTH));
    }
}