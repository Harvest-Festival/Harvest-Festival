package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.network.OpenCalendarScreenPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;

import javax.annotation.Nonnull;

public class CalendarItem extends Item {
    public CalendarItem(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            PenguinNetwork.sendToClient(serverPlayer, new OpenCalendarScreenPacket());
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
