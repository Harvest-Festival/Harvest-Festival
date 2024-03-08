package uk.joshiejack.harvestfestival.world.level.mine.tier;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.ArrayList;
import java.util.List;

public record Entities(ResourceLocation entity, MobCategory type, List<String> ranges, int weight, int min, int max) {
    public static final Codec<Entities> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            ResourceLocation.CODEC.fieldOf("entity").forGetter(inst -> inst.entity),
                            MobCategory.CODEC.optionalFieldOf("type", MobCategory.MONSTER).forGetter(inst -> inst.type),
                            Codec.STRING.listOf().fieldOf("ranges").forGetter(inst -> inst.ranges),
                            Codec.INT.fieldOf("weight").forGetter(inst -> inst.weight),
                            Codec.INT.optionalFieldOf("min", 1).forGetter(inst -> inst.min),
                            Codec.INT.optionalFieldOf("max", 1).forGetter(inst -> inst.max)
                    )
                    .apply(codec, Entities::new)
    );

    public EntityType<?> getEntityType() {
        return BuiltInRegistries.ENTITY_TYPE.get(entity);
    }

    public static class Builder {
        private final List<Entities> entities = new ArrayList<>();

        public static Builder create() {
            return new Builder();
        }

        public Builder add(String entity, MobCategory type, String ranges, int weight, int min, int max) {
            entities.add(new Entities(new ResourceLocation(entity), type, Lists.newArrayList(ranges.split(",")), weight, min, max));
            return this;
        }

        public List<Entities> build() {
            return entities;
        }
    }
}