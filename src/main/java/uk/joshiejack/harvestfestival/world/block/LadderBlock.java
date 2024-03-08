package uk.joshiejack.harvestfestival.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.TeleportType;
import uk.joshiejack.penguinlib.world.block.RotatableBlock;

@SuppressWarnings("deprecation")
public class LadderBlock extends RotatableBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<LadderBlock> CODEC = simpleCodec(LadderBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape LADDER_EAST = Shapes.create(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
    public static final VoxelShape LADDER_WEST = Shapes.create(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final VoxelShape LADDER_SOUTH = Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
    public static final VoxelShape LADDER_NORTH = Shapes.create(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);

    public LadderBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }
    @Override
    public @NotNull MapCodec<LadderBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return super.getStateForPlacement(pContext)
                .setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState otherState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos otherPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, otherState, level, pos, otherPos);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> LADDER_NORTH;
            case SOUTH -> LADDER_SOUTH;
            case WEST -> LADDER_WEST;
            default -> LADDER_EAST;
        };
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity.level().getGameTime() %10 == 0  &&
                entity instanceof Player player && world instanceof ServerLevel server && server.getChunkSource().getGenerator() instanceof MineChunkGenerator generator) {
            //Update the max floor reached for the relevant team
            int floor = MineHelper.getFloorFromPos(server, generator.settings, pos) + 1;

            MineHelper.updateMaximumFloor(player, floor);
            //Attempt to generate an elevator for this floor
            if (floor % generator.settings.elevatorFrequency() == 1)
                MineHelper.getOrGenerate(server, MineHelper.getMineIDFromPos(generator.settings, pos), floor, generator.settings, TeleportType.ELEVATOR);
        }
    }
}