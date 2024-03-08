package uk.joshiejack.harvestfestival.world.level.ticker.crop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.EventPriority;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.network.AddFertilizerPacket;
import uk.joshiejack.harvestfestival.network.RemoveFertilizerPacket;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.level.ticker.CanBeFertilized;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public class SoilTicker implements DailyTicker<SoilTicker>, CanBeFertilized {
    public static final Codec<SoilTicker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HFRegistries.FERTILIZER.byNameCodec().optionalFieldOf("fertilizer", HFFarming.Fertilizers.NONE.get()).forGetter(ticker -> ticker.fertilizer),
            Codec.BOOL.optionalFieldOf("watered", false).forGetter(ticker -> ticker.watered)
    ).apply(instance, SoilTicker::new));

    protected Fertilizer fertilizer;
    protected boolean watered;

    public SoilTicker(Fertilizer fertilizer, boolean watered) {
        this.fertilizer = fertilizer;
        this.watered = watered;
    }

    @Override
    public Codec<SoilTicker> codec() {
        return CODEC;
    }

    @Override
    public SoilTicker createEntry(ServerLevel level, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new SoilTicker(HFFarming.Fertilizers.NONE.get(), applyWaterIfRaining(level, pos, state, false));
    }

    @Override
    public void onAdded(ServerLevel world, BlockPos pos) {
        watered = applyWaterIfRaining(world, pos, world.getBlockState(pos), true);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.LOW; //Ensure soil ticks after crops do
    }

    @Override
    public Fertilizer fertilizer() {
        return fertilizer;
    }

    private boolean applyWaterIfRaining(ServerLevel level, BlockPos pos, BlockState state, boolean affectWorld) {
        if (level.isRainingAt(pos.above()) && state.hasProperty(BlockStateProperties.MOISTURE) &&
                state.getValue(BlockStateProperties.MOISTURE) < 7) {
            if (affectWorld)
                level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.MOISTURE, 7));
            return true;
        } else return false;
    }

    @Override
    public boolean fertilize(Level level, BlockPos pos, BlockState state, Fertilizer fertilizer) {
        if (fertilizer != HFFarming.Fertilizers.NONE.get()) {
            if (this.fertilizer != HFFarming.Fertilizers.NONE.get()) return false;
            if (!level.getBlockState(pos.above()).isAir()) return false;
        }

        //Allow none fertilizer to always be applied
        this.fertilizer = fertilizer;
        if (level instanceof ServerLevel serverLevel) {
            level.getChunk(pos).setUnsaved(true);
            if (fertilizer != HFFarming.Fertilizers.NONE.get())
                PenguinNetwork.sendToNearby(new AddFertilizerPacket(pos, fertilizer), serverLevel, pos.getX(), pos.getY(), pos.getZ(), 64);
            else
                PenguinNetwork.sendToNearby(new RemoveFertilizerPacket(pos, fertilizer), serverLevel, pos.getX(), pos.getY(), pos.getZ(), 64);
        }

        return true; //We can only apply fertilizer when the soil is empty
    }

    @Override
    public CustomPacketPayload onRemoved(BlockPos pos) {
        return new RemoveFertilizerPacket(pos, fertilizer);
    }

    private boolean isWet(BlockState state) {
        return state.hasProperty(BlockStateProperties.MOISTURE) && state.getValue(BlockStateProperties.MOISTURE) == 7;
    }

    private boolean waterRetention(RandomSource random) {
        return random.nextInt(100) <= (fertilizer.retention() - 1);
    }

    @Override
    public void onWeatherChanged(ServerLevel level, BlockPos pos, BlockState state) {
        if (!watered)
            watered = applyWaterIfRaining(level, pos, state, true);
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        watered = waterRetention(level.getRandom()); //Reset the watered state
        if (!state.hasProperty(BlockStateProperties.MOISTURE)) return;
        boolean isWet = isWet(state);
        if (watered && !isWet)
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.MOISTURE, 7)); //Set the moisture
        else if (!watered && isWet)
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.MOISTURE, 0)); //Set the moisture
        else if (!watered && fertilizer == HFFarming.Fertilizers.NONE.get() && !(level.getBlockState(pos.above()).getBlock() instanceof BonemealableBlock)) {
            level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState()); //Set the moisture
        }
    }


}
