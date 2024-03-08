package uk.joshiejack.harvestfestival.world.level.ticker.growable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.farming.SeasonHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

public class SpreadableTicker implements DailyTicker<SpreadableTicker> {
    public static final Codec<SpreadableTicker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("starter", false).forGetter(ticker -> ticker.isStarter),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("age", 0).forGetter(ticker -> ticker.age)
    ).apply(instance, SpreadableTicker::new));

    private boolean isStarter;
    private int age;

    public SpreadableTicker(boolean isStarter, int age) {
        this.isStarter = isStarter;
        this.age = age;
    }

    private SpreadableData getData(BlockState state) {
        SpreadableData data = state.getBlockHolder().getData(HFData.SPREADABLE_DATA);
        return data == null ? SpreadableData.NONE : data;
    }

    public void setStarter() {
        this.isStarter = true;
    }

    @Override
    public Codec<SpreadableTicker> codec() {
        return CODEC;
    }

    @Override
    public SpreadableTicker createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new SpreadableTicker(false, 0);
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        if (isStarter && age <= getData(state).lifespan() && SeasonHandler.isInSeason(level, pos, state)) {
            if (level.random.nextInt(3) == 0) {
                BlockPos target = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos.offset(level.random.nextInt(8) - 4, 0, level.random.nextInt(8) - 4));
                boolean bush = state.getBlock() instanceof BushBlock;
                if (level.getBlockState(target).isAir() && (!bush || ((BushBlock)state.getBlock()).canSurvive(state, level, pos))) {
                    level.setBlockAndUpdate(target, state); //Copy the state below
                    age++;
                } else {
                    BlockState other = level.getBlockState(target);
                    if (other == state && other.getBlock() instanceof BonemealableBlock growable) {
                        if (growable.isValidBonemealTarget(level, target, other)) {
                            DailyTicker<?> oldEntry = DailyTicker.get(level, target);
                            growable.performBonemeal(level, level.random, target, other);
                            DailyTicker<?> newEntry = DailyTicker.get(level, target);
                            if (oldEntry instanceof SpreadableTicker oldTicker && newEntry instanceof SpreadableTicker newTicker) {
                                //Copy over the old data to the new block
                                newTicker.isStarter = oldTicker.isStarter;
                                newTicker.age = oldTicker.age;
                            }

                            age++;
                        }
                    }
                }
            }
        }
    }
}
