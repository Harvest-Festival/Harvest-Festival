package uk.joshiejack.harvestfestival.network.television;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.gui.screens.TelevisionScreen;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public record OpenTVScreenPacket(BlockPos pos) implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("open_tv_screen");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public OpenTVScreenPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        Minecraft.getInstance().setScreen(new TelevisionScreen(pos));
    }
}
