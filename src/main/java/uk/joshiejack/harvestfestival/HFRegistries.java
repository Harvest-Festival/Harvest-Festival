package uk.joshiejack.harvestfestival;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.harvestfestival.world.farming.Quality;
import uk.joshiejack.harvestfestival.world.item.SeedData;
import uk.joshiejack.harvestfestival.world.item.SeedlingData;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.AlwaysGrowStageHandler;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.harvestfestival.world.mail.TextLetter;
import uk.joshiejack.harvestfestival.world.television.TVChannel;
import uk.joshiejack.harvestfestival.world.television.TVProgram;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HFRegistries {
    public static class Keys {
        public static final ResourceLocation NONE = new ResourceLocation(HarvestFestival.MODID, "none");
        public static final ResourceKey<Registry<Codec<? extends AbstractLetter<?>>>> LETTER = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "letter"));
        public static final ResourceKey<Registry<AbstractLetter.LetterSerializer<?>>> LETTER_SERIALIZER = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "letter_serializer"));
        public static final ResourceKey<Registry<Fertilizer>> FERTILIZER = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "fertilizer"));
        public static final ResourceKey<Registry<Codec<? extends DailyTicker<?>>>> TICKER_TYPE = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "ticker_type"));
        public static final ResourceKey<Registry<Codec<? extends StageHandler<?>>>> STAGE_HANDLER = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "stage_handler"));
        public static final ResourceKey<Registry<Quality>> QUALITY = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "quality"));
    }

    //Non-Reloadable Registries (Synced to client on join)
    public static final Registry<Codec<? extends AbstractLetter<?>>> LETTER_TYPE = new RegistryBuilder<>(Keys.LETTER).sync(true).create();
    public static final Registry<AbstractLetter.LetterSerializer<?>> LETTER_SERIALIZER = new RegistryBuilder<>(Keys.LETTER_SERIALIZER).sync(true).create();
    public static final Registry<Fertilizer> FERTILIZER = new RegistryBuilder<>(Keys.FERTILIZER).sync(true).create();
    public static final Registry<Codec<? extends DailyTicker<?>>> TICKER_TYPE = new RegistryBuilder<>(Keys.TICKER_TYPE).sync(true).create();
    public static final Registry<Codec<? extends StageHandler<?>>> STAGE_HANDLER_TYPE = new RegistryBuilder<>(Keys.STAGE_HANDLER).sync(true).create();
    public static final Registry<Quality> QUALITY = new RegistryBuilder<>(Keys.QUALITY).sync(true).create();

    //Reloadable Registries (Sync all data to client on join ala recipes)
    public static final ReloadableRegistry<AbstractLetter<?>> LETTERS = new ReloadableRegistry<>(HarvestFestival.MODID, "letters", LETTER_TYPE.byNameCodec().dispatchStable(AbstractLetter::codec, Function.identity()),
            new TextLetter(null, false, false, false, 0, 0), false);
    public static final ReloadableRegistry<StageHandler<?>> STAGE_HANDLERS = new ReloadableRegistry<>(HarvestFestival.MODID, "stage_handler", STAGE_HANDLER_TYPE.byNameCodec().dispatchStable(StageHandler::codec, Function.identity()),
            new AlwaysGrowStageHandler(0), true);
    public static final ReloadableRegistry<SeedData> SEEDS = new ReloadableRegistry<>(HarvestFestival.MODID, "seeds", SeedData.CODEC, SeedData.EMPTY, true);
    public static final ReloadableRegistry<SeedlingData> SEEDLINGS = new ReloadableRegistry<>(HarvestFestival.MODID, "seedlings", SeedlingData.CODEC, SeedlingData.EMPTY, true);
    public static final ReloadableRegistry<TVChannel> TV_CHANNELS = new ReloadableRegistry<>(HarvestFestival.MODID, "tv_channels", TVChannel.CODEC, TVChannel.OFF, true);
    public static final ReloadableRegistry<TVProgram> TV_PROGRAMS = new ReloadableRegistry<>(HarvestFestival.MODID, "tv_programs", TVProgram.CODEC, TVProgram.OFF, true);
    public static final Codec<DailyTicker<?>> TICKER_CODEC = HFRegistries.TICKER_TYPE.byNameCodec().dispatchStable(DailyTicker::codec, Function.identity());

    @SubscribeEvent
    public static void register(NewRegistryEvent event) {
        event.register(LETTER_TYPE);
        event.register(LETTER_SERIALIZER);
        event.register(FERTILIZER);
        event.register(QUALITY);
        event.register(TICKER_TYPE);
        event.register(STAGE_HANDLER_TYPE);
    }
}
