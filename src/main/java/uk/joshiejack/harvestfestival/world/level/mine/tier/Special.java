package uk.joshiejack.harvestfestival.world.level.mine.tier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.world.level.block.state.BlockState;

public record Special(BlockState state, int minimumFloor, double divider) {
    public static final Codec<Special> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            BlockState.CODEC.fieldOf("state").forGetter(inst -> inst.state),
                            Codec.INT.fieldOf("minimum_floor").forGetter(inst -> inst.minimumFloor),
                            Codec.DOUBLE.fieldOf("divider").forGetter(inst -> inst.divider)
                    )
                    .apply(codec, Special::new)
    );

    public Loot asLoot() {
        return new Loot(state, 1, Integer.MAX_VALUE, 0, 0, Weight.of(1), false);
    }
}