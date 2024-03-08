package uk.joshiejack.harvestfestival.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

@Packet(PacketFlow.CLIENTBOUND)
public class SyncEnergyLevel implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("sync_energy_level");
    @Override
    public ResourceLocation id() {
        return ID;
    }

    private final double energyLevel;

    public SyncEnergyLevel(double energyLevel) {
        this.energyLevel = energyLevel;
    }

    public SyncEnergyLevel(FriendlyByteBuf from) {
        energyLevel = from.readDouble();
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeDouble(energyLevel);
    }

    @Override
    public void handle(Player player) {
        EnergyData stats = player.getFoodData() instanceof EnergyData ? (EnergyData) player.getFoodData() : new EnergyData(player);
        if (player.getFoodData() != stats)
            player.foodData = stats;
        stats.setEnergyLevel(energyLevel);
    }
}
