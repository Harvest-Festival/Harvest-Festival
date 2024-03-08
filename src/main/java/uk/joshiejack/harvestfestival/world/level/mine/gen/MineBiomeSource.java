package uk.joshiejack.harvestfestival.world.level.mine.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

import java.util.Objects;
import java.util.stream.Stream;

public class MineBiomeSource extends BiomeSource implements BiomeManager.NoiseBiomeSource {
    private HolderSet<Biome> allowedBiomes;
    private final MineSettings settings;

    public MineBiomeSource(MineSettings settings) {
        this.settings = settings;
    }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        return allowedBiomes().stream();
    }

    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        return getNoiseBiome(x, y, z);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int xQ, int yQ, int zQ) {
        int x = QuartPos.toBlock(xQ);
        int y  = QuartPos.toBlock(yQ);
        int z = QuartPos.toBlock(zQ);
        Holder<MineTier> tierHolder = settings.getTierByPos(new BlockPos(x, y, z)); //If we can't find the tier, return the first one
        return Objects.requireNonNullElseGet(tierHolder, () -> Holder.direct(settings.tiers().get(0)).value()).value().getBiome(MineHelper.getRelativeFloor(settings, y));
    }

    private HolderSet<Biome> allowedBiomes() {
        if (allowedBiomes == null) {
            allowedBiomes = HolderSet.direct(settings.tiers().stream()
                    .flatMap(tier -> tier.value().getBiomes().stream())
                    .toList());
        }

        return allowedBiomes;
    }
}
