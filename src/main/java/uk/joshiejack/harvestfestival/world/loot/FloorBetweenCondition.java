package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

/* Checks if the multiple number of the mine is between the multiple numbers specified in the loot condition */
public record FloorBetweenCondition(int start, int end) implements FloorCondition {
    public static final Codec<FloorBetweenCondition> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(Codec.INT.fieldOf("start").forGetter(FloorBetweenCondition::start),
                            Codec.INT.fieldOf("end").forGetter(FloorBetweenCondition::end))
                    .apply(inst, FloorBetweenCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.MINE_FLOOR_BETWEEN.value();
    }

    @Override
    public boolean test(int floor) {
        return floor >= start && floor <= end;
    }

    public static LootItemCondition.Builder between(int start, int end) {
        return () -> new FloorBetweenCondition(start, end);
    }
}