package uk.joshiejack.harvestfestival.data.economy;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.shopaholic.Shopaholic;
import uk.joshiejack.shopaholic.world.shop.Shop;
import uk.joshiejack.shopaholic.world.shop.ShopLoader;

import java.util.Map;

public class HFShops extends AbstractPenguinRegistryProvider<Shop> {
    public static final ResourceLocation ANIMAL_RANCH = HarvestFestival.prefix("animal_ranch");
    public static final ResourceLocation BAKED_GOODS = HarvestFestival.prefix("baked_goods");
    public static final ResourceLocation BLACKSMITH = HarvestFestival.prefix("blacksmith");
    public static final ResourceLocation CAFE = HarvestFestival.prefix("cafe");
    public static final ResourceLocation CARPENTER = HarvestFestival.prefix("carpenter");
    public static final ResourceLocation CLINIC = HarvestFestival.prefix("clinic");
    public static final ResourceLocation ENGINEERS_WORKSHOP = HarvestFestival.prefix("engineers_workshop");
    public static final ResourceLocation FLOWER_MARKET = HarvestFestival.prefix("flower_market");
    public static final ResourceLocation GENERAL_STORE = HarvestFestival.prefix("general_store");
    public static final ResourceLocation MARKET_STALL = HarvestFestival.prefix("market_stall");
    public static final ResourceLocation PET_SHOP = HarvestFestival.prefix("pet_shop");
    public static final ResourceLocation POULTRY_FARM = HarvestFestival.prefix("poultry_farm");
    public static final ResourceLocation TACKLE_SHOP = HarvestFestival.prefix("tackle_shop");
    public static final ResourceLocation WITCHING_WARES = HarvestFestival.prefix("witching_wares");

    public HFShops(PackOutput output) {
        super(output, Shopaholic.ShopaholicRegistries.SHOPS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, Shop> map) {
        register(map, ANIMAL_RANCH);
        register(map, BAKED_GOODS);
        register(map, BLACKSMITH);
        register(map, CAFE);
        register(map, CARPENTER);
        register(map, CLINIC);
        register(map, ENGINEERS_WORKSHOP);
        register(map, FLOWER_MARKET);
        register(map, GENERAL_STORE);
        register(map, MARKET_STALL);
        register(map, PET_SHOP);
        register(map, POULTRY_FARM);
        register(map, TACKLE_SHOP);
        register(map, WITCHING_WARES);
    }

    private static void register(Map<ResourceLocation, Shop> map, ResourceLocation id) {
        map.put(id, new Shop(Component.translatable("%s.shop.%s".formatted(id.getNamespace(), id.getPath())), ShopLoader.DEFAULT_BACKGROUND, ShopLoader.EXTRA));
    }
}
