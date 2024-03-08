package uk.joshiejack.harvestfestival.world.achievements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CustomStatAchievement extends AbstractStatAchievement {
    public static final Codec<CustomStatAchievement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Achievement::id),
            Codec.BOOL.optionalFieldOf("custom_shadow", false).forGetter(Achievement::hasShadow),
            ResourceLocation.CODEC.fieldOf("stat").forGetter(achievement -> achievement.stat),
            Codec.INT.fieldOf("requirement").forGetter(achievement -> achievement.requirement)
    ).apply(instance, CustomStatAchievement::new));

    private final ResourceLocation stat;
    private final int requirement;

    public CustomStatAchievement(ResourceLocation id, ResourceLocation stat, int requirement) {
        this(id, false, stat, requirement);
    }

    public CustomStatAchievement(ResourceLocation id, boolean hasCustomShadow, ResourceLocation stat, int requirement) {
        super(id, hasCustomShadow, requirement);
        this.stat = stat;
        this.requirement = requirement;
    }

    @Override
    public Stat<?> getCollection() {
        return null;
    }

    public boolean completed(Player player) {
        return player.level().isClientSide ? completedClient(player) : player instanceof ServerPlayer sp && sp.getStats().getValue(Stats.CUSTOM, stat) >= requirement;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean completedClient(Player player) {
        return player instanceof LocalPlayer lp && lp.getStats().getValue(Stats.CUSTOM, stat) >= requirement;
    }

    @Override
    public Codec<? extends Achievement> codec() {
        return CODEC;
    }
}
