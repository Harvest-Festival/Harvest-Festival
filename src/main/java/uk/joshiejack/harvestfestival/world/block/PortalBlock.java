package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.TeleportType;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.MineTeleporter;
import uk.joshiejack.penguinlib.util.helper.TimeHelper;

import javax.annotation.Nonnull;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class PortalBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.create(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375);
    public static final BooleanProperty EAST_WEST = BooleanProperty.create("ew");
    public static final EnumProperty<Section> SECTION = EnumProperty.create("section", Section.class);
    protected final ResourceKey<Level> dimension;

    public PortalBlock(BlockBehaviour.Properties properties, ResourceKey<Level> dimension) {
        super(properties);
        this.dimension = dimension;
        registerDefaultState(getStateDefinition().any().setValue(EAST_WEST, false).setValue(SECTION, Section.TM));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EAST_WEST, SECTION);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    private boolean isSameBlock(BlockAndTintGetter world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == this;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public BlockState updateShape(@NotNull BlockState state, @NotNull Direction dir, @NotNull BlockState state2, @NotNull LevelAccessor world, BlockPos pos, @NotNull BlockPos pos2) {
        boolean connectedUp = isSameBlock(world, pos.above());
        boolean connectedDown = isSameBlock(world, pos.below());
        boolean connectedEast = isSameBlock(world, pos.east());
        boolean connectedWest = isSameBlock(world, pos.west());
        boolean connectedSouth = isSameBlock(world, pos.south());
        boolean connectedNorth = isSameBlock(world, pos.north());
        if (connectedDown && ((!connectedEast && connectedWest) || (connectedNorth && !connectedSouth))) {
            if (connectedWest) return state.setValue(SECTION, Section.TL).setValue(EAST_WEST, true);
            else return state.setValue(SECTION, Section.TL).setValue(EAST_WEST, false);
        } else if (connectedDown && ((connectedEast && !connectedWest) || (!connectedNorth && connectedSouth))) {
            if (connectedEast) return state.setValue(SECTION, Section.TR).setValue(EAST_WEST, true);
            return state.setValue(SECTION, Section.TR).setValue(EAST_WEST, false);
        } else if (connectedDown && (connectedEast || connectedNorth)) {
            if (connectedWest) return state.setValue(SECTION, Section.TM).setValue(EAST_WEST, true);
            return state.setValue(SECTION, Section.TM).setValue(EAST_WEST, false);
        } else if (connectedUp && ((!connectedEast && connectedWest) || (connectedNorth && !connectedSouth))) {
            if (connectedWest) return state.setValue(SECTION, Section.BL).setValue(EAST_WEST, true);
            return state.setValue(SECTION, Section.BL).setValue(EAST_WEST, false);
        } else if (connectedUp && ((connectedEast && !connectedWest) || (!connectedNorth && connectedSouth))) {
            if (connectedEast) return state.setValue(SECTION, Section.BR).setValue(EAST_WEST, true);
            return state.setValue(SECTION, Section.BR).setValue(EAST_WEST, false);
        } else if (connectedUp && (connectedEast || connectedNorth)) {
            if (connectedEast) return state.setValue(SECTION, Section.BM).setValue(EAST_WEST, true);
            return state.setValue(SECTION, Section.BM).setValue(EAST_WEST, false);
        } else return state;
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (world.isClientSide || !entity.canChangeDimensions() || !(entity instanceof Player)) return;
        if (!state.getValue(SECTION).isMiddle()) return;
        if (MineHelper.isMineWorld((ServerLevel) world)) {
            MineSettings settings = ((MineChunkGenerator)((ServerLevel)world).getChunkSource().getGenerator()).settings;
            int floor = MineHelper.getFloorFromPos(world, settings, pos);
            int realFloor = floor + 1;
            if (realFloor == 1) {
                MineTeleporter.exitMine((ServerLevel) world, entity);
            } else {
                //Teleport within the mine
                boolean upper = floor % settings.floorsPerMine(world.getHeight()) == 0;
                if (upper)
                    MineTeleporter.teleportToMineFloor((ServerLevel) world, entity, MineHelper.getMineIDForTeleport(settings, pos), realFloor - 1, TeleportType.PORTAL);
                boolean lower = floor % settings.floorsPerMine(world.getHeight()) == (settings.floorsPerMine(world.getHeight()) - 1);
                if (lower)
                    MineTeleporter.teleportToMineFloor((ServerLevel) world, entity, MineHelper.getMineIDForTeleport(settings, pos), realFloor + 1, TeleportType.PORTAL);
            }
        } else {
            if (entity.getPortalCooldown()  > 0) return;
            ServerLevel level = world.getServer().getLevel(dimension);
            if (level == null) return;
            long seed = (TimeHelper.getElapsedDays(world) * 31L);
            RandomSource random = RandomSource.create(seed); //Go to the same mine each day depending on your team
            entity.changeDimension(level, new MineTeleporter(1 + random.nextInt(10000), 1, TeleportType.PORTAL));
        }
    }

    public enum Section implements StringRepresentable {
        TL, TM, TR, BL, BM, BR;

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase(Locale.ENGLISH);
        }

        public boolean isMiddle() {
            return this ==  TM || this == BM;
        }
    }
}
