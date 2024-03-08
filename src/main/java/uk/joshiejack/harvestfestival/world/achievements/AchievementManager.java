package uk.joshiejack.harvestfestival.world.achievements;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.stats.StatType;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.PenguinLib;

import java.util.Map;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class AchievementManager extends SimpleJsonResourceReloadListener {
    public static final ResourceKey<Registry<Codec<? extends Achievement>>> ACHIEVEMENT_KEY = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "achievements"));
    public static final DeferredRegister<Codec<? extends Achievement>> ACHIEVEMENT_TYPES = DeferredRegister.create(ACHIEVEMENT_KEY, HarvestFestival.MODID);
    public static final DeferredHolder<Codec<? extends Achievement>, Codec<CustomStatAchievement>> CUSTOM_STAT = ACHIEVEMENT_TYPES.register("custom_stat", () -> CustomStatAchievement.CODEC);
    public static final DeferredHolder<Codec<? extends Achievement>, Codec<EntityKilledStatAchievement>> ENTITIES_KILLED = ACHIEVEMENT_TYPES.register("entities_killed", () -> EntityKilledStatAchievement.CODEC);
    public static final DeferredHolder<Codec<? extends Achievement>, Codec<ItemShippedStatAchievement>> ITEMS_SHIPPED = ACHIEVEMENT_TYPES.register("items_shipped", () -> ItemShippedStatAchievement.CODEC);
    public static final DeferredHolder<Codec<? extends Achievement>, Codec<CompleteCollectionAchievement>> COMPLETE_COLLECTION = ACHIEVEMENT_TYPES.register("complete_collection", () -> CompleteCollectionAchievement.CODEC);
    public static final Registry<Codec<? extends Achievement>> ACHIEVEMENT = ACHIEVEMENT_TYPES.makeRegistry(b -> b.sync(true));

    //Item Shipped Stat
    public static final DeferredRegister<StatType<?>> STAT_TYPES = DeferredRegister.create(Registries.STAT_TYPE, HarvestFestival.MODID);
    public static final DeferredHolder<StatType<?>, StatType<Item>> SHIPPED_ITEMS = STAT_TYPES.register("shipped_items", () -> makeRegistryStatType("shipped_items"));

    private static StatType<Item> makeRegistryStatType(String name) {
        return new StatType<>(BuiltInRegistries.ITEM, Component.translatable("stat_type."+ HarvestFestival.MODID +"." + name));
    }

    public static void register(IEventBus eventBus) {
        ACHIEVEMENT_TYPES.register(eventBus);
        STAT_TYPES.register(eventBus);
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // Manager itself
    public AchievementManager() {
        super(GSON, HarvestFestival.ACHIEVEMENTS_FOLDER);
    }

    @SubscribeEvent
    public static void onReload(AddReloadListenerEvent event) {
        event.addListener(new AchievementManager());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, Achievement> builder = ImmutableMap.builder();
        map.forEach((file, element) -> {
            try {
                DataResult<Achievement> achievement = Achievement.CODEC.parse(JsonOps.INSTANCE, element);
                if (achievement != null && achievement.result().isPresent())
                    builder.put(file, achievement.get().orThrow());
            } catch (Exception exception) {
                PenguinLib.LOGGER.error("Parsing error loading achievement {}: {}", file, exception.getMessage());
            }
        });

        Achievement.ACHIEVEMENTS = builder.build();
    }
}