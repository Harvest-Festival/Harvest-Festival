package uk.joshiejack.harvestfestival.world.television;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;

public record TVProgram(ResourceLocation textureLocation) implements ReloadableRegistry.PenguinRegistry<TVProgram> {
    public static final Codec<TVProgram> CODEC = ResourceLocation.CODEC.xmap(TVProgram::new, TVProgram::textureLocation);
    public static final TVProgram OFF = new TVProgram(HarvestFestival.prefix("tv_program/off"));

    @Override
    public ResourceLocation id() {
        return HFRegistries.TV_PROGRAMS.getID(this);
    }

    @Override
    public TVProgram fromNetwork(FriendlyByteBuf buf) {
        return new TVProgram(buf.readResourceLocation());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeResourceLocation(textureLocation);
    }
}
