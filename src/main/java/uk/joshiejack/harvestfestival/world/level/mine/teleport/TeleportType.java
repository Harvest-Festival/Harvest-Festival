package uk.joshiejack.harvestfestival.world.level.mine.teleport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.world.block.ElevatorBlock;
import uk.joshiejack.harvestfestival.world.block.LadderBlock;
import uk.joshiejack.harvestfestival.world.block.PortalBlock;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum TeleportType {
    PORTAL {
        @Override
        public Pair<BlockPos, Direction> spawn(ServerLevel level, MineSettings settings, ChunkPos originChunk, MineTier tier, int floor) {
            int rows = settings.blocksPerMine();
            int cols = settings.blocksPerMine();
            // Create a single BlockPos instance
            int relativeFloor = ((floor - 1) % settings.floorsPerMine(level.getHeight())) + 1;
            BlockState target = tier.getWall(relativeFloor); //(1-40 = 40), (41-80 = 40), (81-120 = 40) etc
            BlockPos origin = new BlockPos(originChunk.getMinBlockX() + 1, -(relativeFloor * settings.floorHeight()) + 2, originChunk.getMinBlockZ() + 1);
            //Look 1 blockTag inside of the mine origin border to avoid looking at the border blocks
            // Check the rows
            //Build a list of all the rows/col chunk positions then shuffle them
            List<ChunkPos> rowPositions = new ArrayList<>();
            for (int x = 0; x < rows - 3; x++) {
                for (int z = 0; z < cols; z++) {
                    rowPositions.add(new ChunkPos(x, z));
                }
            }

            Collections.shuffle(rowPositions);
            for (ChunkPos pos : rowPositions) {
                BlockPos blockPos = origin.offset(pos.x, 0, pos.z);
                if (level.getBlockState(blockPos) == target && (level.getBlockState(offset(blockPos, 0, 1)) == target && (level.getBlockState(offset(blockPos, 0, 2)) == target))) {
                    if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 1, 0), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 1, 1), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 1, 2), target)))) {
                        if (level.getBlockState(offset(blockPos, -1, 0)) == target && (level.getBlockState(offset(blockPos, -1, 1)) == target && (level.getBlockState(offset(blockPos, -1, 2)) == target))) {
                            return placePortal(level, settings, blockPos, tier, floor, Direction.SOUTH);
                        }
                    }
                    if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, -1, 0), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, -1, 1), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, -1, 2), target)))) {
                        if (level.getBlockState(offset(blockPos, 1, 0)) == target && (level.getBlockState(offset(blockPos, 1, 1)) == target && (level.getBlockState(offset(blockPos, 1, 2)) == target))) {
                            return placePortal(level, settings, blockPos, tier, floor, Direction.NORTH);
                        }
                    }
                }
            }

            List<ChunkPos> colPositions = new ArrayList<>();
            for (int x = 0; x < rows - 3; x++) {
                for (int z = 0; z < cols; z++) {
                    colPositions.add(new ChunkPos(x, z));
                }
            }

            Collections.shuffle(colPositions);

            // Check the columns
            for (ChunkPos pos : colPositions) {
                BlockPos blockPos = origin.offset(pos.x, 0, pos.z);
                if (level.getBlockState(blockPos) == target && (level.getBlockState(offset(blockPos, 1, 0)) == target && (level.getBlockState(offset(blockPos, 2, 0)) == target))) {
                    if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 0, 1), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 1, 1), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 2, 1), target)))) {
                        if (level.getBlockState(offset(blockPos, 0, -1)) == target && (level.getBlockState(offset(blockPos, 1, -1)) == target && (level.getBlockState(offset(blockPos, 2, -1)) == target))) {
                            return placePortal(level, settings, blockPos, tier, floor, Direction.WEST);
                        }
                    }

                    if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 0, -1), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 1, -1), target) && (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, offset(blockPos, 2, -1), target)))) {
                        if (level.getBlockState(offset(blockPos, 0, 1)) == target && (level.getBlockState(offset(blockPos, 1, 1)) == target && (level.getBlockState(offset(blockPos, 2, 1)) == target))) {
                            return placePortal(level, settings, blockPos, tier, floor, Direction.EAST);
                        }
                    }
                }
            }

            return Pair.of(BlockPos.ZERO, Direction.NORTH);
        }
    }, ELEVATOR {
        @Override
        public Pair<BlockPos, Direction> spawn(ServerLevel level, MineSettings settings, ChunkPos originChunk, MineTier tier, int floor) {
            int rows = settings.blocksPerMine();
            int cols = settings.blocksPerMine();
            // Create a single BlockPos instance
            int relativeFloor = ((floor - 1) % settings.floorsPerMine(level.getHeight())) + 1;
            BlockPos origin = new BlockPos(originChunk.getMinBlockX(), -(relativeFloor * settings.floorHeight()) + 2, originChunk.getMinBlockZ());
            BlockState wall = tier.getWall(relativeFloor); //(1-40 = 40), (41-80 = 40), (81-120 = 40) etc
            // Gather all the positions
            List<BlockPos> positions = new ArrayList<>();
            for (int x = 1; x < rows - 1; x++)
                for (int z = 1; z < cols - 1; z++)
                    positions.add(origin.offset(x, 0, z));

            //Find the ladder blockTag
            final BlockPos.MutableBlockPos ladder = new BlockPos.MutableBlockPos();
            positions.stream()
                    .filter(pos -> level.getBlockState(pos).getBlock() instanceof LadderBlock)
                    .findFirst()
                    .ifPresent(ladder::set);

            //If we found the ladder blockTag, then sort by distance to the ladder blockTag
            if (!ladder.equals(BlockPos.ZERO))
                positions.sort(Comparator.comparingDouble(o -> o.distSqr(ladder)));
            else Collections.shuffle(positions);

            for (BlockPos blockPos : positions) {
                //West = towards minimumFloor x
                //East = towards positive x
                //North = towards minimumFloor z
                //South = towards positive z
                //If the blockTag is a wall blockTag and the blockTag above is a wall blockTag and the blocks in front are air blocks, then spawn the elevator
                if (level.getBlockState(blockPos) == wall && level.getBlockState(blockPos.offset(0, 1, 0)) == wall) {
                    //Ensure the blocks to the east and west are wall blocks
                    if (level.getBlockState(blockPos.offset(-1, 0, 0)) == wall && level.getBlockState(blockPos.offset(1, 0, 0)) == wall) {
                        if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(0, 0, -1), wall) && TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(1, 0, -1), wall)) {
                            return placeElevator(level, blockPos, Direction.NORTH);
                        }

                        if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(0, 0, 1), wall) && TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(1, 0, 1), wall)) {
                            return placeElevator(level, blockPos, Direction.SOUTH);
                        }
                    }

                    //Ensure the blocks to the north and south are wall blocks
                    if (level.getBlockState(blockPos.offset(0, 0, -1)) == wall && level.getBlockState(blockPos.offset(0, 0, 1)) == wall) {
                        if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(-1, 0, 0), wall) && TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(-1, 0, 1), wall)) {
                            return placeElevator(level, blockPos, Direction.WEST);
                        }

                        if (TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(1, 0, 0), wall) && TeleportType.isNotWallElevatorOrPortalOrLadderBlock(level, blockPos.offset(1, 0, 1), wall)) {
                            return placeElevator(level, blockPos, Direction.EAST);
                        }
                    }
                }
            }

            return EMPTY;
        }
    };

    private static boolean isNotWallElevatorOrPortalOrLadderBlock(ServerLevel level, BlockPos pos, BlockState target) {
        BlockState state = level.getBlockState(pos); //Allow placement if it isn't the target blockTag or an elevator blockTag or a portal blockTag
        return level.getBlockState(pos) != target && !(state.getBlock() instanceof ElevatorBlock) && !(state.getBlock() instanceof PortalBlock) && !(state.getBlock() instanceof LadderBlock);
    }

    public static final Pair<BlockPos, Direction> EMPTY = Pair.of(BlockPos.ZERO, Direction.NORTH);

    @SuppressWarnings("deprecation")
    public String getNBTName() {
        return WordUtils.capitalize(name().toLowerCase()) + "s";
    }

    private static BlockPos offset(BlockPos original, int x, int z) {
        return original.offset(x, 0, z);
    }

    public static Pair<BlockPos, Direction> placeElevator(ServerLevel level, BlockPos blockPos, Direction direction) {
        level.setBlock(blockPos, HFBlocks.ELEVATOR.get().defaultBlockState().setValue(ElevatorBlock.FACING, direction.getOpposite()).setValue(ElevatorBlock.HALF, DoubleBlockHalf.LOWER), 2);
        level.setBlock(blockPos.above(), HFBlocks.ELEVATOR.get().defaultBlockState().setValue(ElevatorBlock.FACING, direction.getOpposite()).setValue(ElevatorBlock.HALF, DoubleBlockHalf.UPPER), 2);
        level.setBlock(blockPos.relative(direction, 1), Blocks.AIR.defaultBlockState(), 2);
        level.setBlock(blockPos.relative(direction, 1).above(), Blocks.AIR.defaultBlockState(), 2);
        return Pair.of(blockPos, direction);
    }


    public static Pair<BlockPos, Direction> placePortal(ServerLevel level, MineSettings settings, BlockPos pos, MineTier tier, int floor, Direction direction) {
        BlockState portal = tier.getPortal(floor % settings.floorsPerMine(level.getHeight()));
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            for (int i = 0; i <= 2; i++) {
                for (int y = 0; y <= 1; y++) {
                    level.setBlock(pos.relative(direction, i).above(y), portal, 2);
                    //Now that we know this, set the blocks that are in front; to the air blockTag
                    for (int j = 1; j <= 2; j++) {
                        level.setBlock(pos.relative(direction, i).above(y).relative(direction.getCounterClockWise(), j), Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }

            BlockPos lowerCentre = pos.relative(direction).above();
            return Pair.of(lowerCentre, direction.getOpposite());
        } else {
            for (int i = 0; i <= 2; i++) {
                for (int y = 0; y <= 1; y++) {
                    level.setBlock(pos.relative(direction, i).above(y), portal, 2);
                    //Now that we know this, set the blocks that are in front; to the air blockTag
                    for (int j = 1; j <= 2; j++) {
                        level.setBlock(pos.relative(direction, i).above(y).relative(direction.getClockWise(), j), Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }

            BlockPos lowerCentre = pos.relative(direction).above();
            return Pair.of(lowerCentre, direction.getOpposite());
        }
    }

    public abstract Pair<BlockPos, Direction> spawn(ServerLevel level, MineSettings settings, ChunkPos originChunk, MineTier tier, int floor);
}
