package uk.joshiejack.harvestfestival.world.loot.global;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;

public record ApplySkillGlobal(Skill skill) implements IGlobalLootModifier {
    public static final Codec<ApplySkillGlobal> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("skill").forGetter(p_299003_ -> p_299003_.skill.name())
    ).apply(instance, (s) -> new ApplySkillGlobal(Skill.SKILLS.get(s))));

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (context.hasParam(LootContextParams.KILLER_ENTITY)) {
            Entity entity = context.getParam(LootContextParams.KILLER_ENTITY);
            if (entity instanceof Player player) {
                if (context.hasParam(LootContextParams.BLOCK_STATE) && skill.effectiveBlocks() != null
                        && context.getParam(LootContextParams.BLOCK_STATE).is(skill.effectiveBlocks())) {
                    int skillLevel = Skill.getLevel(player, skill, Skill.LevelType.EFFECTIVE);
                    generatedLoot.forEach(stack -> stack.setCount(stack.getCount() + context.getRandom().nextInt(skillLevel / 4)));
                } else if (context.hasParam(LootContextParams.THIS_ENTITY) && skill.effectiveEntities() != null
                        && context.getParam(LootContextParams.THIS_ENTITY).getType().is(skill.effectiveEntities())) {
                    int skillLevel = Skill.getLevel(player, skill, Skill.LevelType.EFFECTIVE);
                    generatedLoot.forEach(stack -> stack.setCount(stack.getCount() + context.getRandom().nextInt(skillLevel / 4)));
                }
            }
        }

        return generatedLoot;
    }
}
