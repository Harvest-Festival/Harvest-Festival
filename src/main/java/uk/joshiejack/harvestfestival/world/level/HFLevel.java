package uk.joshiejack.harvestfestival.world.level;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.feature.*;
import uk.joshiejack.harvestfestival.world.level.feature.config.*;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineBiomeSource;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineChunkGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.*;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

public class HFLevel {
    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_CODECS = DeferredRegister.create(Registries.CHUNK_GENERATOR, HarvestFestival.MODID);
    public static final DeferredHolder<Codec<? extends ChunkGenerator>, Codec<? extends ChunkGenerator>> MINE_CODEC = CHUNK_CODECS.register("mine_chunk_gen", () -> MineChunkGenerator.CODEC);
    public static final DeferredRegister<Codec<? extends BiomeSource>> BIOME_SOURCE_CODECS = DeferredRegister.create(Registries.BIOME_SOURCE, HarvestFestival.MODID);
    public static final DeferredHolder<Codec<? extends BiomeSource>, Codec<? extends BiomeSource>> MINE_BIOME_SOURCE_CODEC = BIOME_SOURCE_CODECS.register("mine_biome_source", () -> MineBiomeSource.CODEC);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, HarvestFestival.MODID);
    public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> HUGE_BROWN_MUSHROOM = FEATURES.register("huge_brown_mushroom", () -> new UncheckedHugeBrownMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> HUGE_RED_MUSHROOM = FEATURES.register("huge_red_mushroom", () -> new UncheckedHugeRedMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<CeilingBlobConfiguration>> BLOB_MODIFIABLE = FEATURES.register("blob_modifiable", () -> new CeilingBlobFeature(CeilingBlobConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<ReplaceFloorConfiguration>> REPLACE_FLOOR = FEATURES.register("replace_floor", () -> new ReplaceFloorFeature(ReplaceFloorConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<LeavesBlobConfiguration>> LEAVES_BLOB = FEATURES.register("leaves_blob", () -> new LeavesBlobFeature(LeavesBlobConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<DecorateFloorConfiguration>> DECORATE_FLOOR = FEATURES.register("decorate_floor", () -> new DecorateFloorFeature(DecorateFloorConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<MiniTreeConfiguration>> MINI_TREE = FEATURES.register("mini_tree", () -> new MiniTreeFeature(MiniTreeConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<BlockBlobConfiguration>> BLOCK_BLOB = FEATURES.register("block_blob", () -> new LargeBlockBlobFeature(BlockBlobConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<HoleDepthConfiguration>> HOLE = FEATURES.register("hole", () -> new HoleFeature(HoleDepthConfiguration.CODEC));
    public static final ResourceKey<Registry<MineTier>> TIER_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "mine_tier"));
    public static final DeferredRegister<MineTier> TIERS = DeferredRegister.create(TIER_REGISTRY_KEY, HarvestFestival.MODID);
    public static final Registry<MineTier> TIER_REGISTRY = TIERS.makeRegistry(builder -> builder.sync(true));
    public static final ResourceKey<Registry<Codec<? extends RoomGenerator>>> ROOM_GEN_KEY = ResourceKey.createRegistryKey(new ResourceLocation(HarvestFestival.MODID, "room_generators"));
    public static final DeferredRegister<Codec<? extends RoomGenerator>> GENERATORS = DeferredRegister.create(ROOM_GEN_KEY, HarvestFestival.MODID);
    public static final Holder<Codec<? extends RoomGenerator>> SPRAWL = GENERATORS.register("sprawling_tunnel", () -> SprawlingTunnelRoomGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> MULTI_TUNNEL = GENERATORS.register("multi_tunnel", () -> MultiTunnelRoomGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> TEMPLATE = GENERATORS.register("template", () -> TemplateRoomGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> SQUARE = GENERATORS.register("square", () -> SquareRoomGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> TUNNEL = GENERATORS.register("tunnel", () -> TunnelRoomGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> CIRCLE = GENERATORS.register("circle", () -> CircleRoomGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> RANDOM_PATH = GENERATORS.register("random_path", () -> RandomPathGenerator.CODEC);
    public static final Holder<Codec<? extends RoomGenerator>> RANDOM_ROOM = GENERATORS.register("random_room", () -> RandomRoomLocations.CODEC);
    public static final Registry<Codec<? extends RoomGenerator>> ROOM_GENERATORS = GENERATORS.makeRegistry(b -> b.sync(false));

    public static void register(IEventBus eventBus) {
        CHUNK_CODECS.register(eventBus);
        BIOME_SOURCE_CODECS.register(eventBus);
        FEATURES.register(eventBus);
        TIERS.register(eventBus);
        GENERATORS.register(eventBus);
    }
}