package uk.joshiejack.harvestfestival.world.entity.player.energy;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.event.EatFoodEvent;
import uk.joshiejack.harvestfestival.network.SyncEnergyLevel;
import uk.joshiejack.harvestfestival.network.SyncEnergyStats;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EnergyData extends FoodData {
    public static final int ENERGY_PER_HUNGER = 64;
    public static final int FOOD_LEVEL_TO_ENERGY = 8;
    public static final int MAX_OVERALL = ENERGY_PER_HUNGER * 20;
    private double energyMaximum = ENERGY_PER_HUNGER * HFConfig.startingEnergy.get();
    private double energyLevel = ENERGY_PER_HUNGER * HFConfig.startingEnergy.get();
    public double maxHearts = HFConfig.startingHealth.get();
    private Player player;

    public EnergyData(Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getEnergyLevel() {
        return (int) energyLevel;
    }

    @Override
    public int getFoodLevel() { //Scale it back to 20, for other pruposed
        return (int) Math.min(20, (double) energyLevel / (double) energyMaximum * 20);
    }

    public void useEnergy(double energy) {
        energyLevel = Math.max(0, energyLevel - energy);
        if (player instanceof ServerPlayer sp) {
            EnergyUse.updateEffectInstances(sp);
            PenguinNetwork.sendToClient(sp, new SyncEnergyLevel(energyLevel));
        }
    }

    @Override
    public float getSaturationLevel() {
        return getFoodLevel();
    }

    public int getMaxEnergy() {
        return (int) energyMaximum;
    }

    public int getMaxEnergyAsFood() {
        return (int) (((double) energyMaximum / (double) MAX_OVERALL) * 10);
    }

    public void setMaxHealth(double maxHearts) {
        this.maxHearts = Math.max(1, Math.min(20, maxHearts));
        if (player instanceof ServerPlayer sp) {
            PenguinNetwork.sendToClient(sp, new SyncEnergyStats(this));
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(this.maxHearts);
        }
    }

    public boolean increaseMaxHealth() {
        if (maxHearts < 20) {
            setMaxHealth(maxHearts + 2D);
            return true;
        }

        return false;
    }

    public void setMaxEnergy(double level) {
        this.energyMaximum = Math.max(1, Math.min(MAX_OVERALL, level));
        if (player instanceof ServerPlayer sp) {
            PenguinNetwork.sendToClient(sp, new SyncEnergyStats(this));
        }
    }

    public boolean increaseMaxEnergy() {
        if (energyMaximum < MAX_OVERALL) {
            setMaxEnergy(energyMaximum + ENERGY_PER_HUNGER * 2);
            return true;
        }

        return false;
    }

    @Override
    public void eat(Item item, @NotNull ItemStack stack, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.LivingEntity entity) {
        if (item.isEdible()) {
            FoodProperties food = item.getFoodProperties(stack, player);
            EatFoodEvent event = new EatFoodEvent(player, stack, food.getNutrition(), food.getSaturationModifier());
            NeoForge.EVENT_BUS.post(event);
            eat(event.getNewNutrition(), event.getNewSaturation());
        }
    }

    @Override
    public void eat(int foodLevelIn, float foodSaturationModifier) {
        boolean healing = player.getHealth() < player.getMaxHealth();
        float health = player.getHealth();
        if (healing) player.heal(foodLevelIn * (1F + foodSaturationModifier));
        float newHealth = player.getHealth();

        float totalRestore = ((foodLevelIn * FOOD_LEVEL_TO_ENERGY) * foodSaturationModifier) - (newHealth - health);
        if (totalRestore > 0) {
            energyLevel = (int) Math.min(energyMaximum, energyLevel + totalRestore);
            if (player instanceof ServerPlayer sp)
                EnergyUse.updateFatigue(sp, false);
        }
    }

    public void set(double energyLevel, double energyMaximum, double maxHealth) {
        this.energyLevel = energyLevel;
        this.energyMaximum = Math.max(ENERGY_PER_HUNGER, energyMaximum);
        this.maxHearts = maxHealth;
    }

    public void setEnergyLevel(double energyLevel) {
        this.energyLevel = energyLevel;
        if (player instanceof ServerPlayer sp) {
            EnergyUse.updateEffectInstances(sp);
            PenguinNetwork.sendToClient(sp, new SyncEnergyLevel(energyLevel));
        }
    }

    @Override
    public void tick(@Nonnull Player player) {

    } //No natural loss

    @Override
    public void addExhaustion(float exhaustion) {
        if (HFConfig.energyFromExhaustion.get())
            useEnergy((int) (exhaustion * 5));
    }

    @Override
    public boolean needsFood() {
        return energyLevel < energyMaximum || player.getHealth() < maxHearts;
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("energyLevel", 99))
            energyLevel = compound.getDouble("energyLevel");
        if (compound.contains("energyMaximum"))
            energyMaximum = Math.max(ENERGY_PER_HUNGER, compound.getDouble("energyMaximum"));
        if (compound.contains("maxHearts"))
            maxHearts = compound.getDouble("maxHearts");
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putDouble("energyLevel", (short) energyLevel);
        compound.putDouble("energyMaximum", (short) energyMaximum);
        compound.putDouble("maxHearts", maxHearts);
    }
}
