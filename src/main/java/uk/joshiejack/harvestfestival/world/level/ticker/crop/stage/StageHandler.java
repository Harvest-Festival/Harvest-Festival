package uk.joshiejack.harvestfestival.world.level.ticker.crop.stage;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;

public abstract class StageHandler<S extends StageHandler<S>> implements ReloadableRegistry.PenguinRegistry<StageHandler<?>> {
    protected final int maximumStage;

    public StageHandler(int maximumStage) {
        this.maximumStage = maximumStage;
    }

    public abstract Codec<S> codec();

    /** The maximum stage of this crop **/
    public int maximumStage() {
        return maximumStage;
    }

    /** Whether the crop can grow to the next stage **/
    public abstract boolean grow(int stage);

    @Override
    public ResourceLocation id() {
        return HFRegistries.STAGE_HANDLERS.getID(this);
    }
}
