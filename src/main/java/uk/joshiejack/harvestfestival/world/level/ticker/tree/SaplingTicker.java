package uk.joshiejack.harvestfestival.world.level.ticker.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class SaplingTicker extends AbstractGrowingTreeTicker<SaplingTicker> {
    public static final Codec<SaplingTicker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("age", 0).forGetter(ticker -> ticker.age)
    ).apply(instance, SaplingTicker::new));

    public SaplingTicker(int age) {
        super(age);
    }

    @Override
    public Codec<SaplingTicker> codec() {
        return CODEC;
    }

    @Override
    public SaplingTicker createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new SaplingTicker(0);
    }
}
