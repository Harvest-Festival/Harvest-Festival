package uk.joshiejack.harvestfestival.world.level.ticker.crop.stage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class PatternBasedStageHandler extends StageHandler<PatternBasedStageHandler> {
    public static final Codec<PatternBasedStageHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.listOf().fieldOf("can_grow").forGetter(ticker -> ticker.canGrow)
    ).apply(instance, PatternBasedStageHandler::new));

    private final List<Boolean> canGrow;

    public PatternBasedStageHandler(boolean... canGrow) {
        this(BooleanList.of(canGrow));
    }
    public PatternBasedStageHandler(List<Boolean> canGrow) {
        super(canGrow.size());
        this.canGrow = canGrow;
    }

    @Override
    public Codec<PatternBasedStageHandler> codec() {
        return CODEC;
    }

    @Override
    public boolean grow(int stage) {
        return canGrow.get(stage - 1);
    }

    @Override
    public StageHandler<?> fromNetwork(FriendlyByteBuf buf) {
        return new PatternBasedStageHandler(buf.readList(FriendlyByteBuf::readBoolean));
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeCollection(canGrow, FriendlyByteBuf::writeBoolean);
    }
}
