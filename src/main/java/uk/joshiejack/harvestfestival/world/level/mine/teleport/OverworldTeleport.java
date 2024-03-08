package uk.joshiejack.harvestfestival.world.level.mine.teleport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.neoforged.neoforge.common.util.ITeleporter;

import java.util.EnumSet;
import java.util.function.Function;

public class OverworldTeleport implements ITeleporter {
    private final BlockPos target;
    private final Direction direction;

    public OverworldTeleport(BlockPos target, Direction direction) {
        this.target = target;
        this.direction = direction;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity.setPortalCooldown(20);
        entity.teleportTo(destWorld, (double) target.getX() + 0.5, target.getY(), (double) target.getZ() + 0.5, EnumSet.noneOf(RelativeMovement.class), direction.getOpposite().toYRot(), 0F);
        return entity;
    }
}
