package uk.joshiejack.harvestfestival.world.achievements;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.function.Function;

public abstract class Achievement {
    public static final Codec<Achievement> CODEC = AchievementManager.ACHIEVEMENT.byNameCodec().dispatchStable(Achievement::codec, Function.identity());
    public static Map<ResourceLocation, Achievement> ACHIEVEMENTS = new Object2ObjectOpenHashMap<>();

    public abstract Codec<? extends Achievement> codec();
    private final ResourceLocation id;
    private final boolean shadow;
    private final Component component;

    public Achievement(ResourceLocation id, boolean shadow) {
        this.id = id;
        this.shadow = shadow;
        this.component = Component.translatable("achievement." + id.getNamespace() + "." + id.getPath());
    }

    public ResourceLocation id() {
        return id;
    }

    public boolean hasShadow() {
        return shadow;
    }

    public abstract boolean completed(Player player);

    public Component component() {
        return component;
    }
}