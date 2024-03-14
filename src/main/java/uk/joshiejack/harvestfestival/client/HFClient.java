package uk.joshiejack.harvestfestival.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.gui.PageAchievements;
import uk.joshiejack.harvestfestival.client.gui.PageCollections;
import uk.joshiejack.harvestfestival.client.renderer.blockentity.MailboxBlockEntityRenderer;
import uk.joshiejack.harvestfestival.client.renderer.entity.FrostBatRenderer;
import uk.joshiejack.harvestfestival.client.renderer.entity.FrostSlimeRenderer;
import uk.joshiejack.harvestfestival.client.renderer.entity.HellBatRenderer;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.block.entity.HFBlockEntities;
import uk.joshiejack.harvestfestival.world.collections.Collections;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;
import uk.joshiejack.harvestfestival.world.inventory.HFMenus;
import uk.joshiejack.harvestfestival.world.item.FertilizerItem;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.harvestfestival.world.item.SeedData;
import uk.joshiejack.harvestfestival.world.item.SeedlingData;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.page.PageNotes;
import uk.joshiejack.penguinlib.client.gui.book.tab.NotesTab;
import uk.joshiejack.penguinlib.client.gui.book.tab.Tab;
import uk.joshiejack.penguinlib.data.PenguinRegistries;
import uk.joshiejack.penguinlib.util.icon.ItemIcon;
import uk.joshiejack.penguinlib.world.inventory.AbstractBookMenu;
import uk.joshiejack.penguinlib.world.note.Note;

import java.util.Comparator;
import java.util.Objects;

@OnlyIn(value = Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = HarvestFestival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HFClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(HFItems.NOTE.asItem(), new ResourceLocation(HarvestFestival.MODID, "special"), (stack, world, entity, seed) -> {
            Note note = PenguinRegistries.NOTES.getOrEmpty(new ResourceLocation(stack.hasTag() ? Objects.requireNonNull(stack.getTag()).getString("Note") : "none"));
            return note.isHidden() ? 1F : 0F;
        }));

        //Use the hashcode id for the fertilizer texture
        ClampedItemPropertyFunction function = (stack, world, entity, seed) -> stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains("Fertilizer")
                ? FertilizerItem.getFertilizerFromStack(stack).itemModelIndex() : 0F;
        event.enqueueWork(() -> ItemProperties.register(HFItems.FERTILIZER.get(), new ResourceLocation(HarvestFestival.MODID, "fertilizer"), function));
    }

    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(HFMenus.BOOK.get(), ((AbstractBookMenu container, Inventory inv, Component text) ->
                Book.getInstance(HarvestFestival.MODID, container, inv, text, (Book bs) -> {
                    Tab tab = bs.withTab(new Tab(Component.translatable("gui." + HarvestFestival.MODID + ".collections"), PageCollections.ICON));
                    tab.withPage(new PageAchievements()); //Add the achievements page
                    Collections.COLLECTIONS.values().stream().sorted(Comparator.comparingInt(Collections.Collection::order))
                            .forEach(collection -> tab.withPage(new PageCollections(collection)));
                    Tab notes = bs.withTab(new NotesTab(Component.translatable("gui." + HarvestFestival.MODID + ".notes"), new ItemIcon(HFItems.NOTE.toStack())));
                    PenguinRegistries.CATEGORIES.stream().forEach(category -> notes.withPage(new PageNotes(category)));
//                    bs.withTab(new NotesTab(Component.translatable("gui." + HarvestFestival.MODID + ".notes"), new TextureIcon(Icon.DEFAULT_LOCATION, 0, 0))
//                            .withCategory(new ResourceLocation(HarvestFestival.MODID, "care_category")));
                })
        ));
    }

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HFEntities.FROST_SLIME.get(), rm -> new FrostSlimeRenderer(rm, 0.5F));
        event.registerEntityRenderer(HFEntities.FROST_BAT.get(), FrostBatRenderer::new);
        event.registerEntityRenderer(HFEntities.HELL_BAT.get(), HellBatRenderer::new);
        event.registerEntityRenderer(HFEntities.ICEBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HFEntities.FIREBALL.get(), ThrownItemRenderer::new);
        event.registerBlockEntityRenderer(HFBlockEntities.MAILBOX.get(), MailboxBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, blockAndTintGetter, pos, num) -> blockAndTintGetter != null && pos != null
                        ? BiomeColors.getAverageGrassColor(blockAndTintGetter, pos)
                        : GrassColor.getDefaultColor(),
                HFBlocks.WEEDS.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, num) -> {
            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, num);
        }, HFBlocks.WEEDS.get());

        event.register((stack, tintIndex) -> {
            SeedData data = HFItems.SEED_BAG.get().fromStack(stack);
            return data != null ? data.getColor() : -1;
        }, HFItems.SEED_BAG.get());

        event.register((stack, tintIndex) -> {
            SeedlingData data = HFItems.SEEDLING_BAG.get().getSeedling(stack);
            return data != null ? data.getColor() : -1;
        }, HFItems.SEEDLING_BAG.get());
    }
}
