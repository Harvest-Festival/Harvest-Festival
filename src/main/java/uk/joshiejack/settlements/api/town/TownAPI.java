package uk.joshiejack.settlements.api.town;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface TownAPI {
    /**
     * Returns the town at this position
     * @param level     the world
     * @param blockPos  the position
     * @return  a town, or empty if there is none
     */
    Optional<Town> getTown(Level level, BlockPos blockPos);
}
