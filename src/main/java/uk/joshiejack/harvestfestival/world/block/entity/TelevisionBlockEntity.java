package uk.joshiejack.harvestfestival.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import uk.joshiejack.harvestfestival.network.television.SetTVProgramPacket;
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.world.block.entity.PenguinBlockEntity;

import javax.annotation.Nonnull;

public class TelevisionBlockEntity extends PenguinBlockEntity {
    public static final ModelProperty<TVProgram> TV_PROGRAM = new ModelProperty<>();
    private TVProgram program = TVProgram.OFF;

    public TelevisionBlockEntity(BlockPos pos, BlockState state) {
        super(HFBlockEntities.TELEVISION.get(), pos, state);
        requestModelDataUpdate(); //Call when created?
    }

    @Override
    @Nonnull
    public ModelData getModelData() {
        return ModelData.builder().with(TV_PROGRAM, program).build();
    }

    @Nonnull
    public TVProgram getProgram() {
        return program;
    }

    public void setProgram(TVProgram program) {
        if (level == null) return;
        this.program = program;
        if (!level.isClientSide) {
            PenguinNetwork.sendToNearby(this, new SetTVProgramPacket(getBlockPos(), program));
        } else {
            requestModelDataUpdate();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }

        //requestModelDataUpdate();

    }
}
