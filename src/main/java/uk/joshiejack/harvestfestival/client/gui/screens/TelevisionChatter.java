package uk.joshiejack.harvestfestival.client.gui.screens;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import uk.joshiejack.harvestfestival.network.television.SetTVChannelPacket;
import uk.joshiejack.harvestfestival.world.television.TVChannel;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

public class TelevisionChatter extends ChatterScreen {
    private final BlockPos pos;

    public TelevisionChatter(BlockPos pos, Component text) {
        super(text);
        this.pos = pos;
    }

    @Override
    public void removed() {
        PenguinNetwork.sendToServer(new SetTVChannelPacket(pos, TVChannel.OFF));
    }
}
