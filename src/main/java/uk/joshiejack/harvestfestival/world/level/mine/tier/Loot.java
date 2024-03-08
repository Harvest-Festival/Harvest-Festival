package uk.joshiejack.harvestfestival.world.level.mine.tier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Loot implements WeightedEntry {
    public static final Codec<Loot> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            BlockState.CODEC.fieldOf("state").forGetter(inst -> inst.state),
                            Codec.INT.optionalFieldOf("min_floor", 1).forGetter(inst -> inst.min_floor),
                            Codec.INT.optionalFieldOf("max_floor", Integer.MAX_VALUE).forGetter(inst -> inst.max_floor),
                            Codec.INT.fieldOf("cluster_distance").forGetter(inst -> inst.cluster_distance),
                            Codec.INT.fieldOf("cluster_amount").forGetter(inst -> inst.cluster_amount),
                            Weight.CODEC.fieldOf("weight").forGetter(inst -> inst.weight),
                            Codec.BOOL.optionalFieldOf("stackable", false).forGetter(inst -> inst.stackable)
                    )
                    .apply(codec, Loot::new)
    );
    private final BlockState state;
    private final int min_floor;
    private final int max_floor;
    private final int cluster_distance;
    private final int cluster_amount;
    private final Weight weight;
    private final boolean stackable;

    public Loot(BlockState state, int min_floor, int max_floor, int cluster_distance, int cluster_amount, Weight weight, boolean stackable) {
        this.state = state;
        this.min_floor = min_floor;
        this.max_floor = max_floor;
        this.cluster_distance = cluster_distance;
        this.cluster_amount = cluster_amount;
        this.weight = weight;
        this.stackable = stackable;
    }

    public BlockState state() {
        return state;
    }

    public int minFloor() {
        return min_floor;
    }

    public int maxFloor() {
        return max_floor;
    }

    public int clusterDistance() {
        return cluster_distance;
    }

    public int clusterAmount() {
        return cluster_amount;
    }

    @Override
    public @NotNull Weight getWeight() {
        return weight;
    }
    public boolean stackable() {
        return stackable;
    }

    public static class Builder {
        private final List<Loot> loots = Lists.newArrayList();
        private BlockState state;
        private int min_floor;
        private int max_floor;
        private int cluster_distance;
        private int cluster_amount;
        private Weight weight;
        private boolean stackable;

        private Builder() {
        }

        private Builder reset() {
            state = null;
            min_floor = 1;
            max_floor = Integer.MAX_VALUE;
            cluster_distance = 0;
            cluster_amount = 0;
            weight = Weight.of(1);
            stackable = false;
            return this;
        }

        public static Builder create() {
            return new Builder().reset();
        }

        public Builder state(DeferredBlock<Block> block) {
            return state(block.get().defaultBlockState());
        }

        public Builder state(Block block) {
            return state(block.defaultBlockState());
        }

        public Builder state(BlockState state) {
            this.state = state;
            return this;
        }

        public Builder min(int min) {
            this.min_floor = min;
            return this;
        }

        public Builder max(int max) {
            this.max_floor = max;
            return this;
        }

        public Builder cluster(int distance, int amount) {
            this.cluster_distance = distance;
            this.cluster_amount = amount;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = Weight.of((int) (weight * 10));
            return this;
        }

        public Builder stackable() {
            this.stackable = true;
            return this;
        }

        public Builder add() {
            if (state == null)
                throw new IllegalStateException("Cannot add a loot without a state");
            loots.add(new Loot(state, min_floor, max_floor, cluster_distance, cluster_amount, weight, stackable));
            reset();
            return reset();
        }

        public WeightedRandomList<Loot> build() {
            return WeightedRandomList.create(loots);
        }
    }
}
