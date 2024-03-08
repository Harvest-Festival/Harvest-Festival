package uk.joshiejack.harvestfestival.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;

public abstract class SkillLevelEvent extends PlayerEvent {
    public SkillLevelEvent(Player player) {
        super(player);
    }


    public static class Get extends SkillLevelEvent {
        private final Skill skill;
        private final int rawLevel;
        private int effectiveLevel;

        /**
         * This event is fired when the effectiveBlocks level of a skill is requested.
         * It is fired whenever the effectiveBlocks level of a skill is requested.
         * The level can be modified by other mods.
         *
         * @param player    the player
         * @param skill     the skill
         * @param rawLevel  the raw level that the player has without any modifiers
         * @param effectiveLevel the effectiveBlocks level that the player has with all modifiers (from mob effects etc)
         */
        public Get(Player player, Skill skill, int rawLevel, int effectiveLevel) {
            super(player);
            this.skill = skill;
            this.rawLevel = rawLevel;
            this.effectiveLevel = effectiveLevel;
        }

        public Skill getSkill() {
            return skill;
        }

        public int getRawLevel() {
            return rawLevel;
        }

        public int getEffectiveLevel() {
            return effectiveLevel;
        }

        public void setEffectiveLevel(int effectiveLevel) {
            this.effectiveLevel = effectiveLevel;
        }
    }
}
