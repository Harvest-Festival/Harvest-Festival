package uk.joshiejack.harvestfestival.world.level.ticker.crop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.farming.Quality;
import uk.joshiejack.harvestfestival.world.farming.SeasonHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.CanBeFertilized;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.HasQuality;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;

import java.util.Comparator;
import java.util.List;

public class CropTicker implements DailyTicker<CropTicker>, HasQuality {
    public static final Codec<CropTicker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HFRegistries.FERTILIZER.byNameCodec().optionalFieldOf("fertilizer", HFFarming.Fertilizers.NONE.get()).forGetter(ticker -> ticker.fertilizer),
            Quality.CODEC.optionalFieldOf("quality", HFFarming.QualityLevels.NORMAL.get()).forGetter(ticker -> ticker.quality),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("stage", 0).forGetter(ticker -> ticker.stage)
    ).apply(instance, CropTicker::new));

    private StageHandler<?> handler;
    private final Fertilizer fertilizer;
    private Quality quality;
    private int stage;

    public CropTicker(Fertilizer fertilizer, Quality quality, int stage) {
        this.fertilizer = fertilizer;
        this.quality = quality;
        this.stage = stage;
    }

    @Override
    public Quality getQuality() {
        return quality;
    }

    @Override
    public Codec<CropTicker> codec() {
        return CODEC;
    }

    private CropData getData(BlockState state) {
        CropData data = state.getBlockHolder().getData(HFData.CROP_DATA);
        return data == null ? CropData.NONE : data;
    }

    @Override
    public CropTicker createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        Fertilizer fertilizer = HFFarming.Fertilizers.NONE.get();
        BlockPos pos1 = pos.below();
        DailyTicker<?> entry = DailyTicker.get(world, pos1);
        if (entry instanceof CanBeFertilized soil)
            fertilizer = soil.fertilizer(); //Copy the stats of the block underneath us
        return new CropTicker(fertilizer, HFFarming.QualityLevels.NORMAL.get(), 0);
    }


    private StageHandler<?> getHandler(CropData data) {
        if (handler == null)
            handler = HFRegistries.STAGE_HANDLERS.get(data.stageHandler());
        return handler;
    }


    private boolean isValidSoil(ServerLevel world, BlockPos below, CropData data) {
        return data.validSoils() == null || world.getBlockState(below).is(data.validSoils());
    }

    private boolean isValidSeason(ServerLevel world, BlockPos below, BlockState state) {
        return SeasonHandler.isInSeason(world, below, state);
    }

    private boolean grow(StageHandler<?> handler, RandomSource random) {
        boolean grew = false;
        if (stage < handler.maximumStage()) {
            stage++;
            grew = handler.grow(stage);
        }

        if (stage == handler.maximumStage()) {
            if (fertilizer.quality() > 0) {
                quality = fertilizer.getCropQuality(random);
            } else {
                quality = HFFarming.QualityLevels.NORMAL.get();
                var chance = random.nextInt(0, 99);
                List<Quality> qualityList = HFRegistries.QUALITY.stream().filter(Quality::appliesToCrops).sorted(Comparator.comparingDouble(Quality::modifier)).toList();
                var initial = 1;
                var size = (qualityList.size() / 3) * 10;
                for (Quality quality: qualityList) {
                    if (chance < initial) {
                        this.quality = quality;
                        break;
                    }

                    initial *= size;
                }
            }
        }


        return grew;
    }

    public static void setLight(ServerLevel world, BlockPos pos) {
        BlockLightEngine lightEngine = (BlockLightEngine) world.getLightEngine().blockEngine;
        assert lightEngine != null;
        LayerLightSectionStorage<?> storage = lightEngine.storage;
        storage.setStoredLevel(pos.asLong(), 15);
    }

    private boolean fertilised() {
        int mod = 100 / fertilizer.speed();
        return (stage - 1) %mod == 0;
    }

    @Override
    public void tick(ServerLevel world, BlockPos pos, BlockState state) {
        BlockPos below = pos.below();
        BlockPos pos1 = pos.below();
        CropData data = getData(state);
        StageHandler<?> handler = getHandler(data);
        if (!isValidSoil(world, below, data)) return; //Only allow certain tagged soils?
        if (data.requiresWater() && !world.getBlockState(pos1).isFertile(world, pos1)) return;
        if (!isValidSeason(world, below, state)
                && SeasonHandler.applyOutOfSeasonEffect(world, pos)) {
            return;
        }

        if (grow(handler, world.random)) {
            //Hack the light engine
            setLight(world, pos.above()); //Force the light engine to always be 15 when ticking blocks
            state.randomTick(world, pos, world.random);
        }

        //Increase growth speed
        if (fertilizer.speed() > 0) {
            int growths = 0;
            while (fertilised() && grow(handler, world.random)) {
                setLight(world, pos.above()); //Force the light engine to always be 15 when ticking blocks
                state.randomTick(world, pos, world.random);
                growths++;

                if (growths >= handler.maximumStage()) {
                    break;
                }
            }
        }
    }
}
