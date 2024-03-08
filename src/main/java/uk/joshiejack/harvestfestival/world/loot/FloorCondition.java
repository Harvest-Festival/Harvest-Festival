package uk.joshiejack.harvestfestival.world.loot;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;

public interface FloorCondition extends LootItemCondition {
    @Override
    default boolean test(LootContext lootContext) {
        Vec3 pos = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        return pos != null && lootContext.getLevel().getChunkSource().getGenerator() instanceof MineChunkGenerator generator
                && test(MineHelper.getFloorFromPos(lootContext.getLevel(), generator.settings, pos));
    }

    boolean test(int floor);
}
