package uk.joshiejack.harvestfestival.world.mail;

import com.mojang.serialization.Codec;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.penguinlib.util.PenguinGroup;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractLetter<O extends AbstractLetter<O>> implements ReloadableRegistry.PenguinRegistry<AbstractLetter<?>> {
    protected final Component text;
    protected final PenguinGroup group;
    protected final boolean startsWith;
    protected final boolean rejectable;
    protected final boolean repeatable;
    protected final int expiry;
    protected final int deliveryTime;

    public AbstractLetter(@Nullable Component text, boolean startsWith, boolean rejectable, boolean repeatable, int deliveryTime, int expiry) {
        this.group = PenguinGroup.PLAYER;
        this.text = text;
        this.startsWith = startsWith;
        this.rejectable = rejectable;
        this.repeatable = repeatable;
        this.deliveryTime = deliveryTime;
        this.expiry = expiry;
    }

    @Override
    public ResourceLocation id() {
        return HFRegistries.LETTERS.getID(this);
    }

    public abstract Codec<? extends AbstractLetter<O>> codec();

    public abstract LetterSerializer<O> serializer();

    @Override
    public O fromNetwork(FriendlyByteBuf friendlyByteBuf) {
        return serializer().fromNetwork(friendlyByteBuf);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf) {
        serializer().toNetwork(friendlyByteBuf, (O) this);
    }

    public boolean startsWith() {
        return true;
    }

    public boolean isRejectable() {
        return rejectable;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public boolean expires() {
        return expiry > 0;
    }

    public int getExpiry() {
        return expiry;
    }

    public void accept(Player player) {}

    public void reject(Player player) {}

    public void onClosed(Player player) {}


    /** Called when the gui is init, so that you can add buttons
     *  Take note that buttons that accept the letter or reject
     *  are automatically added
     * @param list  the list of buttons
     * @param x     guiLeft
     * @param y     guiTop */
    @OnlyIn(Dist.CLIENT)
    public void initGui(List<AbstractWidget> list, int x, int y) {}

    /** Called when rendering the letter
     * @param gui   the gui
     * @param font  the font
     * @param x     guiLeft
     * @param y     guiTop
     * @param mouseX    x position of mouse
     * @param mouseY    y position of mouse */
    @OnlyIn(Dist.CLIENT)
    public void renderLetter(GuiGraphics gui, Font font, int x, int y, int mouseX, int mouseY) {
        gui.drawWordWrap(font, text == null ? Component.translatable("letter." + id().getNamespace() + "." + id().getPath() + ".text") : text, x + 15, y + 15, 142, 4210752);
    }

    public PenguinGroup getGroup() {
        return group;
    }

    public abstract static class LetterSerializer<O extends AbstractLetter<O>> {
        @SuppressWarnings("unchecked")
        public O fromNetwork(FriendlyByteBuf buf) {
            return (O) new TextLetter(
                    buf.readBoolean() ? buf.readComponent() : null,
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readVarInt(),
                    buf.readVarInt());
        }
        public void toNetwork(FriendlyByteBuf buf, O letter) {
            buf.writeBoolean(letter.text != null);
            if (letter.text != null)
                buf.writeComponent(letter.text);
            buf.writeBoolean(letter.startsWith);
            buf.writeBoolean(letter.rejectable);
            buf.writeBoolean(letter.repeatable);
            buf.writeVarInt(letter.deliveryTime);
            buf.writeVarInt(letter.expiry);
        }
    }
}
