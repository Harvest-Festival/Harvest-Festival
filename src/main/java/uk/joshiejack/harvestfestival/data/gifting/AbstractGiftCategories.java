package uk.joshiejack.harvestfestival.data.gifting;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.settlements.Settlements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractGiftCategories extends DataMapProvider {
    private static final List<String> CATEGORIES =
            List.of("art", "building", "cooking", "egg", "fish", "flower", "fruit", "gem", "herb", "junk", "knowledge",
                    "machine", "magic", "meat", "milk", "mineral", "money", "monster", "mushroom", "plant", "vegetable", "wool");

    private final String modid;

    public AbstractGiftCategories(String modid, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
        this.modid = modid;
    }

    @Override
    public @NotNull String getName() {
        return "Gift Category %s Data Map".formatted(modid);
    }

    private void add(Builder<String, Item> builder, TagKey<Item> tag, String value) {
        builder.add(tag, value, false);
    }

    private void add(Builder<String, Item> builder, ItemLike item, String value) {
        builder.add(item.asItem().builtInRegistryHolder(), value, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void gather() {
        final var shippingData = builder(Settlements.Data.GIFT_CATEGORIES);
        for (String category : CATEGORIES) {
            try {
                Method method = this.getClass().getDeclaredMethod(category);
                List<Object> objects = (List<Object>) method.invoke(this);
                for (Object object : objects) {
                    if (object instanceof TagKey) {
                        add(shippingData, (TagKey<Item>) object, category);
                    } else if (object instanceof ItemLike) {
                        add(shippingData, (ItemLike) object, category);
                    }
                }
            } catch (NoSuchMethodException ignored) {
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
