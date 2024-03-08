package uk.joshiejack.settlements.api.npc;

import net.minecraft.world.entity.LivingEntity;

public interface NPC {
    /** Returns the entity for this npc **/
    LivingEntity asEntity();
}
