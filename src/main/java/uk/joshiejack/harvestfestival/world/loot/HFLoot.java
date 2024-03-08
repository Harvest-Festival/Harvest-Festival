package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.loot.global.*;

public class HFLoot {
    //Deferred Register and holder to register the FLOOR_NUMBER_CHECK to the Registries.LOOT_CONDITION_TYPE
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, HarvestFestival.MODID);
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, HarvestFestival.MODID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, HarvestFestival.MODID);
    public static final Holder<LootItemConditionType> MINE_FLOOR_EXACT = LOOT_CONDITION_TYPES.register("mine_floor_exact", () -> new LootItemConditionType(FloorExactCondition.CODEC));
    public static final Holder<LootItemConditionType> MINE_FLOOR_BETWEEN = LOOT_CONDITION_TYPES.register("mine_floor_between", () -> new LootItemConditionType(FloorBetweenCondition.CODEC));
    public static final Holder<LootItemConditionType> MINE_FLOOR_BEFORE = LOOT_CONDITION_TYPES.register("mine_floor_before", () -> new LootItemConditionType(FloorBeforeCondition.CODEC));
    public static final Holder<LootItemConditionType> MINE_FLOOR_FROM = LOOT_CONDITION_TYPES.register("mine_floor_from", () -> new LootItemConditionType(FloorFromCondition.CODEC));
    public static final Holder<LootItemConditionType> MINE_FLOOR_MULTIPLE_OF = LOOT_CONDITION_TYPES.register("mine_floor_multiple_of", () -> new LootItemConditionType(FloorMultipleOfCondition.CODEC));
    public static final Holder<LootItemConditionType> MINE_FLOOR_BETWEEN_SCALED = LOOT_CONDITION_TYPES.register("mine_floor_between_scaled", () -> new LootItemConditionType(FloorBetweenScaledCondition.CODEC));
    public static final Holder<LootItemConditionType> OBTAINED_ITEM = LOOT_CONDITION_TYPES.register("obtained", () -> new LootItemConditionType(ObtainedItemCondition.CODEC));
    public static final DeferredHolder<LootItemFunctionType, LootItemFunctionType> APPLY_MINING_SKILL = LOOT_FUNCTION_TYPES.register("apply_mining_skill", () -> new LootItemFunctionType(ApplySkillBonus.CODEC));
    public static final DeferredHolder<LootItemFunctionType, LootItemFunctionType> APPLY_SET_QUALITY = LOOT_FUNCTION_TYPES.register("apply_set_quality", () -> new LootItemFunctionType(ApplySetQuality.CODEC));
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<DisableDropModifier>> DISABLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("disable_drops", () -> DisableDropModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<ApplyQualityModifier>> APPLY_QUALITY = GLOBAL_LOOT_MODIFIERS.register("apply_quality", () -> ApplyQualityModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<ApplySkillGlobal>> APPLY_SKILL_GLOBAL = GLOBAL_LOOT_MODIFIERS.register("apply_skill_global", () -> ApplySkillGlobal.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<DisableDropTagModifier>> DISABLE_DROPS_TAG = GLOBAL_LOOT_MODIFIERS.register("disable_drops_tag", () -> DisableDropTagModifier.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<RequireToolModifier>> REQUIRE_TOOL = GLOBAL_LOOT_MODIFIERS.register("require_tool", () -> RequireToolModifier.CODEC);
    public static void register(IEventBus eventBus) {
        LOOT_CONDITION_TYPES.register(eventBus);
        LOOT_FUNCTION_TYPES.register(eventBus);
        GLOBAL_LOOT_MODIFIERS.register(eventBus);
    }
}
