package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the multiple number of the mine is equal to the multiple number specified in the loot condition
 **/
public record FloorExactCondition(int floor) implements FloorCondition {
    public static final Codec<FloorExactCondition> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.INT.fieldOf("multiple")
            .forGetter(FloorExactCondition::floor))
            .apply(inst, FloorExactCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.MINE_FLOOR_EXACT.value();
    }

    @Override
    public boolean test(int floor) {
        return this.floor == floor;
    }

    public static LootItemCondition.Builder exact(int floor) {
        return () -> new FloorExactCondition(floor);
    }
}