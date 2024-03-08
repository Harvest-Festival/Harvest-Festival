package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.shapes.VoxelShape;
import uk.joshiejack.penguinlib.world.block.ShapedBlock;

public class StoneBlock extends ShapedBlock {
    public StoneBlock(Properties properties, VoxelShape shape) {
        super(properties.strength(1.5F).sound(SoundType.STONE).noOcclusion(), shape);
    }
}
