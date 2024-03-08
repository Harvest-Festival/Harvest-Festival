package uk.joshiejack.harvestfestival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import uk.joshiejack.harvestfestival.HarvestFestival;

public class HFSoundDefinitions extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public HFSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, HarvestFestival.MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(HarvestFestival.HFSounds.ROCK_SMASH, definition().subtitle("subtitles.smashrock").with(sound("rock_smash")));
        this.add(HarvestFestival.HFSounds.WOOD_CHOP, definition().subtitle("subtitles.smashwood").with(sound("wood_chop")));
        this.add(HarvestFestival.HFSounds.BLESSING, definition().subtitle("subtitles.goddess_blessing").with(sound("blessing")));
        this.add(HarvestFestival.HFSounds.GODDESS, definition().subtitle("subtitles.goddess_spawn").with(sound("goddess")));
        this.add(HarvestFestival.HFSounds.YAWN, definition().subtitle("subtitles.yawn").with(sound("yawn_1").volume(0.25F), sound("yawn_2").volume(0.33F)));
        this.add(HarvestFestival.HFSounds.FROST_SLIME, definition().subtitle("subtitles.frost_slime").with(sound("frost_slime")));
    }

    protected static SoundDefinition.Sound sound(final String name) {
        return sound(new ResourceLocation(HarvestFestival.MODID, name));
    }
}
