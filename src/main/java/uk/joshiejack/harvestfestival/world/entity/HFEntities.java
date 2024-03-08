package uk.joshiejack.harvestfestival.world.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.entity.projectile.Fireball;
import uk.joshiejack.harvestfestival.world.entity.projectile.Iceball;


@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HFEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, HarvestFestival.MODID);
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(Registries.ITEM, HarvestFestival.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<FrostSlime>> FROST_SLIME = createEntity("frost_slime", FrostSlime::new, 2.04F, 2.04F, 0xB1CACC, 0x03ECFC);
    public static final DeferredHolder<EntityType<?>, EntityType<FrostBat>> FROST_BAT = createEntity("frost_bat", FrostBat::new, 0.5F, 0.9F, 0x8CBBC1, 0x03ECFC);
    public static final DeferredHolder<EntityType<?>, EntityType<Iceball>> ICEBALL = createProjectile(new ResourceLocation(HarvestFestival.MODID, "iceball"), Iceball::new, true);
    public static final DeferredHolder<EntityType<?>, EntityType<HellBat>> HELL_BAT = createFireproofEntity("hell_bat", HellBat::new, 0.5F, 0.9F, 0x5F0809, 0xEFF0F0);
    public static final DeferredHolder<EntityType<?>, EntityType<Fireball>> FIREBALL = createProjectile(new ResourceLocation(HarvestFestival.MODID, "fireball"), Fireball::new, true);
    //Personal note: client.model, client.renderer.entity, entity, HFClient, HFEntityLootTables, (This class register spawn placements and attributes)

    //createEntity functin with keys id, category, width, height, and a primary and secondary color
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> createFireproofEntity(String id, EntityType.EntityFactory<E> factory, float width, float height, int primary, int secondary) {
        return createEntity(new ResourceLocation(HarvestFestival.MODID, id), factory, width, height, true, primary, secondary);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> factory, float width, float height, int primary, int secondary) {
        return createEntity(new ResourceLocation(HarvestFestival.MODID, id), factory, width, height, false, primary, secondary);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> createEntity(ResourceLocation id, EntityType.EntityFactory<E> factory, float width, float height, boolean fireproof, int primary, int secondary) {
        EntityType.Builder<E> builder = EntityType.Builder.of(factory, MobCategory.MONSTER)
                .sized(width, height)
                .setTrackingRange(80)
                .setUpdateInterval(3)
                .setShouldReceiveVelocityUpdates(true);
        if (fireproof)
            builder.fireImmune();
        DeferredHolder<EntityType<?>, EntityType<E>> holder = ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
        if (primary != 0 && secondary != 0)
            SPAWN_EGGS.register(id.getPath() + "_spawn_egg", () -> new DeferredSpawnEggItem(() -> (EntityType<? extends Mob>) holder.get(), primary, secondary, new Item.Properties()));
        return holder;
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> createProjectile(ResourceLocation id, EntityType.EntityFactory<E> factory, boolean fireproof) {
        EntityType.Builder<E> builder = EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4)
                .updateInterval(10);
        if (fireproof)
            builder.fireImmune();
        return ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(FROST_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(FROST_BAT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HELL_BAT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FROST_SLIME.get(), FrostSlime.registerAttributes().build());
        event.put(FROST_BAT.get(), FrostBat.registerAttributes().build());
        event.put(HELL_BAT.get(), HellBat.registerAttributes().build());
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
        SPAWN_EGGS.register(eventBus);
    }
}
