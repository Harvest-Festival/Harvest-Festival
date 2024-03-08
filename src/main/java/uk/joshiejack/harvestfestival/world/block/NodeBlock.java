package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.feature.HoleFeature;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;
import uk.joshiejack.penguinlib.world.block.ShapedBlock;

public class NodeBlock extends ShapedBlock {
    public NodeBlock(Properties properties) {
        super(properties, Shapes.create(0.1F, 0F, 0.1F, 0.9F, 0.8F, 0.9F));
    }

    @Override
    public boolean onDestroyedByPlayer(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, boolean willHarvest, @NotNull FluidState fluid) {
        if (level instanceof ServerLevel serverLevel) {
            if (MineHelper.isMineWorld(serverLevel)) {
                MineSettings settings = ((MineChunkGenerator) serverLevel.getChunkSource().getGenerator()).settings;
                Holder<MineTier> tier = settings.getTierByPos(pos);
                if (tier != null) {
                    //if (level.getRandom().nextDouble() <= HFConfig.holeSpawnChanceNodes.get()) {
                        playerWillDestroy(level, pos, state, player);
                        int floor = MineHelper.getRelativeFloor(settings, pos.getY());
                        boolean ret = level.setBlock(pos, fluid.createLegacyBlock(), 3);
                        HoleFeature.attemptToGenerateHole(level, pos, tier.value().getHole(floor), tier.value().getWall(floor), settings.floorHeight());
                        return ret;
                   // }
                }
            }
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
