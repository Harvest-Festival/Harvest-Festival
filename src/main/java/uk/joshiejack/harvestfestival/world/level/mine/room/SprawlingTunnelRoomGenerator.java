package uk.joshiejack.harvestfestival.world.level.mine.room;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

public class SprawlingTunnelRoomGenerator extends TunnelRoomGenerator {
    public static final Codec<SprawlingTunnelRoomGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("min_random", 12).forGetter(generator -> generator.minRandom),
            Codec.INT.optionalFieldOf("min_tunnel_width", 2).forGetter(generator -> generator.minTunnelWidth),
            Codec.INT.optionalFieldOf("max_tunnel_width", 8).forGetter(generator -> generator.maxTunnelWidth),
            Codec.INT.optionalFieldOf("connection_count", 30).forGetter(generator -> generator.connectionCount),
            Codec.INT.optionalFieldOf("connection_distance", 16).forGetter(generator -> generator.connectionDistance),
            Codec.INT.optionalFieldOf("distance_min", 60).forGetter(generator -> generator.distanceMin),
            Codec.INT.optionalFieldOf("distance_random", 20).forGetter(generator -> generator.distanceRandom)
    ).apply(instance, SprawlingTunnelRoomGenerator::new));

    private final int connectionCount; //Default 100
    private final int connectionDistance; //Default 20
    private final int distanceMin; //Default 80
    private final int distanceRandom;

    public SprawlingTunnelRoomGenerator(int min_random, int tunnelMin, int tunnelMax, int connectionCount, int connectionDistance, int distanceMin, int distanceRandom) {
        super(min_random, tunnelMin, tunnelMax);
        this.connectionCount = connectionCount;
        this.connectionDistance = connectionDistance;
        this.distanceMin = distanceMin;
        this.distanceRandom = distanceRandom;
    }

    public static SprawlingTunnelRoomGenerator sprawlingTunnelRoomGenerator() {
        return new SprawlingTunnelRoomGenerator(12, 2, 8, 30, 16, 60, 20);
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladderIn) {
        generateSimpleRoom(world, ladderIn); //Generate initial room
        //BlockPos target2 = ladderIn.add(20, 0, 20);
        //boolean doReverse = world.rand.nextBoolean();
        boolean reverse = false;
        for (int i = connectionCount; i >= 0; i -= connectionDistance) {
            int x = reverse ? world.settings.blocksPerMine() - i : i;
            BlockPos target = new BlockPos(x, 0, ladderIn.getZ());
            connect(world, ladderIn, target);

            int z = reverse ? world.settings.blocksPerMine() - i : i;
            ladderIn = new BlockPos(target.getX(), 0, z);
            connect(world, target, ladderIn);
            reverse = !reverse;
        }

        int dRandom = Math.max(distanceRandom, 1);
        BlockPos end = new BlockPos(distanceMin - world.rand.nextInt(dRandom) + world.rand.nextInt(dRandom), 0, distanceMin - world.rand.nextInt(dRandom) + world.rand.nextInt(dRandom));
        connect(world, ladderIn, end);
        return generateSimpleRoom(world, end).getRight();
    }
}
