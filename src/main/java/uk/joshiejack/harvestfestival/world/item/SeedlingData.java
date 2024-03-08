package uk.joshiejack.harvestfestival.world.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.level.ticker.tree.TreeData;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;

import static net.minecraft.network.chat.TextColor.parseColor;

//TODO: Season requirements
public record SeedlingData(@Nullable Component name, Block block, String color) implements ReloadableRegistry.PenguinRegistry<SeedlingData> {
    public static final SeedlingData EMPTY = new SeedlingData(Component.empty(), Blocks.AIR, "0");
    public static Object2IntMap<SeedlingData> COLORS = new Object2IntOpenHashMap<>();

    @SuppressWarnings("ConstantConditions")
    public static final Codec<SeedlingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("Name", Component.empty()).forGetter(data -> data.name),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("Block").forGetter(data -> data.block),
            Codec.STRING.optionalFieldOf("Color", "0").forGetter(data -> data.color)
    ).apply(instance, SeedlingData::new));

    //Default crop type
    public static SeedlingData of(Component name, Block block, String color) {
        return new SeedlingData(name, block, color);
    }

    //No name
    public static SeedlingData of(Block block, String color) {
        return new SeedlingData(Component.empty(), block, color);
    }

    @SuppressWarnings("deprecation")
    public int days() {
        TreeData seedling = block.builtInRegistryHolder().getData(HFData.TREE_DATA);
        if (seedling == null) return -1;
        TreeData sapling = seedling.next().getBlockHolder().getData(HFData.TREE_DATA);
        if (sapling == null) return -1;
        TreeData juvenile = sapling.next().getBlockHolder().getData(HFData.TREE_DATA);
        if (juvenile == null) return -1; //TODO: Check the maths
        return seedling.days() + sapling.days() + juvenile.days();
    }

    @SuppressWarnings("deprecation")
    public int getColor() {
        if (!color.startsWith("#") && ChatFormatting.getByName(color) == null) return -1;
        return COLORS.computeIfAbsent(this, k -> parseColor(color).get().left().get().getValue());
    }

    @SuppressWarnings("ConstantConditions")
    public Component getName() {
        return name.getContents().equals(Component.empty().getContents()) ? block == null ? Component.literal(BuiltInRegistries.BLOCK.getKey(block).toString()) : block.getName() : name;//name.getString().isEmpty() ? seeds.isEmpty() ? Component.literal(BuiltInRegistries.BLOCK.getKey(block).toString()) : seeds.getDisplayName() : name;
    }

    @Override
    public ResourceLocation id() {
        return HFRegistries.SEEDLINGS.getID(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public SeedlingData fromNetwork(FriendlyByteBuf buf) {
        return new SeedlingData(buf.readComponent(), buf.readById(BuiltInRegistries.BLOCK), buf.readUtf());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeComponent(name);
        buf.writeId(BuiltInRegistries.BLOCK, block);
        buf.writeUtf(color);
    }
}
