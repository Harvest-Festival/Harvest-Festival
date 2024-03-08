package uk.joshiejack.harvestfestival.world.item.tool;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.event.BlockSmashedEvent;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;
import uk.joshiejack.penguinlib.event.DatabaseLoadedEvent;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class ToolEvents {
    private static final Object2IntMap<Item> SMASH_LEVELS = new Object2IntOpenHashMap<>();

    public static boolean isCursed(ItemStack stack) {
        return stack.is(ItemTags.TOOLS) && stack.getEnchantmentLevel(Enchantments.BINDING_CURSE) > 0;
    }

    @SubscribeEvent
    public static void onDatabaseLoadedEvent(DatabaseLoadedEvent event) {
        SMASH_LEVELS.clear();
        event.table("critical_smash_tools").rows().forEach(row -> {
            Item item = row.item();
            if (item != Items.AIR) {
                SMASH_LEVELS.put(item, row.getAsInt("area"));
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTagReload(TagsUpdatedEvent event) {
        if (event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) {
            //Fuck with all of the blocks the blocks but maybe we need to actually do this on reload instead?
           // BuiltInRegistries.BLOCK.getTag(PenguinTags.MINEABLE_SICKLE).stream().flatMap(HolderSet.ListBacked::stream).forEach(((Holder<Block> block) -> {
            //TODO: Add tag for requires sickle
                Blocks.WHEAT.getStateDefinition().getPossibleStates().forEach(state -> state.requiresCorrectToolForDrops = true);
           // }));
        }
    }

    private static double getExperience(BlockState state) {
        return state.is(HFTags.Blocks.LOW_TOOL_EXPERIENCE) ? 0.1 : state.is(HFTags.Blocks.HIGH_TOOL_EXPERIENCE) ? 2.5 : 1;
    }

    private static void addToolExperienceIfUpgradable(ItemStack stack, double experience) {
        Upgrades.Upgrade upgrade = Upgrades.getUpgrade(stack);
        if (upgrade != null && stack.getOrCreateTag().getDouble("Experience") < upgrade.experienceRequirement()) {
            stack.getOrCreateTag().putDouble("Experience", Math.min(stack.getOrCreateTag().getDouble("Experience") + experience, upgrade.experienceRequirement()));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHoeUse(BlockEvent.BlockToolModificationEvent event) {
        if (event.isSimulated()) return;
        if (event.getToolAction() == ToolActions.HOE_TILL) { //TODO: Add a new ToolActions.WATERING_CAN_WATER and award level up points for watering crops
            addToolExperienceIfUpgradable(event.getHeldItemStack(), 1);
            if (event.getPlayer() instanceof ServerPlayer player) {
                Skill.FARMING.addExperience(player, 1);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onKillEntity(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            ItemStack stack = player.getMainHandItem();
            if (stack.is(HFTags.Items.AWARDS_COMBAT_EXPERIENCE)) {
                double award = event.getEntity().getType().is(HFTags.Entities.AWARDS_HIGH_COMBAT_EXPERIENCE) ? 2.5D :
                        !(event.getEntity() instanceof Enemy) || event.getEntity().getType().is(HFTags.Entities.AWARDS_LOW_COMBAT_EXPERIENCE) ? 0.1D : 1D;
                addToolExperienceIfUpgradable(stack, award);
                if (event.getEntity() instanceof Enemy) {
                    Skill.COMBAT.addExperience(player, 1);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemFished(ItemFishedEvent event) {
        boolean awardsFishingExperience = event.getDrops().stream().anyMatch(stack -> stack.is(HFTags.Items.AWARDS_FISHING_EXPERIENCE));
        addToolExperienceIfUpgradable(event.getEntity().getMainHandItem(), awardsFishingExperience ? 1 : 0.1);
        if (event.getEntity() instanceof ServerPlayer player && awardsFishingExperience) {
            Skill.FISHING.addExperience(player, 1);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        //Skill Handling (Mining & Gathering)
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (event.getPlayer().getMainHandItem().isCorrectToolForDrops(event.getState()))
                addToolExperienceIfUpgradable(event.getPlayer().getMainHandItem(), getExperience(event.getState()));
            if (event.getState().is(HFTags.Blocks.AWARDS_MINING_EXPERIENCE)) {
                Skill.MINING.addExperience(player, 1);
            } else if (event.getState().is(HFTags.Blocks.AWARDS_GATHERING_EXPERIENCE)) {
                Skill.GATHERING.addExperience(player, 1);
            } else if (event.getState().is(HFTags.Blocks.AWARDS_FARMING_EXPERIENCE)) {
                Skill.FARMING.addExperience(player, 1);
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalSmash(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getHand() == InteractionHand.MAIN_HAND && player.getDeltaMovement().y <= -0.1F) {
            ItemStack stack = player.getItemInHand(event.getHand());
            Integer area = SMASH_LEVELS.containsKey(stack.getItem()) ? SMASH_LEVELS.getInt(stack.getItem()) : stack.getItemHolder().getData(HFData.CRITICAL_SMASH);
            if (area != null) {
                BlockPos.betweenClosedStream(event.getPos().offset(-area, 0, -area), event.getPos().offset(area, 0, area)).forEach(pos -> {
                    BlockState state = player.level().getBlockState(pos);
                    if (player.hasCorrectToolForDrops(state) && state.is(HFTags.Blocks.SMASHABLE)
                            && stack.getDamageValue() < stack.getMaxDamage() && player.getDigSpeed(state, pos) > 0.0F) {
                        BlockSmashedEvent smashedEvent = new BlockSmashedEvent(player, state, pos);
                        if (!smashedEvent.isCanceled()) {
                            player.getItemInHand(event.getHand()).hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(event.getHand()));
                            //Spawn an explosion particle at the blockTag pos
                            player.level().addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0, 0, 0);
                            player.gameMode.destroyBlock(pos);
                            SoundType soundtype = state.getSoundType(player.level(), pos, player);
                            player.level().playSound(null, pos, soundtype.getBreakSound(), player.getSoundSource(), (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        }
                    }
                });
            }
        }
    }
}
