package uk.joshiejack.harvestfestival.client.renderer.fertilizer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.IBlockEntityRendererExtension;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.penguinlib.client.level.ghost.GhostBlockGetter;

import java.util.Collection;

public class FertilizerBlockGetter extends GhostBlockGetter<Collection<BlockPos>> {
    public FertilizerBlockGetter(Collection<BlockPos> positions) {
        super(positions, 0, 0, 0);
    }

    @Override
    public @NotNull BlockState getBlockState(@NotNull BlockPos pos) {
        return data.contains(pos) ? Blocks.FARMLAND.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public AABB getAABB() {
        return IBlockEntityRendererExtension.INFINITE_EXTENT_AABB;
    }
}