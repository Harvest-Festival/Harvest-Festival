package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;

public class PowerBerryItem extends Item {
    public PowerBerryItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (entity instanceof ServerPlayer player && player.getFoodData() instanceof EnergyData data) {
            data.increaseMaxEnergy();
            data.setEnergyLevel(data.getMaxEnergy());
        }

        return super.finishUsingItem(stack, level, entity);
    }
}