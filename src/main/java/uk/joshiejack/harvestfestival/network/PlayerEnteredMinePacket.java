package uk.joshiejack.harvestfestival.network;

import net.minecraft.core.HolderSet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.MineClientData;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public class PlayerEnteredMinePacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("player_entered_mine");
    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final MineSettings settings;

    public PlayerEnteredMinePacket(MineSettings settings) {
        this.settings = settings;
    }

    public PlayerEnteredMinePacket(FriendlyByteBuf buf) {
        settings = new MineSettings(HolderSet.direct(), buf.readByte(), buf.readByte(), buf.readByte(), buf.readByte());
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeByte(settings.chunksPerMine());
        buf.writeByte(settings.floorHeight());
        buf.writeByte(settings.elevatorFrequency());
    }

    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        MineClientData.setSettings(settings);
    }
}
