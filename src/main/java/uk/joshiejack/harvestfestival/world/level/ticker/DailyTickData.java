package uk.joshiejack.harvestfestival.world.level.ticker;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DailyTickData implements INBTSerializable<ListTag> {
    @SuppressWarnings("rawtypes")
    private final Long2ObjectMap<DailyTicker> entries = new Long2ObjectOpenHashMap<>();
    private final Set<Long> toRemove = new HashSet<>();

    public static DailyTickData getDataForChunk(LevelChunk chunk) {
        if (!chunk.hasData(HFData.DAILY_TICKER_DATA)) {
            chunk.setData(HFData.DAILY_TICKER_DATA, new DailyTickData());
        }

        return chunk.getData(HFData.DAILY_TICKER_DATA);
    }

    public List<Pair<Fertilizer, BlockPos>> getFertilizerData() {
        List<Pair<Fertilizer, BlockPos>> list = Lists.newArrayList();
        entries.forEach((k, v) -> {
            if (v instanceof CanBeFertilized) {
                list.add(Pair.of(((CanBeFertilized) v).fertilizer(), BlockPos.of(k)));
            }
        });

        return list;
    }

    public void replaceEntry(ServerLevel world, LevelChunk chunk, BlockPos pos, BlockState state, DailyTicker<?> ticker) {
        DailyTicker<?> oldTicker = entries.get(pos.asLong());
        if (oldTicker != null) {
            CustomPacketPayload packet = oldTicker.onRemoved(pos);
            if (packet != null)
                world.getPlayers(p -> true).forEach(p -> p.connection.send(packet));
        }

        DailyTicker<?> newTicker = ticker.createEntry(world, chunk, pos, state);
        entries.put(pos.asLong(), ticker);
        world.getServer().submit(() -> newTicker.onAdded(world, pos));
        chunk.setUnsaved(true);
    }

    public void addEntry(ServerLevel world, LevelChunk chunk, BlockPos pos, BlockState state, DailyTicker<?> ticker) {
        DailyTicker<?> newTicker = ticker.createEntry(world, chunk, pos, state);
        entries.put(pos.asLong(), newTicker);
        world.getServer().submit(() -> newTicker.onAdded(world, pos));
        chunk.setUnsaved(true); //Perform world creation data on the main thread
    }

    public void removeEntry(ServerLevel level, LevelChunk chunk, BlockPos pos) {
        DailyTicker<?> ticker = entries.get(pos.asLong());
        if (ticker == null) return;
        CustomPacketPayload packet = ticker.onRemoved(pos);
        if (packet != null) {
            level.getPlayers(p -> true).forEach(p -> p.connection.send(packet));
        }

        toRemove.add(pos.asLong());
        chunk.setUnsaved(true);
    }

    @SuppressWarnings("unchecked")
    public List<Pair<BlockPos, DailyTicker<?>>> getSorted() {
        //Remove everything before we tick it?
        remove(); //Are we sure??? //TODO: Check this
        List<Pair<BlockPos, DailyTicker<?>>> list = Lists.newArrayList();
        entries.forEach((k, v) -> list.add(Pair.of(BlockPos.of(k), v)));
        list.sort(Comparator.comparingInt(pair -> pair.getValue().getPriority().ordinal()));
        return list;
    }

    public void remove() {
        toRemove.forEach(e -> entries.remove((long) e));
        toRemove.clear();
    }

    public DailyTicker<?> get(BlockPos pos) {
        return entries.get(pos.asLong());
    }

    @SuppressWarnings("all")
    @Override
    public ListTag serializeNBT() {
        ListTag list = new ListTag();
        remove(); //Clear everything up here
        //Serialize the data, in a more efficient manner
        for (ResourceLocation key : HFRegistries.TICKER_TYPE.keySet()) {
            CompoundTag dataTag = new CompoundTag();
            ListTag entriesList = new ListTag();
            entries.long2ObjectEntrySet().stream()
                    .filter(e -> HFRegistries.TICKER_TYPE.getKey(e.getValue().codec()).equals(key))
                    .forEach(e -> {
                        CompoundTag tag = new CompoundTag();
                        tag.putLong("Pos", e.getLongKey());
                        e.getValue().codec()
                                .encodeStart(NbtOps.INSTANCE, e.getValue())
                                .result()
                                .ifPresent(inst -> {
                                    if (((Tag) inst).sizeInBytes() > 48) {
                                        tag.put("Data", (Tag) inst);
                                    }
                                });
                        entriesList.add(tag);
                    });
            if (entriesList.isEmpty()) continue;
            dataTag.putString("Type", key.toString());
            dataTag.put("Entries", entriesList);
            list.add(dataTag);
        }

        return list;
    }

    private static final CompoundTag EMPTY = new CompoundTag();

    @Override
    public void deserializeNBT(ListTag nbt) {
        for (int i = 0; i < nbt.size(); i++) {
            CompoundTag tag = nbt.getCompound(i);
            ResourceLocation key = new ResourceLocation(tag.getString("Type"));
            Codec<? extends DailyTicker<?>> codec = HFRegistries.TICKER_TYPE.get(key);
            assert codec != null;
            ListTag entriesList = tag.getList("Entries", 10);
            for (int j = 0; j < entriesList.size(); j++) {
                CompoundTag entry = entriesList.getCompound(j);
                CompoundTag tag1 = entry.contains("Data") ? entry.getCompound("Data") : EMPTY;
                DataResult<? extends DailyTicker<?>> result = codec.parse(NbtOps.INSTANCE, tag1);
                if (result != null && result.result().isPresent())
                    entries.put(entry.getLong("Pos"), result.result().get());
            }
        }
    }
}