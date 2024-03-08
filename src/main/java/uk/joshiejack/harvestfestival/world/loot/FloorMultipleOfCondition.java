package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

/* Checks if the multiple is a multiple of the number specified in the loot condition */
public record FloorMultipleOfCondition(int multiple) implements FloorCondition {
    public static final Codec<FloorMultipleOfCondition> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.INT.fieldOf("multiple")
            .forGetter(FloorMultipleOfCondition::multiple))
            .apply(inst, FloorMultipleOfCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.MINE_FLOOR_MULTIPLE_OF.value();
    }

    @Override
    public boolean test(int floor) {
        return floor % multiple == 0;
    }

    public static LootItemCondition.Builder multipleOf(int multiple) {
        return () -> new FloorMultipleOfCondition(multiple);
    }
}