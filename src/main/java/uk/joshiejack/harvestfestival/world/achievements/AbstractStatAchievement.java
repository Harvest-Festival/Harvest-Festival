package uk.joshiejack.harvestfestival.world.achievements;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class AbstractStatAchievement extends Achievement {
    protected final int requirement;

    public AbstractStatAchievement(ResourceLocation id, boolean shadow, int requirement) {
        super(id, shadow);
        this.requirement = requirement;
    }

    public boolean completed(Player player) {
        return player.level().isClientSide ? completedClient(player) : player instanceof ServerPlayer sp && sp.getStats().getValue(getCollection()) >= requirement;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean completedClient(Player player) {
        return player instanceof LocalPlayer && ((LocalPlayer) player).getStats().getValue(getCollection()) >= requirement;
    }

    public abstract Stat<?> getCollection();
}
