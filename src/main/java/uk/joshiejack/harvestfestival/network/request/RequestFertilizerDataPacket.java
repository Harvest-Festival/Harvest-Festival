package uk.joshiejack.harvestfestival.network.request;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTickData;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.SERVERBOUND)
public class RequestFertilizerDataPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("request_fertilizer_data");
    private final ChunkPos pos;
    private final RequestType type;

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public RequestFertilizerDataPacket(ChunkPos pos, RequestType type) {
        this.pos = pos;
        this.type = type;
    }

    public RequestFertilizerDataPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(RequestType.class);
        this.pos = buf.readChunkPos();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeChunkPos(pos);
    }

    @Override
    public void handleServer(ServerPlayer player) {
        DailyTickData data = DailyTickData.getDataForChunk(player.level().getChunk(pos.x, pos.z));
        PenguinNetwork.sendToClient(player, new SendFertilizerDataPacket(data.getFertilizerData(), type));
    }

    public enum RequestType {
        LOAD, UNLOAD
    }
}