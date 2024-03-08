package uk.joshiejack.harvestfestival.world.level.ticker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.network.request.RequestFertilizerDataPacket;
import uk.joshiejack.harvestfestival.world.level.ticker.processor.DailyTickProcessor;
import uk.joshiejack.harvestfestival.world.level.ticker.processor.UpdateProcessor;
import uk.joshiejack.harvestfestival.world.level.ticker.processor.WeatherChangeProcessor;
import uk.joshiejack.penguinlib.event.NewDayEvent;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class DailyTickLevelListener {
    @SubscribeEvent
    public static void onGrowthTick(BlockEvent.CropGrowEvent.Pre event) {
        if (DailyTicker.has(event.getState())) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    private static final Set<Block> originalRandomlyTicking = new HashSet<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTagReload(TagsUpdatedEvent event) {
        if (event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) {
            //Fuck with all of the blocks the blocks but maybe we need to actually do this on reload instead?
            BuiltInRegistries.BLOCK.forEach(block -> {
                block.getStateDefinition().getPossibleStates().forEach(state -> {
                    if (DailyTicker.has(state)) {
                        if (state.isRandomlyTicking()) {
                            originalRandomlyTicking.add(state.getBlock());
                            state.isRandomlyTicking = false;
                            HarvestFestival.LOGGER.info("Disabled " + state + " from randomly ticking");
                        }
                    } else if (originalRandomlyTicking.contains(state.getBlock())) {
                        state.isRandomlyTicking = true;
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        event.getLevel().getServer().submit(() ->
                UpdateProcessor.process(event.getLevel(), (level, list) -> //Tell minecraft to update the world
                        NeoForge.EVENT_BUS.register(new DailyTickProcessor(level, list))));
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getLevel() instanceof Level level && level.isClientSide)
            PenguinNetwork.sendToServer(new RequestFertilizerDataPacket(event.getChunk().getPos(), RequestFertilizerDataPacket.RequestType.LOAD));
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getLevel() instanceof Level level && level.isClientSide)
            PenguinNetwork.sendToServer(new RequestFertilizerDataPacket(event.getChunk().getPos(), RequestFertilizerDataPacket.RequestType.UNLOAD));
    }

    @SubscribeEvent
    public static void retroGenBlockLoading(ChunkEvent.Load event) {
        if (!HFConfig.retroactivelyLoadTickers.get() || !(event.getLevel() instanceof ServerLevel)) return;
        if (event.getChunk() instanceof LevelChunk chunk) {
            //Reload in any tickers that didn't exist before?
            ChunkAccess access = event.getChunk();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            LevelChunkSection[] sections = access.getSections();
            for (int i = sections.length - 1; i >= 0; --i) {
                LevelChunkSection section = sections[i];
                if (section != null) {
                    //Sections are going from the top to the bottom of the world
                    for (int y = 15; y >= 0; y--) {
                        //Reload in any old blocks retroactively, add config for this
                        int actualY = ((i * 16) + y) + event.getLevel().getMinBuildHeight();
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                onBlockLoaded((ServerLevel) event.getLevel(), chunk, pos.set(access.getPos().getMinBlockX(), actualY, access.getPos().getMinBlockZ()), section.getBlockState(x, y, z));
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean weatherInit = false;

    public static void onWeatherChanged(ServerLevel serverLevel, float oRainLevel, float rainLevel) {
        if (oRainLevel == rainLevel && weatherInit) return; //No change, no need to do anything
        serverLevel.getServer().submit(() ->
                UpdateProcessor.process(serverLevel, (level, list) -> //Tell minecraft to update the world
                        NeoForge.EVENT_BUS.register(new WeatherChangeProcessor(level, list)))); //Tell minecraft to update the world
        weatherInit = true;
    }

    public static void onBlockLoaded(ServerLevel serverLevel, LevelChunk chunk, BlockPos pos, BlockState state) {
        if (state == null || state.isAir()) return; //Air and null stages can't have tickers
        DailyTicker<?> ticker = state.getBlockHolder().getData(HFData.TICKERS);
        if (ticker == null) return; //This block doesn't have a ticker so move on
        DailyTickData dailyTickData = DailyTickData.getDataForChunk(chunk);
        if (dailyTickData.get(pos) != null) return; //This block already has a ticker, so move on
        dailyTickData.addEntry(serverLevel, chunk, pos, state, ticker);
    }

    public static void onBlockChanged(ServerLevel serverLevel, LevelChunk chunk, BlockPos pos, BlockState oldState, BlockState newState) {
        if (newState == null || oldState == null) return; //No change, no need to do anything
        DailyTicker<?> oldTicker = oldState.getBlockHolder().getData(HFData.TICKERS);
        DailyTicker<?> newTicker = newState.getBlockHolder().getData(HFData.TICKERS);
        if (oldTicker == null && newTicker == null) return; //No change, no need to do anything
        DailyTickData dailyTickData = DailyTickData.getDataForChunk(chunk);
        if (oldTicker == null)
            dailyTickData.addEntry(serverLevel, chunk, pos, newState, newTicker);
        else if (newTicker == null)
            dailyTickData.removeEntry(serverLevel, chunk, pos);
        else if (oldTicker.codec() == newTicker.codec()) {
            serverLevel.getServer().submit(() -> newTicker.onStateChanged(serverLevel, pos, oldTicker, newState));
        } else {
            dailyTickData.replaceEntry(serverLevel, chunk, pos, newState, newTicker);
        }
    }
}