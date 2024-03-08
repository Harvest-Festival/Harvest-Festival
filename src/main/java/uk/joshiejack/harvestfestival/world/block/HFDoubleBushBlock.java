package uk.joshiejack.harvestfestival.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.neoforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class HFDoubleBushBlock extends DoublePlantBlock {
    public static final MapCodec<HFDoubleBushBlock> CODEC = simpleCodec(HFDoubleBushBlock::new);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private PlantType type = PlantType.PLAINS;

    public HFDoubleBushBlock(Properties properties) {
        super(properties);
    }

    public HFDoubleBushBlock withPlantType(PlantType type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull PlantType getPlantType(@NotNull BlockGetter level, @NotNull BlockPos pos) {
        return type;
    }

    @Override
    public @NotNull MapCodec<HFDoubleBushBlock> codec() {
        return CODEC;
    }
}