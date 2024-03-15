package uk.joshiejack.harvestfestival;

import com.mojang.logging.LogUtils;
import net.minecraft.DetectedVersion;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import uk.joshiejack.harvestfestival.client.HFClientConfig;
import uk.joshiejack.harvestfestival.command.HFCommands;
import uk.joshiejack.harvestfestival.data.*;
import uk.joshiejack.harvestfestival.data.economy.HFDepartments;
import uk.joshiejack.harvestfestival.data.economy.HFShipping;
import uk.joshiejack.harvestfestival.data.economy.HFShops;
import uk.joshiejack.harvestfestival.data.economy.HFStockMechanics;
import uk.joshiejack.harvestfestival.data.farming.HFDataMapProvider;
import uk.joshiejack.harvestfestival.data.farming.HFSeedProvider;
import uk.joshiejack.harvestfestival.data.farming.HFSeedlingProvider;
import uk.joshiejack.harvestfestival.data.farming.HFStageHandlers;
import uk.joshiejack.harvestfestival.data.loot.HFLootModifier;
import uk.joshiejack.harvestfestival.data.loot.HFLootTables;
import uk.joshiejack.harvestfestival.data.tags.HFBiomeTags;
import uk.joshiejack.harvestfestival.data.tags.HFBlockTags;
import uk.joshiejack.harvestfestival.data.tags.HFEntityTags;
import uk.joshiejack.harvestfestival.data.tags.HFItemTags;
import uk.joshiejack.harvestfestival.data.town.HFGiftQualities;
import uk.joshiejack.harvestfestival.data.town.HFNPCs;
import uk.joshiejack.harvestfestival.data.town.HFTownQuests;
import uk.joshiejack.harvestfestival.world.achievements.AchievementManager;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.block.HFFluids;
import uk.joshiejack.harvestfestival.world.block.entity.HFBlockEntities;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.inventory.HFMenus;
import uk.joshiejack.harvestfestival.world.item.HFCreativeTabs;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.harvestfestival.world.level.HFLevel;
import uk.joshiejack.harvestfestival.world.level.ticker.HFDailyTickTypes;
import uk.joshiejack.harvestfestival.world.loot.HFLoot;
import uk.joshiejack.harvestfestival.world.mail.HFLetters;

import java.util.Optional;

import static uk.joshiejack.harvestfestival.HarvestFestival.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(MODID)
public class HarvestFestival {
    public static final String MODID = "harvestfestival";
    public static final String ACHIEVEMENTS_FOLDER = MODID + "/achievements";
    public static final Logger LOGGER = LogUtils.getLogger();

    public HarvestFestival(IEventBus eventBus) {
        HFLetters.register(eventBus);
        HFData.register(eventBus);
        HFDailyTickTypes.register(eventBus);
        HFCommands.ARGUMENT_TYPES.register(eventBus);
        HFBlocks.BLOCKS.register(eventBus);
        HFItems.ITEMS.register(eventBus);
        HFBlockEntities.BLOCK_ENTITY_TYPES.register(eventBus);
        HFEntities.register(eventBus);
        HFFarming.register(eventBus);
        HFFluids.register(eventBus);
        HFEffects.EFFECTS.register(eventBus);
        HFLevel.register(eventBus);
        HFLoot.register(eventBus);
        HFSounds.SOUNDS.register(eventBus);
        HFMenus.CONTAINERS.register(eventBus);
        HFCreativeTabs.CREATIVE_MODE_TABS.register(eventBus);
        AchievementManager.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HFConfig.create());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HFClientConfig.create());
    }

    @SubscribeEvent
    public static void onDataGathering(GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput output = event.getGenerator().getPackOutput();
        //Add the datapack entries
        //Client
        generator.addProvider(event.includeClient(), new HFBlockStates(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new HFItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new HFQualityModels(output, MODID, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new HFLanguage(output));
        generator.addProvider(event.includeClient(), new HFSoundDefinitions(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new HFSpriteSourceProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));

        //Server
        HFBlockTags blocktags = new HFBlockTags(output, event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blocktags);
        generator.addProvider(event.includeServer(), new HFDatapackEntries(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new HFLootTables(output));
        generator.addProvider(event.includeServer(), new HFLootModifier(output));
        generator.addProvider(event.includeServer(), new HFItemTags(output, event.getLookupProvider(), blocktags.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new HFEntityTags(output, event.getLookupProvider(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new HFBiomeTags(output, event.getLookupProvider(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new HFDatabase(output));
        generator.addProvider(event.includeServer(), new HFNoteGeneration(output));
        generator.addProvider(event.includeServer(), new HFAchievements(output));
        generator.addProvider(event.includeServer(), new HFLetterGeneration(output));
        generator.addProvider(event.includeServer(), new HFStageHandlers(output));
        generator.addProvider(event.includeServer(), new HFSeedProvider(output));
        generator.addProvider(event.includeServer(), new HFSeedlingProvider(output));
        generator.addProvider(event.includeServer(), new HFDataMapProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new HFTVPrograms(output));
        generator.addProvider(event.includeServer(), new HFTVChannels(output));

        //Economy
        generator.addProvider(event.includeServer(), new HFStockMechanics(output));
        generator.addProvider(event.includeServer(), new HFShops(output));
        generator.addProvider(event.includeServer(), new HFDepartments(output));
        generator.addProvider(event.includeServer(), new HFShipping(output, event.getLookupProvider()));

        //Town
        generator.addProvider(event.includeServer(), new HFGiftQualities(output));
        generator.addProvider(event.includeServer(), new HFNPCs(output));
        generator.addProvider(event.includeServer(), new HFTownQuests(output));

        //PackMetadataGenerator
        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for Harvest Festival"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path.toLowerCase());
    }

    public static String suffix(Item item, String text) {
        return HarvestFestival.MODID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + "." + text;
    }

    public static class HFSounds {
        public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(net.minecraft.core.registries.Registries.SOUND_EVENT, MODID);
        public static final DeferredHolder<SoundEvent, SoundEvent> ROCK_SMASH = createSoundEvent("rock_smash");
        public static final DeferredHolder<SoundEvent, SoundEvent> WOOD_CHOP = createSoundEvent("wood_chop");
        public static final DeferredHolder<SoundEvent, SoundEvent> BLESSING = createSoundEvent("blessing");
        public static final DeferredHolder<SoundEvent, SoundEvent> GODDESS = createSoundEvent("goddess");
        public static final DeferredHolder<SoundEvent, SoundEvent> YAWN = createSoundEvent("yawn");
        public static final DeferredHolder<SoundEvent, SoundEvent> FROST_SLIME = createSoundEvent("frost_slime");
        public static final Holder<SoundEvent> TREE_FALL = createSoundEvent("tree_fall");
        public static final Holder<SoundEvent> TREE_CHOP = createSoundEvent("tree_chop");


        private static DeferredHolder<SoundEvent, SoundEvent> createSoundEvent(String name) {
            return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
        }
    }
}
