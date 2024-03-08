package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.data.generator.AbstractNoteProvider;
import uk.joshiejack.penguinlib.data.generator.builder.CategoryBuilder;
import uk.joshiejack.penguinlib.world.note.Category;
import uk.joshiejack.penguinlib.world.note.Note;

import java.util.Map;

public class HFNoteGeneration extends AbstractNoteProvider {
    public HFNoteGeneration(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildNotes(Map<ResourceLocation, Category> categories, Map<ResourceLocation, Note> notes) {
        CategoryBuilder.category().withItemIcon(HFItems.BASIC_AXE)
                .withNote("energy").withNoteIcon().end()
                .withNote("fishing").withItemIcon(Items.FISHING_ROD).setNoteType("lifespan").end() //TODO: With the piscary fishing rod
                .withNote("shovel").withItemIcon(Items.STONE_SHOVEL).end() //TODO: With the HF shovel
                .withNote("hammer").withItemIcon(HFItems.BASIC_HAMMER).end()
                .withNote("axe").withItemIcon(HFItems.BASIC_AXE).end()
                .save(categories, notes, new ResourceLocation(HarvestFestival.MODID, "activities"));
    }
}
