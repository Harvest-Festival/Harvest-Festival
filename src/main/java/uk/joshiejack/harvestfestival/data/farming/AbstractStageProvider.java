package uk.joshiejack.harvestfestival.data.farming;

import com.google.common.collect.Maps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractStageProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final Map<ResourceLocation, StageHandler<?>> entries = Maps.newHashMap();

    public AbstractStageProvider(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, HFRegistries.STAGE_HANDLERS.dir());
    }

    @Override
    public @NotNull String getName() {
        return "Stage Handlers";
    }

    @Override
    public @NotNull CompletableFuture<?> run(final @NotNull CachedOutput output) {
        final List<CompletableFuture<?>> list = new ArrayList<>();
        buildStages(entries);
        entries.forEach((key, category) -> list.add(DataProvider.saveStable(output, HFRegistries.STAGE_HANDLERS.codec(), category, pathProvider.json(key))));
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected abstract void buildStages(Map<ResourceLocation, StageHandler<?>> notes);
}
