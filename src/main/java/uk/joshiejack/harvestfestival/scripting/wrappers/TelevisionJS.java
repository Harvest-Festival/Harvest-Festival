package uk.joshiejack.harvestfestival.scripting.wrappers;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.network.television.DisplayTVChatterPacket;
import uk.joshiejack.harvestfestival.world.block.entity.TelevisionBlockEntity;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.scripting.wrapper.AbstractJS;
import uk.joshiejack.penguinlib.scripting.wrapper.PlayerJS;

@SuppressWarnings("unused")
public class TelevisionJS extends AbstractJS<TelevisionBlockEntity> {
    public TelevisionJS(TelevisionBlockEntity television) {
        super(television);
    }

    public void chatterLiteral(PlayerJS player, String text) {
        PenguinNetwork.sendToClient((ServerPlayer) player.get(), new DisplayTVChatterPacket(penguinScriptingObject.getBlockPos(), Component.literal(text)));
    }

    public void chatterTranslated(PlayerJS player, String text) {
        PenguinNetwork.sendToClient((ServerPlayer) player.get(), new DisplayTVChatterPacket(penguinScriptingObject.getBlockPos(), Component.translatable(text)));
    }

    public void watch(String text) {
        penguinScriptingObject.setProgram(HFRegistries.TV_PROGRAMS.get(new ResourceLocation(text)));
    }
}
