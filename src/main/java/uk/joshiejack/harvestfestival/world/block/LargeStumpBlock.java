package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import uk.joshiejack.penguinlib.world.block.ShapedBlock;

public class LargeStumpBlock extends ShapedBlock {
    public LargeStumpBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion(), Shapes.create(0.0125F, 0F, 0.0125F, 0.9875F, 0.9F, 0.9875F));
    }
}
