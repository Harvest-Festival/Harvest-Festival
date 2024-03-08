package uk.joshiejack.harvestfestival.world.level.mine.tier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.world.level.mine.room.RoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.TemplateRoomGenerator;

import java.util.ArrayList;
import java.util.List;

public record RoomSettings(RoomGenerator generator) {
    public static final Codec<RoomSettings> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            RoomGenerator.CODEC.fieldOf("generator").forGetter(RoomSettings::generator)
                    )
                    .apply(codec, RoomSettings::new)
    );
    public static final RoomSettings DEFAULT = new RoomSettings(MineTier.DEFAULT_GENERATOR);

    public static class Builder {
        List<RoomSettings> settingsList = new ArrayList<>();

        public static Builder create() {
            return new Builder();
        }

        public Builder template(String texture) {
            settingsList.add(new RoomSettings(new TemplateRoomGenerator(new ResourceLocation(texture))));
            return this;
        }

        public Builder add(RoomGenerator generator) {
            settingsList.add(new RoomSettings(generator));
            return this;
        }

        public List<RoomSettings> build() {
            return settingsList;
        }
    }
}