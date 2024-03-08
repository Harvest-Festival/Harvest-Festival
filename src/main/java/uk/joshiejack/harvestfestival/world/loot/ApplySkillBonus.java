package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;

import java.util.List;

public class ApplySkillBonus extends LootItemConditionalFunction {
    public static final Codec<ApplySkillBonus> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(ExtraCodecs.strictOptionalField(LootItemConditions.CODEC.listOf(), "conditions", List.of()).forGetter(p_299114_ -> p_299114_.predicates),
                            Codec.STRING.fieldOf("skill").forGetter(p_299003_ -> p_299003_.skill.name()))
                    .apply(instance, (cond, v1) -> new ApplySkillBonus(cond, Skill.SKILLS.get(v1)))
    );

    private final Skill skill;

    public ApplySkillBonus(List<LootItemCondition> conditions, Skill skill) {
        super(conditions);
        this.skill = skill;
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, @NotNull LootContext context) {
        Entity entity = context.getParam(LootContextParams.KILLER_ENTITY);
        if (entity instanceof Player player) {
            int skillLevel = Skill.getLevel(player, skill, Skill.LevelType.EFFECTIVE);
            stack.setCount(stack.getCount() + context.getRandom().nextInt(skillLevel / 4));
        }

        return stack;
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return HFLoot.APPLY_MINING_SKILL.get();
    }

    public static ApplySkillBonus.Builder applySkill(Skill skill) {
        return new ApplySkillBonus.Builder(skill);
    }

    public static class Builder extends LootItemConditionalFunction.Builder<ApplySkillBonus.Builder> {
        private final Skill skill;

        public Builder(Skill skill) {
            this.skill = skill;
        }

        protected ApplySkillBonus.@NotNull Builder getThis() {
            return this;
        }

        @Override
        public @NotNull LootItemFunction build() {
            return new ApplySkillBonus(this.getConditions(), this.skill);
        }
    }
}
