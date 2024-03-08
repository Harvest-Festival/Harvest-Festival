package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.network.OpenLetterScreenPacket;
import uk.joshiejack.harvestfestival.world.block.entity.MailboxBlockEntity;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.harvestfestival.world.mail.PostalOffice;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.world.block.RotatableBlock;

import javax.annotation.Nonnull;
import java.util.List;

public class MailboxBlock extends RotatableBlock implements EntityBlock {
    private static final VoxelShape NORTH = Shapes.create(0.2D, 0.2D, 0.6D, 0.8D, 0.9D, 1.4D);
    private static final VoxelShape SOUTH = Shapes.create(0.2D, 0.2D, -0.4D, 0.8D, 0.9D, 0.4D);
    private static final VoxelShape EAST = Shapes.create(-0.4D, 0.2D, 0.2D, 0.4D, 0.9D, 0.8D);
    private static final VoxelShape WEST = Shapes.create(0.6D, 0.2D, 0.2D, 1.4D, 0.9D, 0.8D);

    public MailboxBlock(Block.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            default -> WEST;
        };
    }

    @Override
    public @NotNull InteractionResult use(@Nonnull BlockState state, @NotNull Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult blockRayTraceResult) {
        List<AbstractLetter<?>> letters = PostalOffice.getLetters(player);
        if (!letters.isEmpty()) {
            if (player instanceof ServerPlayer serverPlayer)
                PenguinNetwork.sendToClient(serverPlayer, new OpenLetterScreenPacket(null));
            return InteractionResult.SUCCESS;
        } else return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new MailboxBlockEntity(pos, state);
    }
}