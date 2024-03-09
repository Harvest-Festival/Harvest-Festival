package uk.joshiejack.harvestfestival.world.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.PlantType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.HFData;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.CropData;
import uk.joshiejack.harvestfestival.world.level.ticker.crop.stage.StageHandler;
import uk.joshiejack.penguinlib.util.PenguinTags;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;
import uk.joshiejack.penguinlib.world.item.PenguinRegistryItem;

import static net.minecraft.network.chat.TextColor.parseColor;

public record SeedData(@Nullable Component name, ItemStack seeds, Block block, String color, IPlantable plantable) implements ReloadableRegistry.PenguinRegistry<SeedData>, PenguinRegistryItem.Nameable {
    public static final SeedData EMPTY = new SeedData(Component.empty(), ItemStack.EMPTY, Blocks.AIR, "0", PlantType.CROP);
    public static Object2IntMap<SeedData> COLORS = new Object2IntOpenHashMap<>();

    @SuppressWarnings("ConstantConditions")
    public static final Codec<SeedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("Name", Component.empty()).forGetter(data -> data.name),
            ItemStack.CODEC.fieldOf("Seeds").forGetter(data -> data.seeds),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("Block").forGetter(data -> data.block),
            Codec.STRING.optionalFieldOf("Color", "0").forGetter(data -> data.color),
            Codec.STRING.optionalFieldOf("Type", "CROP").forGetter(data -> data.plantable.getPlantType(null, null).getName())
    ).apply(instance, (name, seeds, block, color, type) -> new SeedData(name, seeds, block, color, PlantType.get(type))));

    public SeedData(@Nullable Component name, ItemStack seeds, Block block, String color, PlantType type) {
        this(name, seeds, block, color, new IPlantable() {
            @Override
            public @NotNull PlantType getPlantType(@NotNull BlockGetter world, @NotNull BlockPos pos) {
                return type;
            }

            @Override
            public @NotNull BlockState getPlant(@NotNull BlockGetter level, @NotNull BlockPos pos) {
                return block.defaultBlockState();
            }
        });
    }

    //Default crop type
    public static SeedData of(Component name, ItemStack seeds, Block block, String color) {
        return new SeedData(name, seeds, block, color, PlantType.CROP);
    }

    //No name
    public static SeedData of(ItemStack seeds, Block block, String color) {
        return new SeedData(Component.empty(), seeds, block, color, PlantType.CROP);
    }

    public BlockState blockState() {
        return block.defaultBlockState();
    }

    public IPlantable plantable() {
        return plantable;
    }

    public ItemStack seeds() {
        return seeds;
    }

    @Nullable
    private CropData data() {
        return block.builtInRegistryHolder().getData(HFData.CROP_DATA);
    }

    public int days() {
        CropData data =  data();
        if (data == null) return -1;
        StageHandler<?> handler = HFRegistries.STAGE_HANDLERS.get(data.stageHandler());
        return handler == null ? -1 : handler.maximumStage() + 1;
    }

    @SuppressWarnings("deprecation")
    public boolean requiresSickle() {
        return block.defaultBlockState().requiresCorrectToolForDrops() && block.builtInRegistryHolder().is(PenguinTags.MINEABLE_SICKLE);
    }

    public boolean requiresWater() {
        CropData data = data();
        return data != null && data.requiresWater();
    }

    public int getColor() {
        if (!color.startsWith("#") && ChatFormatting.getByName(color) == null) return -1;
        return COLORS.computeIfAbsent(this, k -> parseColor(color).get().left().get().getValue());
    }

    @SuppressWarnings("ConstantConditions")
    public Component getName() {
        return name.getContents().equals(Component.empty().getContents()) ? seeds.isEmpty() ? Component.literal(BuiltInRegistries.BLOCK.getKey(block).toString()) : seeds.getDisplayName() : name;//name.getString().isEmpty() ? seeds.isEmpty() ? Component.literal(BuiltInRegistries.BLOCK.getKey(block).toString()) : seeds.getDisplayName() : name;
    }

    @Override
    public ResourceLocation id() {
        return HFRegistries.SEEDS.getID(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public SeedData fromNetwork(FriendlyByteBuf buf) {
        return new SeedData(buf.readComponent(), buf.readItem(), buf.readById(BuiltInRegistries.BLOCK), buf.readUtf(), PlantType.get(buf.readUtf()));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeComponent(name);
        buf.writeItem(seeds);
        buf.writeId(BuiltInRegistries.BLOCK, block);
        buf.writeUtf(color);
        buf.writeUtf(plantable.getPlantType(null, null).getName());
    }
}
