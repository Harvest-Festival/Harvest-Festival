package uk.joshiejack.harvestfestival.world.date;

import net.minecraft.network.chat.Component;

public record CalendarDate(int day, Season season) {

    public enum Season {
        SPRING, SUMMER, AUTUMN, WINTER;

        public static final Season[] MAIN = {SPRING, SUMMER, AUTUMN, WINTER};
        public Component asComponent() {
            return Component.translatable("season.harvestfestival." + name().toLowerCase());
        }
    }
}
