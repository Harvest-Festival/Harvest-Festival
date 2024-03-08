package uk.joshiejack.harvestfestival.world.mail;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;
import uk.joshiejack.penguinlib.util.icon.ListIcon;

import java.util.List;
import java.util.stream.Collectors;

public class GiftLetter extends AbstractLetter<GiftLetter> {
    public static final Codec<GiftLetter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("text", null).forGetter(letter -> letter.text),
            Codec.BOOL.optionalFieldOf("start_with", true).forGetter(AbstractLetter::startsWith),
            Codec.BOOL.optionalFieldOf("rejectable", true).forGetter(letter -> letter.rejectable),
            Codec.BOOL.optionalFieldOf("repeatable", false).forGetter(letter -> letter.repeatable),
            Codec.INT.optionalFieldOf("delivery_time", 1).forGetter(letter -> letter.deliveryTime),
            Codec.INT.optionalFieldOf("expiry", 0).forGetter(letter -> letter.expiry),
            ItemStack.CODEC.listOf().fieldOf("items").forGetter(giftLetter -> giftLetter.stacks)
    ).apply(instance, GiftLetter::new));
    private final NonNullList<ItemStack> stacks = NonNullList.create();
    private final Icon icon;

    public GiftLetter(AbstractLetter<?> letter, List<ItemStack> stacks) {
        this(letter.text, letter.startsWith(), letter.isRejectable(), letter.isRepeatable(), letter.getDeliveryTime(), letter.getExpiry(), stacks);
    }

    public GiftLetter(@Nullable Component text, boolean startWith, boolean rejectable, boolean repeatable, int delay, int expiry, List<ItemStack> stacks) {
        super(text, startWith, rejectable, repeatable, delay, expiry);
        this.stacks.addAll(stacks);
        this.icon = new ListIcon(stacks.stream().map(ItemIcon::new).collect(Collectors.toList()));
    }

    public Codec<GiftLetter> codec() {
        return CODEC;
    }

    @Override
    public LetterSerializer<GiftLetter> serializer() {
        return HFLetters.GIFT_SERIALIZER.value();
    }

    @Override
    public void accept(Player player) {
        stacks.forEach(stack -> ItemHandlerHelper.giveItemToPlayer(player, stack));
    }

    @OnlyIn(Dist.CLIENT)
    public void renderLetter(GuiGraphics gui, Font font, int x, int y, int mouseX, int mouseY) {
        super.renderLetter(gui, font, x, y, mouseX, mouseY);
        gui.pose().pushPose();
        gui.pose().scale(5F, 5F, 1F);
        icon.render(Minecraft.getInstance(), gui, (x+45)/5, (y+100)/5);
        gui.pose().popPose();
    }

    public static class Serializer extends LetterSerializer<GiftLetter> {
        @Override
        public GiftLetter fromNetwork(FriendlyByteBuf buf) {
            return new GiftLetter(super.fromNetwork(buf), buf.readList(FriendlyByteBuf::readItem));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, GiftLetter letter) {
            super.toNetwork(buf, letter);
            buf.writeCollection(letter.stacks, FriendlyByteBuf::writeItem);
        }
    }
}
