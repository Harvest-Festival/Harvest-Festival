package uk.joshiejack.harvestfestival.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;
import uk.joshiejack.harvestfestival.world.entity.player.energy.EnergyData;

public class HealthCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        //Registers the following commands
        // /hf energy <player> max <amount> (Adjusts the current maximum energy level)
        // /hf energy <player> set <amount> (Sets the current energy level)
        return Commands.literal("health")
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("max")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 20))
                                        .executes(ctx -> {
                                            Player player = EntityArgument.getPlayer(ctx, "player");
                                            EnergyData data = player.getFoodData() instanceof EnergyData ? (EnergyData) player.getFoodData() : new EnergyData(player);
                                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                            data.setMaxHealth(amount);
                                            return 0;
                                        })))
                        .then(Commands.literal("set")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0, EnergyData.MAX_OVERALL))
                                        .executes(ctx -> {
                                            Player player = EntityArgument.getPlayer(ctx, "player");
                                            EnergyData data = player.getFoodData() instanceof EnergyData ? (EnergyData) player.getFoodData() : new EnergyData(player);
                                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                            data.setFoodLevel(amount);
                                            return 0;
                                        }))));
    }
}