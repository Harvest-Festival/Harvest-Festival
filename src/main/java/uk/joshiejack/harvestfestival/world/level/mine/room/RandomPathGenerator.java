package uk.joshiejack.harvestfestival.world.level.mine.room;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

public class RandomPathGenerator extends TunnelRoomGenerator {
    public static final Codec<RandomPathGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("max_circle", 8).forGetter(generator -> generator.minRandom),
            Codec.INT.optionalFieldOf("min_tunnel_width", 3).forGetter(generator -> generator.minTunnelWidth),
            Codec.INT.optionalFieldOf("max_tunnel_width", 5).forGetter(generator -> generator.maxTunnelWidth),
            Codec.INT.optionalFieldOf("tunnel_count_min", 2).forGetter(generator -> generator.pathAmountMin),
            Codec.INT.optionalFieldOf("tunnel_count_max", 3).forGetter(generator -> generator.pathAmountMax),
            Codec.INT.optionalFieldOf("tunnel_length_min", 6).forGetter(generator -> generator.pathLengthMin),
            Codec.INT.optionalFieldOf("tunnel_length_max", 12).forGetter(generator -> generator.pathLengthMax)
    ).apply(instance, RandomPathGenerator::new));

    private final int pathAmountMin;
    private final int pathAmountMax;
    private final int pathLengthMin;
    private final int pathLengthMax;

    public RandomPathGenerator(int max, int minTunnelWidth, int maxTunnelWidth, int tunnelCount, int tunnelCountMax, int tunnelLengthMin, int tunnelLengthMax) {
        super(max, minTunnelWidth, maxTunnelWidth);
        this.pathAmountMin = tunnelCount;
        this.pathAmountMax = tunnelCountMax;
        this.pathLengthMin = tunnelLengthMin;
        this.pathLengthMax = tunnelLengthMax;
    }

    public static RandomPathGenerator randomPathGenerator() {
        return new RandomPathGenerator(8, 3, 5, 2, 3, 6, 12);
    }

    @Override
    protected Codec<? extends RoomGenerator> codec() {
        return CODEC;
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladderIn) {
        generateSimpleRoom(world, ladderIn); //Generate initial room
        BlockPos.MutableBlockPos current = new BlockPos.MutableBlockPos();
        current.set(ladderIn);
        int amount = world.rand.nextInt(pathAmountMin, pathAmountMax);
        for (int a = 0; a < amount; a++) {
            int pathLength = world.rand.nextInt(pathLengthMin, pathLengthMax);
            BlockPos previous = new BlockPos(current);
            for (int i = 0; i < pathLength; i++) {
                Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(world.rand);
                world.setBlockState(current, world.tier.getFloor(world.floor));
                int yNum = world.settings.floorHeight() / 2;
                for (int y = 1; y < yNum + world.rand.nextInt(yNum); y++) {
                    world.setBlockState(current.above(y), Blocks.AIR.defaultBlockState());
                }

                previous = new BlockPos(current);
                current.set(current.relative(dir));
            }

            generateSimpleRoom(world, previous);
            connect(world, previous, current);
        }

        //Add a direct path for funsies
        connect(world, current, ladderIn);
        return generateSimpleRoom(world, current).getRight();
    }
}
