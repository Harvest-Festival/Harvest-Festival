package uk.joshiejack.harvestfestival.scripting;

import joptsimple.internal.Strings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import org.apache.commons.io.IOUtils;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.scripting.wrappers.TelevisionJS;
import uk.joshiejack.harvestfestival.world.block.entity.TelevisionBlockEntity;
import uk.joshiejack.penguinlib.PenguinLib;
import uk.joshiejack.penguinlib.event.ScriptingEvents;
import uk.joshiejack.penguinlib.scripting.ScriptLoader;

import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class HFScripting {
    @SubscribeEvent
    public static void onCollectWrappers(ScriptingEvents.CollectWrapper event) {
        event.register(TelevisionJS.class, TelevisionBlockEntity.class);
    }

    public static String getJavascriptFromResourceLocation(ResourceManager manager, ResourceLocation resourcelocation) {
        String namespace = resourcelocation.getNamespace();
        String path = PenguinLib.MODID + "/scripts/" + resourcelocation.getPath();
        ResourceLocation location = new ResourceLocation(namespace, path + (path.contains(".js") ? Strings.EMPTY : ".js"));
        try (Reader reader = manager.getResource(location).get().openAsReader()) {
            String javascript = IOUtils.toString(reader);
            return ScriptLoader.requireToJavascript(manager, javascript);
        } catch (IOException | NoSuchElementException e) {
            return "print('Could not find a quest script @ %s')".formatted(location); //Nothing found so let's return a simple print
        }
    }
}