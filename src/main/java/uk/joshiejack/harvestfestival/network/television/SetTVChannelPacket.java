package uk.joshiejack.harvestfestival.network.television;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.block.entity.TelevisionBlockEntity;
import uk.joshiejack.harvestfestival.world.television.TVChannel;
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.scripting.ScriptFactory;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.SERVERBOUND)
public record SetTVChannelPacket(BlockPos pos, TVChannel channel) implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("set_tv_channel");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public SetTVChannelPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), PenguinNetwork.readRegistry(HFRegistries.TV_CHANNELS, buf));
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBlockPos(pos);
        PenguinNetwork.writeRegistry(channel, to);
    }

    @Override
    public void handle(Player player) {
        BlockEntity tile = player.level().getBlockEntity(pos);
        if (tile instanceof TelevisionBlockEntity television) {
            if (channel == TVChannel.OFF)
                television.setProgram(TVProgram.OFF);
            if (channel.getScript() != null)
                ScriptFactory.callFunction(channel.getScript(), "watch", player, television);
        }
    }
}