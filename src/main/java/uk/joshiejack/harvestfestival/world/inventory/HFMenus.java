package uk.joshiejack.harvestfestival.world.inventory;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;

public class HFMenus {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, HarvestFestival.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<HFBookMenu>> BOOK = CONTAINERS.register("mob_tracker", () -> IMenuTypeExtension.create((id, inv, data) -> new HFBookMenu(id)));
}
