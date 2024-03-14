package uk.joshiejack.harvestfestival.data.farming;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.SeedData;
import uk.joshiejack.horticulture.world.block.HorticultureBlocks;
import uk.joshiejack.horticulture.world.item.HorticultureItems;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;

import java.util.Map;

public class HFSeedProvider extends AbstractPenguinRegistryProvider<SeedData> {
    public HFSeedProvider(PackOutput output) {
        super(output, HFRegistries.SEEDS);
    }

    @Override
    public @NotNull String getName() {
        return "Seeds";
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, SeedData> map) {
        map.put(HarvestFestival.prefix("potato"), SeedData.of(Component.translatable("item.harvestfestival.potato_seeds"), new ItemStack(Items.POTATO), Blocks.POTATOES, "#BE8D2B"));
        map.put(HarvestFestival.prefix("carrot"), SeedData.of(Component.translatable("item.harvestfestival.carrot_seeds"), new ItemStack(Items.CARROT), Blocks.CARROTS, "#F8AC33"));
        map.put(HarvestFestival.prefix("wheat"), SeedData.of(new ItemStack(Items.WHEAT_SEEDS), Blocks.WHEAT, "#EAC715"));
        map.put(HarvestFestival.prefix("beetroot"), SeedData.of(new ItemStack(Items.BEETROOT_SEEDS), Blocks.BEETROOTS, "#690000"));
        map.put(HarvestFestival.prefix("melon"), SeedData.of(new ItemStack(Items.MELON_SEEDS), Blocks.MELON_STEM, "#C92B3E"));
        map.put(HarvestFestival.prefix("pumpkin"), SeedData.of(new ItemStack(Items.PUMPKIN_SEEDS), Blocks.PUMPKIN_STEM, "#E09A39"));
        map.put(HarvestFestival.prefix("nether_wart"), SeedData.of(new ItemStack(Items.NETHER_WART), Blocks.NETHER_WART, "#8B0000"));
        map.put(HarvestFestival.prefix("torch_flower"), SeedData.of(new ItemStack(Items.TORCHFLOWER_SEEDS), Blocks.TORCHFLOWER_CROP, "#FFD800"));

        //Horticulture Seeds
        map.put(HarvestFestival.prefix("turnip"), SeedData.of(HorticultureItems.TURNIP_SEEDS.toStack(), HorticultureBlocks.TURNIPS.get(), "#FFFFFF"));
        map.put(HarvestFestival.prefix("cucumber"), SeedData.of(HorticultureItems.CUCUMBER_SEEDS.toStack(), HorticultureBlocks.CUCUMBERS.get(), "#36B313"));
        map.put(HarvestFestival.prefix("strawberry"), SeedData.of(HorticultureItems.STRAWBERRY_SEEDS.toStack(), HorticultureBlocks.STRAWBERRIES.get(), "#FF7BEA"));
        map.put(HarvestFestival.prefix("cabbage"), SeedData.of(HorticultureItems.CABBAGE_SEEDS.toStack(), HorticultureBlocks.CABBAGES.get(), "#8FFF40"));
        map.put(HarvestFestival.prefix("onion"), SeedData.of(HorticultureItems.ONION_SEEDS.toStack(), HorticultureBlocks.ONIONS.get(), "#DCC307"));
        map.put(HarvestFestival.prefix("tomato"), SeedData.of(HorticultureItems.TOMATO_SEEDS.toStack(), HorticultureBlocks.TOMATOES.get(), "#E60820"));
        map.put(HarvestFestival.prefix("corn"), SeedData.of(HorticultureItems.CORN_SEEDS.toStack(), HorticultureBlocks.CORN.get(), "#D4BD45"));
        map.put(HarvestFestival.prefix("spinach"), SeedData.of(HorticultureItems.SPINACH_SEEDS.toStack(), HorticultureBlocks.SPINACH.get(), "#90AE15"));
        map.put(HarvestFestival.prefix("eggplant"), SeedData.of(HorticultureItems.EGGPLANT_SEEDS.toStack(), HorticultureBlocks.EGGPLANTS.get(), "#A25CC4"));
        map.put(HarvestFestival.prefix("sweet_potato"), SeedData.of(HorticultureItems.SWEET_POTATO_SEEDS.toStack(), HorticultureBlocks.SWEET_POTATOES.get(), "#D82AAC"));
        map.put(HarvestFestival.prefix("green_pepper"), SeedData.of(HorticultureItems.GREEN_PEPPER_SEEDS.toStack(), HorticultureBlocks.GREEN_PEPPERS.get(), "#56D213"));
    }
}
