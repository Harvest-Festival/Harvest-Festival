package uk.joshiejack.settlements.api.town;

import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.settlements.api.npc.NPC;

import java.util.Optional;

public interface Town {
    /**
     * Gets the npc with this key
     *
     * @param key the key
     */
    Optional<NPC> getNPC(ResourceLocation key);

    /**
     * Gets the npc or forces them to spawn if they don't exist
     *
     * @param key the key
     * @return the npc if the key canbe found or empty if it cannot
     */
    Optional<NPC> getOrCreateNPC(ResourceLocation key);
}
