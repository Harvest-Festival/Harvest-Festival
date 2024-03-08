package uk.joshiejack.harvestfestival.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.client.Inbox;
import uk.joshiejack.harvestfestival.client.gui.screens.LetterScreen;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.SimplePacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public class OpenLetterScreenPacket implements SimplePacket {
    public static final ResourceLocation ID = PenguinLib.prefix("open_letter_screen");
    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public OpenLetterScreenPacket(@Nullable FriendlyByteBuf buf) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        if (!Inbox.PLAYER.getLetters().isEmpty())
            Minecraft.getInstance().setScreen(new LetterScreen(Inbox.PLAYER.getLetters().get(0)));
    }
}