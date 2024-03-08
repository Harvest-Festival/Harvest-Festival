package uk.joshiejack.harvestfestival.world.mail;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

import javax.annotation.Nullable;

public class TextLetter extends AbstractLetter<TextLetter> {
    public static final Codec<TextLetter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("text", null).forGetter(letter -> letter.text),
            Codec.BOOL.optionalFieldOf("start_with", true).forGetter(AbstractLetter::startsWith),
            Codec.BOOL.optionalFieldOf("rejectable", false).forGetter(letter -> letter.rejectable),
            Codec.BOOL.optionalFieldOf("repeatable", false).forGetter(letter -> letter.repeatable),
            Codec.INT.optionalFieldOf("delivery_time", 1).forGetter(letter -> letter.deliveryTime),
            Codec.INT.optionalFieldOf("expiry_time", 0).forGetter(letter -> letter.expiry)
    ).apply(instance, TextLetter::new));

    public TextLetter(@Nullable Component text, boolean startsWith, boolean rejectable, boolean repeatable, int deliveryTime, int expiry) {
        super(text, startsWith, rejectable, repeatable, deliveryTime, expiry);
    }

    public Codec<TextLetter> codec() {
        return CODEC;
    }

    public LetterSerializer<TextLetter> serializer() {
        return HFLetters.LETTER_SERIALIZER.value();
    }

    public static class Serializer extends LetterSerializer<TextLetter> {
        @Override
        public TextLetter fromNetwork(FriendlyByteBuf buf) {
            return super.fromNetwork(buf);
        }
    }
}
