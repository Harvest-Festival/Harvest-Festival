package uk.joshiejack.harvestfestival.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;
import uk.joshiejack.harvestfestival.world.loot.global.ApplyQualityModifier;
import uk.joshiejack.harvestfestival.world.loot.global.ApplySkillGlobal;
import uk.joshiejack.harvestfestival.world.loot.global.DisableDropModifier;
import uk.joshiejack.harvestfestival.world.loot.global.DisableDropTagModifier;

public class HFLootModifier extends GlobalLootModifierProvider {
    public HFLootModifier(PackOutput output) {
        super(output, HarvestFestival.MODID);
    }

    @Override
    protected void start() {
        add("disable_apples", new DisableDropModifier(DisableDropModifier.blocks(Blocks.OAK_LEAVES), DisableDropModifier.items(Items.APPLE)));
        add("disable_seeds", DisableDropTagModifier.of(Tags.Items.SEEDS));
        add("apply_mining_skill", new ApplySkillGlobal(Skill.MINING));
        add("apply_farming_skill", new ApplySkillGlobal(Skill.FARMING));
        add("apply_gathering_skill", new ApplySkillGlobal(Skill.GATHERING));
        add("apply_combat_skill", new ApplySkillGlobal(Skill.COMBAT));
        add("apply_quality", ApplyQualityModifier.of());
    }
}
