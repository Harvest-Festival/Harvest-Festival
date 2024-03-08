package uk.joshiejack.harvestfestival.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.MineTeleporter;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.TeleportType;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.SERVERBOUND)
public class ElevatorButtonClickedPacket implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("elevator_button_clicked");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    private final int floor;

    public ElevatorButtonClickedPacket(int floor) {
        this.floor = floor;
    }

    public ElevatorButtonClickedPacket(FriendlyByteBuf buf) {
        floor = buf.readShort();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeShort(floor);
    }

    @Override
    public void handleServer(ServerPlayer player) {
        int maximumFloor = MineHelper.getMaximumFloorReached(player);
        if (floor <= maximumFloor && player.level() instanceof ServerLevel sl
                && sl.getChunkSource().getGenerator() instanceof MineChunkGenerator gen && floor % gen.settings.elevatorFrequency() == 1) {
            int mineID = MineHelper.getMineIDForTeleport(gen.settings, player.blockPosition());
            MineTeleporter.teleportToMineFloor(sl, player, mineID, floor, TeleportType.ELEVATOR);
        }
    }
}
