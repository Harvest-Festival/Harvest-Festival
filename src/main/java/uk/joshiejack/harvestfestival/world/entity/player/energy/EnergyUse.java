package uk.joshiejack.harvestfestival.world.entity.player.energy;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.effect.HFEffects;
import uk.joshiejack.harvestfestival.world.entity.player.Profession;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;
import uk.joshiejack.penguinlib.util.PenguinTags;

import java.util.Set;

@SuppressWarnings("WeakerAccess, unused")
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class EnergyUse {
    private static final int VERY_LOW = 1;
    private static final int LOW = 2;
    private static final int MEDIUM = 4;
    private static final int HIGH = 8;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        float strength = event.getState().getBlock().defaultDestroyTime();
        if (strength >= 0.1F) {
            Player player = event.getPlayer();
            boolean isHoldingBlockBreakingTool = isHoldingBlockBreakingTool(player);
            boolean requiresTool = event.getState().requiresCorrectToolForDrops();
            boolean correctTool = event.getPlayer().getMainHandItem().isCorrectToolForDrops(event.getState());
            float modifier = event.getState().is(HFTags.Blocks.HIGH_ENERGY_CONSUMPTION) ? 2F : event.getState().is(HFTags.Blocks.LOW_ENERGY_CONSUMPTION) ? 0.25F : 1F;
            Skill skill = event.getState().is(HFTags.Blocks.AWARDS_MINING_EXPERIENCE) ? Skill.MINING : event.getState().is(HFTags.Blocks.AWARDS_FARMING_EXPERIENCE) ? Skill.FARMING : event.getState().is(HFTags.Blocks.AWARDS_GATHERING_EXPERIENCE) ? Skill.GATHERING : null;
            int skillMultiplier = skill == null ? 0 : Skill.getLevel(player, skill, Skill.LevelType.EFFECTIVE);
            double baseEnergyUsage = LOW * modifier;
            if (requiresTool && (!isHoldingBlockBreakingTool || !correctTool))
                baseEnergyUsage *= 2;
            baseEnergyUsage += Math.max(10F, (strength - 3F) / 5F);
            double energyUse = Math.max(0.1, baseEnergyUsage - (skillMultiplier * 0.375D));
            consumeEnergy(player, energyUse);
        }
    }

    private static final Set<TagKey<Item>> miningTools = ImmutableSet.of(
            ItemTags.AXES, ItemTags.PICKAXES, ItemTags.SHOVELS, PenguinTags.HAMMERS, PenguinTags.SICKLES
    );

    private static boolean isHoldingBlockBreakingTool(Player player) {
        return player.getMainHandItem().getItem() instanceof DiggerItem || miningTools.stream().anyMatch(player.getMainHandItem()::is);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
        if (player != null && isHoldingWateringCanOrHoe(player)) {
            int skill = Skill.getLevel(player, Skill.FARMING, Skill.LevelType.EFFECTIVE);
            double energyUse = Math.max(0.2, MEDIUM - (skill * 0.375D));
            consumeEnergy(player, energyUse);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onMultiPlaced(BlockEvent.EntityMultiPlaceEvent event) {
        Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
        if (player != null && isHoldingWateringCanOrHoe(player)) {
            int skill = Skill.getLevel(player, Skill.FARMING, Skill.LevelType.EFFECTIVE);
            double energyUse = Math.max(0.2, LOW - (skill * 0.375D));
            consumeEnergy(player, energyUse * event.getReplacedBlockSnapshots().size());
        }
    }

    private static boolean isHoldingWateringCanOrHoe(Player player) {
        return isWateringCanOrHoe(player.getMainHandItem()) || isWateringCanOrHoe(player.getOffhandItem());
    }

    private static boolean isWateringCanOrHoe(ItemStack item) {
        return item.is(ItemTags.HOES) || item.is(PenguinTags.WATERING_CANS);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFishing(ItemFishedEvent event) {
        int skill = Skill.getLevel(event.getEntity(), Skill.FISHING, Skill.LevelType.EFFECTIVE);
        double energyUse = Math.max(1, HIGH - (skill * 0.75D));
        consumeEnergy(event.getEntity(), energyUse);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack held = event.getEntity().getMainHandItem();
        if (held.getItem() == Items.BUCKET || held.is(Tags.Items.SHEARS)) {
            boolean isRancher = Skill.hasProfession(event.getEntity(), Profession.RANCHER);
            double energyUse = Math.max(0.1, MEDIUM - (isRancher ? 3D : 0D));
            consumeEnergy(event.getEntity(), energyUse);
        }
    }

    private static void consumeEnergy(Player player, double amount) {
        if (!player.isCreative())
            ((EnergyData) player.getFoodData()).useEnergy(amount);
    }

    public static void updateTiredness(ServerPlayer player) {
        if (player.isCreative() && player.hasEffect(HFEffects.TIRED.get())) {
            player.removeEffect(HFEffects.TIRED.get());
        } else {
            int slept = Mth.clamp(player.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            if (slept > 19000L && !player.hasEffect(HFEffects.BUZZED.get())) {
                player.addEffect(new MobEffectInstance(HFEffects.TIRED.get(), Integer.MAX_VALUE, 0, false, false)); //Unlimited
            } else if (player.hasEffect(HFEffects.TIRED.get())) player.removeEffect(HFEffects.TIRED.get());
        }
    }

    public static void updateFatigue(ServerPlayer player, boolean expiring) {
        boolean fatigued = player.hasEffect(HFEffects.FATIGUED.get());
        boolean exhausted = player.hasEffect(HFEffects.EXHAUSTED.get());
        if (!player.hasEffect(HFEffects.BUZZED.get())) {
            int energy = ((EnergyData) player.getFoodData()).getEnergyLevel();
            if (energy <= 2 * EnergyData.ENERGY_PER_HUNGER) {
                if (!fatigued && !exhausted && energy > 0)
                    player.addEffect(new MobEffectInstance(HFEffects.FATIGUED.get(), 600, 0, false, false));
                if ((expiring && energy <= 1) || (!fatigued && energy <= 0))
                    player.addEffect(new MobEffectInstance(HFEffects.EXHAUSTED.get(), Integer.MAX_VALUE, 0, false, false));
            } else if (fatigued) {
                player.removeEffect(HFEffects.FATIGUED.get());
            } else if (exhausted) {
                player.removeEffect(HFEffects.EXHAUSTED.get());
            }
        } else {
            if (fatigued) player.removeEffect(HFEffects.FATIGUED.get());
            if (exhausted) player.removeEffect(HFEffects.EXHAUSTED.get());
        }
    }

    public static void updateEffectInstances(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.isCreative()) {
                serverPlayer.removeEffect(HFEffects.TIRED.get());
                serverPlayer.removeEffect(HFEffects.FATIGUED.get());
                serverPlayer.removeEffect(HFEffects.EXHAUSTED.get());
            } else {
                updateTiredness(serverPlayer);
                updateFatigue(serverPlayer, false);
            }
        }
    }
}
