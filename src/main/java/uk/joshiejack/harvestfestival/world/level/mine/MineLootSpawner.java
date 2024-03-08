package uk.joshiejack.harvestfestival.world.level.mine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.ChunkDataEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.Loot;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.LevelChunkSectionWrapper;
import uk.joshiejack.penguinlib.util.helper.TimeHelper;

/* This class is responsible for redecorating the mine with new loot, it will
 * only redecorate the mine once per day, and will only redecorate the mine
 * if the mine is loaded. */
@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class MineLootSpawner {

    /* This method is called when a chunk is loaded, it will redecorate the mine
     * with new loot, if the mine is not loaded, it will not redecorate it */
    @SubscribeEvent
    public static void onChunkLoaded(ChunkDataEvent.Load event) {
        if (event.getChunk().getPos().x >= 0 && event.getChunk().getPos().z >= 0 &&
                event.getChunk() instanceof LevelChunk && event.getLevel() instanceof ServerLevel level) {
            if (level.getChunkSource().getGenerator() instanceof MineChunkGenerator generator) {
                int days = TimeHelper.getElapsedDays(level);
                long lastUpdate = event.getData().getLong("LastUpdate"); //The last time this chunk was updated
                int lastDayUpdate = TimeHelper.getElapsedDays(lastUpdate);
                if (days - lastDayUpdate < 1) return; //Only redecorate once per day
                generateLoot(event.getChunk(), generator, level);
            }
        }
    }

    /* This method is responsible for redecorating the mine with new loot, it
     * will only redecorate the mine if the mine is valid. */
    public static void generateLoot(ChunkAccess access, MineChunkGenerator generator, WorldGenLevel level) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        LevelChunkSection[] sections = access.getSections();
        for (int i = sections.length - 1; i >= 0; --i) {
            LevelChunkSection section = sections[i];
            if (section != null) {
                //Sections are going from the top to the bottom of the world
                for (int y = 15; y >= 0; y--) {
                    int actualY = ((i * 16) + y) + level.getMinBuildHeight();
                    if (isValidFloor(generator.settings, actualY)) {
                        BlockPos actualPos = pos.set(access.getPos().getMinBlockX(), actualY, access.getPos().getMinBlockZ());
                        Holder<MineTier> tier = generator.settings.getTierByPos(actualPos);
                        if (tier == null) continue;
                        clearOldLoot(section, y, tier);
                        int relativeFloor = MineHelper.getRelativeFloor(generator.settings, actualY);
                        int floor = MineHelper.getFloorFromPos(level, generator.settings, actualPos);
                        //i = 16 is the top of the world
                        //i = 15 is the second to top of the world
                        spawnNewLoot(new LevelChunkSectionWrapper(level, section, i > 0 ? sections[i - 1] : null, generator.settings, tier.value(), level.getRandom(), floor, relativeFloor), tier, level.getRandom(), y, pos);
                    }
                }
            }
        }
    }

    /* This method spawns new loot in the mine, it will only spawn loot if the
     * mine is valid, and the loot is valid for the tier. */
    private static void spawnNewLoot(LevelChunkSectionWrapper wrapper, Holder<MineTier> tier, RandomSource random, int y, BlockPos.MutableBlockPos pos) {
        //Redecorate
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                pos.set(x, y, z);
                if (!tier.value().canDecorate(random, wrapper.floor, wrapper.relativeFloor) || !wrapper.isAirBlock(pos)) continue;
                Loot loot = tier.value().getLoot(wrapper.rand, wrapper.floor, wrapper.relativeFloor);
                if (loot != null) {
                    decorate(wrapper, pos, loot);
                }
            }
        }
    }

    /* This method clears out the old loot from the mine, so that it can be
     * redecorated with new loot. */
    private static void clearOldLoot(LevelChunkSection section, int y, Holder<MineTier> tier) {
        //Clear everything out
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockState state = section.getBlockState(x, y, z);
                if (tier.value().isLoot(state))
                    section.setBlockState(x, y, z, Blocks.AIR.defaultBlockState());
            }
        }
    }

    /* This method checks if the floor is valid for redecoration */
    public static boolean isValidFloor(MineSettings settings, int yHeight) {
        return Math.abs(yHeight % settings.floorHeight()) == 6;
    }

    public static boolean isValidSpawnLocation(LevelChunkSectionWrapper world, BlockPos pos) {
        return world.getBlockState(pos).isAir() && world.getBlockState(pos.below()).isFaceSturdy(world.level, pos.below(), net.minecraft.core.Direction.UP);
    }

    public static void decorate(LevelChunkSectionWrapper world, BlockPos pos, Loot loot) {
        if (loot.stackable()) {
            RandomSource random = world.rand;
            int i = 2 + random.nextInt(2);
            int j = 2 + random.nextInt(2);

            for(BlockPos blockpos1 : BlockPos.betweenClosed(pos.offset(-i, 0, -j), pos.offset(i, 1, j))) {
                int k = pos.getX() - blockpos1.getX();
                int l = pos.getZ() - blockpos1.getZ();
                if (!world.hasBuffer(blockpos1, 1)) continue;
                if ((float)(k * k + l * l) <= random.nextFloat() * 10.0F - random.nextFloat() * 6.0F) {
                    if (isValidSpawnLocation(world, blockpos1) && !world.isAirBlock(blockpos1.below()))
                        world.setBlockState(blockpos1, loot.state());
                } else if ((double)random.nextFloat() < 0.031 && !world.isAirBlock(blockpos1.below())) {
                    if (isValidSpawnLocation(world, blockpos1))
                        world.setBlockState(blockpos1, loot.state());
                }
            }
        } else {
            if (loot.clusterDistance() > 0) {
                for (int i = 0; i < loot.clusterAmount(); ++i) {
                    BlockPos blockpos = pos.offset(world.rand.nextInt(loot.clusterDistance()) - world.rand.nextInt(loot.clusterDistance()), 0, world.rand.nextInt(loot.clusterDistance()) - world.rand.nextInt(loot.clusterDistance()));
                    if (isValidSpawnLocation(world, blockpos)) {
                        world.setBlockState(blockpos, loot.state());
                    }
                }
            }

            if (isValidSpawnLocation(world, pos)) {
                world.setBlockState(pos, loot.state());
            }
        }
    }
}
