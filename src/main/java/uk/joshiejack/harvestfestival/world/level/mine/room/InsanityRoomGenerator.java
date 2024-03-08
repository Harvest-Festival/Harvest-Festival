package uk.joshiejack.harvestfestival.world.level.mine.room;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsanityRoomGenerator extends TunnelRoomGenerator {
    public static final Codec<InsanityRoomGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("min_random", 16).forGetter(generator -> generator.minRandom),
            Codec.INT.optionalFieldOf("min_tunnel_width", 3).forGetter(generator -> generator.minTunnelWidth),
            Codec.INT.optionalFieldOf("max_tunnel_width", 6).forGetter(generator -> generator.maxTunnelWidth),
            Codec.INT.optionalFieldOf("connection_count", 4).forGetter(generator -> generator.connectionCount)
    ).apply(instance, InsanityRoomGenerator::new));

    private final int connectionCount; //Default 8

    public InsanityRoomGenerator(int min_random, int tunnelMin, int tunnelMax, int connectionCount) {
        super(min_random, tunnelMin, tunnelMax);
        this.connectionCount = connectionCount;
    }

    public static InsanityRoomGenerator insanityRoomGenerator() {
        return new InsanityRoomGenerator(16, 3, 6, 4);
    }

    @Override
    public BlockPos generate(DecoratorWrapper world, BlockPos ladderIn) {
        generateSimpleRoom(world, ladderIn); //Generate initial room

        BlockPos.MutableBlockPos target = new BlockPos.MutableBlockPos();
        target.set(ladderIn); //Initialise the target
        BlockPos.MutableBlockPos ladderOut = new BlockPos.MutableBlockPos();
        List<BlockPos> connections = new ArrayList<>();

        for (int i = 0; i < connectionCount; i++) {
            ladderOut.set(world.rand.nextInt(world.settings.blocksPerMine() - 20), 0, world.rand.nextInt(world.settings.blocksPerMine() - 20)); //Generate a new ladder out
            generateSimpleRoom(world, ladderOut); //Generate a new room at the location
            connect(world, target, ladderOut); //Connect the old room to the new room
            target.set(ladderOut); //Set the old room to the new room
            connections.add(ladderOut.immutable());
        }

        List<BlockPos> reversed = new ArrayList<>(connections);
        Collections.reverse(reversed);
        for (int i = 0 ; i < reversed.size(); i++) {
            connect(world, connections.get(i), reversed.get(i));
        }

        return generateSimpleRoom(world, ladderOut).getRight();
    }
}
