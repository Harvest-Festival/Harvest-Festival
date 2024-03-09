package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFConfig;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.util.helper.FakePlayerHelper;
import uk.joshiejack.penguinlib.world.item.PenguinRegistryItem;

import javax.annotation.Nullable;
import java.util.List;

public class SeedBagItem extends PenguinRegistryItem<SeedData> {
    public SeedBagItem(Item.Properties properties)  {
        super(HFRegistries.SEEDS, "Crop", properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        SeedData data = fromStack(pStack);
        if (data != null) {
            if (data.requiresSickle()) pTooltipComponents.add(Component.translatable("info.%s.requires_sickle".formatted(HarvestFestival.MODID)).withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (!data.requiresWater()) pTooltipComponents.add(Component.translatable("info.%s.no_water_required".formatted(HarvestFestival.MODID)).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
//            for (Season season: data.seasons) {
//                pTooltipComponents.add(SeasonData.DATA.get(season).hud().append(StringHelper.localize(Seasons.MODID + "." + season.name().toLowerCase(Locale.ENGLISH))));
//            } //TODO:

            pTooltipComponents.add(Component.translatable("info.%s.days".formatted(HarvestFestival.MODID),data.days()));
        }
    }

    private static FakePlayer getFakePlayerWithItem(ServerLevel world, UseOnContext context, ItemStack stack) {
        FakePlayer fake = FakePlayerHelper.getFakePlayerWithPosition(world, context.getClickedPos());
        fake.setItemInHand(context.getHand(), stack.copy());
        return fake;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        InteractionHand hand = context.getHand();
        SeedData seeds = fromStack(player.getItemInHand(hand));
        if (seeds != null) {
            BlockPos pos = context.getClickedPos();
            ItemStack held = player.getItemInHand(hand).copy();
            boolean placed = false;
            for (BlockPos p : BlockPos.betweenClosed(pos.north(HFConfig.seedBagRange.get()).east(HFConfig.seedBagRange.get()), pos.south(HFConfig.seedBagRange.get()).west(HFConfig.seedBagRange.get()))) {
//                if (!seeds.seeds().isEmpty()) {
//                    ItemStack seedsCopy = seeds.seeds().copy();
//                    player.setItemInHand(hand, seedsCopy);
//                    UseOnContext newContext = new UseOnContext(player, hand, context.getHitResult().withPosition(p));
//                    if (seedsCopy.getItem().useOn(newContext) == InteractionResult.SUCCESS) {
//                        System.out.println("Placed at" + p);
//                        placed = true;
//                    }
//                } else {
                    Level world = context.getLevel();
                    ItemStack itemstack = player.getItemInHand(hand);
                    BlockState state = world.getBlockState(p);
                    Direction facing = context.getClickedFace();
                    if (facing == Direction.UP// && player.canPlayerEdit(p, facing, itemstack) //TODO: Check if the player can edit the block
                            && state.canSustainPlant(world, p, Direction.UP, seeds.plantable()) && world.getBlockState(p.above()).isAir()) {
                        world.setBlockAndUpdate(p.above(), seeds.blockState());

                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, p.above(), itemstack);
                        }

                        placed = true;
                    }
//                }
            }

            if (placed) {
                if (!player.getAbilities().instabuild)
                    held.shrink(1); //Reduce the stack
            }

            //Set back to the initial
            player.setItemInHand(hand, held.copy());
            return placed ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }
}
