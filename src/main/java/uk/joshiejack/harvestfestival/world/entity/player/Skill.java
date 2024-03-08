package uk.joshiejack.harvestfestival.world.entity.player;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.event.SkillLevelEvent;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;
import uk.joshiejack.penguinlib.util.helper.TagHelper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public record Skill(String name, TagKey<Item> effectiveItems, @Nullable TagKey<Block> effectiveBlocks,
                    @Nullable TagKey<EntityType<?>> effectiveEntities, DeferredHolder<MobEffect, MobEffect> effect,
                    BiConsumer<Player, Integer> onLevelUp) {
    private static final BiConsumer<Player, Integer> DO_NOTHING = (player, level) -> {};
    private static final int MAX_EXPERIENCE = 65535; //10 Levels
    private static final int EXP_PER_LEVEL = MAX_EXPERIENCE / 10; //10 Levels
    public static final Map<String, Skill> SKILLS = new Object2ObjectOpenHashMap<>();
    public static final Skill FARMING = new Skill("farming", HFTags.Items.FARMING_TOOLS, HFTags.Blocks.FARMING_SKILL_BONUS, null, HFEffects.FARMING, DO_NOTHING);
    public static final Skill MINING = new Skill("mining", HFTags.Items.MINING_TOOLS, HFTags.Blocks.MINING_SKILL_BONUS, null, HFEffects.MINING, DO_NOTHING);
    public static final Skill FISHING = new Skill("fishing", HFTags.Items.FISHING_TOOLS, null, null, HFEffects.FISHING, DO_NOTHING);
    public static final Skill GATHERING = new Skill("gathering", HFTags.Items.GATHERING_TOOLS, HFTags.Blocks.GATHERING_SKILL_BONUS, null, HFEffects.GATHERING, DO_NOTHING);
    public static final Skill COMBAT = new Skill("combat", HFTags.Items.COMBAT_TOOLS, null, HFTags.Blocks.COMBAT_SKILL_BONUS, HFEffects.COMBAT, (player, level) -> {
        //Award the player with a heart every level except 5 and 10
        if (level != 5 && level != 10 && player.getFoodData() instanceof EnergyData data) {
            data.increaseMaxHealth();
        }
    });

    public Skill(String name, TagKey<Item> effectiveItems, TagKey<Block> effectiveBlocks, TagKey<EntityType<?>> effectiveEntities, DeferredHolder<MobEffect, MobEffect> effect, BiConsumer<Player, Integer> onLevelUp) {
        this.name = name;
        this.effectiveItems = effectiveItems;
        this.effectiveBlocks = effectiveBlocks;
        this.effectiveEntities = effectiveEntities;
        this.effect = effect;
        this.onLevelUp = onLevelUp;
        SKILLS.put(name, this);
    }

    private static CompoundTag getHFTag(Player player) {
        return TagHelper.getOrCreateTag(player.getPersistentData(), HarvestFestival.MODID);
    }

    private static CompoundTag getOrCreateSkills(Player player) {
        return TagHelper.getOrCreateTag(getHFTag(player), "Skills");
    }

    public static boolean hasProfession(Player player, Profession profession) {
        return TagHelper.getOrCreateTag(getHFTag(player), "Professions").contains(profession.name());
    }

    public void addExperience(Player player, int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Can't remove experience!");
        CompoundTag tag = getOrCreateSkills(player);
        int oldLevel = getLevel(player, this, LevelType.RAW);
        int experience = tag.getInt(name) + amount;
        if (experience > MAX_EXPERIENCE) experience = MAX_EXPERIENCE;
        tag.putInt(name, experience);
        int newLevel = getLevel(player, this, LevelType.RAW);
        if (newLevel > oldLevel)
            onLevelUp.accept(player, newLevel);
    }

    public static int getExperience(Player player, Skill skill) {
        return getOrCreateSkills(player).getInt(skill.name().toLowerCase());
    }

    /**
     * Returns the level of the skill. Posts to the SkillLevelEvent.Get event
     * so that any mods can modify the level before it is returned.
     *
     * @param player the player
     * @param skill  the skill
     * @param type   the type of level to return
     * @return the level
     */
    public static int getLevel(Player player, Skill skill, LevelType type) {
        int rawLevel = Mth.floor((float) getExperience(player, skill) / EXP_PER_LEVEL);
        //Boost the skill level of this player if they have the relevant effect
        if (type == LevelType.EFFECTIVE) {
            int level = rawLevel;
            if (player.hasEffect(skill.effect.get())) {
                level += (Objects.requireNonNull(player.getEffect(skill.effect.get())).getAmplifier() + 1);
            }

            SkillLevelEvent.Get event = new SkillLevelEvent.Get(player, skill, rawLevel, level);
            NeoForge.EVENT_BUS.post(event);
            return event.getEffectiveLevel();
        } else return rawLevel;
    }

    public enum LevelType {
        RAW, EFFECTIVE
    }
}
