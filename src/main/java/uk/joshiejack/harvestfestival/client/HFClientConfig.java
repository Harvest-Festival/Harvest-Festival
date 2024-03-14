package uk.joshiejack.harvestfestival.client;

import net.neoforged.neoforge.common.ModConfigSpec;

public class HFClientConfig {
    public static ModConfigSpec.BooleanValue enableHUD;
    public static ModConfigSpec.BooleanValue enableEnergyBar;
    public static ModConfigSpec.BooleanValue enableHealthBar;
    public static ModConfigSpec.DoubleValue blinkMaximum;
    public static ModConfigSpec.BooleanValue renderQuality;

    HFClientConfig(ModConfigSpec.Builder builder) {
        builder.push("Information");
        enableHUD = builder.define("Enable mine HUD", true);
        enableEnergyBar = builder.define("Enable energy bar", true);
        enableHealthBar = builder.define("Enable health bar", true);
        builder.pop();
        builder.push("Rendering Effects");
        blinkMaximum = builder.defineInRange("Blink Maximum", 0D, 0D, 24D);
        renderQuality = builder.define("Render the Quality of items", true);
    }

    public static ModConfigSpec create() {
        return new ModConfigSpec.Builder().configure(HFClientConfig::new).getValue();
    }
}