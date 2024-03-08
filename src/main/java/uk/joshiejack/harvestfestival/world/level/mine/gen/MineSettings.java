package uk.joshiejack.harvestfestival.world.level.mine.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

import javax.annotation.Nullable;
import java.util.Comparator;

public record MineSettings(HolderSet<MineTier> tiers, int chunksPerMine, int floorHeight, int elevatorFrequency, int worldHeightStart) {
    public static final Codec<MineSettings> DIRECT_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            MineTier.LIST_CODEC.fieldOf("tiers").forGetter(inst -> inst.tiers),
                            Codec.INT.optionalFieldOf("chunks_per_mine", 10).forGetter(inst -> inst.chunksPerMine),
                            Codec.INT.optionalFieldOf("floor_height", 8).forGetter(inst -> inst.floorHeight),
                            Codec.INT.optionalFieldOf("elevator_frequency", 5).forGetter(inst -> inst.elevatorFrequency),
                            Codec.INT.optionalFieldOf("world_height_start", 0).forGetter(inst -> inst.worldHeightStart)
                    )
                    .apply(instance, MineSettings::new)
    );

    public int blocksPerMine() {
        return chunksPerMine * 16;
    }

    @Nullable
    public Holder<MineTier> getTierByPos(BlockPos pos) {
        int tierNumber = MineHelper.getTierFromPos(this, pos);
        if (tierNumber < 0)
            return null;
        if (tierNumber >= tiers.size())
            tierNumber = tiers.size() - 1;

        //Return the stream sorted by the tier order
        return tiers.stream().sorted(Comparator.comparingInt(tier -> tier.value().order)).toList().get(tierNumber);
    }

    public int floorsPerMine(int worldHeight) {
        return worldHeight / floorHeight;
    }
}
