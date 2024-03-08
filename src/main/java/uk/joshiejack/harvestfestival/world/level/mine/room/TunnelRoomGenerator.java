package uk.joshiejack.harvestfestival.world.level.mine.room;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

public class TunnelRoomGenerator extends RoomGenerator {
    public static final Codec<TunnelRoomGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("min_random", 12).forGetter(generator -> generator.minRandom),
            Codec.INT.optionalFieldOf("min_tunnel_width", 3).forGetter(generator -> generator.minTunnelWidth),
            Codec.INT.optionalFieldOf("max_tunnel_width", 8).forGetter(generator -> generator.maxTunnelWidth)
    ).apply(instance, TunnelRoomGenerator::new));

    protected final int minRandom;
    protected final int minTunnelWidth;
    protected final int maxTunnelWidth;

    public TunnelRoomGenerator(int minRandom, int minTunnelWidth, int maxTunnelWidth) {
        this.minRandom = minRandom;
        this.minTunnelWidth = Math.max(minTunnelWidth, 1);
        this.maxTunnelWidth = Math.max(maxTunnelWidth, 1);
    }

    public static TunnelRoomGenerator tunnelRoomGenerator() {
        return new TunnelRoomGenerator(12, 3, 8);
    }

    @Override
    protected Codec<? extends RoomGenerator> codec() {
        return CODEC;
    }

    @Override
    public Complexity getComplexity() {
        return Complexity.COMPLEX;
    }

    void connect(DecoratorWrapper world, BlockPos ladder, BlockPos target) {
        int ladderX = ladder.getX();
        int ladderZ = ladder.getZ();
        RandomSource rand = world.rand;
        int timeassameX = 0;
        int timeassameZ = 0;
        int prevX = ladderX;
        int prevZ = ladderZ;
        int walkawaycounter = 0;
        int randomisertimer = 0;
        int ladder7 = world.rand.nextInt(7, 11);
        int ladderFive = world.rand.nextInt(5, 8);
        while (ladderX != target.getX() || ladderZ != target.getZ()) {
            if (walkawaycounter <= 0) {
                if (rand.nextInt(16) == 0) {
                    walkawaycounter = 6 + rand.nextInt(6);
                }
            }

            boolean traverseX = rand.nextBoolean();
            int width = minTunnelWidth + rand.nextInt(Math.max(maxTunnelWidth - minTunnelWidth, 1));
            if (prevX == ladderX) timeassameX++;
            else timeassameX = 0;
            if (prevZ == ladderZ) timeassameZ++;
            else timeassameZ = 0;

            prevX = ladderX;
            prevZ = ladderZ;

            if ((timeassameX >= ladderFive || timeassameZ >= ladderFive || randomisertimer > 0) && ladderX > ladder7 && ladderX < (world.settings.blocksPerMine() - ladder7) && ladderZ > ladder7 && ladderZ < (world.settings.blocksPerMine() - ladder7)) {
                ladderX = ladderX - 1 + rand.nextInt(3);
                ladderZ = ladderZ - 1 + rand.nextInt(3);
                if (randomisertimer <= 0) randomisertimer = 2 + rand.nextInt(3);
                else randomisertimer--;
            } else if (walkawaycounter > 0) {
                walkawaycounter--;
                if (ladderX > 7 && ladderX < (world.settings.blocksPerMine() - ladder7) && ladderZ > 7 && ladderZ < (world.settings.blocksPerMine() - ladder7)) {
                    if (traverseX) {
                        if (ladderX > target.getX()) ladderX++;
                        else if (ladderX < target.getX()) ladderX--;
                    } else {
                        if (ladderZ > target.getZ()) ladderZ++;
                        else if (ladderZ < target.getZ()) ladderZ--;
                    }
                }
            } else if (traverseX) {
                if (ladderX > target.getX()) ladderX--;
                else if (ladderX < target.getX()) ladderX++;
            } else {
                if (ladderZ > target.getZ()) ladderZ--;
                else if (ladderZ < target.getZ()) ladderZ++;
            }

            for (int w = 0; w < width; w++) {
                BlockPos connectTarget = new BlockPos(ladderX + (!traverseX ? w : 0), 0, ladderZ + (traverseX ? w : 0));
                world.setBlockState(connectTarget, world.tier.getFloor(world.floor));
                int yNum = world.settings.floorHeight() / 2;
                for (int y = 1; y < yNum + rand.nextInt(yNum); y++) {
                    world.setBlockState(connectTarget.above(y), Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladderIn) {
        int min = minRandom + 1;
        int max = world.settings.chunksPerMine() * 16 - (min * 2);
        BlockPos target = new BlockPos(min + world.rand.nextInt(max), 0, min + world.rand.nextInt(max));
        BlockPos ladder = generateSimpleRoom(world, target).getRight();
        connect(world, ladderIn, ladder);
        return ladder;
    }
}
