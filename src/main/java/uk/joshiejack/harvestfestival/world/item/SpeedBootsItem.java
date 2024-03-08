package uk.joshiejack.harvestfestival.world.item;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.item.tool.HFTiers;

import java.util.UUID;

public class SpeedBootsItem extends ArmorItem {
    private final boolean isCursed;

    public SpeedBootsItem(String name, Properties pProperties, int enchant, double speed) {
        super(Armor.create(name, enchant), Type.BOOTS, pProperties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(Type.BOOTS);
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", getMaterial().getDefenseForType(Type.BOOTS), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", getToughness(), AttributeModifier.Operation.ADDITION));
        if (knockbackResistance > 0.0F) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("Speed modifier", speed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
        isCursed = speed < 0;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return isCursed ? !pStack.isEnchanted() : super.isFoil(pStack);
    }
    
    public record Armor(String name, int durability, int defense, int enchantment, SoundEvent sound, float toughness, float knockback) implements ArmorMaterial {

        public static Armor create(String name, int enchantment) {
            return new Armor(name, HFTiers.BLESSED.getEnchantmentValue(), 5, enchantment, SoundEvents.ARMOR_EQUIP_GENERIC, 3, 0);
        }

        @Override
        public int getDurabilityForType(@NotNull Type type) {
            return durability;
        }

        @Override
        public int getDefenseForType(@NotNull Type type) {
            return defense;
        }

        @Override
        public int getEnchantmentValue() {
            return enchantment;
        }

        @Override
        public @NotNull SoundEvent getEquipSound() {
            return sound;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(Items.AIR);
        }

        @Override
        public @NotNull String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockback;
        }
    }
}
