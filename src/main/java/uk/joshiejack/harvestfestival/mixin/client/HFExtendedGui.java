package uk.joshiejack.harvestfestival.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;

@Mixin(value= ExtendedGui.class, priority = 999)
public abstract class HFExtendedGui extends Gui {
    public HFExtendedGui(Minecraft mc, ItemRenderer ir) {
        super(mc, ir);
    }

    @SuppressWarnings("ConstantConditions")
    @ModifyConstant(method = "renderFood", constant = @Constant(intValue = 10, ordinal = 1))
    public int renderEnergy(int constant) {
        return minecraft.player.getFoodData() instanceof EnergyData ? ((EnergyData) minecraft.player.getFoodData()).getMaxEnergyAsFood() : 10;
    }
}