package uk.joshiejack.harvestfestival.world.collections;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.network.SyncNewObtainedItem;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import java.util.HashSet;
import java.util.Set;

/* Per player data on how many of an item they have obtained
 *
 * Items should only count when obtained through relevant methods
 * For example, fish should only be added when they are caught from fishing
 * Cooking foods should only be obtained when they are cooked or crafted
 * Shipping should only be obtained when you have shipped the items
 * Mining should only count when you have mined the item or looted it */
public class HFPlayerData implements INBTSerializable<CompoundTag> {
    private final Set<Item> obtained = new HashSet<>();
    private int bagSize;

    private int getOrSetBagSize() {
        if (bagSize == 0)
            bagSize = HFConfig.defaultBagSize.get();
        return bagSize;
    }

    public boolean isUnlocked(int slot) {
        int bagSize = getOrSetBagSize();
        if (slot < 9 || slot >= 36) return true;
        if (slot >= 27) return bagSize >= 2;
        if (slot >= 18) return bagSize >= 3;
        return bagSize >= 4;
    }

    public boolean hasObtained(Item item) {
        return obtained.contains(item);
    }

    public void obtain(Player player, Item item) {
        if (player instanceof ServerPlayer serverPlayer && !hasObtained(item))
            PenguinNetwork.sendToClient(serverPlayer, new SyncNewObtainedItem(item));
        obtained.add(item);
    }

    public Set<Item> getAll() {
        return obtained;
    }

    @Override
    public @NotNull CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        int bagSize = getOrSetBagSize();
        tag.putInt("BagSize", bagSize);
        ListTag list = new ListTag();
        for (Item item : this.obtained) {
            if (item != null)
                list.add(StringTag.valueOf(BuiltInRegistries.ITEM.getKey(item).toString()));
        }

        tag.put("Obtained", list);
        return tag;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundTag tag) {
        bagSize = tag.getInt("BagSize");
        ListTag list = tag.getList("Obtained", 8);
        obtained.clear();
        for (int i = 0; i < list.size(); i++) {
            obtained.add(BuiltInRegistries.ITEM.get(new ResourceLocation(list.getString(i))));
        }
    }
}
