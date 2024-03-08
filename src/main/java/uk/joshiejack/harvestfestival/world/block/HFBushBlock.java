package uk.joshiejack.harvestfestival.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class HFBushBlock extends BushBlock {
    public static final MapCodec<HFBushBlock> CODEC = simpleCodec(HFBushBlock::new);
    private PlantType type = PlantType.PLAINS;

    public HFBushBlock(Properties properties) {
        super(properties);
    }

    public HFBushBlock withPlantType(PlantType type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull PlantType getPlantType(@NotNull BlockGetter level, @NotNull BlockPos pos) {
        return type;
    }

    @Override
    protected @NotNull MapCodec<HFBushBlock> codec() {
        return CODEC;
    }
}