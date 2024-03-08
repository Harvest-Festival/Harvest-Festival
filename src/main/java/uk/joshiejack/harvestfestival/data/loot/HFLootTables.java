package uk.joshiejack.harvestfestival.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class HFLootTables extends LootTableProvider {
    public HFLootTables(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(HFBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(HFEntityLootTables::new, LootContextParamSets.ENTITY)));
    }
}