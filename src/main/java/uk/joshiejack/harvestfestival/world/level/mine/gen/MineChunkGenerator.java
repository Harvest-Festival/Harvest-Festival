package uk.joshiejack.harvestfestival.world.level.mine.gen;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.level.mine.MineHelper;
import uk.joshiejack.harvestfestival.world.level.mine.MineLootSpawner;
import uk.joshiejack.harvestfestival.world.level.mine.tier.MineTier;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

public class MineChunkGenerator extends ChunkGenerator {
    public static final Codec<MineChunkGenerator> CODEC = MineSettings.DIRECT_CODEC.fieldOf("settings").xmap(MineChunkGenerator::new, inst -> inst.settings).codec();

    private static final BlockState[] EMPTY_COLUMN = new BlockState[0];
    public final MineSettings settings;
    private final Supplier<List<FeatureSorter.StepFeatureData>> featuresPerStep;
    private final Function<Holder<Biome>, BiomeGenerationSettings> generationSettingsGetter;

    public MineChunkGenerator(MineSettings settings) {
        super(new MineBiomeSource(settings));
        this.settings = settings;
        this.generationSettingsGetter = (c) -> c.value().getGenerationSettings();
        this.featuresPerStep = Suppliers.memoize(
                () -> FeatureSorter.buildFeaturesPerStep(List.copyOf(biomeSource.possibleBiomes()), p_223216_ -> generationSettingsGetter.apply(p_223216_).features(), true)
        );
    }

    @Override
    public void applyBiomeDecoration(@NotNull WorldGenLevel world, ChunkAccess access, @NotNull StructureManager sm) {
        int mineID = MineHelper.getMineIDFromChunk(settings, access.getPos());
        if (mineID % 10 != 0) return; //Only generate every 10th mine, We'll pretend the others don't exist
        Holder<MineTier> tier = settings.getTierByPos(access.getPos().getWorldPosition());
        if (tier == null) return;
        //Decorate the mine if applicable?
        ChunkPos chunkpos = access.getPos();
        if (!SharedConstants.debugVoidTerrain(chunkpos)) {
            SectionPos sectionpos = SectionPos.of(chunkpos, world.getMinSection());
            BlockPos blockpos = sectionpos.origin();
            List<FeatureSorter.StepFeatureData> list = this.featuresPerStep.get();
            WorldgenRandom worldgenrandom = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
            long i = worldgenrandom.setDecorationSeed(world.getSeed(), blockpos.getX(), blockpos.getZ());
            Set<Holder<Biome>> set = new ObjectArraySet<>();
            ChunkPos.rangeClosed(sectionpos.chunk(), 1).forEach(p_223093_ -> {
                ChunkAccess chunkaccess = world.getChunk(p_223093_.x, p_223093_.z);

                for(LevelChunkSection levelchunksection : chunkaccess.getSections()) {
                    levelchunksection.getBiomes().getAll(set::add);
                }
            });
            set.retainAll(this.biomeSource.possibleBiomes());
            int j = list.size();
            try {
                Registry<PlacedFeature> registry1 = world.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
                int i1 = Math.max(GenerationStep.Decoration.values().length, j);
                for(int s = 0; s < i1; ++s) {
                    if (s < j) {
                        IntSet intset = new IntArraySet();

                        for(Holder<Biome> holder : set) {
                            List<HolderSet<PlacedFeature>> list1 = this.generationSettingsGetter.apply(holder).features();
                            if (s < list1.size()) {
                                HolderSet<PlacedFeature> holderset = list1.get(s);
                                FeatureSorter.StepFeatureData featuresorter$stepfeaturedata1 = list.get(s);
                                holderset.stream()
                                        .map(Holder::value)
                                        .forEach(p_223174_ -> intset.add(featuresorter$stepfeaturedata1.indexMapping().applyAsInt(p_223174_)));
                            }
                        }

                        int j1 = intset.size();
                        int[] aint = intset.toIntArray();
                        Arrays.sort(aint);
                        FeatureSorter.StepFeatureData featuresorter$stepfeaturedata = list.get(s);

                        int floorCounter = 0;
                        while(floorCounter < settings.floorsPerMine(world.getHeight())) {
                            //For every "floor" decorate
                            for (int k1 = 0; k1 < j1; ++k1) {
                                int l1 = aint[k1];
                                PlacedFeature placedfeature = featuresorter$stepfeaturedata.features().get(l1);
                                Supplier<String> supplier1 = () -> registry1.getResourceKey(placedfeature).map(Object::toString).orElseGet(placedfeature::toString);
                                worldgenrandom.setFeatureSeed(i, l1, s + floorCounter);

                                try {
                                    world.setCurrentlyGenerating(supplier1);
                                    int actualY = (world.getMaxBuildHeight() - (floorCounter + 1) * settings.floorHeight() + 2);
                                    placedfeature.placeWithBiomeCheck(world, this, worldgenrandom, blockpos.atY(actualY));
                                } catch (Exception exception1) {
                                    CrashReport crashreport2 = CrashReport.forThrowable(exception1, "Feature placement");
                                    crashreport2.addCategory("Feature").setDetail("Description", supplier1::get);
                                    throw new ReportedException(crashreport2);
                                }
                            }

                            floorCounter++;
                        }
                    }
                }

                world.setCurrentlyGenerating(null);
            } catch (Exception exception2) {
                CrashReport crashreport = CrashReport.forThrowable(exception2, "Biome decoration");
                crashreport.addCategory("Generation").setDetail("CenterX", chunkpos.x).setDetail("CenterZ", chunkpos.z).setDetail("Seed", i);
                throw new ReportedException(crashreport);
            }
        }

        //Decorate the loot drops
        MineLootSpawner.generateLoot(access, this, world);
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public @NotNull WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt
            (@NotNull Holder<Biome> biome, @NotNull StructureManager sm, @NotNull MobCategory category, @NotNull BlockPos
                    pos) {
        return super.getMobsAt(biome, sm, category, pos);
//        //If we can'#t find the tier, return the first one
//        Holder<MineTier> tierHolder = settings.getTierByPos(pos);
//        if (tierHolder == null) return settings.tiers().get(0).value().getMobs(category);
//        return tierHolder.value().getMobs(category);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long rand, @NotNull RandomState random,
                             @NotNull BiomeManager biomeManager, @NotNull StructureManager structureManager,
                             @NotNull ChunkAccess access, GenerationStep.@NotNull Carving carving) {
    }

    @Override
    public void buildSurface(@NotNull WorldGenRegion worldGenRegion, @NotNull StructureManager
            p_223051_, @NotNull RandomState p_223052_, @NotNull ChunkAccess p_223053_) {
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion p_62167_) {
    }

    @Override
    public int getGenDepth() {
        return 0;
    }

    private void fillInRoofWithWall(ChunkAccess access, Heightmap heightmap, Heightmap heightmap1, BlockState wall) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) { //Fix the ceiling
                access.setBlockState(pos.set(x, access.getMaxBuildHeight() - 1, z), wall, false);
                heightmap.update(x, access.getMaxBuildHeight(), z, wall);
                heightmap1.update(x, access.getMaxBuildHeight(), z, wall);
            }
        }
    }

    private CompletableFuture<ChunkAccess> fillWithBedrock(ChunkAccess access) {
        Heightmap heightmap = access.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = access.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) { //Fix the ceiling
                for (int y = access.getMinBuildHeight(); y < access.getMaxBuildHeight(); y++) {
                    access.setBlockState(blockpos$mutableblockpos.set(x, y, z), Blocks.BEDROCK.defaultBlockState(), false);
                    heightmap.update(x, y, z, Blocks.BEDROCK.defaultBlockState());
                    heightmap1.update(x, y, z, Blocks.BEDROCK.defaultBlockState());
                }
            }
        }

        return CompletableFuture.completedFuture(access);
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(@NotNull Executor executor, @NotNull Blender
            blender, @NotNull RandomState rs, @NotNull StructureManager structureManager, @NotNull ChunkAccess access) {
        if (access instanceof ProtoChunk) {
            ((ProtoChunk) access).setBelowZeroRetrogen(null);
        }

        if (!(biomeSource instanceof MineBiomeSource)) return fillWithBedrock(access);
        int mineID = MineHelper.getMineIDFromChunk(settings, access.getPos());
        if (mineID % 10 != 0) return fillWithBedrock(access); //Only generate every 10th mine, We'll pretend the others don't exist
        int tierNumber = MineHelper.getTierFromChunk(settings, access.getPos());
        if (tierNumber < 0 || mineID < 0) return fillWithBedrock(access);
        long generateID = new BlockPos(mineID, 0, tierNumber).asLong();
        RandomSource random = RandomSource.create(generateID);
        Holder<MineTier> tier = settings.getTierByPos(access.getPos().getWorldPosition());
        //If the tier isn't null then call fill from noise on it otherwise, return
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Heightmap heightmap = access.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = access.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
        assert tier != null;
        tier.value().fillFromNoise(access, heightmap, heightmap1, mutableBlockPos, generateID, settings);
        //Fill in the roof with the wall
        fillInRoofWithWall(access, heightmap, heightmap1, tier.value().getWall(0));
        return CompletableFuture.completedFuture(access);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return -320;
    }

    @Override
    public int getBaseHeight(int p_223032_, int p_223033_, Heightmap.@NotNull Types p_223034_,
                             @NotNull LevelHeightAccessor p_223035_, @NotNull RandomState p_223036_) {
        return 1;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, @NotNull LevelHeightAccessor p_224157_,
                                              @NotNull RandomState p_224158_) {
        return new NoiseColumn(0, EMPTY_COLUMN);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addDebugScreenInfo(@NotNull List<String> list, @NotNull RandomState state, @NotNull BlockPos pos) {
        list.add("Mine: " + (MineHelper.getMineIDFromPos(settings, pos)) / 10); //Divide by 10 to get the "real" mine id
        int floor = MineHelper.getFloorFromPos(Minecraft.getInstance().level, settings, pos);
        if (floor < 0) {
            list.add("Floor: Unknown");
        } else
            list.add("Floor: " + (MineHelper.getFloorFromPos(Minecraft.getInstance().level, settings, pos) + 1));
        int tier = MineHelper.getTierFromPos(settings, pos);
        if (tier < 0) {
            list.add("Tier: Unknown");
        } else list.add("Tier: " + (MineHelper.getTierFromPos(settings, pos) + 1));
    }
}
