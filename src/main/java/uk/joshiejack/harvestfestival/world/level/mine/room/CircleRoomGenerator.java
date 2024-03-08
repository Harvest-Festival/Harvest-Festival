package uk.joshiejack.harvestfestival.world.level.mine.room;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

import java.util.List;

public class CircleRoomGenerator extends RoomGenerator {
    public static final Codec<CircleRoomGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("max", 12).forGetter(generator -> generator.max)
    ).apply(instance, CircleRoomGenerator::new));

    protected final int max;

    public CircleRoomGenerator(int max) {
        this.max = max;
    }

    public static CircleRoomGenerator circleRoomGenerator() {
        return new CircleRoomGenerator(12);
    }

    public static CircleRoomGenerator circleRoomGenerator(int max) {
        return new CircleRoomGenerator(max);
    }

    @Override
    protected Codec<? extends RoomGenerator> codec() {
        return CODEC;
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladder) {
        int radius = (max / 4) + 1 + world.rand.nextInt((max / 2) + 1);
        int adjustTimer = max * 2;
        while(adjustTimer > 0) {
            int x = -(radius / 2) + world.rand.nextInt(radius);
            int z = -(radius / 2) + world.rand.nextInt(radius);
            BlockPos target = ladder.offset(x, 0, z);
            if (world.hasBuffer(target, max) && world.rand.nextInt(radius * 10) == 0) {
                ladder = target;
                break;
            }

            adjustTimer--;
        }

        List<BlockPos> positions = Lists.newArrayList();
        for (int i = -radius; i <= radius; i++) {
            for (int l = -radius; l <= radius; l++) {
                if (i * i + l * l >= (radius + 0.50f) * (radius + 0.50f)) {
                    continue;
                }

                BlockPos target = ladder.offset(i, 0, l);
                world.setBlockState(target, world.tier.getFloor(world.floor));
                int minY = i == -radius || i == radius || l == -radius || l == radius ? 2 : (i < -radius + 4 || i > radius - 4 || l < -radius + 4 || l > radius - 4) ? 3: 4;
                for (int y = 1; y < (minY + world.rand.nextInt(world.settings.floorHeight() - minY)); y++) {
                    world.setBlockState(target.above(y), Blocks.AIR.defaultBlockState());
                }

                positions.add(target);
            }
        }

        return getLadderPosition(world, positions, max, ladder);
    }
}
