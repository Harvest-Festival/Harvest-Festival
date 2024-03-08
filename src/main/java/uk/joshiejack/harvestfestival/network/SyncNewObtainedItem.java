package uk.joshiejack.harvestfestival.network;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.collections.HFPlayerData;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public class SyncNewObtainedItem implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("sync_obtained_item");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final Item item;

    public SyncNewObtainedItem(Item item) {
        this.item = item;
    }

    public SyncNewObtainedItem(FriendlyByteBuf buf) {
        item = buf.readById(BuiltInRegistries.ITEM);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(item));
    }

    @Override
    public void handle(Player player) {
        HFPlayerData obtained = player.getData(HFData.PLAYER_DATA);
        obtained.obtain(player, item);
    }
}