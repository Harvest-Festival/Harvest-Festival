package uk.joshiejack.harvestfestival.world.level.mine.room;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

import java.util.ArrayList;
import java.util.List;

public class RandomRoomLocations extends TunnelRoomGenerator {
    public static final Codec<RandomRoomLocations> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("max_circle", 8).forGetter(generator -> generator.minRandom),
            Codec.INT.optionalFieldOf("min_tunnel_width", 3).forGetter(generator -> generator.minTunnelWidth),
            Codec.INT.optionalFieldOf("max_tunnel_width", 5).forGetter(generator -> generator.maxTunnelWidth),
            Codec.INT.optionalFieldOf("random_passes", 3).forGetter(generator -> generator.randomPasses)
    ).apply(instance, RandomRoomLocations::new));

    private final int randomPasses;

    public RandomRoomLocations(int max, int minTunnelWidth, int maxTunnelWidth, int randomPasses) {
        super(max, minTunnelWidth, maxTunnelWidth);
        this.randomPasses = randomPasses;
    }

    public static RandomRoomLocations randomRoomLocations() {
        return new RandomRoomLocations(8, 3, 5, 3);
    }

    @Override
    protected Codec<? extends RoomGenerator> codec() {
        return CODEC;
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladderIn) {
        generateSimpleRoom(world, ladderIn); //Generate initial room
        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < randomPasses; i++) {
            BlockPos pos = null;
            while (pos == null || !world.hasBuffer(pos, 8)) {
                pos = new BlockPos(world.rand.nextInt(16, world.settings.blocksPerMine() - 16), 0, world.rand.nextInt(16, world.settings.blocksPerMine() - 16));
            }

            positions.add(pos);
        }

        //Generate a simple room at all the positions
        for (BlockPos pos: positions) {
            generateSimpleRoom(world, pos);
        }

        //Connect all the rooms together
        int i;
        for (i = 0; i < positions.size() - 1; i++) {
            connect(world, positions.get(i), positions.get(i + 1));
        }

        //Connect the last room to the first
        connect(world, positions.get(i), ladderIn);
        return generateSimpleRoom(world, positions.get(i)).getRight();
    }
}
