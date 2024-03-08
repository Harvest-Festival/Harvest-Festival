package uk.joshiejack.harvestfestival.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.gui.screens.CalendarScreen;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.SimplePacket;
import uk.joshiejack.penguinlib.util.registry.Packet;
import uk.joshiejack.penguinlib.util.helper.PlayerHelper;

@Packet(value = PacketFlow.CLIENTBOUND)
public record OpenCalendarScreenPacket() implements SimplePacket {
    public static final ResourceLocation ID = PenguinLib.prefix("open_calendar_screen");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        Minecraft.getInstance().setScreen(new CalendarScreen(PlayerHelper.getClient()));
    }
}
