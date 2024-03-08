package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.data.PenguinRegistries;
import uk.joshiejack.penguinlib.world.note.Note;

import java.util.Objects;

public class NoteItem extends Item {
    public NoteItem(Properties properties) {
        super(properties);
    }

    private Note getNoteFromStack(ItemStack stack) {
        return PenguinRegistries.NOTES.getOrEmpty(new ResourceLocation(Objects.requireNonNull(stack.getTag()).getString("Note")));
    }

    @SuppressWarnings("all")
    @Override
    public Component getName(ItemStack pStack) {
        return Component.translatable("%s: %s", Component.translatable(this.getDescriptionId(pStack)), pStack.hasTag() ? getNoteFromStack(pStack).getTitle() : Component.translatable("note." + HarvestFestival.MODID + ".unknown"));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.hasTag() && Objects.requireNonNull(itemstack.getTag()).contains("Note")) {
            Note note = getNoteFromStack(itemstack);
            if (note.isUnlocked(player))
                return InteractionResultHolder.pass(itemstack);
            else {
                if (!level.isClientSide) {
                    note.unlock(player);
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
