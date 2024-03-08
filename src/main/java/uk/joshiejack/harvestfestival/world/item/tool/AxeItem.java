package uk.joshiejack.harvestfestival.world.item.tool;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AxeItem extends net.minecraft.world.item.AxeItem {
    private final boolean isCursed;
    private final int hits;
    public AxeItem(HFTiers tier, float damage, float speed, Properties properties, int hits) {
        super(tier, damage, speed, properties);
        this.isCursed = tier == HFTiers.CURSED;
        this.hits = hits;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return isCursed ? !pStack.isEnchanted() : super.isFoil(pStack);
    }

    @Override
    public boolean onBlockStartBreak(@NotNull ItemStack stack, @NotNull BlockPos pos, @NotNull Player player) {
        if (player.isCreative()) return super.onBlockStartBreak(stack, pos, player);
        if (stack.getDamageValue() < stack.getMaxDamage()) {
            if (!player.isShiftKeyDown() && TreeTasks.findTree(player.level(), pos)) {
                if (canChopTree(player, stack, pos))
                    return chopTree(pos, player, stack);
                else if (isWood(player.level(), pos))
                    return TreeTasks.replaceTree(pos, player);
            }
        }

        return false;
    }

    private boolean isWood(Level world, BlockPos pos) {
        return world.getBlockState(pos).is(BlockTags.LOGS);
    }

    private boolean canChopTree(Player player, ItemStack stack, BlockPos pos) {
        CompoundTag tag = stack.getOrCreateTagElement("Chopping");
        if (tag.contains("Block")) {
            BlockPos internal = BlockPos.of(tag.getLong("Block"));
            if (internal.equals(pos)) {
                int times = tag.getInt("Times");
                tag.putInt("Times", times + 1);
                player.level().playSound(null, pos, HarvestFestival.HFSounds.TREE_CHOP.value(), SoundSource.BLOCKS, (player.level().random.nextFloat() * 0.25F) * times * 10F, player.level().random.nextFloat() + 0.5F);
                return times >= hits;
            }
        }

        int times = 1;
        tag.putLong("Block", pos.asLong());
        tag.putInt("Times", times);
        player.level().playSound(null, pos, HarvestFestival.HFSounds.TREE_CHOP.value(), SoundSource.BLOCKS, (player.level().random.nextFloat() * 0.25F) * times * 10F, player.level().random.nextFloat() + 0.5F);
        return times > hits;
    }

    private boolean chopTree(BlockPos pos, Player player, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement("Chopping");
        tag.remove("Block"); //Remove the data now we're chopping
        tag.remove("Times"); //Remove the data now we're chopping
        if (player.level().isClientSide) return true;
        NeoForge.EVENT_BUS.register(new TreeTasks.ChopTree(pos, player, stack));
        player.level().playSound(null, pos, HarvestFestival.HFSounds.TREE_FALL.value(), SoundSource.BLOCKS, player.level().random.nextFloat() * 0.25F, player.level().random.nextFloat() + 0.5F);
        return true;
    }

    public static class TreeTasks {
        //Borrowed from Tinkers Construct
        public static boolean findTree(Level world, BlockPos origin) {
            BlockPos pos = null;
            Stack<BlockPos> candidates = new Stack<>();
            candidates.add(origin);
            while(!candidates.isEmpty()) {
                BlockPos candidate = candidates.pop();
                if((pos == null || candidate.getY() > pos.getY()) && world.getBlockState(candidate).is(BlockTags.LOGS)) {
                    pos = candidate.above();
                    while(world.getBlockState(pos).is(BlockTags.LOGS)) {
                        pos = pos.above();
                    }

                    candidates.add(pos.north());
                    candidates.add(pos.east());
                    candidates.add(pos.south());
                    candidates.add(pos.west());
                }
            }

            if(pos == null) return false;
            int d = 5;
            int o = -1; // -(d-1)/2
            int leaves = 0;
            for(int x = 0; x < d; x++) {
                for(int y = 0; y < d; y++) {
                    for(int z = 0; z < d; z++) {
                        BlockPos leaf = pos.offset(o + x, o + y, o + z);
                        BlockState state = world.getBlockState(leaf);
                        if (state.is(BlockTags.LEAVES)) {
                            if (++leaves >= 5) return true;
                        }
                    }
                }
            }

            return false;
        }

        public static boolean replaceTree(BlockPos pos, Player player) {
            if(player.level().isClientSide) return true;
            NeoForge.EVENT_BUS.register(new TreeReplace(player.level(), pos, player.level().getBlockState(pos)));
            return true;
        }

        static class TreeReplace {
            private final Level world;
            private final BlockState state;
            private final BlockPos pos;
            private int timer;

            TreeReplace(Level world, BlockPos pos, BlockState state) {
                this.world = world;
                this.pos = pos;
                this.state = state;
                this.timer = 0;
            }

            @SubscribeEvent
            public void replaceTrunk(TickEvent.LevelTickEvent event) {
                if(event.level.dimension() != world.dimension()) return;
                timer++;
                if(timer > 3) {
                    world.setBlockAndUpdate(pos, state);
                    NeoForge.EVENT_BUS.unregister(this);
                }
            }
        }

        //Borrowed from Tinkers Construct by boni
        @SuppressWarnings("WeakerAccess")
        public static class ChopTree {
            private final ServerLevel world;
            private final Player player;
            private final ItemStack stack;
            private final Queue<BlockPos> blocks = Lists.newLinkedList();
            private final Set<BlockPos> visited = new HashSet<>();
            private final NonNullList<ItemStack> drops = NonNullList.create();
            private final BlockPos start;

            public ChopTree(BlockPos start, Player player, ItemStack stack) {
                this.world = (ServerLevel) player.level();
                this.player = player;
                this.stack = stack;
                this.start = start;
                this.blocks.add(start);
            }

            private void finishChoppingTree() {
                drops.forEach(i -> Block.popResource(world, start, i));
                NeoForge.EVENT_BUS.unregister(this);
            }

            @SubscribeEvent
            public void chopTree(TickEvent.LevelTickEvent event) {
                if(event.level.dimension() != world.dimension()) return;
                int remaining = 4;
                while(remaining > 0) {
                    if (blocks.isEmpty()) {
                        finishChoppingTree(); //Finish the dropping
                        return;
                    }

                    BlockPos pos = blocks.remove();
                    if(!visited.add(pos)) continue; //If we've visited the block skip it
                    BlockState state = world.getBlockState(pos);
                    if (!state.is(BlockTags.LOGS)) continue; //If this block isn't wood, skip it

                    //Add surrounding blocks
                    for(Direction facing : Direction.Plane.HORIZONTAL) {
                        BlockPos pos2 = pos.relative(facing);
                        if(!visited.contains(pos2)) blocks.add(pos2);
                    }

                    //Add layer above
                    for(int x = 0; x < 3; x++) {
                        for(int z = 0; z < 3; z++) {
                            BlockPos pos2 = pos.offset(-1 + x, 1, -1 + z);
                            if(!visited.contains(pos2))  blocks.add(pos2);
                        }
                    }

                    //Break the extra blocks
                    drops.addAll(Block.getDrops(state, world, pos, world.getBlockEntity(pos), player, stack));
                    if (stack.getDamageValue() < stack.getMaxDamage()) {
                        stack.hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                    }

                    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()); //No particles
                    remaining--;
                }
            }
        }
    }
}
