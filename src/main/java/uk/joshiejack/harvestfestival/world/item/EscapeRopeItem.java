package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.MineTeleporter;

public class EscapeRopeItem extends Item {
    public EscapeRopeItem(Properties properties) {
        super(properties);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level instanceof ServerLevel server && MineHelper.isMineWorld(server)) {
            MineTeleporter.exitMine(server, player);
            if (!player.getAbilities().instabuild)
                player.getItemInHand(hand).shrink(1);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.success(player.getItemInHand(hand));
        } else return super.use(level, player, hand);
    }
}
