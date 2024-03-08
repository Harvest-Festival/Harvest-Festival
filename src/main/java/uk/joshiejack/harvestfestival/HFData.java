package uk.joshiejack.harvestfestival;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import uk.joshiejack.harvestfestival.world.collections.HFPlayerData;
import uk.joshiejack.harvestfestival.world.entity.player.energy.FoodOverrides;
import uk.joshiejack.harvestfestival.world.item.tool.Upgrades;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTickData;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.CropData;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.GrowthData;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.SpreadableData;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.TreeData;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HFData {
    public static final DataMapType<Item, FoodOverrides.FoodStats> NUTRITION_OVERRIDES = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "nutrition"), net.minecraft.core.registries.Registries.ITEM,
            FoodOverrides.FoodStats.CODEC).synced(FoodOverrides.FoodStats.CODEC, true).build();
    public static final DataMapType<Item, Integer> CRITICAL_SMASH = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "critical_smash"), net.minecraft.core.registries.Registries.ITEM,
            Codec.INT).synced(Codec.INT, true).build();
    public static final DataMapType<Item, Upgrades.Upgrade> UPGRADES = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "upgrades"), net.minecraft.core.registries.Registries.ITEM,
            Upgrades.Upgrade.CODEC).synced(Upgrades.Upgrade.CODEC, true).build();
    public static final DataMapType<Block, DailyTicker<?>> TICKERS = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "tickers"), net.minecraft.core.registries.Registries.BLOCK,
            HFRegistries.TICKER_CODEC).synced(HFRegistries.TICKER_CODEC, true).build();
    public static final DataMapType<Block, CropData> CROP_DATA = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "crop_data"), net.minecraft.core.registries.Registries.BLOCK,
            CropData.CODEC).synced(CropData.CODEC, true).build();
    public static final DataMapType<Block, TreeData> TREE_DATA = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "tree_data"), net.minecraft.core.registries.Registries.BLOCK,
            TreeData.CODEC).synced(TreeData.CODEC, true).build();
    public static final DataMapType<Block, SpreadableData> SPREADABLE_DATA = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "spreadable_data"), net.minecraft.core.registries.Registries.BLOCK,
            SpreadableData.CODEC).synced(SpreadableData.CODEC, true).build();
    public static final DataMapType<Block, GrowthData> GROWTH_DATA = DataMapType.builder(new ResourceLocation(HarvestFestival.MODID, "growth_data"), net.minecraft.core.registries.Registries.BLOCK,
            GrowthData.CODEC).synced(GrowthData.CODEC, true).build();

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, HarvestFestival.MODID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HFPlayerData>> PLAYER_DATA = ATTACHMENT_TYPES.register("hf_player_data",() -> AttachmentType.serializable(HFPlayerData::new).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DailyTickData>> DAILY_TICKER_DATA = ATTACHMENT_TYPES.register("daily_ticker",() -> AttachmentType.serializable(DailyTickData::new).copyOnDeath().build());


    @SubscribeEvent
    public static void onDataMap(RegisterDataMapTypesEvent event) {
        event.register(NUTRITION_OVERRIDES);
        event.register(CRITICAL_SMASH);
        event.register(UPGRADES);
        event.register(TICKERS);
        event.register(CROP_DATA);
        event.register(TREE_DATA);
        event.register(SPREADABLE_DATA);
        event.register(GROWTH_DATA);
    }
}