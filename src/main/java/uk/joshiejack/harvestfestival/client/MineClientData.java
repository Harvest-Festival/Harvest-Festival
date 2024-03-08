package uk.joshiejack.harvestfestival.client;

import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;

public class MineClientData {
    static MineSettings SETTINGS = null;

    public static void setSettings(MineSettings settings) {
        SETTINGS = settings;
    }

    public static MineSettings getSettings() {
        return SETTINGS;
    }
}
