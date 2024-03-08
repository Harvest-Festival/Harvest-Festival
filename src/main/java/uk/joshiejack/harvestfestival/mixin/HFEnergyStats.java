package uk.joshiejack.harvestfestival.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;

@Mixin(Player.class)
public class HFEnergyStats {
    @Shadow
    public FoodData foodData = new EnergyData((Player)(Object)this);
}
