package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.harvestfestival.world.mail.PostalOffice;

import java.util.Objects;

public class MailItem extends Item {
    public MailItem(Properties properties) {
        super(properties);
    }

    private AbstractLetter<?> getNoteFromStack(ItemStack stack) {
        return HFRegistries.LETTERS.getOrDefault(new ResourceLocation(Objects.requireNonNull(stack.getTag()).getString("Letter")), HFRegistries.LETTERS.emptyEntry());
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player player, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = player.getItemInHand(pUsedHand);
        if (itemstack.hasTag() && Objects.requireNonNull(itemstack.getTag()).contains("Letter")) {
            AbstractLetter<?> letter = getNoteFromStack(itemstack);
            if (PostalOffice.getLetters(player).contains(letter))
                return InteractionResultHolder.pass(itemstack);
            else {
                if (!pLevel.isClientSide) {
                    PostalOffice.get((ServerLevel) pLevel).send(pLevel, player, letter);
                    CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, itemstack);
                }

                if (!player.getAbilities().instabuild)
                    itemstack.shrink(1);
                return InteractionResultHolder.success(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(itemstack);
        }
    }
}
