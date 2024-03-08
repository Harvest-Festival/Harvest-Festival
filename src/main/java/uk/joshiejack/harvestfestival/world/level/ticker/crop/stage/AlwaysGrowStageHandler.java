package uk.joshiejack.harvestfestival.world.level.ticker.crop.stage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

public class AlwaysGrowStageHandler extends StageHandler<AlwaysGrowStageHandler> {
    public static final Codec<AlwaysGrowStageHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("maximum_stage").forGetter(ticker -> ticker.maximumStage)
    ).apply(instance, AlwaysGrowStageHandler::new));

    public AlwaysGrowStageHandler(int maximumStage) {
        super(maximumStage);
    }

    @Override
    public Codec<AlwaysGrowStageHandler> codec() {
        return CODEC;
    }

    @Override
    public boolean grow(int stage) {
        return true;
    }

    @Override
    public StageHandler<?> fromNetwork(FriendlyByteBuf friendlyByteBuf) {
        return new AlwaysGrowStageHandler(friendlyByteBuf.readInt());
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(maximumStage());
    }
}
