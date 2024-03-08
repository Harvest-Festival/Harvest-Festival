package uk.joshiejack.harvestfestival.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.client.renderer.fertilizer.FertilizerLevelOverlay;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public class AddFertilizerPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("add_fertilizer");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final BlockPos pos;
    private final Fertilizer fertilizer;

    public AddFertilizerPacket(BlockPos pos, Fertilizer fertilizer) {
        this.pos = pos;
        this.fertilizer = fertilizer;
    }

    public AddFertilizerPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.fertilizer = HFRegistries.FERTILIZER.get(buf.readResourceLocation());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeId(HFRegistries.FERTILIZER, fertilizer);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        FertilizerLevelOverlay.addBlock(fertilizer, pos);
    }
}