package uk.joshiejack.harvestfestival.data.economy;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.settlements.world.entity.SettlementsEntities;
import uk.joshiejack.shopaholic.Shopaholic;
import uk.joshiejack.shopaholic.data.shop.ShopBuilder;
import uk.joshiejack.shopaholic.data.shop.Vendor;
import uk.joshiejack.shopaholic.world.shop.Department;

import java.util.Map;

public class HFDepartments extends AbstractPenguinRegistryProvider<Department> {
    public HFDepartments(PackOutput output) {
        super(output, Shopaholic.ShopaholicRegistries.DEPARTMENTS);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, Department> map) {
        //Register the shops
        buildAnimalRanch(map);
        buildBakedGoods(map);
        buildBlacksmith(map);
        buildCafe(map);
        buildCarpenter(map);
        buildClinic(map);
        buildEngineersWorkshop(map);
        buildFlowerMarket(map);
        buildGeneralStore(map);
        buildMarketStall(map);
        buildPetShop(map);
        buildPoultryFarm(map);
        buildTackleShop(map);
        buildWitchingWares(map);
    }

    private void buildAnimalRanch(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.ANIMAL_RANCH)
                .vendor(Vendor.entity(SettlementsEntities.NPC.get()))
                .save(map);
    }

    private void buildBakedGoods(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.BAKED_GOODS)

                .save(map);
    }

    private void buildBlacksmith(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.BLACKSMITH)

                .save(map);
    }

    private void buildCafe(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.CAFE)

                .save(map);
    }

    private void buildCarpenter(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.CARPENTER)

                .save(map);
    }

    private void buildClinic(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.CLINIC)

                .save(map);
    }

    private void buildEngineersWorkshop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.ENGINEERS_WORKSHOP)

                .save(map);
    }

    private void buildFlowerMarket(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.FLOWER_MARKET)

                .save(map);
    }

    private void buildGeneralStore(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.GENERAL_STORE)

                .save(map);
    }

    private void buildMarketStall(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.MARKET_STALL)

                .save(map);
    }

    private void buildPetShop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.PET_SHOP)

                .save(map);
    }

    private void buildPoultryFarm(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.POULTRY_FARM)

                .save(map);
    }

    private void buildTackleShop(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.TACKLE_SHOP)

                .save(map);
    }

    private void buildWitchingWares(Map<ResourceLocation, Department> map) {
        ShopBuilder.of(HFShops.WITCHING_WARES)

                .save(map);
    }
}
