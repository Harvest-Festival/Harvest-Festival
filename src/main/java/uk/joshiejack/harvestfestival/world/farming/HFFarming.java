package uk.joshiejack.harvestfestival.world.farming;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;

public class HFFarming {
    public static class Fertilizers {
        public static final DeferredRegister<Fertilizer> FERTILIZERS = DeferredRegister.create(HFRegistries.FERTILIZER, HarvestFestival.MODID);
        public static final DeferredHolder<Fertilizer, Fertilizer> AQUA_GUARD = FERTILIZERS.register("aqua_guard", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/aqua_guard"), 0.8F,0, 0, 66));
        public static final DeferredHolder<Fertilizer, Fertilizer> RETENTION = FERTILIZERS.register("retention", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/retention"),0.7F, 0, 0, 33));
        public static final DeferredHolder<Fertilizer, Fertilizer> TURBO = FERTILIZERS.register("turbo", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/turbo"), 0.6F,25, 0, 0));
        public static final DeferredHolder<Fertilizer, Fertilizer> SPEED = FERTILIZERS.register("speed", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/speed"), 0.5F, 10, 0, 0));
        public static final DeferredHolder<Fertilizer, Fertilizer> QUALITY = FERTILIZERS.register("quality", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/quality"), 0.4F, 0, 40, 0));
        public static final DeferredHolder<Fertilizer, Fertilizer> BASIC = FERTILIZERS.register("basic", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/basic"), 0.3F,0, 30, 0));
        public static final DeferredHolder<Fertilizer, Fertilizer> BONE_MEAL = FERTILIZERS.register("bone_meal", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/bone_meal"),0.2F, 1, 5, 0));
        public static final DeferredHolder<Fertilizer, Fertilizer> NONE = FERTILIZERS.register("none", () -> new Fertilizer(HarvestFestival.prefix("fertilizer/none"), 0.1F,0, 0, 0));
    }

    public static class QualityLevels {
        public static final DeferredRegister<Quality> QUALITY_LEVELS = DeferredRegister.create(HFRegistries.QUALITY, HarvestFestival.MODID);
        public static final DeferredHolder<Quality, Quality> NORMAL = QUALITY_LEVELS.register("normal", () -> new Quality(1D, true));
        public static final DeferredHolder<Quality, Quality> ROTTEN = QUALITY_LEVELS.register("rotten", () -> new Quality(0.5D, false));
        public static final DeferredHolder<Quality, Quality> SILVER = QUALITY_LEVELS.register("silver", () -> new Quality(1.75D, true));
        public static final DeferredHolder<Quality, Quality> GOLD = QUALITY_LEVELS.register("gold", () -> new Quality(2D, true));
        public static final DeferredHolder<Quality, Quality> MYSTRIL = QUALITY_LEVELS.register("mystril", () -> new Quality(1.5D, true));
    }

    public static void register(IEventBus eventBus) {
        Fertilizers.FERTILIZERS.register(eventBus);
        QualityLevels.QUALITY_LEVELS.register(eventBus);
    }
}
