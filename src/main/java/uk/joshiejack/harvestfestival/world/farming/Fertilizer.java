package uk.joshiejack.harvestfestival.world.farming;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import uk.joshiejack.harvestfestival.HFRegistries;

import java.util.Comparator;
import java.util.List;

public record Fertilizer(ResourceLocation sprite, float itemModelIndex, int speed, int quality, int retention) {
    public Quality getCropQuality(RandomSource random) {
        var chance = random.nextInt(0, 99);
        List<Quality> qualityList = HFRegistries.QUALITY.stream().filter(Quality::appliesToCrops).sorted(Comparator.comparingDouble(Quality::modifier)).toList();
        var initial = 100 - quality;
        for (Quality quality: qualityList) {
            if (chance > initial)
                return quality;
            initial /= 2;
        }

        return HFFarming.QualityLevels.NORMAL.get();
    }

    public Component getName() {
        return Component.translatable("fertilizer." + sprite.getNamespace() + "." + sprite.getPath().replace("fertilizer/", ""));
    }
}
