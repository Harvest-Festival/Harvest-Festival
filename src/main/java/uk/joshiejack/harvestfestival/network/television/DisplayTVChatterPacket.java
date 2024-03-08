package uk.joshiejack.harvestfestival.network.television;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.gui.screens.TelevisionChatter;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public record DisplayTVChatterPacket(BlockPos pos, Component component) implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("tv_chatter");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public DisplayTVChatterPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readComponent());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeComponent(component);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        Minecraft.getInstance().setScreen(new TelevisionChatter(pos, component));
    }
}

