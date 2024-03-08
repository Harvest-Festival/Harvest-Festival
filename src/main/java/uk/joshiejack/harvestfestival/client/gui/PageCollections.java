package uk.joshiejack.harvestfestival.client.gui;

import joptsimple.internal.Strings;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.gui.components.CollectionEntryLabel;
import uk.joshiejack.harvestfestival.network.request.RequestObtainedItemsPacket;
import uk.joshiejack.harvestfestival.world.collections.Collections;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.page.AbstractMultiPage;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.SpriteIcon;

import java.util.List;
import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class PageCollections extends AbstractMultiPage.Both<ItemStack> {
    private EditBox searchBox;
    public String search = "";
    public static final Icon ICON = new SpriteIcon(new ResourceLocation(HarvestFestival.MODID, "book/collections"));

    private final Collections.Collection collection;

    public PageCollections(Collections.Collection collection) {
        super(Component.translatable("collection." + collection.id().getNamespace() + "." + collection.id().getPath()), 64);
        this.collection = collection;
    }

    @Override
    protected Icon getIcon() {
        return collection.icon();
    }

    @Override
    public void initLeft(Book book, int left, int top) {
        //Request the obtained data when we open the collections page
        PenguinNetwork.sendToServer(new RequestObtainedItemsPacket());
        super.initLeft(book, left, top);
        System.out.println("RE-INIT LEFT");
        System.out.println("Old value? " + (searchBox == null ? "null" : searchBox.getValue()));
        searchBox = new EditBox(book.minecraft().font,
                107,
                202,
                100,
                20,
                searchBox,
                Component.literal("Filter"));
        searchBox.setMaxLength(64);
        searchBox.setResponder((extt) -> {
            book.renderables.removeIf((renderable) -> renderable instanceof CollectionEntryLabel);
            super.initLeft(book, left, top);
            super.initRight(book, left, top);
        });

        book.addRenderableWidget(searchBox);
        //book.setInitialFocus(this.name);

        //book.addRenderableWidget(searchBox);
//System.out.println(inFocus);
//        book.addRenderableWidget(new FilterCollectionWidget(new Button.Builder(
//                Component.translatable("gui.neoforged.collections.search"),
//                (button) -> {})
//                .pos(177, 202), book, this, collection.id().toString(), 32));


        //book.setFocused(inFocus != null ? inFocus : book.getFocused());
        //setFocus(inFocus);

        //if (entries.isEmpty())
        //book.addRenderableOnly(new InformationLabel(left, top, book.minecraft().font));
    }

    @Override
    protected void initEntry(Book book, int x, int y, int id, ItemStack stack) {
        book.addRenderableOnly(new CollectionEntryLabel(stack, x + 17 + ((id % 8) * 16), y + 4 + ((id / 8) * 16)));
    }

    protected List<ItemStack> getEntries() {
        String search  = searchBox == null ? Strings.EMPTY : searchBox.getValue();
        System.out.println(search);
        return Collections.COLLECTIONS.get(collection.id()).getItems().stream()
                .filter((stack) -> {
                    if (search.isEmpty()) return true;
                    long value = 0;//TODO:ShippingRegistry.INSTANCE.getValue(stack);
                    String text = search.toLowerCase(Locale.ENGLISH);
                    if (text.startsWith("=")) {
                        try {
                            return value == Long.parseLong(text.substring(1).trim());
                        } catch (Exception ignored) {}
                    } else if (text.startsWith("<=")) {
                        try {
                            return value <= Long.parseLong(text.substring(2).trim());
                        } catch (Exception ignored) {}
                    } else if (text.startsWith("<")) {
                        try {
                            return value < Long.parseLong(text.substring(1).trim());
                        } catch (Exception ignored) {}
                    } else if (text.startsWith(">=")) {
                        try {
                            return value >= Long.parseLong(text.substring(2).trim());
                        } catch (Exception ignored) {}
                    } else if (text.startsWith(">")) {
                        try {
                            return value > Long.parseLong(text.substring(1).trim());
                        } catch (Exception ignored) {}
                    } else if (text.startsWith("@")) {
                        String modid = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().toLowerCase();
                        return modid.contains(text.substring(1));
                    }

                    String str = stack.getDisplayName().getString().replace("[", "").replace("]", "");
                    return str.toLowerCase(Locale.ENGLISH).startsWith(text);
                }).toList();
    }
}