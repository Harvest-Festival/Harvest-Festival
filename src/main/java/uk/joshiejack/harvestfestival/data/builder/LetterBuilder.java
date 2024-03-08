package uk.joshiejack.harvestfestival.data.builder;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.world.mail.GiftLetter;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.harvestfestival.world.mail.TextLetter;
import uk.joshiejack.penguinlib.data.generator.builder.NoteBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LetterBuilder {
    private final List<ItemStack> items = new ArrayList<>();
    private final List<Pair<String, NoteBuilder>> notes = new ArrayList<>();
    private Component text;
    private boolean startWith = false;
    private boolean repeatable = false;
    private boolean rejectable = false;
    private int deliveryTime = 0;
    private int expiryTime = 0;

    public static LetterBuilder category() {
        return new LetterBuilder();
    }

    public LetterBuilder startWith() {
        this.startWith = true;
        return this;
    }

    public LetterBuilder repeatable() {
        this.repeatable = true;
        return this;
    }

    public LetterBuilder rejectable() {
        this.rejectable = true;
        return this;
    }

    public LetterBuilder forceAccept() {
        this.rejectable = false;
        return this;
    }

    public LetterBuilder daysToDeliver(int days) {
        this.deliveryTime = days;
        return this;
    }

    public LetterBuilder expires(int days) {
        this.expiryTime = days;
        return this;
    }

    public LetterBuilder withLiteralText(String text) {
        this.text = Component.literal(text);
        return this;
    }

    public LetterBuilder withItems(ItemStack... items) {
        this.rejectable = true;
        this.items.addAll(Lists.newArrayList(items));
        return this;
    }

    public void save(Map<ResourceLocation, AbstractLetter<?>> letters, ResourceLocation resource) {
        AbstractLetter<?> letter = items.isEmpty() ? new TextLetter(text, startWith, rejectable, repeatable, deliveryTime, expiryTime) :
                new GiftLetter(text, startWith, rejectable, repeatable, deliveryTime, expiryTime, items);
        letters.put(resource, letter);
    }
}
