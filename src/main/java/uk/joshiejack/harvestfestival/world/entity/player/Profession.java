package uk.joshiejack.harvestfestival.world.entity.player;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public record Profession(String name) {
    public static final Map<String, Profession> PROFESSIONS = new Object2ObjectOpenHashMap<>();
    public static final Profession RANCHER = new Profession("rancher");
    public static final Profession HORTICULTURIST = new Profession("horticulturist");

    public Profession(String name) {
        this.name = name;
        PROFESSIONS.put(name, this);
    }
}
