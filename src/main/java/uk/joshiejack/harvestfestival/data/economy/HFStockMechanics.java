package uk.joshiejack.harvestfestival.data.economy;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.shopaholic.Shopaholic;
import uk.joshiejack.shopaholic.world.shop.inventory.StockMechanic;

import java.util.Map;

public class HFStockMechanics extends AbstractPenguinRegistryProvider<StockMechanic> {
    public static final ResourceLocation LIMITED_THREE = new ResourceLocation("harvestfestival", "limited_three");
    public static final ResourceLocation LIMITED_FOUR = new ResourceLocation("harvestfestival", "limited_four");
    public static final ResourceLocation LIMITED_FIVE = new ResourceLocation("harvestfestival", "limited_five");
    public HFStockMechanics(PackOutput output) {
        super(output, Shopaholic.ShopaholicRegistries.STOCK_MECHANICS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, StockMechanic> map) {
        map.put(LIMITED_THREE, new StockMechanic(3, 3));
        map.put(LIMITED_FOUR, new StockMechanic(4, 4));
        map.put(LIMITED_FIVE, new StockMechanic(5, 5));
    }
}
