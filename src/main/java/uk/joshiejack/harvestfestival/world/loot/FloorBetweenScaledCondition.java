package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the multiple number of the mine is between the multiple numbers specified in the loot condition
 * But only for every X floors
 **/
public record FloorBetweenScaledCondition(int start, int end, int scale) implements FloorCondition {
    public static final Codec<FloorBetweenScaledCondition> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(Codec.INT.fieldOf("start").forGetter(FloorBetweenScaledCondition::start),
                            Codec.INT.fieldOf("end").forGetter(FloorBetweenScaledCondition::end),
                            Codec.INT.optionalFieldOf("scale", 100).forGetter(FloorBetweenScaledCondition::scale))
                    .apply(inst, FloorBetweenScaledCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.MINE_FLOOR_BETWEEN_SCALED.value();
    }

    @Override
    public boolean test(int floor) {
        int remainder = floor % scale;
        return remainder == 0 && start == scale || remainder >= start && remainder <= end;
    }

    public static LootItemCondition.Builder betweenScaled(int start, int end) {
        return () -> new FloorBetweenScaledCondition(start, end, 100);
    }

    public static LootItemCondition.Builder betweenScaled(int start, int end, int scale) {
        return () -> new FloorBetweenScaledCondition(start, end, scale);
    }
}