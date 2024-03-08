package uk.joshiejack.harvestfestival.world.achievements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemShippedStatAchievement extends Achievement {
    public static final Codec<ItemShippedStatAchievement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Achievement::id),
            Codec.BOOL.optionalFieldOf("custom_shadow", false).forGetter(Achievement::hasShadow),
            TagKey.codec(Registries.ITEM).fieldOf("itemTag").forGetter(achievement -> achievement.tag),
            Codec.INT.fieldOf("requirement").forGetter(achievement -> achievement.requirement)
    ).apply(instance, ItemShippedStatAchievement::new));

    private final TagKey<Item> tag;
    private final int requirement;

    public ItemShippedStatAchievement(ResourceLocation id, TagKey<Item> tag, int requirement) {
        this(id, false, tag, requirement);
    }
    public ItemShippedStatAchievement(ResourceLocation id, boolean shadow, TagKey<Item> tag, int requirement) {
        super(id, shadow);
        this.tag = tag;
        this.requirement = requirement;
    }

    private int getValue(StatsCounter stats) {
        int value = 0;
        for (Holder<Item> item: BuiltInRegistries.ITEM.getTagOrEmpty(tag)) {
            value += stats.getValue(AchievementManager.SHIPPED_ITEMS.get(), item.value());
        }

        return value;
    }

    public boolean completed(Player player) {
        return player.level().isClientSide ? completedClient(player) : player instanceof ServerPlayer sp && getValue(sp.getStats()) >= requirement;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean completedClient(Player player) {
        return player instanceof LocalPlayer lp && getValue(lp.getStats()) >= requirement;
    }

    @Override
    public Codec<? extends Achievement> codec() {
        return CODEC;
    }
}