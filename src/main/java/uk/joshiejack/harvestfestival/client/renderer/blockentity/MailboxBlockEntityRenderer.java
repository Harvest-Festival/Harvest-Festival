package uk.joshiejack.harvestfestival.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.client.Inbox;
import uk.joshiejack.harvestfestival.world.block.entity.MailboxBlockEntity;
import uk.joshiejack.penguinlib.client.renderer.block.entity.AbstractItemTileEntityRenderer;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class MailboxBlockEntityRenderer extends AbstractItemTileEntityRenderer<MailboxBlockEntity> {
    private static final Supplier<ItemStack> TEST = () -> new ItemStack(Items.PAPER);

    public MailboxBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull MailboxBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (!Inbox.PLAYER.getLetters().isEmpty())
            renderSpeechBubble(TEST.get(), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }
}

