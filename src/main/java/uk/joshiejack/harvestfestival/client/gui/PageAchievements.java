package uk.joshiejack.harvestfestival.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.gui.components.AchievementLabel;
import uk.joshiejack.harvestfestival.world.achievements.Achievement;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.page.AbstractMultiPage;
import uk.joshiejack.penguinlib.util.icon.Icon;
import uk.joshiejack.penguinlib.util.icon.SpriteIcon;

import java.util.List;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class PageAchievements extends AbstractMultiPage.Both<Achievement> {
    public static final Icon ICON = new SpriteIcon(new ResourceLocation(HarvestFestival.MODID, "achievements/earn_250k"), AchievementLabel.DEFAULT_SHADOW);

    public PageAchievements() {
        super(Component.translatable("gui." + HarvestFestival.MODID + ".achievements"), 64);
    }

    @Override
    protected Icon getIcon() {
        return ICON;
    }

    @Override
    public void initLeft(Book book, int left, int top) {
        //Request the statistics when we open the achievements page
        Objects.requireNonNull(Minecraft.getInstance().getConnection()).send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
        super.initLeft(book, left, top);
        //if (entries.isEmpty())
            //book.addRenderableOnly(new InformationLabel(left, top, book.minecraft().font));
    }

    @Override
    protected void initEntry(Book book, int x, int y, int id, Achievement achievement) {
        book.addRenderableOnly(new AchievementLabel(achievement, x + 17 + ((id % 8) * 17), y + 4 + ((id / 8) * 17)));
    }

    protected List<Achievement> getEntries() {
        return Achievement.ACHIEVEMENTS.values().stream().toList();
    }
}