package uk.joshiejack.harvestfestival.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.TeleportType;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.teleport.MineTeleporter;

public class TPMineCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("mine")
                .then(Commands.argument("dimension", MineDimensionArgument.mineDimension())
                        .then(Commands.argument("id", IntegerArgumentType.integer(0, 10000))
                                .then(Commands.argument("floor", IntegerArgumentType.integer(1, 10000))
                                        .executes(ctx -> {
                                            ServerLevel world = ctx.getSource().getLevel();
                                            Entity entity = ctx.getSource().getEntity();
                                            if (entity instanceof Player) {
                                                ServerLevel dimension = MineDimensionArgument.getDimension(ctx, "dimension");
                                                if (!(dimension.getChunkSource().getGenerator() instanceof MineChunkGenerator generator)) {
                                                    ctx.getSource().sendFailure(Component.literal(dimension.dimension().location() + " is not a valid mine dimension"));
                                                    return 0;
                                                }

                                                MineSettings settings = generator.settings;
                                                int floor = IntegerArgumentType.getInteger(ctx, "floor");
                                                int remainder = (floor - 1) % settings.floorsPerMine(dimension.getHeight());
                                                int id = IntegerArgumentType.getInteger(ctx, "id");
                                                if (id < 0) {
                                                    ctx.getSource().sendFailure(Component.literal("ID must be positive"));
                                                    return 0;
                                                } else if (id > 10000) {
                                                    ctx.getSource().sendFailure(Component.literal("ID must be less than 10000"));
                                                    return 0;
                                                }

                                                TeleportType type = remainder == 0 || remainder == (settings.floorsPerMine(dimension.getHeight()) - 1) ? TeleportType.PORTAL : floor % settings.elevatorFrequency() != 1 ? null : TeleportType.ELEVATOR;
                                                //If floor not a multiple of 10 display error
                                                if (type == null) {
                                                    ctx.getSource().sendFailure(Component.literal(IntegerArgumentType.getInteger(ctx, "floor") + " is not a valid mine floor, must be divisible after removing 1 by " + settings.elevatorFrequency()));
                                                    return 0;
                                                }

                                                if (dimension != world) {
                                                    entity.changeDimension(dimension, new MineTeleporter(id, floor, type));
                                                } else
                                                    MineTeleporter.teleportToMineFloor(world, entity, id, floor, type);
                                            }

                                            return 0;
                                        }))));
    }
}