package uk.joshiejack.harvestfestival.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.HarvestFestival;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class SeedlingBagItem extends BlockItem {
    public SeedlingBagItem(Properties properties)  {
        super(Blocks.OAK_SAPLING, properties);
    }

    public ItemStack withSeedling(SeedlingData data) {
        return withSeedling(new ItemStack(this), data);
    }

    public ItemStack withSeedling(ItemStack stack, SeedlingData data) {
        stack.getOrCreateTag().putString("Tree", data.id().toString());
        return stack;
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public SeedlingData getSeedling(ItemStack stack) {
        return stack.hasTag() ? HFRegistries.SEEDLINGS.get(new ResourceLocation(stack.getTag().getString("Tree"))) : null;
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack stack) {
        SeedlingData data = getSeedling(stack);
        return data != null ? data.getName() : super.getName(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        SeedlingData data = getSeedling(pStack);
        if (data != null) {
//            for (Season season: data.seasons) {
//                pTooltipComponents.add(SeasonData.DATA.get(season).hud().append(StringHelper.localize(Seasons.MODID + "." + season.name().toLowerCase(Locale.ENGLISH))));
//            } //TODO:

            pTooltipComponents.add(Component.translatable("info.%s.days".formatted(HarvestFestival.MODID),data.days()));
        }
    }

    @Override
    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        SeedlingData data = getSeedling(pContext.getItemInHand());
        if (data == null) return null;
        BlockState blockstate = data.block().getStateForPlacement(pContext);
        return blockstate != null && this.canPlace(pContext, blockstate) ? blockstate : null;
    }

    @Override
    public void registerBlocks(@NotNull Map<Block, Item> pBlockToItemMap, @NotNull Item pItem) { }

    @Override
    public void removeFromBlockToItemMap(@NotNull Map<Block, Item> blockToItemMap, @NotNull Item itemIn) { }
}
