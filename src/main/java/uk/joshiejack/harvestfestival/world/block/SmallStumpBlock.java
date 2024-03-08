package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import uk.joshiejack.penguinlib.world.block.ShapedBlock;

public class SmallStumpBlock extends ShapedBlock {
    public SmallStumpBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion(), Shapes.create(0.15F, 0F, 0.15F, 0.85F, 0.15F, 0.85F));
    }
}
