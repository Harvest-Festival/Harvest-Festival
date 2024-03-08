package uk.joshiejack.harvestfestival.world.entity.player.energy;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.network.SyncEnergyStats;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class ReplaceFood {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onWorldCreated(LevelEvent.Load event) {
        if (HFConfig.forceNaturalRegenDisabling.get() && event.getLevel() instanceof ServerLevel serverLevel)
            serverLevel.getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION)
                    .set(false, serverLevel.getServer());
    }

    @SubscribeEvent
    public static void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player)
            replaceFoodStats(player);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void replaceFoodStats(EntityJoinLevelEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer player)
            replaceFoodStats(player);
    }

    public static void replaceFoodStats(ServerPlayer player) {
//        if (!(player.getFoodData() instanceof EnergyData)) {
//            EnergyData stats = new EnergyData(player);
//            stats.readAdditionalSaveData(player.getPersistentData()); //Read in the instance we saved
//            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(stats.maxHearts);
//            ObfuscationReflectionHelper.setPrivateValue(Player.class, player, stats, "foodData");
//        }

        PenguinNetwork.sendToClient(player, new SyncEnergyStats((EnergyData)player.getFoodData()));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCloning(PlayerEvent.Clone event) {
        ObfuscationReflectionHelper.setPrivateValue(Player.class, event.getEntity(), event.getOriginal().getFoodData(), "foodData");
        if (event.getEntity().getFoodData() instanceof EnergyData) {
            ((EnergyData)event.getEntity().getFoodData()).setPlayer(event.getEntity());
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void saveFoodStats(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity().getFoodData() instanceof EnergyData stats) {
            stats.addAdditionalSaveData(event.getEntity().getPersistentData());
        }
    }

    @SubscribeEvent
    public static void onPlayerTakenDamage(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.STARVE)) event.setCanceled(true);
    }
}
