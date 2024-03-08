package uk.joshiejack.harvestfestival.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

public class EatFoodEvent extends LivingEvent {
    private final ItemStack stack;
    private final int nutrition;
    private final float saturation;
    private int newNutrition;
    private float newSaturation;

    public EatFoodEvent(@Nullable LivingEntity entity, ItemStack stack, int nutritionAmount, float saturationModifier) {
        super(entity);
        this.stack = stack;
        this.nutrition = nutritionAmount;
        this.newNutrition = nutritionAmount;
        this.saturation = saturationModifier;
        this.newSaturation = saturationModifier;
    }

    @Nullable
    @Override
    public LivingEntity getEntity() {
        return super.getEntity();
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getNutrition() {
        return nutrition;
    }

    public float getSaturation() {
        return saturation;
    }

    public int getNewNutrition() {
        return newNutrition;
    }

    public float getNewSaturation() {
        return newSaturation;
    }

    public void setNewNutrition(int newHealth) {
        this.newNutrition = newHealth;
    }

    public void setNewSaturation(float newSaturation) {
        this.newSaturation = newSaturation;
    }
}

