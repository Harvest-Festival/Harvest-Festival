package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the multiple number of the mine is greater than or equal to the multiple number specified in the loot condition
 **/
public record FloorFromCondition(int floor) implements FloorCondition {
    public static final Codec<FloorFromCondition> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.INT.fieldOf("multiple")
            .forGetter(FloorFromCondition::floor))
            .apply(inst, FloorFromCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.MINE_FLOOR_FROM.value();
    }

    @Override
    public boolean test(int floor) {
        return floor >= this.floor;
    }

    public static LootItemCondition.Builder from(int floor) {
        return () -> new FloorMultipleOfCondition(floor);
    }
}