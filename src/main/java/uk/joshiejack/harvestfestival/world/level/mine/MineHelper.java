package uk.joshiejack.harvestfestival.world.level.mine;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.TeleportType;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.penguinlib.util.helper.TagHelper;
import uk.joshiejack.penguinlib.world.team.PenguinTeam;
import uk.joshiejack.penguinlib.world.team.PenguinTeams;

public class MineHelper {
    private static final BlockPos.MutableBlockPos MUTABLE_BLOCK_POS = new BlockPos.MutableBlockPos();

    public static CompoundTag getTeamMineData(Player player) {
        PenguinTeam team = PenguinTeams.getTeamForPlayer(player);
        CompoundTag mineData = TagHelper.getOrCreateTag(team.getData(), "Mines");
        return TagHelper.getOrCreateTag(mineData, player.level().dimension().location().toString());
    }

    public static int getMaximumFloorReached(Player player) {
        return getTeamMineData(player).getInt("MaxFloorReached");
    }

    public static void updateMaximumFloor(Player player, int floor) {
        if (player.level() instanceof ServerLevel level) {
            CompoundTag dimData = getTeamMineData(player);
            int maxFloor = dimData.getInt("MaxFloorReached");
            if (floor > maxFloor) {
                dimData.putInt("MaxFloorReached", floor);
                PenguinTeams.getTeamForPlayer(player).syncSubTag("Mines", level);
                PenguinTeams.get(level).setDirty();
            }
        }
    }

    //Vec3 version
    public static int getFloorFromPos(LevelReader level, MineSettings settings, Vec3 pos) {
        return getFloorFromYZ(level, settings, (int) pos.y, (int) pos.z);
    }

    public static int getFloorFromPos(LevelReader level, MineSettings settings, BlockPos pos) {
        return getFloorFromYZ(level, settings, pos.getY(), pos.getZ());
    }

    public static int getMineIDForTeleport(MineSettings settings, BlockPos pos) {
        return getMineIDFromPos(settings, pos) / 10;
    }

    public static int getMineIDFromPos(MineSettings settings, BlockPos pos) {
        // Calculate the mine id based on pos.getX(), incrementing by 1 for every BLOCKS_PER_MINE units
        return pos.getX() / settings.blocksPerMine() + (pos.getX() < 0 ? -1 : 0);
    }

    public static int getMineIDFromChunk(MineSettings settings, ChunkPos chunkPos) {
        return getMineIDFromPos(settings, MUTABLE_BLOCK_POS.set(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ()));
    }

    private static int getFloorFromYZ(LevelReader level, MineSettings settings, int y, int z) {
        int floor = (y * -1) / settings.floorHeight();
        if (z < 0)
            return -1;
        else
            floor += (getTierFromPos(settings, z) * settings.floorsPerMine(level.getHeight()));
        return floor;
    }

    public static int getTierFromPos(MineSettings settings, BlockPos pos) {
        return getTierFromPos(settings, pos.getZ());
    }

    private static int getTierFromPos(MineSettings settings, int z) {
        if (z < 0)
            return -1;
        return z / settings.blocksPerMine();
    }

    //Get the tier from the chunk pos
    public static int getTierFromChunk(MineSettings settings, ChunkPos chunkPos) {
        return getTierFromPos(settings, chunkPos.getMinBlockZ());
    }

    public static int getRelativeFloor(MineSettings settings, int y) {
        return Mth.ceil((float) (settings.worldHeightStart() - y) / settings.floorHeight());
    }

    private static int getFloorFromTierNumber(MineSettings settings, int height, int tier, int y) {
        return getRelativeFloor(settings, y) + (tier * settings.floorsPerMine(height));
    }

    /* Get the floor from the chunk and y coordinate
    * Note: if z coordinates are minimumFloor you are out of bounds */
    public static int getActualFloorFromRelative(MineSettings settings, int height, ChunkPos chunk, int y) {
        return getFloorFromTierNumber(settings, height, getTierFromChunk(settings, chunk), y);
    }

    public static ChunkPos getChunkTarget(ServerLevel level, MineSettings settings, int mineID, int floorTarget) {
        int originX = mineID * settings.chunksPerMine();
        int originZ = (Mth.floor((float) (floorTarget - 1) / settings.floorsPerMine(level.getHeight())) * settings.chunksPerMine());
        return new ChunkPos(originX, originZ);
    }

    /* Returns the teleport data for the mine floor */
    public static Pair<BlockPos, Direction> getOrGenerate(ServerLevel level, int mineID, int floorTarget, MineSettings settings, TeleportType type) {
        ChunkPos chunkTarget = getChunkTarget(level, settings, mineID, floorTarget);
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(chunkTarget.getMinBlockX() + 1, 0, chunkTarget.getMinBlockZ() + 1);
        Holder<MineTier> tierHolder = settings.getTierByPos(check);
        if (tierHolder == null) return TeleportType.EMPTY;

        MineData data = MineData.get(level.getLevel());
        Int2ObjectMap<Pair<BlockPos, Direction>> teleportData = data.getTeleportData(type, mineID);
        if (type == TeleportType.PORTAL) {
            //Add the bottom and top floor portals
            int upper = getFloorFromPos(level, settings, check.setY(-(settings.floorHeight()) + 2)) + 1;
            int lower = getFloorFromPos(level, settings, check.setY(-(settings.floorsPerMine(level.getHeight()) * settings.floorHeight()) + 2)) + 1;
            teleportData.computeIfAbsent(upper, k -> type.spawn(level, settings, getChunkTarget(level, settings, mineID, upper), tierHolder.value(), upper));
            teleportData.computeIfAbsent(lower, k -> type.spawn(level, settings, getChunkTarget(level, settings, mineID, lower), tierHolder.value(), lower));
        }

        boolean hasPortalAndElevator = floorTarget % settings.floorsPerMine(level.getHeight()) == 1;
        if (hasPortalAndElevator) { //Calculate the portal, or the elevator depending on which type of teleport this is
            if (type == TeleportType.ELEVATOR) data.getTeleportData(TeleportType.PORTAL, mineID).computeIfAbsent(floorTarget, k -> TeleportType.PORTAL.spawn(level, settings, chunkTarget, tierHolder.value(), floorTarget));
            else data.getTeleportData(TeleportType.ELEVATOR, mineID).computeIfAbsent(floorTarget, k -> TeleportType.ELEVATOR.spawn(level, settings, chunkTarget, tierHolder.value(), floorTarget));
        }

        //Force MineData as dirty
        MineData.get(level.getLevel()).setDirty();
        return teleportData.computeIfAbsent(floorTarget, k -> type.spawn(level, settings, chunkTarget, tierHolder.value(), floorTarget));
    }

    public static boolean isMineWorld(ServerLevel level) {
        return level.getChunkSource().getGenerator() instanceof MineChunkGenerator;
    }
}