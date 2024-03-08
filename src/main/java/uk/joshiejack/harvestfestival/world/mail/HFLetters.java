package uk.joshiejack.harvestfestival.world.mail;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;

public class HFLetters {
    //Deferred Registries (Synced to client on join)
    public static final DeferredRegister<Codec<? extends AbstractLetter<?>>> LETTER_TYPES = DeferredRegister.create(HFRegistries.Keys.LETTER, HarvestFestival.MODID);
    public static final DeferredRegister<AbstractLetter.LetterSerializer<?>> LETTER_TYPE_SERIALIZERS = DeferredRegister.create(HFRegistries.Keys.LETTER_SERIALIZER, HarvestFestival.MODID);

    public static final DeferredHolder<Codec<? extends AbstractLetter<?>>, Codec<? extends AbstractLetter<TextLetter>>> LETTER = LETTER_TYPES.register("text", () -> TextLetter.CODEC);
    public static final DeferredHolder<Codec<? extends AbstractLetter<?>>, Codec<? extends AbstractLetter<GiftLetter>>> GIFT = LETTER_TYPES.register("gift", () -> GiftLetter.CODEC);

    public static final DeferredHolder<AbstractLetter.LetterSerializer<?>, AbstractLetter.LetterSerializer<GiftLetter>> GIFT_SERIALIZER = LETTER_TYPE_SERIALIZERS.register("gift", GiftLetter.Serializer::new);
    public static final DeferredHolder<AbstractLetter.LetterSerializer<?>, AbstractLetter.LetterSerializer<TextLetter>> LETTER_SERIALIZER = LETTER_TYPE_SERIALIZERS.register("text", TextLetter.Serializer::new);

    public static void register(IEventBus eventBus) {
        LETTER_TYPES.register(eventBus);
        LETTER_TYPE_SERIALIZERS.register(eventBus);
    }
}
