package uk.joshiejack.harvestfestival.world.level.mine;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.TeleportType;

import java.util.EnumMap;

public class MineData extends SavedData {
    private final EnumMap<TeleportType, Int2ObjectMap<Int2ObjectMap<Pair<BlockPos, Direction>>>> mineIDToFloorToTeleportPosMap = new EnumMap<>(TeleportType.class);
    private final Int2IntMap mineIDToMaxFloorReached = new Int2IntOpenHashMap();

    public static MineData load(CompoundTag tag) {
        MineData data = new MineData();
        CompoundTag teleportData = tag.getCompound("Teleports");
        for (TeleportType type: TeleportType.values()) {
            Int2ObjectMap<Int2ObjectMap<Pair<BlockPos, Direction>>> map = new Int2ObjectOpenHashMap<>();
            readMineDataByID(map, teleportData.getCompound(type.getNBTName()));
            data.mineIDToFloorToTeleportPosMap.put(type, map);
        }

        CompoundTag maxFloorTag = tag.getCompound("MaxFloors");
        for (String mineID : tag.getCompound("MaxFloors").getAllKeys())
            data.mineIDToMaxFloorReached.put(Integer.parseInt(mineID), maxFloorTag.getInt(mineID));
        return data;
    }

    public static MineData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(new SavedData.Factory<>(MineData::new, MineData::load), "mines");
    }

    public boolean hasReachedFloor(int id, int floor) {
        return floor <= mineIDToMaxFloorReached.get(id);
    }

    public int getMaxFloorForID(int mineID) {
        return mineIDToMaxFloorReached.get(mineID);
    }

    public void updateMaxFloor(int mineID, int maxFloor) {
        if (mineIDToMaxFloorReached.get(mineID) < maxFloor) {
            mineIDToMaxFloorReached.put(mineID, maxFloor);
            setDirty();
        }
    }

    public Int2ObjectMap<Pair<BlockPos, Direction>> getTeleportData(TeleportType type, int id) {
        return mineIDToFloorToTeleportPosMap.computeIfAbsent(type, k-> new Int2ObjectOpenHashMap<>()).computeIfAbsent(id, k -> new Int2ObjectOpenHashMap<>());
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        //Write the mineID_floor_pos map to the itemTag
        CompoundTag teleportTag = new CompoundTag();
        mineIDToFloorToTeleportPosMap.forEach((type, map) -> teleportTag.put(type.getNBTName(), writeMineDataByID(map)));
        tag.put("Teleports", teleportTag);
        CompoundTag maxFloorTag = new CompoundTag();
        mineIDToMaxFloorReached.forEach((mineID, floor) -> maxFloorTag.putInt(String.valueOf(mineID), floor));
        tag.put("MaxFloors", maxFloorTag);
        return tag;
    }

    /**
     * Writes the mine data to the itemTag
     **/
    private static CompoundTag writeMineDataByID(Int2ObjectMap<Int2ObjectMap<Pair<BlockPos, Direction>>> map) {
        CompoundTag tag = new CompoundTag();
        map.forEach((mineID, floors) -> {
            CompoundTag floorTag = new CompoundTag();
            floors.forEach((floor, data) ->
                    floorTag.putIntArray(String.valueOf(floor), new int[]{data.getLeft().getX(), data.getLeft().getY(), data.getLeft().getZ(), data.getRight().get3DDataValue()})
            );
            tag.put(String.valueOf(mineID), floorTag);
        });
        return tag;
    }

    /**
     * Reads the mine data from the itemTag
     **/
    private static void readMineDataByID(Int2ObjectMap<Int2ObjectMap<Pair<BlockPos, Direction>>> map, CompoundTag tag) {
        tag.getAllKeys().forEach(mineID -> {
            CompoundTag floors = tag.getCompound(mineID);
            Int2ObjectMap<Pair<BlockPos, Direction>> floorMap = new Int2ObjectOpenHashMap<>();
            floors.getAllKeys().forEach(floor -> {
                int[] data = floors.getIntArray(floor);
                floorMap.put(Integer.parseInt(floor), Pair.of(new BlockPos(data[0], data[1], data[2]), Direction.from3DDataValue(data[3])));
            });
            map.put(Integer.parseInt(mineID), floorMap);
        });
    }
}
