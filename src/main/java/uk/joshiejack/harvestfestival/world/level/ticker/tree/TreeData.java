package uk.joshiejack.harvestfestival.world.level.ticker.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public record TreeData(BlockState next, int days) {
    public static final Codec<TreeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("next").forGetter(ticker -> ticker.next),
            ExtraCodecs.POSITIVE_INT.fieldOf("days").forGetter(ticker -> ticker.days)
    ).apply(instance, TreeData::new));

    public static final TreeData NONE = new TreeData(Blocks.AIR.defaultBlockState(), 1);
}
