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
import uk.joshiejack.penguinlib.network.packet.PenguinRegistryListPacket;
import uk.joshiejack.penguinlib.util.PenguinGroup;
import uk.joshiejack.penguinlib.util.registry.Packet;

import java.util.List;

@Packet(PacketFlow.CLIENTBOUND)
public class SyncLettersPacket extends PenguinRegistryListPacket<AbstractLetter<?>> {
    public static final ResourceLocation ID = PenguinLib.prefix("sync_letters");
    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public SyncLettersPacket(List<AbstractLetter<?>> letter) {
        super(HFRegistries.LETTERS, letter);
    }

    public SyncLettersPacket(FriendlyByteBuf buffer) {
        super(HFRegistries.LETTERS, buffer);
    }

    @Override
    public void handle(Player player, List<AbstractLetter<?>> letters) {
        Inbox.PLAYER.set(letters, PenguinGroup.PLAYER, PenguinGroup.TEAM, PenguinGroup.GLOBAL);
    }
}