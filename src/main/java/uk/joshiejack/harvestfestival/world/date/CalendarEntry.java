package uk.joshiejack.harvestfestival.world.date;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface CalendarEntry {
    /**
     * Returns the icon for this entry
     * @return the icon
     */
    Renderable getIcon();

    /**
     * Adds the tooltip for this entry
     */
    void addTooltip(List<Component> components);
}
