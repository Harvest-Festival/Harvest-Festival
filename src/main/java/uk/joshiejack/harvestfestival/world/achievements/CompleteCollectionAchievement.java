package uk.joshiejack.harvestfestival.world.achievements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.collections.Collections;
import uk.joshiejack.harvestfestival.world.collections.HFPlayerData;

public class CompleteCollectionAchievement extends Achievement {
    public static final Codec<CompleteCollectionAchievement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Achievement::id),
            Codec.BOOL.optionalFieldOf("custom_shadow", false).forGetter(Achievement::hasShadow),
            ResourceLocation.CODEC.fieldOf("collection").forGetter(achievement -> achievement.collection)
    ).apply(instance, CompleteCollectionAchievement::new));

    private final ResourceLocation collection;

    public CompleteCollectionAchievement(ResourceLocation id, ResourceLocation collection) {
        this(id, false, collection);
    }

    public CompleteCollectionAchievement(ResourceLocation id, boolean hasCustomShadow, ResourceLocation collection) {
        super(id, hasCustomShadow);
        this.collection = collection;
    }

    public boolean completed(Player player) {
        HFPlayerData data = player.getData(HFData.PLAYER_DATA);
        return Collections.COLLECTIONS.get(collection).getItems().stream().allMatch(item -> data.hasObtained(item.getItem()));
    }

    @Override
    public Codec<? extends Achievement> codec() {
        return CODEC;
    }
}
