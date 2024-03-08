package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the multiple number of the mine is less than the multiple number specified in the loot condition
 **/
public record FloorBeforeCondition(int floor) implements FloorCondition {
    public static final Codec<FloorBeforeCondition> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.INT.fieldOf("multiple")
            .forGetter(FloorBeforeCondition::floor))
            .apply(inst, FloorBeforeCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.MINE_FLOOR_BEFORE.value();
    }

    @Override
    public boolean test(int floor) {
        return floor < this.floor;
    }

    public static LootItemCondition.Builder before(int floor) {
        return () -> new FloorBeforeCondition(floor);
    }
}