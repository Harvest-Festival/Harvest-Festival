package uk.joshiejack.harvestfestival.world.level.ticker.crop.stage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class NumberBasedStageHandler extends StageHandler<NumberBasedStageHandler> {
    public static final Codec<NumberBasedStageHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("maximum_stage").forGetter(ticker -> ticker.maximumStage),
            Codec.INT.listOf().fieldOf("valid_stages").forGetter(ticker -> ticker.validStages)
    ).apply(instance, NumberBasedStageHandler::new));

    private final List<Integer> validStages;

    public NumberBasedStageHandler(int maximumStage, List<Integer> validStages) {
        super(maximumStage);
        this.validStages = validStages;
    }

    @Override
    public Codec<NumberBasedStageHandler> codec() {
        return CODEC;
    }

    @Override
    public boolean grow(int stage) {
        return validStages.contains(stage);
    }

    @Override
    public StageHandler<?> fromNetwork(FriendlyByteBuf buf) {
        return new NumberBasedStageHandler(buf.readInt(), buf.readList(FriendlyByteBuf::readInt));
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeInt(maximumStage());
        buf.writeCollection(validStages, FriendlyByteBuf::writeInt);
    }
}
