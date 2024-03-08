package uk.joshiejack.harvestfestival.world.level.ticker.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.farming.Quality;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.HasQuality;

public record QualityRetainingBlock(Quality quality) implements HasQuality, DailyTicker<QualityRetainingBlock> {
    public static final Codec<QualityRetainingBlock> CODEC = Quality.CODEC.fieldOf("quality").xmap(QualityRetainingBlock::new, QualityRetainingBlock::getQuality).codec();

    @Override
    public Codec<QualityRetainingBlock> codec() {
        return CODEC;
    }

    @Override
    public QualityRetainingBlock createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new QualityRetainingBlock(HFFarming.QualityLevels.NORMAL.get());
    }

    @Override
    public void tick(ServerLevel world, BlockPos pos, BlockState state) {
        // NO-OP
    }

    @Override
    public Quality getQuality() {
        return quality;
    }
}
