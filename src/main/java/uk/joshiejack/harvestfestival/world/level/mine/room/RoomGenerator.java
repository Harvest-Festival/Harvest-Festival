package uk.joshiejack.harvestfestival.world.level.mine.room;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import org.apache.commons.lang3.tuple.Pair;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.HFLevel;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

import java.util.List;
import java.util.function.Function;


public abstract class RoomGenerator {
    public static final Codec<RoomGenerator> CODEC = HFLevel.ROOM_GENERATORS.byNameCodec().dispatchStable(RoomGenerator::codec, Function.identity());

    public enum Complexity {
        SIMPLE, COMPLEX
    }

    protected abstract Codec<? extends RoomGenerator> codec();
    public Complexity getComplexity() {
        return Complexity.SIMPLE;
    }

    /**
     * @param   world       the wrapper for modifying the statemap
     * @param   origin      the x and z where the ladder enters this multiple
     * @return the location that we spawned a ladder,
     * int[] must be length of two with the x and z values in 0 and 1 **/
    public abstract BlockPos generate(DecoratorWrapper world, BlockPos origin);

    public boolean canGenerate(MineSettings settings, BlockPos target) {
        return true;
    }

    BlockPos getLadderPosition(DecoratorWrapper world, List<BlockPos> pairs, int distance, BlockPos original) {
        //Prevent infinite loops
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            BlockPos pair = pairs.get(world.rand.nextInt(pairs.size()));
            if (world.hasBuffer(pair, distance)) {
                return pair;
            }
        }

        HarvestFestival.LOGGER.error("Failed to find a valid ladder position for the room generator of type " + this + " at " + original);
        return original;
    }

    protected Pair<RoomGenerator, BlockPos> generateSimpleRoom(DecoratorWrapper world, BlockPos ladder) {
        RoomGenerator generator = world.tier.getValidGeneratorFromList(Complexity.SIMPLE, world.settings, world.rand, ladder);
        BlockPos pos = generator.generate(world, ladder);
        return Pair.of(generator, pos);
    }
}
