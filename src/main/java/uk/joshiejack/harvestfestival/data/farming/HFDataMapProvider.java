package uk.joshiejack.harvestfestival.data.farming;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.neoforged.neoforge.common.data.DataMapProvider;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.HFDailyTickTypes;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.CropData;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.CropTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.SoilTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.data.QualityRetainingBlock;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.GrowthTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.growable.SpreadableTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.JuvenileTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.SaplingTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.SeedlingTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.TreeData;
import uk.joshiejack.horticulture.world.block.HorticultureBlocks;

import java.util.concurrent.CompletableFuture;

public class HFDataMapProvider extends DataMapProvider {
    public HFDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void gather() {
        final var cropData = builder(HFData.CROP_DATA);
        final var tickers = builder(HFData.TICKERS);
        final var treeData = builder(HFData.TREE_DATA);
        tickers.add(Blocks.FARMLAND.builtInRegistryHolder(), new SoilTicker(HFFarming.Fertilizers.NONE.get(), false), false);
        tickers.add(Blocks.PUMPKIN.builtInRegistryHolder(), new QualityRetainingBlock(HFFarming.QualityLevels.NORMAL.get()), false);
        tickers.add(Blocks.MELON.builtInRegistryHolder(), new QualityRetainingBlock(HFFarming.QualityLevels.NORMAL.get()), false);
        tickers.add(Blocks.SHORT_GRASS.builtInRegistryHolder(), new SpreadableTicker(false, 0), false);
        tickers.add(Blocks.TALL_GRASS.builtInRegistryHolder(), new SpreadableTicker(false, 0), false);
        tickers.add(Blocks.VINE.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.SUGAR_CANE.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.CACTUS.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.BAMBOO.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.COCOA.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.WEEPING_VINES.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.CHORUS_PLANT.builtInRegistryHolder(), new GrowthTicker(), false);
        tickers.add(Blocks.SWEET_BERRY_BUSH.builtInRegistryHolder(), new GrowthTicker(), false);

        cropData.add(Blocks.BEETROOTS.builtInRegistryHolder(), new CropData(HFDailyTickTypes.FOUR_DAYS), false);
        cropData.add(Blocks.CARROTS.builtInRegistryHolder(), new CropData(HFDailyTickTypes.EIGHT_DAYS), false);
        cropData.add(Blocks.POTATOES.builtInRegistryHolder(), new CropData(HFDailyTickTypes.EIGHT_DAYS), false);
        cropData.add(Blocks.WHEAT.builtInRegistryHolder(), new CropData(HFDailyTickTypes.WHEAT), false);
        cropData.add(Blocks.MELON_STEM.builtInRegistryHolder(), new CropData(HFDailyTickTypes.MELON), false);
        cropData.add(Blocks.PUMPKIN_STEM.builtInRegistryHolder(), new CropData(HFDailyTickTypes.PUMPKIN), false);

        addCrop(tickers, cropData, Blocks.BEETROOTS, HFDailyTickTypes.FOUR_DAYS, true);
        addCrop(tickers, cropData, Blocks.CARROTS, HFDailyTickTypes.EIGHT_DAYS, true);
        addCrop(tickers, cropData, Blocks.POTATOES, HFDailyTickTypes.EIGHT_DAYS, true);
        addCrop(tickers, cropData, Blocks.WHEAT, HFDailyTickTypes.WHEAT, false);
        addCrop(tickers, cropData, Blocks.MELON_STEM, HFDailyTickTypes.MELON, true);
        addCrop(tickers, cropData, Blocks.PUMPKIN_STEM, HFDailyTickTypes.PUMPKIN, true);
        addCrop(tickers, cropData, Blocks.NETHER_WART, HFDailyTickTypes.FOUR_DAYS, false);
        addCrop(tickers, cropData, Blocks.TORCHFLOWER_CROP, HFDailyTickTypes.FOUR_DAYS, false);

        //Horticulture Crops
        addCrop(tickers, cropData, HorticultureBlocks.TURNIPS.get(), HFDailyTickTypes.TEXTURE_3_DAYS_5, true);
        addCrop(tickers, cropData, HorticultureBlocks.CUCUMBERS.get(), HFDailyTickTypes.TEXTURE_4_DAYS_10, true);
        addCrop(tickers, cropData, HorticultureBlocks.STRAWBERRIES.get(), HFDailyTickTypes.TEXTURE_4_DAYS_9, true);
        addCrop(tickers, cropData, HorticultureBlocks.CABBAGES.get(), HFDailyTickTypes.CABBAGE, true);
        addCrop(tickers, cropData, HorticultureBlocks.ONIONS.get(), HFDailyTickTypes.ONION, true);
        addCrop(tickers, cropData, HorticultureBlocks.TOMATOES.get(), HFDailyTickTypes.TOMATO, true);
        addCrop(tickers, cropData, HorticultureBlocks.CORN.get(), HFDailyTickTypes.CORN, true);
        addCrop(tickers, cropData, HorticultureBlocks.PINEAPPLES.get(), HFDailyTickTypes.PINEAPPLE, true);
        addCrop(tickers, cropData, HorticultureBlocks.SPINACH.get(), HFDailyTickTypes.SPINACH, true);
        addCrop(tickers, cropData, HorticultureBlocks.EGGPLANTS.get(), HFDailyTickTypes.TEXTURE_4_DAYS_10, true);
        addCrop(tickers, cropData, HorticultureBlocks.SWEET_POTATOES.get(), HFDailyTickTypes.SWEET_POTATO, true);
        addCrop(tickers, cropData, HorticultureBlocks.GREEN_PEPPERS.get(), HFDailyTickTypes.GREEN_PEPPER, true);

        addTree(tickers, treeData, HFBlocks.OAK_SEEDLING.get(), Blocks.OAK_SAPLING, HFBlocks.OAK_JUVENILE.get(), 5, 5, 10);
        addTree(tickers, treeData, HFBlocks.BIRCH_SEEDLING.get(), Blocks.BIRCH_SAPLING, HFBlocks.BIRCH_JUVENILE.get(), 5, 10, 15);
        addTree(tickers, treeData, HFBlocks.SPRUCE_SEEDLING.get(), Blocks.SPRUCE_SAPLING, HFBlocks.SPRUCE_JUVENILE.get(), 5, 5, 10);
        addTree(tickers, treeData, HFBlocks.JUNGLE_SEEDLING.get(), Blocks.JUNGLE_SAPLING, HFBlocks.JUNGLE_JUVENILE.get(), 3, 5, 7);
        addTree(tickers, treeData, HFBlocks.ACACIA_SEEDLING.get(), Blocks.ACACIA_SAPLING, HFBlocks.ACACIA_JUVENILE.get(), 2, 3, 5);
        addTree(tickers, treeData, HFBlocks.DARK_OAK_SEEDLING.get(), Blocks.DARK_OAK_SAPLING, HFBlocks.DARK_OAK_JUVENILE.get(), 5, 5, 10);
        addTree(tickers, treeData, HFBlocks.CHERRY_SEEDLING.get(), Blocks.CHERRY_SAPLING, HFBlocks.CHERRY_JUVENILE.get(), 11, 15, 18);
        addTree(tickers, treeData, HFBlocks.MANGROVE_SEEDLING.get(), Blocks.MANGROVE_PROPAGULE, HFBlocks.MANGROVE_JUVENILE.get(), 3, 6, 6);
    }

    @SuppressWarnings("deprecation")
    private void addCrop(Builder<DailyTicker<?>, Block> tickers, Builder<CropData, Block> cropData, Block block, ResourceLocation handler, boolean water) {
        tickers.add(block.builtInRegistryHolder(), new CropTicker(HFFarming.Fertilizers.NONE.get(), HFFarming.QualityLevels.NORMAL.get(), 0), false);
        cropData.add(block.builtInRegistryHolder(), new CropData(handler, null, water), false);
    }

    @SuppressWarnings("deprecation")
    private void addTree(Builder<DailyTicker<?>, Block> tickers, Builder<TreeData, Block> treeData, Block seedling, Block sapling, Block juvenile, int seedlingDays, int saplingDays, int juvenileDays) {
        tickers.add(seedling.builtInRegistryHolder(), new SeedlingTicker(0), false);
        tickers.add(sapling.builtInRegistryHolder(), new SaplingTicker(0), false);
        tickers.add(juvenile.builtInRegistryHolder(), new JuvenileTicker(0), false);
        treeData.add(seedling.builtInRegistryHolder(), new TreeData(sapling.defaultBlockState(), seedlingDays), false);
        treeData.add(sapling.builtInRegistryHolder(), new TreeData(juvenile.defaultBlockState(), saplingDays), false);
        treeData.add(juvenile.builtInRegistryHolder(), new TreeData(sapling.defaultBlockState().setValue(SaplingBlock.STAGE, 1), juvenileDays), false);
    }
}