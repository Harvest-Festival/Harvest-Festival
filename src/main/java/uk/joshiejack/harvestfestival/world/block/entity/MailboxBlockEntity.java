package uk.joshiejack.harvestfestival.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MailboxBlockEntity extends BlockEntity {
    public MailboxBlockEntity(BlockPos pos, BlockState state) {
        super(HFBlockEntities.MAILBOX.get(), pos, state);
    }
}
