package uk.joshiejack.harvestfestival.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.text.WordUtils;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.item.HFItems;

public class HFLanguage extends LanguageProvider {
    public HFLanguage(PackOutput output) {
        super(output, HarvestFestival.MODID, "en_us");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void addTranslations() {
        add(HarvestFestival.suffix(HFBlocks.ELEVATOR.get().asItem(), "none"), "No elevators located!");
        add("hud." + HarvestFestival.MODID + ".mine", "Floor %s");
        add("itemGroup." + HarvestFestival.MODID + ".mine", "Harvest Festival - Mining");
        add("itemGroup." + HarvestFestival.MODID + ".gathering", "Harvest Festival - Gathering");
        add("itemGroup." + HarvestFestival.MODID + ".cheat", "Harvest Festival - Cheating");
        add("itemGroup." + HarvestFestival.MODID + ".farming", "Harvest Festival - Farming");
        HFEntities.ENTITIES.getEntries().forEach((entity) -> addEntityType(entity, WordUtils.capitalizeFully(entity.getId().getPath().replace("_", " "))));
        HFItems.ITEMS.getEntries().forEach((item) -> addItem(item, WordUtils.capitalizeFully(item.getId().getPath().replace("_", " "))));
        HFBlocks.BLOCKS.getEntries().stream().filter(key -> !BuiltInRegistries.ITEM.containsKey((key.getId()))).forEach((block) -> addBlock(block, WordUtils.capitalizeFully(block.getId().getPath().replace("_", " "))));
        add("item.%s.potato_seeds".formatted(HarvestFestival.MODID), "Potato Seeds");
        add("item.%s.carrot_seeds".formatted(HarvestFestival.MODID), "Carrot Seeds");
        //Add the spawn egg translations
        HFEntities.SPAWN_EGGS.getEntries().forEach((egg) -> add("item." + HarvestFestival.MODID + "." + egg.getId().getPath(), "Spawn " + WordUtils.capitalizeFully(egg.getId().getPath().replace("_", " "))));
        addAchievement("kill_10_slimes", "Kill 10 Slimes");
        addAchievement("complete_cooking_collection", "Complete the Cooking Collection");
        addAchievement("complete_fishing_collection", "Complete the Fishing Collection");
        addAchievement("complete_mining_collection", "Complete the Mining Collection");
        addAchievement("complete_shipping_collection", "Complete the Shipping Collection");
        addAchievement("ship_1000_crops", "Ship 1000 Crops");
        addAchievement("catch_100_fish", "Catch 100 Fish");

        addCollection("shipping", "Shipping");
        addCollection("cooking", "Cooking");
        addCollection("fishing", "Fishing");
        addCollection("mining", "Mining");
        HFEffects.EFFECTS.getEntries().forEach((effect) -> addEffect(effect, WordUtils.capitalizeFully(effect.getId().getPath().replace("_", " "))));
        add("note.harvestfestival.unknown", "Unknown");
        HFFarming.Fertilizers.FERTILIZERS.getEntries().forEach((fertilizer) -> addFertilizer(fertilizer, WordUtils.capitalizeFully(fertilizer.getId().getPath().replace("_", " "))));
        add("info." + HarvestFestival.MODID + ".requires_sickle", "Requires a Sickle");
        add("info." + HarvestFestival.MODID + ".no_water_required", "No Water Required");
        add("info." + HarvestFestival.MODID + ".days", "%s Days");

        add("season.harvestfestival.spring", "Spring");
        add("season.harvestfestival.summer", "Summer");
        add("season.harvestfestival.autumn", "Autumn");
        add("season.harvestfestival.winter", "Winter");
        add("harvestfestival.weekday.monday", "Mon");
        add("harvestfestival.weekday.tuesday", "Tue");
        add("harvestfestival.weekday.wednesday", "Wed");
        add("harvestfestival.weekday.thursday", "Thu");
        add("harvestfestival.weekday.friday", "Fri");
        add("harvestfestival.weekday.saturday", "Sat");
        add("harvestfestival.weekday.sunday", "Sun");

        addGuiTranslations();
        addTVTranslations();
    }

    private void addGuiTranslations() {
        addGui("collections", "Collections");
        addGui("notes", "Notes");
        addGui("achievements", "Achievements");
        addGui("calendar.format", "%s - Year %s");
        addGui("television.select_channel", "Select a Channel");
    }

    private void addTVTranslations() {
        addTVChannel("weather", "The Weather Channel");
        addTVChannel("cooking", "The Cooking Channel");
        addTVChannel("fishing", "Fishing Hour");
        addTVChannel("farming", "Life on the Farm");
        addTVProgram("adult", "watch", "Ohhh Ohhh OMG. What have I stumbled upon?! GET IT OFF THE SCREEN!");
        addTVProgram("weather", "clear.1", "Tomorrow is looking to be a beautiful day with clear skies all around!");
        addTVProgram("weather", "clear.2", "The weather tomorrow is looking to be a beautiful day with clear skies all around!");
        addTVProgram("weather", "clear.3", "You can expect blasts of sunshine and clear skies through the day.");
        addTVProgram("weather", "clear.4", "It's looking like a fantastic day tomorrow as the sun shines and the sky is out.");
        addTVProgram("weather", "clear.5", "Enjoy a sunny day tomorrow with clear skies and pleasant temperatures for outdoor activities.");
        addTVProgram("weather", "clear.6", "Plan your day with confidence as tomorrow promises clear weather, perfect for picnics and outdoor events.");
        addTVProgram("weather", "clear.7", "Experience a crystal-clear sky tomorrow, providing a perfect backdrop for a day filled with sunshine and warmth.");
        addTVProgram("weather", "snow.1", "Tomorrow is looking to be a snowy day with snowfall all around!");
        addTVProgram("weather", "snow.2", "In your local area, it is looking like we will have drops of snow fall tomorrow.");
        addTVProgram("weather", "snow.3", "Look out in you area for carpets of snow to fall as you go throughout your day.");
        addTVProgram("weather", "snow.4", "Time to get out those winter jackets, and get ready to make some snowmen as we'll be seeing a snow fall tomorrow.");
        addTVProgram("weather", "snow.5", "Gear up for a snowy adventure tomorrow as the forecast predicts a delightful snowfall covering the landscape.");
        addTVProgram("weather", "snow.6", "Tomorrow's snowfall promises a winter wonderland, creating a magical atmosphere for all to enjoy.");
        addTVProgram("weather", "snow.7", "Prepare for a snowy day ahead, with gentle snowflakes creating a serene and picturesque scene.");
        addTVProgram("weather", "rain.1", "Tomorrow is looking to be a rainy day with showers all around!");
        addTVProgram("weather", "rain.2", "It's time for a little shower tomorrow as rain is due to fall throughout the day.");
        addTVProgram("weather", "rain.3", "You can expect some beautiful downpours tomorrow as rain is set to come in.");
        addTVProgram("weather", "rain.4", "Put on your wellies and raincoat for tomorrow folks as it will be raining all day.");
        addTVProgram("weather", "rain.5", "Tomorrow brings a refreshing rain shower, providing much-needed hydration for gardens and a cozy ambiance.");
        addTVProgram("weather", "rain.6", "Embrace the soothing sound of raindrops tomorrow as a gentle rain showers the area throughout the day.");
        addTVProgram("weather", "rain.7", "Experience a rainy day tomorrow, perfect for staying indoors with a good book or movie.");
        addTVProgram("weather", "blizzard", "Be very careful tomorrow as a blizzard is due to arrive. We advise you to stay in doors.");
        addTVProgram("weather", "storm", "Heavy rains, thunder and lightining will occur throughout the day tomorrow so please be careful.");
        addTVProgram("weather", "fog", "Tomorrow is looking to be a foggy day with mist all around!");

    }

    //Translate achievements
    private void addAchievement(String key, String value) {
        add("achievement." + HarvestFestival.MODID + "." + key, value);
    }

    private void addGui(String key, String value) {
        add("gui." + HarvestFestival.MODID + "." + key, value);
    }

    private void addCollection(String key, String value) {
        add("collection." + HarvestFestival.MODID + "." + key, value);
    }

    private void addFertilizer(DeferredHolder<Fertilizer, ? extends Fertilizer> key, String value) {
        add("fertilizer." + key.getId().getNamespace() + "." + key.getId().getPath(), value + " Fertilizer");
    }

    private void addTVProgram(String program, String suffix, String translation) {
        add("tv_program.harvestfestival.%s_%s".formatted(program, suffix), translation);
    }

    private void addTVChannel(String channel, String translation) {
        add("tv_channel.harvestfestival.%s.name".formatted(channel), translation);
    }
}