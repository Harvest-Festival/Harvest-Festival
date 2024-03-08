package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.achievements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HFAchievements implements DataProvider {
    private final PackOutput.PathProvider achievementsPath;
    private final List<Achievement> achievements = new ArrayList<>();

    public HFAchievements(PackOutput output) {
        this.achievementsPath = output.createPathProvider(PackOutput.Target.DATA_PACK, HarvestFestival.ACHIEVEMENTS_FOLDER);
    }

    public @NotNull String getName() {
        return "achievements";
    }

    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildAchievements(this.achievements);
        this.achievements.forEach((achievement) -> list.add(DataProvider.saveStable(output, Achievement.CODEC, achievement, this.achievementsPath.json(achievement.id()))));

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected void buildAchievements(List<Achievement> achievements) {
        achievements.add(new CustomStatAchievement(new ResourceLocation(HarvestFestival.MODID, "catch_100_fish"), Stats.FISH_CAUGHT, 100));
        //Complete collections
        achievements.add(new CompleteCollectionAchievement(new ResourceLocation(HarvestFestival.MODID, "complete_shipping_collection"), new ResourceLocation(HarvestFestival.MODID, "shipping")));
achievements.add(new CompleteCollectionAchievement(new ResourceLocation(HarvestFestival.MODID, "complete_fishing_collection"), new ResourceLocation(HarvestFestival.MODID, "fishing")));
        achievements.add(new CompleteCollectionAchievement(new ResourceLocation(HarvestFestival.MODID, "complete_cooking_collection"), new ResourceLocation(HarvestFestival.MODID, "cooking")));
        achievements.add(new CompleteCollectionAchievement(new ResourceLocation(HarvestFestival.MODID, "complete_mining_collection"), new ResourceLocation(HarvestFestival.MODID, "mining")));
        achievements.add(new EntityKilledStatAchievement(new ResourceLocation(HarvestFestival.MODID, "kill_10_slimes"), EntityType.SLIME, 10));
        achievements.add(new ItemShippedStatAchievement(new ResourceLocation(HarvestFestival.MODID, "ship_1000_crops"), Tags.Items.CROPS, 1000));
    }
}
