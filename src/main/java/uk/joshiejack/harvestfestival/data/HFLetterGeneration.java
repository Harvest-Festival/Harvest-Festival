package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.data.builder.LetterBuilder;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;

import java.util.Map;

public class HFLetterGeneration extends AbstractLetterProvider {
    public HFLetterGeneration(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildLetters(Map<ResourceLocation, AbstractLetter<?>> notes) {
        LetterBuilder.category().withLiteralText("I hope you're enjoying the festival, I've sent you a little something to help you out!")
                .withItems(new ItemStack(Items.CARROT)).startWith().repeatable().daysToDeliver(0).expires(7).save(notes, new ResourceLocation(HarvestFestival.MODID, "crop_gift"));
    }
}
