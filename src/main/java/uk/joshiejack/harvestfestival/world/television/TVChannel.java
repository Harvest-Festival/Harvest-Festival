package uk.joshiejack.harvestfestival.world.television;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Lazy;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.penguinlib.scripting.Interpreter;
import uk.joshiejack.penguinlib.scripting.ScriptFactory;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;

import javax.annotation.Nullable;

public class TVChannel implements ReloadableRegistry.PenguinRegistry<TVChannel> {
    public static final Codec<TVChannel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("script").forGetter(channel -> channel.scriptID),
            Codec.BOOL.optionalFieldOf("selectable", true).forGetter(channel -> channel.selectable)
    ).apply(instance, TVChannel::new));
    public static final TVChannel OFF = new TVChannel(null, false);

    //The texture displayed in the menu selection and the coordinates, 62x46 pixels
    private final ResourceLocation scriptID;
    private final boolean selectable;
    private final Lazy<Interpreter<?>> script = Lazy.of(() -> ScriptFactory.getScript(getScriptID()));

    public TVChannel(@Nullable ResourceLocation script, boolean selectable) {
        this.scriptID = script;
        this.selectable = selectable;
    }

    public ResourceLocation getScriptID() {
        return scriptID;
    }

    public boolean isSelectable() {
        return selectable;
    }

    @Nullable
    public Interpreter<?> getScript() {
        return script.get();
    }

    public Component getName() {
        return Component.translatable("tv_channel.%s.%s.name".formatted(id().getNamespace(), id().getPath()));
    }

    @Override
    public ResourceLocation id() {
        return HFRegistries.TV_CHANNELS.getID(this);
    }

    @Override
    public TVChannel fromNetwork(FriendlyByteBuf buf) {
        return new TVChannel(buf.readBoolean() ? buf.readResourceLocation() : null, buf.readBoolean());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeBoolean(scriptID != null);
        if (scriptID != null)
            buf.writeResourceLocation(scriptID);
        buf.writeBoolean(selectable);
    }
}
