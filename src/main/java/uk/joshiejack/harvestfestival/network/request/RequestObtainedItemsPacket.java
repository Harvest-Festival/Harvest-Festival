package uk.joshiejack.harvestfestival.network.request;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.network.connection.SyncObtainedItems;
import uk.joshiejack.harvestfestival.world.collections.HFPlayerData;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.SimplePacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.SERVERBOUND)
public record RequestObtainedItemsPacket() implements SimplePacket {
    public static final ResourceLocation ID = PenguinLib.prefix("request_obtained_items");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public RequestObtainedItemsPacket() {}
    public RequestObtainedItemsPacket(FriendlyByteBuf buf) { this(); }

    @Override
    public void handleServer(ServerPlayer player) {
        HFPlayerData obtained = player.getData(HFData.PLAYER_DATA);
        PenguinNetwork.sendToClient(player, new SyncObtainedItems(obtained.getAll())); //Sync obtained items
    }
}