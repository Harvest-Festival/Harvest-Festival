package uk.joshiejack.harvestfestival.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class BlockSmashedEvent extends PlayerEvent implements ICancellableEvent {
    private final BlockState state;
    private final BlockPos pos;

    public BlockSmashedEvent(Player player, BlockState state, BlockPos pos) {
        super(player);
        this.state = state;
        this.pos = pos;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }
}
