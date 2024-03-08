package uk.joshiejack.harvestfestival.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class HFCommands {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, HarvestFestival.MODID);
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<?, ?>> MINE_ARGUMENT = ARGUMENT_TYPES.register("mine_dimension", () -> ArgumentTypeInfos.registerByClass(MineDimensionArgument.class, SingletonArgumentInfo.contextFree(MineDimensionArgument::mineDimension)));

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(HarvestFestival.MODID)
                        .requires(cs -> cs.hasPermission(2))
                        .then(TPMineCommand.register())
                        .then(EnergyCommand.register())
                        .then(HealthCommand.register())
        );
    }
}

