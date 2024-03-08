package uk.joshiejack.harvestfestival.world.collections;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.event.DatabaseLoadedEvent;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.SpriteIcon;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class Collections {
    public static final Map<ResourceLocation, Collection> COLLECTIONS = new HashMap<>();
    public static final Cache<Collection, NonNullList<ItemStack>> ITEMS = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    //Achievement Collection is a special exception as it doesn't use items
    public static final TagKey<Item> COOKING_COLLECTION = ItemTags.create(new ResourceLocation(HarvestFestival.MODID, "cooking_collection"));
    public static final TagKey<Item> MINING_COLLECTION = ItemTags.create(new ResourceLocation(HarvestFestival.MODID, "mineral_collection"));
    public static final TagKey<Item> FISHING_COLLECTION = ItemTags.create(new ResourceLocation(HarvestFestival.MODID, "fishing_collection"));
    public static final TagKey<Item> SHIPPING_COLLECTION = ItemTags.create(new ResourceLocation(HarvestFestival.MODID, "farming_collection"));

    @SubscribeEvent
    public static void onDatabaseLoaded(DatabaseLoadedEvent event) {
        COLLECTIONS.clear();
        event.table("collections").rows().forEach(row -> {
            ResourceLocation id = row.getRL("id");
            int order = row.getAsInt("order");
            TagKey<Item> tag = row.isEmpty("tag") ? null : ItemTags.create(row.getRL("tag"));
            Icon icon = new SpriteIcon(row.getRL("sprite"), row.getRL("shadow sprite"));
            COLLECTIONS.put(id, new Collection(id, order, tag, icon));
        });
    }

    public record Collection(ResourceLocation id, int order, @Nullable TagKey<Item> tag, Icon icon) {
        public static final Codec<Collection> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(Collection::id),
                Codec.INT.fieldOf("order").forGetter(Collection::order),
                TagKey.codec(Registries.ITEM).fieldOf("itemTag").forGetter(Collection::tag),
                Icon.CODEC.fieldOf("icon").forGetter(Collection::icon)
        ).apply(instance, Collection::new));


        public NonNullList<ItemStack> getItems() {
            try {
                return ITEMS.get(this, () -> {
                    NonNullList<ItemStack> list = NonNullList.create();
                    if (tag == null) return list;
                    BuiltInRegistries.ITEM.forEach(item -> {
                        ItemStack stack = new ItemStack(item);
                        if (stack.is(tag)) list.add(stack);
                    });

                    return list;
                });
            } catch (Exception e) {
                return NonNullList.create();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Collection that = (Collection) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
