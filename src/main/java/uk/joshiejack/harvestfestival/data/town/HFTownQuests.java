package uk.joshiejack.harvestfestival.data.town;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.penguinlib.util.PenguinGroup;
import uk.joshiejack.settlements.Settlements;
import uk.joshiejack.settlements.world.quest.Quest;
import uk.joshiejack.settlements.world.quest.settings.Repeat;
import uk.joshiejack.settlements.world.quest.settings.Settings;

import java.util.Map;

public class HFTownQuests extends AbstractPenguinRegistryProvider<Quest> {
    public static final Settings DEFAULT = new Settings(Repeat.NONE, PenguinGroup.TEAM, false, true, "onRightClickNPC");

    public HFTownQuests(PackOutput output) {
        super(output, Settlements.Registries.QUESTS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, Quest> map) {
        //map.put(HarvestFestival.prefix("npcs/jenni/hearts/3"), new Quest(DEFAULT));
    }
}
