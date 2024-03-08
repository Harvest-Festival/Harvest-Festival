package uk.joshiejack.harvestfestival.world.achievements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;

public class EntityKilledStatAchievement extends AbstractStatAchievement {
    public static final Codec<EntityKilledStatAchievement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Achievement::id),
            Codec.BOOL.optionalFieldOf("custom_shadow", false).forGetter(Achievement::hasShadow),
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(achievement -> achievement.entityType),
            Codec.INT.fieldOf("requirement").forGetter(achievement -> achievement.requirement)
    ).apply(instance, EntityKilledStatAchievement::new));

    private final EntityType<?> entityType;

    public EntityKilledStatAchievement(ResourceLocation id, EntityType<?> entityType, int requirement) {
        this(id, false, entityType, requirement);
    }
    public EntityKilledStatAchievement(ResourceLocation id, boolean hasCustomShadow, EntityType<?> entityType, int requirement) {
        super(id, hasCustomShadow, requirement);
        this.entityType = entityType;
    }

    @Override
    public Stat<?> getCollection() {
        return Stats.ENTITY_KILLED.get(entityType);
    }

    @Override
    public Codec<? extends Achievement> codec() {
        return CODEC;
    }
}