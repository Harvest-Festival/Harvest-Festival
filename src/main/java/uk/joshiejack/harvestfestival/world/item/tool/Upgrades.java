package uk.joshiejack.harvestfestival.world.item.tool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.penguinlib.event.DatabaseLoadedEvent;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class Upgrades {
    private static final Object2ObjectMap<Item, Upgrade> UPGRADES = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void onDatabaseLoad(DatabaseLoadedEvent event) {
        UPGRADES.clear();
        event.table("tool_upgrades").rows().forEach(row -> {
            Item item = row.item();
            if (item != Items.AIR) {
                UPGRADES.put(item, new Upgrade(item, row.item("upgrade"), row.getAsInt("experience")));
            }
        });
    }

    public static Upgrade getUpgrade(ItemStack stack) {
        return UPGRADES.containsKey(stack.getItem()) ? UPGRADES.get(stack.getItem()) : stack.getItemHolder().getData(HFData.UPGRADES);
    }

    public record Upgrade(Item item, Item upgrade, int experienceRequirement) {
        public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(Upgrade::item),
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("upgrade").forGetter(Upgrade::upgrade),
                Codec.INT.fieldOf("experience").forGetter(Upgrade::experienceRequirement)
        ).apply(instance, Upgrade::new));
    }
}
