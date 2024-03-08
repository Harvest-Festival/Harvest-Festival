package uk.joshiejack.harvestfestival.world.level.mine.tier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public record NamedRange(Holder<Biome> biome, int min, int max, @Nonnull BlockState floor, @Nonnull BlockState wall, @Nonnull BlockState hole, int maxEntities,
                         float lootChanceMin, float lootChanceMax, int lootChanceDivisor) {
    public static final Codec<NamedRange> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            Biome.CODEC.fieldOf("biome").forGetter(inst -> inst.biome),
                            Codec.INT.fieldOf("min").forGetter(inst -> inst.min),
                            Codec.INT.fieldOf("max").forGetter(inst -> inst.max),
                            BlockState.CODEC.optionalFieldOf("floor", Blocks.AIR.defaultBlockState()).forGetter(inst -> inst.floor),
                            BlockState.CODEC.optionalFieldOf("wall", Blocks.AIR.defaultBlockState()).forGetter(inst -> inst.wall),
                            BlockState.CODEC.optionalFieldOf("hole", Blocks.AIR.defaultBlockState()).forGetter(inst -> inst.hole),
                            Codec.INT.fieldOf("max_mobs").forGetter(inst -> inst.maxEntities),
                            Codec.FLOAT.optionalFieldOf("loot_chance_min", 0.0025F).forGetter(inst -> inst.lootChanceMin),
                            Codec.FLOAT.optionalFieldOf("loot_chance_max", 0.05F).forGetter(inst -> inst.lootChanceMax),
                            Codec.INT.optionalFieldOf("loot_chance_divisor", 8192).forGetter(inst -> inst.lootChanceDivisor)
                    )
                    .apply(codec, NamedRange::new)
    );

    public NamedRange(Holder<Biome> biome, int min, int max) {
        this(biome, min, max, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), 8, 0.0025F, 0.1F, 4096);
    }

    public BlockState floor(BlockState defaultState) {
        return floor == Blocks.AIR.defaultBlockState() ? defaultState : floor;
    }

    public BlockState wall(BlockState defaultState) {
        return wall == Blocks.AIR.defaultBlockState() ? defaultState : wall;
    }

    public BlockState hole(BlockState defaultState) {
        return floor == Blocks.AIR.defaultBlockState() ? defaultState : floor;
    }

    //Builder class to build a list of named ranges

    public static class Builder {
        private final List<NamedRange> ranges = new ArrayList<>();
        private Holder<Biome> biome;
        private int min;
        private int max;
        private BlockState floor;
        private BlockState wall;
        private BlockState hole;
        private int maxEntities;
        private float lootChanceMin;
        private float lootChanceMax;
        private int lootChanceDivisor;

        public static Builder create() {
            return new Builder().setToDefault();
        }

        private Builder setToDefault() {
            biome = null;
            min = 1;
            max = 10;
            floor = Blocks.AIR.defaultBlockState();
            wall = Blocks.AIR.defaultBlockState();
            hole = Blocks.AIR.defaultBlockState();
            maxEntities = 8;
            lootChanceMin = 0.005F;
            lootChanceMax = 0.25F;
            lootChanceDivisor = 960;
            return this;
        }

        public Builder biome(Holder<Biome> biome) {
            this.biome = biome;
            return this;
        }

        public Builder min(int min) {
            this.min = min;
            return this;
        }

        public Builder max(int max) {
            this.max = max;
            return this;
        }

        public Builder floor(BlockState floor) {
            this.floor = floor;
            return this;
        }

        public Builder wall(BlockState wall) {
            this.wall = wall;
            return this;
        }

        public Builder hole(BlockState hole) {
            this.hole = hole;
            return this;
        }

        public Builder maxEntities(int maxEntities) {
            this.maxEntities = maxEntities;
            return this;
        }

        public Builder lootChanceMin(float lootChanceMin) {
            this.lootChanceMin = lootChanceMin;
            return this;
        }

        public Builder lootChanceMax(float lootChanceMax) {
            this.lootChanceMax = lootChanceMax;
            return this;
        }

        public Builder lootChanceDivisor(int lootChanceDivisor) {
            this.lootChanceDivisor = lootChanceDivisor;
            return this;
        }

        public Builder add() {
            if (biome == null)
                throw new IllegalStateException("Biome cannot be null");
            ranges.add(new NamedRange(biome, min, max, floor, wall, hole, maxEntities, lootChanceMin, lootChanceMax, lootChanceDivisor));
            return setToDefault();
        }

        public List<NamedRange> build() {
            return ranges;
        }
    }
}