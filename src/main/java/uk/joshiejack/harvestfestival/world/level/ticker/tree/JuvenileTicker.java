package uk.joshiejack.harvestfestival.world.level.ticker.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.ChunkAccess;

public class JuvenileTicker extends AbstractGrowingTreeTicker<JuvenileTicker> {
    public static final Codec<JuvenileTicker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("age", 0).forGetter(ticker -> ticker.age)
    ).apply(instance, JuvenileTicker::new));

    public JuvenileTicker(int age) {
        super(age);
    }

    @Override
    public Codec<JuvenileTicker> codec() {
        return CODEC;
    }

    @Override
    public JuvenileTicker createEntry(ServerLevel world, ChunkAccess chunk, BlockPos pos, BlockState state) {
        return new JuvenileTicker(0);
    }

    @Override
    protected boolean canGrow(ServerLevel world, BlockPos pos, BlockState state) {
        return super.canGrow(world, pos, state) &&
                state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    protected void grow(ServerLevel world, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof BonemealableBlock) {
            super.grow(world, pos, state); //Grow the tree up =]
            ((BonemealableBlock) state.getBlock()).performBonemeal(world, world.random, pos, state);
        }
    }
}
