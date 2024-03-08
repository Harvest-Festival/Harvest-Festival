package uk.joshiejack.harvestfestival.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MineDimensionArgument implements ArgumentType<ResourceLocation> {
    private static final Collection<String> EXAMPLES = Stream.of("harvestfestival:mine").collect(Collectors.toList());
    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType(
            p_304084_ -> Component.translatableEscape("argument.dimension.invalid", p_304084_)
    );

    public ResourceLocation parse(StringReader p_88807_) throws CommandSyntaxException {
        return ResourceLocation.read(p_88807_);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return context.getSource() instanceof SharedSuggestionProvider
                ? SharedSuggestionProvider.suggestResource(((SharedSuggestionProvider)context.getSource()).levels().stream().filter(k -> {
                    if (context.getSource() instanceof CommandSourceStack) {
                        ServerLevel level = ((CommandSourceStack)context.getSource()).getLevel();
                        return level.getChunkSource().getGenerator() instanceof MineChunkGenerator;
                    } else return false;
        }).map(ResourceKey::location), builder)
                : Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static MineDimensionArgument mineDimension() {
        return new MineDimensionArgument();
    }

    public static ServerLevel getDimension(CommandContext<CommandSourceStack> source, String argument) throws CommandSyntaxException {
        ResourceLocation resourcelocation = source.getArgument(argument, ResourceLocation.class);
        ResourceKey<Level> resourcekey = ResourceKey.create(Registries.DIMENSION, resourcelocation);
        ServerLevel serverlevel = source.getSource().getServer().getLevel(resourcekey);
        if (serverlevel == null || !(serverlevel.getChunkSource().getGenerator() instanceof MineChunkGenerator)) {
            throw ERROR_INVALID_VALUE.create(resourcelocation);
        } else {
            return serverlevel;
        }
    }
}
