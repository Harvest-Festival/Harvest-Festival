package uk.joshiejack.harvestfestival;

import net.neoforged.neoforge.common.ModConfigSpec;

public class HFConfig {
    public static ModConfigSpec.BooleanValue energyFromExhaustion;
    public static ModConfigSpec.BooleanValue forceNaturalRegenDisabling;
    public static ModConfigSpec.BooleanValue sleepRestoresEnergy;
    public static ModConfigSpec.BooleanValue sleepAnytime;
    public static ModConfigSpec.IntValue startingEnergy ;
    public static ModConfigSpec.IntValue startingHealth;

    public static ModConfigSpec.DoubleValue holeSpawnChanceNodes;
    public static ModConfigSpec.IntValue holeMinDistance;
    public static ModConfigSpec.IntValue holeBlockExtraDistance;

    public static ModConfigSpec.IntValue tickersPerMinecraftTick;
    public static ModConfigSpec.BooleanValue retroactivelyLoadTickers;

    public static ModConfigSpec.IntValue seedBagRange;

    //Annoyance Settings
    public static ModConfigSpec.BooleanValue cursedToolsUnequippable;
    public static ModConfigSpec.BooleanValue cursedToolsStayInInventory;
    public static ModConfigSpec.BooleanValue toolsRecoverable;
    public static ModConfigSpec.IntValue maxStackSize;
    public static ModConfigSpec.BooleanValue enableBagUpgrades;
    public static ModConfigSpec.IntValue defaultBagSize;

    HFConfig(ModConfigSpec.Builder builder) {
        builder.push("Energy Settings");
        energyFromExhaustion = builder.define("Exhaustion uses energy", false);
        forceNaturalRegenDisabling = builder.define("Disable natural health regeneration", false);
        sleepRestoresEnergy = builder.define("Sleeping restores energy", true);
        sleepAnytime = builder.define("Sleep anytime", true);
        startingEnergy = builder.defineInRange("Starting energy", 6, 1, 20);
        startingHealth = builder.defineInRange("Starting health", 6, 1, 20);
        builder.pop();
        builder.push("Mine Settings");
        holeSpawnChanceNodes = builder.comment("This is the chance that a hole will spawn when you mine a node in the mine, defined as a value between 0 and 1. (0 Will disable holes and 1 will ensure they always occur)")
                .defineInRange("Hole spawn chance", 0.01D, 0.0D, 1.0D);
        holeMinDistance = builder.comment("This is the minimum number of floors a hole will take the player down when they fall in")
                .defineInRange("Hole min distance", 3, 1, 1000);
        holeBlockExtraDistance = builder.comment("This is a multiplier. Holes will take the player down the minimum + the number of floors in the mine * this value")
                .defineInRange("Hole block extra distance", 1, 1, 1000);
        builder.pop();
        builder.push("Ticker Settings");
        tickersPerMinecraftTick = builder.comment("This is the number of daily validStages that will be processed per minecraft tick")
                .defineInRange("Tickers per minecraft tick", 100, 1, Short.MAX_VALUE);
        retroactivelyLoadTickers = builder.comment("This will retroactively load in blocks that should tick only daily every time a chunk loads. Only use this if you are adding this mod to an existing world.")
                .define("Retroactively load tickers", false);
        builder.pop();
        builder.push("Farm Settings");
        seedBagRange = builder.comment("This is the radius that a seed bag will plant in. (In old versions of HF this was 1)")
                .defineInRange("Seed bag range", 0, 0, 64);
        builder.pop();
        builder.push("Gameplay Settings");
        cursedToolsUnequippable = builder.comment("With the enabled, cursed tools cannot be be un-equipped when selected on your hotbar.")
                .define("Cursed tools unequippable", true);
        cursedToolsStayInInventory = builder.comment("With the enabled, cursed tools will stay in your inventory when you die.")
                .define("Cursed tools stay in inventory", true);
        toolsRecoverable = builder.comment("With this enabled you will receive your upgradable tools back when you die. If this is disabled, you will lose them.")
                .define("Tools recoverable", true);
        maxStackSize = builder.comment("This is the maximum stack size for items in the game. (Default is 999)")
                .defineInRange("Max stack size", 999, 1, Short.MAX_VALUE);
        enableBagUpgrades = builder.comment("With this enabled, bag upgrades will be enabled. Disable it to prevent slot locking.")
                .define("Enable bag upgrades", true);
        defaultBagSize = builder.comment("This is the default size of the bag, if bag upgrades are enabled. Value between 1 and 4.")
                .defineInRange("Default bag size", 2, 1, 4);
    }

    public static ModConfigSpec create() {
        return new ModConfigSpec.Builder().configure(HFConfig::new).getValue();
    }
}
