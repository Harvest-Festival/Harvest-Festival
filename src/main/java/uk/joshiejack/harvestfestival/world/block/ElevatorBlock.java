package uk.joshiejack.harvestfestival.world.block;


import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.network.OpenElevatorScreenPacket;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.penguinlib.world.block.RotatableDoubleBlock;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import javax.annotation.Nonnull;
import java.util.Map;

public class ElevatorBlock extends RotatableDoubleBlock {
    public static final MapCodec<Block> CODEC = simpleCodec(ElevatorBlock::new);
    private static final VoxelShape SHAPE_WEST_FACE = Block.box(0, 0, 0, 1, 16, 16);
    private static final VoxelShape SHAPE_EAST_FACE = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_NORTH_FACE = Block.box(0, 0, 0, 16, 16, 1);
    private static final VoxelShape SHAPE_SOUTH_FACE = Block.box(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SHAPE_FLOOR = Block.box(0, 0, 0, 16, 1, 16);
    private static final VoxelShape SHAPE_CEILING = Block.box(0, 15, 0, 16, 16, 16);
    private static final Map<Direction, VoxelShape> COLLISION_BOTTOM = ImmutableMap.of(
            Direction.NORTH, Shapes.or(SHAPE_FLOOR, Shapes.or(SHAPE_NORTH_FACE, Shapes.or(SHAPE_WEST_FACE, SHAPE_EAST_FACE))),
            Direction.SOUTH, Shapes.or(SHAPE_FLOOR, Shapes.or(SHAPE_SOUTH_FACE, Shapes.or(SHAPE_EAST_FACE, SHAPE_WEST_FACE))),
            Direction.EAST, Shapes.or(SHAPE_FLOOR, Shapes.or(SHAPE_EAST_FACE, Shapes.or(SHAPE_NORTH_FACE, SHAPE_SOUTH_FACE))),
            Direction.WEST, Shapes.or(SHAPE_FLOOR, Shapes.or(SHAPE_WEST_FACE, Shapes.or(SHAPE_SOUTH_FACE, SHAPE_NORTH_FACE)))
    );

    private static final Map<Direction, VoxelShape> COLLISION_TOP = ImmutableMap.of(
            Direction.NORTH, Shapes.or(SHAPE_CEILING, Shapes.or(SHAPE_NORTH_FACE, Shapes.or(SHAPE_WEST_FACE, SHAPE_EAST_FACE))),
            Direction.SOUTH, Shapes.or(SHAPE_CEILING, Shapes.or(SHAPE_SOUTH_FACE, Shapes.or(SHAPE_EAST_FACE, SHAPE_WEST_FACE))),
            Direction.EAST, Shapes.or(SHAPE_CEILING, Shapes.or(SHAPE_EAST_FACE, Shapes.or(SHAPE_NORTH_FACE, SHAPE_SOUTH_FACE))),
            Direction.WEST, Shapes.or(SHAPE_CEILING, Shapes.or(SHAPE_WEST_FACE, Shapes.or(SHAPE_SOUTH_FACE, SHAPE_NORTH_FACE)))
    );

    public ElevatorBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(HALF)  == DoubleBlockHalf.LOWER ? COLLISION_BOTTOM.get(state.getValue(FACING)) : COLLISION_TOP.get(state.getValue(FACING));
    }

    @Override
    public @NotNull InteractionResult use(@Nonnull BlockState state, @NotNull Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult blockRayTraceResult) {
        if (isTop(state))
            return use(world.getBlockState(pos.below()), world, pos.below(), player, hand, blockRayTraceResult);
        if (player.blockPosition().equals(pos)) {
            if (player instanceof ServerPlayer sp) {
                PenguinNetwork.sendToClient(sp, new OpenElevatorScreenPacket(MineHelper.getMaximumFloorReached(player)));
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
