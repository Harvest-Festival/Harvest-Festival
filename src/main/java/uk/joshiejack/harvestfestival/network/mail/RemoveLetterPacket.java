package uk.joshiejack.harvestfestival.network.mail;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.client.Inbox;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinRegistryPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(PacketFlow.CLIENTBOUND)
public class RemoveLetterPacket extends PenguinRegistryPacket<AbstractLetter<?>> {
    public static final ResourceLocation ID = PenguinLib.prefix("remove_letter");
    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public RemoveLetterPacket(AbstractLetter letter) {
        super(HFRegistries.LETTERS, letter);
    }

    public RemoveLetterPacket(FriendlyByteBuf buffer) {
        super(HFRegistries.LETTERS, buffer);
    }

    @Override
    protected void handle(Player player, AbstractLetter letter) {
        Inbox.PLAYER.remove(letter);
    }
}