package uk.joshiejack.harvestfestival.client.gui.screens;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.date.CalendarDate;
import uk.joshiejack.harvestfestival.world.date.CalendarEntry;
import uk.joshiejack.harvestfestival.world.farming.SeasonHandler;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.client.gui.PenguinFonts;
import uk.joshiejack.penguinlib.data.TimeUnitRegistry;

import java.time.DayOfWeek;
import java.util.EnumMap;

public class CalendarScreen extends PenguinScreen {
    private static final DayOfWeek[] WEEKDAYS = DayOfWeek.values();
    private static final EnumMap<DayOfWeek, Component> WEEKDAY_NAMES = new EnumMap<>(DayOfWeek.class);
    public static final ResourceLocation CALENDAR_TEXTURE = guiTexture(HarvestFestival.MODID, "calendar");
    private final Multimap<CalendarDate, CalendarEntry> entries = HashMultimap.create();
    private CalendarDate.Season season;
    public static int year = -1;
    private int rows;
    static {
        for (DayOfWeek weekday: WEEKDAYS) { //First three letters of the day
            WEEKDAY_NAMES.put(weekday, Component.translatable("harvestfestival.weekday." + weekday.name().toLowerCase()));
        }
    }


    public CalendarScreen(Player player) {
        super(HFItems.CALENDAR.get().getName(HFItems.CALENDAR.toStack()), CALENDAR_TEXTURE, 226, 176);
        season = SeasonHandler.getSeason(player.level());
        year = SeasonHandler.getYear(player.level());
        //TODO: Add Festivals

//        CalendarAPI.INSTANCE.getFestivals().forEach((key, value) -> {
//            if (!value.isHidden()) {
//                entries.get(getNextDay(key)).add(value);
//            }
//        });

        //TODO: Add NPC Birthdays
//        NPC.REGISTRY.values().forEach(npc -> {
//            if (HFApi.player.getRelationsForPlayer(player).isStatusMet(npc, RelationStatus.MET)) {
//                entries.get(npc.getBirthday()).add(npc);
//            }
//        });
    }

    //    private CalendarDate getNextDay(CalendarDate date) {
//        if (date.getDay() < 30) return new CalendarDate(date.getDay() + 1, date.getSeason(), date.getYear());
//        else {
//            if (date.getSeason() == Season.WINTER) return new CalendarDate(1, Season.SPRING, date.getYear());
//            else {
//                Season next = CalendarHelper.SEASONS[season.ordinal() + 1];
//                return new CalendarDate(1, next, date.getYear());
//            }
//        }
//    }

    @Override
    public void initScreen(@NotNull Minecraft minecraft, @NotNull Player player) {
        rows = getNumberOfRows();
//        //int yExtra = rows
//        for (int day = 0; day < 30; day++) {
//            CalendarDate date = new CalendarDate(day, season, year);
//            buttonList.add(new ButtonDate(this, day, getStacksForDate(new CalendarDate(day * (DAYS_PER_SEASON / 30), season, year)), guiLeft + getXForDate(date), guiTop + getYForDate(date)));
//        }
//
//        if ((GuiCalendar.year > 0 || (GuiCalendar.year == 0 && GuiCalendar.season != Season.SPRING))) {
//            buttonList.add(new ButtonPrevious(this, buttonList.size(), guiLeft + 30, guiTop - 12));
//        }
//
//        buttonList.add(new ButtonNext(this, buttonList.size(), guiLeft + 180, guiTop - 12));
    }

//    private List<CalendarEntry> getStacksForDate(CalendarDate date) {
//        return entries.entries().stream().filter(entry -> entry.getKey().isSameDay(date)).map(Entry::getValue).collect(Collectors.toList());
//    }

    private int getNumberOfRows() {
        //TODO, Potentially increase rows if wanted?
//        int max = 0;
//        for (int day = 0; day < 30; day++) {
//            CalendarDate date = new CalendarDate(day, season, year);
//            int season = (date.getYear() * 4) + date.getSeason().ordinal();
//            int x = (((date.getDay() + (season * 2)) % 7));
//            int value = (int) ((double) (date.getDay() - x) + 13) / 7;
//            if (value > max) max = value;
//        }

        return TimeUnitRegistry.getOrDefault("season_length_multiplier", 4);
    }

    private int getXForDate(CalendarDate date) {
        //Always same?
        return 14;
//        int season = (date.getYear() * 4) + date.getSeason().ordinal();
//        int value = (((date.getDay() + (season * 2)) % 7));
//        return 14 + (value * 30);
    }

    private int getYForDate(CalendarDate date) {
        //always same?
        return -6;
//        int season = (date.getYear() * 4) + date.getSeason().ordinal();
//        int x = (((date.getDay() + (season * 2)) % 7));
//        int value = (int)((double)(date.getDay() - x) + 13) / 7;
//        return (value * 30) - 6;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int x, int y, float pPartialTick) {
        super.renderBackground(graphics, x, y, pPartialTick);
        for (int i = 0; i < rows; i++) {
            if (i == 0) graphics.blit(texture, leftPos + 8, topPos + 22, 0, 224, 226, 32);
            else graphics.blit(texture, leftPos + 8, topPos + 22 + 30 * i, 0, 224, 226, 32);

            //if (i == 0) graphics.blit(texture, leftPos + 8, topPos + 26, 8, 26, 226, 26);
            //else graphics.blit(texture, leftPos + 8, topPos + 22 + 30 * i, 8, 22, 210, 30);
            //if (i == 0) graphics.blit(texture, leftPos + 10, topPos + 22, 8, 26, 210, 26);
            //else graphics.blit(texture, leftPos + 8, topPos + 18 + 30 * i, 8, 22, 210, 30);
        }

        //graphics.blit(texture, leftPos + 10, topPos + 18 + 30 * rows, 8, 52, 210, 2);
        graphics.drawCenteredString(font,
                Component.translatable("gui.harvestfestival.calendar.format",
                        Component.translatable("season.harvestfestival." + season.name().toLowerCase()),
                        (year + 1)),
                leftPos + 113, topPos - 10, 0xFFFFFF);
    }

    @Override
    public void renderForeground(GuiGraphics graphics, int x, int y, float pPartialTick) {
        for (DayOfWeek weekday: WEEKDAYS) {
            graphics.drawString(PenguinFonts.FANCY.get(), WEEKDAY_NAMES.get(weekday),leftPos + 12 + weekday.ordinal() * 30, topPos + 10, 0xF1B81F);
        }
    }
}