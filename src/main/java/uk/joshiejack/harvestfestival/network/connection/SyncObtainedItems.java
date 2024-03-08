package uk.joshiejack.harvestfestival.network.connection;

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

import java.util.*;

@Packet(value = PacketFlow.CLIENTBOUND)
public class SyncObtainedItems implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("sync_obtained_items");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final Set<Item> items;

    public SyncObtainedItems(Set<Item> items) {
        this.items = items;
    }

    public SyncObtainedItems(FriendlyByteBuf buf) {
        items = new HashSet<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            items.add(buf.readById(BuiltInRegistries.ITEM));
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(items.size());
        for (Item item: items) {
            buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(item));
        }
    }

    @Override
    public void handle(Player player) {
        HFPlayerData obtained = player.hasData(HFData.PLAYER_DATA) ? player.getData(HFData.PLAYER_DATA) : new HFPlayerData();
        items.forEach((item) -> obtained.obtain(player, item));
        player.setData(HFData.PLAYER_DATA, obtained);
    }
}