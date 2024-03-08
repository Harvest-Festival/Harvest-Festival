package uk.joshiejack.harvestfestival.world.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.date.CalendarDate;
import uk.joshiejack.penguinlib.util.helper.TimeHelper;
import uk.joshiejack.simplyseasons.api.SSeasonsAPI;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SeasonHandler {
    public static ISeasonHandler INSTANCE;
    public static boolean isInSeason(Level level, BlockPos pos, BlockState state) {
        return INSTANCE == null || INSTANCE.isInSeason(level, pos, state);
    }

    public static boolean applyOutOfSeasonEffect(Level level, BlockPos pos) {
        return INSTANCE != null && INSTANCE.applyOutOfSeasonEffect(level, pos);
    }

    //Helper to get the season of the world
    public static CalendarDate.Season getSeason(Level world) {
        return CalendarDate.Season.SPRING;
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("simplyseasons"))
            INSTANCE = new SimplySeasonsHandler();
        /*else if (ModList.get().isLoaded("sereneseasons")) TODO:
            INSTANCE = new SereneSeasonsHandler(); */
    }

    public static double getSeasonLength(long time) {
        return 28;
    }

    public static int getYear(Level level) {
        return getYear(level.getDayTime());
    }

    public static int getYear(long time) {
        return 1 + (int) Math.floor((double) TimeHelper.getElapsedDays(time) / 4.0 / getSeasonLength(time));
    }

    public interface ISeasonHandler {
        boolean isInSeason(Level level, BlockPos pos, BlockState state);
        boolean applyOutOfSeasonEffect(Level level, BlockPos pos);
    }

    public static class SimplySeasonsHandler implements ISeasonHandler {
        @Override
        public boolean isInSeason(Level level, BlockPos pos, BlockState state) {
            return SSeasonsAPI.instance().canGrow(level, pos, state);
        }

        @Override
        public boolean applyOutOfSeasonEffect(Level level, BlockPos pos) {
            return SSeasonsAPI.instance().applyOutOfSeasonEffect(level, pos);
        }
    }
//TODO:
//    public static class SereneSeasonsHandler implements ISeasonHandler {
//        @Override
//        public boolean isInSeason(Level level, BlockPos pos, BlockState state) {
//            return ModFertility
//        }
//
//        @Override
//        public boolean applyOutOfSeasonEffect(Level level, BlockPos pos) {
//            return false;
//        }
//    }
}
