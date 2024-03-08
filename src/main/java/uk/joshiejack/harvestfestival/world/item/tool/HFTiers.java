package uk.joshiejack.harvestfestival.world.item.tool;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFTags;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum HFTiers implements Tier {
    STONE(1, 128, 4F, 1F, 0, () -> Ingredient.of(Tags.Items.STONE)),
    COPPER(2, 256, 5F, 1.5F, 0, () -> Ingredient.of(Tags.Items.INGOTS_COPPER)),
    SILVER(3, 512, 6F, 2.5F, 0, () -> Ingredient.of(HFTags.Items.INGOTS_SILVER)),
    GOLD(4, 1024, 8F, 3F, 0, () -> Ingredient.of(Tags.Items.INGOTS_GOLD)),
    MYSTRIL(5, 2048, 9F, 4F, 0, () -> Ingredient.of(HFTags.Items.INGOTS_MYSTRIL)),
    CURSED(6, 4096, 15F, 11F, 0, () -> Ingredient.EMPTY),
    BLESSED(6, 4096, 15F, 11F, 0, () -> Ingredient.EMPTY),
    MYTHIC(7, 8192, 30F, 21F, 0, () -> Ingredient.of(HFTags.Items.MYTHIC_STONE));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    HFTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
