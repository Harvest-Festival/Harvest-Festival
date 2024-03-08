package uk.joshiejack.harvestfestival.data;

import com.google.common.collect.Maps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractLetterProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final Map<ResourceLocation, AbstractLetter<?>> entries = Maps.newHashMap();

    public AbstractLetterProvider(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, HFRegistries.LETTERS.dir());
    }

    @Override
    public @NotNull String getName() {
        return "Letters";
    }

    @Override
    public @NotNull CompletableFuture<?> run(final @NotNull CachedOutput output) {
        final List<CompletableFuture<?>> list = new ArrayList<>();
        buildLetters(entries);
        entries.forEach((key, category) -> list.add(DataProvider.saveStable(output, HFRegistries.LETTERS.codec(), category, pathProvider.json(key))));
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected abstract void buildLetters(Map<ResourceLocation, AbstractLetter<?>> notes);
}
