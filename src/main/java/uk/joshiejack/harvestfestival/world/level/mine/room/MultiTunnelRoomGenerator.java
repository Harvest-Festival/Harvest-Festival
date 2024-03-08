package uk.joshiejack.harvestfestival.world.level.mine.room;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

public class MultiTunnelRoomGenerator extends TunnelRoomGenerator {
    public static final Codec<MultiTunnelRoomGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("max_circle", 12).forGetter(generator -> generator.minRandom),
            Codec.INT.optionalFieldOf("min_tunnel_width", 3).forGetter(generator -> generator.minTunnelWidth),
            Codec.INT.optionalFieldOf("max_tunnel_width", 8).forGetter(generator -> generator.maxTunnelWidth),
            Codec.INT.optionalFieldOf("tunnel_count_min", 2).forGetter(generator -> generator.tunnelCountMin),
            Codec.INT.optionalFieldOf("tunnel_count_max", 6).forGetter(generator -> generator.tunnelCountMax)
    ).apply(instance, MultiTunnelRoomGenerator::new));

    private final int tunnelCountMin;
    private final int tunnelCountMax;

    public MultiTunnelRoomGenerator(int max, int minTunnelWidth, int maxTunnelWidth, int tunnelCount, int tunnelCountMax) {
        super(max, minTunnelWidth, maxTunnelWidth);
        this.tunnelCountMin = tunnelCount;
        this.tunnelCountMax = tunnelCountMax;
    }

    public static MultiTunnelRoomGenerator multiTunnelRoomGenerator() {
        return new MultiTunnelRoomGenerator(13, 2, 8, 2, 6);
    }

    @Override
    protected Codec<? extends RoomGenerator> codec() {
        return CODEC;
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladderIn) {
        generateSimpleRoom(world, ladderIn); //Generate initial room
        int amount = tunnelCountMin + world.rand.nextInt(Math.max(tunnelCountMax - tunnelCountMin, 1));
        int min = minRandom + 1;
        int max = world.settings.blocksPerMine() - (min * 2);
        while (true) {
            BlockPos target = new BlockPos(min + world.rand.nextInt(max), 0, min + world.rand.nextInt(max));
            //generator = world.tier.getValidGeneratorFromList(world.tier.getSimpleGenerators(), world.rand, target);
            BlockPos ladder = generateSimpleRoom(world, target).getRight();
            connect(world, ladderIn, ladder);
            ladderIn = ladder;
            if (amount == 0) {
                return ladder;
            }

            amount--;
        }
    }
}
