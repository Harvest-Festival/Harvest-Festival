package uk.joshiejack.harvestfestival.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.MineHUDRenderer;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public class PlayerChangedTierPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("player_changed_tier");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final ResourceLocation hud;

    public PlayerChangedTierPacket(ResourceLocation hud) {
        this.hud = hud;
    }

    public PlayerChangedTierPacket(FriendlyByteBuf buf) {
        hud = buf.readResourceLocation();
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeResourceLocation(hud);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClient() {
        MineHUDRenderer.setHUD(hud);
    }
}
