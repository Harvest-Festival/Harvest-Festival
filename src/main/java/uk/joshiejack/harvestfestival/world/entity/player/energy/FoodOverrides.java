package uk.joshiejack.harvestfestival.world.entity.player.energy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.event.EatFoodEvent;
import uk.joshiejack.harvestfestival.network.connection.SyncObtainedItems;
import uk.joshiejack.harvestfestival.world.collections.HFPlayerData;
import uk.joshiejack.penguinlib.event.DatabaseLoadedEvent;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class FoodOverrides {
    private static final Object2ObjectMap<Item, FoodStats> REGISTRY = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFoodConsumed(EatFoodEvent event) {
        FoodStats stats = REGISTRY.containsKey(event.getStack().getItem()) ? REGISTRY.get(event.getStack().getItem()) :
                event.getStack().getItemHolder().getData(HFData.NUTRITION_OVERRIDES);
        if (stats != null) {
            if (stats.nutrition >= 0)
                event.setNewNutrition(stats.nutrition);
            if (stats.saturation >= 0)
                event.setNewSaturation(stats.saturation);
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            HFPlayerData obtained = player.getData(HFData.PLAYER_DATA);
            PenguinNetwork.sendToClient(player, new SyncObtainedItems(obtained.getAll())); //Sync obtained items
        }
    }

    @SubscribeEvent
    public static void onDatabaseLoad(DatabaseLoadedEvent event) {
        REGISTRY.clear();
        event.table("food_overrides").rows()
                .forEach(row -> {
                    Item item = row.item();
                    if (item != null)
                        REGISTRY.put(item, new FoodStats(row.getAsInt("nutrition"), row.getAsFloat("saturation")));
                });
    }

    public record FoodStats(int nutrition, float saturation) {
        public static final Codec<FoodStats> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("nutrition").forGetter(FoodStats::nutrition),
                Codec.FLOAT.fieldOf("saturation").forGetter(FoodStats::saturation)
        ).apply(instance, FoodStats::new));
    }
}
