package uk.joshiejack.harvestfestival.world.effect;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyUse;
import uk.joshiejack.penguinlib.world.potion.IncurableEffect;
import uk.joshiejack.penguinlib.world.potion.IncurableExpiringEffect;
import uk.joshiejack.penguinlib.world.potion.PenguinEffect;

import java.util.function.Consumer;

public class HFEffects {
    private static final Consumer<ServerPlayer> UPDATE_FATIGUE = player -> EnergyUse.updateFatigue(player, true);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, HarvestFestival.MODID);
    public static final DeferredHolder<MobEffect, MobEffect> TIRED = EFFECTS.register("tired", () -> new PenguinEffect(MobEffectCategory.NEUTRAL, 0x666666)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "14A10B84-0FE6-4ED3-AB73-E8D1001E41E3", -0.2, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "7B08B09B-CBDB-4188-9DF2-5F459A0F1B54", -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> FATIGUED = EFFECTS.register("fatigued", () -> new IncurableExpiringEffect(MobEffectCategory.NEUTRAL, 0xD9D900, UPDATE_FATIGUE)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "CF71B5F5-172F-45C1-A5AD-E7102734319E", -0.1, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "076C5F29-43EA-4C6B-A30E-1F4316ECFA54", -0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> EXHAUSTED = EFFECTS.register("exhausted", () -> new IncurableEffect(MobEffectCategory.NEUTRAL, 0xD9D900)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "9DA5E6D1-5B23-4270-B66A-1F8511B34FF4", -0.5, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "ACCC3F91-F5C0-4B9E-83AC-ADBEE6639702", -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> BUZZED = EFFECTS.register("buzzed", () -> new IncurableExpiringEffect(MobEffectCategory.BENEFICIAL, 0xA5A29C, UPDATE_FATIGUE)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "6BB34E5D-1312-411E-A45C-EA289BEE3D23", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "2A4EE23F-FC15-4DD6-A841-1EFCC7420CAC", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> CURSED = EFFECTS.register("cursed", () -> new IncurableEffect(MobEffectCategory.HARMFUL, 0x4C4C4C)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "9DA5E6D2-5C23-4270-C66A-1A8511B44FF5", -0.33, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "9DA5E6D2-5B23-4270-B66A-1F8511B44FF4", -0.33, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "ACCC3F91-C5C0-4B9E-83AC-ADBFD6639702", -0.33, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> FARMING = EFFECTS.register("farming", () -> new PenguinEffect(MobEffectCategory.BENEFICIAL, 0x32A848));
    public static final DeferredHolder<MobEffect, MobEffect> FISHING = EFFECTS.register("fishing", () -> new PenguinEffect(MobEffectCategory.BENEFICIAL, 0x326FA8));
    public static final DeferredHolder<MobEffect, MobEffect> MINING = EFFECTS.register("mining", () -> new PenguinEffect(MobEffectCategory.BENEFICIAL, 0x7A8187));
    public static final DeferredHolder<MobEffect, MobEffect> GATHERING = EFFECTS.register("gathering", () -> new PenguinEffect(MobEffectCategory.BENEFICIAL, 0x032604));
    public static final DeferredHolder<MobEffect, MobEffect> COMBAT = EFFECTS.register("combat", () -> new PenguinEffect(MobEffectCategory.BENEFICIAL, 0x913748)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "50660B40-96A1-4155-954D-5B495D2ED107", 1.5, AttributeModifier.Operation.ADDITION));
}
