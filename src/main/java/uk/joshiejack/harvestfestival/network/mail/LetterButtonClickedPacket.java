package uk.joshiejack.harvestfestival.network.mail;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.harvestfestival.world.mail.PostalOffice;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinRegistryPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(PacketFlow.SERVERBOUND)
public class LetterButtonClickedPacket extends PenguinRegistryPacket<AbstractLetter<?>> {
    public static final ResourceLocation ID = PenguinLib.prefix("clicked_letter_button");
    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final Action action;

    public LetterButtonClickedPacket(AbstractLetter<?> letter, Action action) {
        super(HFRegistries.LETTERS, letter);
        this.action = action;
    }

    public LetterButtonClickedPacket(FriendlyByteBuf buffer) {
        super(HFRegistries.LETTERS, buffer);
        this.action = buffer.readEnum(Action.class);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        super.write(buffer);
        buffer.writeEnum(action);
    }

    @Override
    protected void handle(Player player, AbstractLetter letter) {
        ServerLevel level = (ServerLevel) player.level();
        PostalOffice office = PostalOffice.get(level);
        if (office.contains(player, letter)) {
            if (action == Action.ACCEPT) letter.accept(player);
            else if (letter.isRejectable()) letter.reject(player);
            office.remove(level, player, letter);
            office.setDirty();
        }
    }

    public enum Action {
        ACCEPT(18), REJECT(14);

        private final int width;

        Action(int width) {
            this.width = width;
        }

        public int width() {
            return width;
        }
    }
}