package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.network.television.OpenTVScreenPacket;
import uk.joshiejack.harvestfestival.world.block.entity.TelevisionBlockEntity;
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.world.block.RotatableBlock;

import javax.annotation.Nonnull;

public class TelevisionBlock extends RotatableBlock implements EntityBlock {
    private static final VoxelShape SOUTH_AABB = Shapes.create(0.125D, 0D, 0.0625D, 0.875D, 0.9375D, 0.625D);
    private static final VoxelShape NORTH_AABB = Shapes.create(0.125D, 0D, 0.375D, 0.875D, 0.9375D, 0.9375D);
    private static final VoxelShape EAST_AABB = Shapes.create(0.0625D, 0D, 0.125D, 0.625D, 0.9375D, 0.875D);
    private static final VoxelShape WEST_AABB = Shapes.create(0.375D, 0D, 0.125D, 0.9375D, 0.9375D, 0.875D);

    public TelevisionBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).strength(1.5F).sound(SoundType.WOOD).noOcclusion());
    }

    @Override
    public @NotNull InteractionResult use(@Nonnull BlockState state, @NotNull Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult blockRayTraceResult) {
        if (world.getBlockEntity(pos) instanceof TelevisionBlockEntity television) {
            if (television.getProgram() == TVProgram.OFF) {
                if (!world.isClientSide) {
                    PenguinNetwork.sendToClient((ServerPlayer) player, new OpenTVScreenPacket(pos));
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_AABB;
            case EAST -> EAST_AABB;
            case SOUTH -> SOUTH_AABB;
            default -> WEST_AABB;
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TelevisionBlockEntity(pos, state);
    }
}