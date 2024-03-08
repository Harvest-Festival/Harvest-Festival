package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.farming.Fertilizer;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.level.ticker.CanBeFertilized;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

import java.util.Objects;

public class FertilizerItem extends Item {
    public FertilizerItem(Properties properties) {
        super(properties);
    }

    public static Fertilizer getFertilizerFromStack(ItemStack stack) {
        Fertilizer fertilizer = HFRegistries.FERTILIZER.get(new ResourceLocation(Objects.requireNonNull(stack.getTag()).getString("Fertilizer")));
        return fertilizer == null ? HFFarming.Fertilizers.NONE.get() : fertilizer;
    }

    @SuppressWarnings("all")
    @Override
    public Component getName(ItemStack pStack) {
        return pStack.hasTag() ? getFertilizerFromStack(pStack).getName() : Component.translatable("fertilizer." + HarvestFestival.MODID + ".unknown");
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        ItemStack itemstack = player.getItemInHand(context.getHand());
        if (itemstack.hasTag() && Objects.requireNonNull(itemstack.getTag()).contains("Fertilizer")) {
            Fertilizer fertilizer = getFertilizerFromStack(itemstack);
            if (fertilizer == HFFarming.Fertilizers.NONE.get()) return InteractionResult.PASS;
            else {
                DailyTicker<?> ticker = DailyTicker.get(context.getLevel(), context.getClickedPos());
                if (ticker instanceof CanBeFertilized canBeFertilized &&
                        canBeFertilized.fertilize(context.getLevel(), context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()), fertilizer)) {
                    if (!context.getLevel().isClientSide) {
                        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, itemstack);
                    }

                    if (!player.getAbilities().instabuild)
                        itemstack.shrink(1);

                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }
}