package uk.joshiejack.harvestfestival.world.level.ticker.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class SeedlingTicker extends AbstractGrowingTreeTicker<SeedlingTicker> {
    public static final Codec<SeedlingTicker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("age", 0).forGetter(ticker -> ticker.age)
    ).apply(instance, SeedlingTicker::new));

    public SeedlingTicker(int age) {
        super(age);
    }

    @Override
    public SeedlingTicker createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new SeedlingTicker(0);
    }

    @Override
    public Codec<SeedlingTicker> codec() {
        return CODEC;
    }
}
