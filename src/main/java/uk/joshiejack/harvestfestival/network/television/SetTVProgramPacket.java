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
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(value = PacketFlow.CLIENTBOUND)
public record SetTVProgramPacket(BlockPos pos, TVProgram program) implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("set_tv_program");

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }

    public SetTVProgramPacket(FriendlyByteBuf from) {
        this(from.readBlockPos(), PenguinNetwork.readRegistry(HFRegistries.TV_PROGRAMS, from));
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeLong(pos.asLong());
        PenguinNetwork.writeRegistry(program, to);
    }

    @Override
    public void handle(Player player) {
        BlockEntity tile = player.level().getBlockEntity(pos);
        if (tile instanceof TelevisionBlockEntity) {
            ((TelevisionBlockEntity)tile).setProgram(program);
        }
    }
}

