package uk.joshiejack.harvestfestival.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.registry.Packet;

import javax.annotation.Nonnull;
import java.util.Objects;

@Packet(PacketFlow.CLIENTBOUND)
public class SyncEnergyStats implements PenguinPacket {
    public static final ResourceLocation ID = PenguinLib.prefix("sync_energy_stats");
    @Override
    public @Nonnull ResourceLocation id() {
        return ID;
    }

    private final double energyLevel;
    private final double energyMaximum;
    private final double maxHealth;

    public SyncEnergyStats(EnergyData stats) {
        energyLevel = stats.getEnergyLevel();
        energyMaximum = stats.getMaxEnergy();
        maxHealth = stats.maxHearts;
    }

    public SyncEnergyStats (FriendlyByteBuf from) {
        energyLevel = from.readShort();
        energyMaximum = from.readShort();
        maxHealth = from.readDouble();
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeDouble(energyLevel);
        to.writeDouble(energyMaximum);
        to.writeDouble(maxHealth);
    }

    @Override
    public void handle(Player player) {
        EnergyData stats = player.getFoodData() instanceof EnergyData ? (EnergyData) player.getFoodData() : new EnergyData(player);
        if (player.getFoodData() != stats)
            player.foodData = stats;
        stats.set(energyLevel, energyMaximum, maxHealth);
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(stats.maxHearts);
    }
}
