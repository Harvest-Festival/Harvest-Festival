package uk.joshiejack.harvestfestival.network.request;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.client.renderer.fertilizer.FertilizerLevelOverlay;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

import java.util.List;

@Packet(value = PacketFlow.CLIENTBOUND)
public class SendFertilizerDataPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("send_fertilizer_data");
    private final List<Pair<Fertilizer, BlockPos>> data;
    private final RequestFertilizerDataPacket.RequestType type;

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public SendFertilizerDataPacket(List<Pair<Fertilizer, BlockPos>> data, RequestFertilizerDataPacket.RequestType type) {
        this.data = data;
        this.type = type;
    }

    public SendFertilizerDataPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(RequestFertilizerDataPacket.RequestType.class);
        this.data = buf.readList((buf1) -> {
            Fertilizer fertilizer = buf.readById(HFRegistries.FERTILIZER);
            BlockPos pos = buf1.readBlockPos();
            return Pair.of(fertilizer, pos);
        });
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeCollection(data, (buf1, pair) -> {
            buf1.writeId(HFRegistries.FERTILIZER, pair.getLeft());
            buf1.writeBlockPos(pair.getRight());
        });
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        FertilizerLevelOverlay.addRenderData(data, type);
    }
}