package uk.joshiejack.harvestfestival.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.gui.screens.ElevatorScreen;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public class OpenElevatorScreenPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("open_elevator_screen");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final int maximumFloor;

    public OpenElevatorScreenPacket(int maximumFloor) {
        this.maximumFloor = maximumFloor;
    }

    public OpenElevatorScreenPacket(FriendlyByteBuf buf) {
        maximumFloor = buf.readShort();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeShort(maximumFloor);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        Minecraft.getInstance().setScreen(new ElevatorScreen(HFBlocks.ELEVATOR.get().getName(), maximumFloor));
    }
}
