package uk.joshiejack.harvestfestival.world.level.mine.tier;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.level.HFLevel;
import uk.joshiejack.harvestfestival.world.level.mine.gen.MineSettings;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.BlockStateMapWrapper;
import uk.joshiejack.harvestfestival.world.level.mine.gen.wrappers.DecoratorWrapper;
import uk.joshiejack.harvestfestival.world.level.mine.room.CircleRoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.RoomGenerator;
import uk.joshiejack.harvestfestival.world.level.mine.room.TemplateRoomGenerator;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class MineTier {
    public static final ResourceLocation NO_HUD = new ResourceLocation(HarvestFestival.MODID, "empty");
    private static final ResourceLocation DEFAULT_HUD = new ResourceLocation(HarvestFestival.MODID, "textures/gui/mine_hud.png");
    public static final Codec<MineTier> DIRECT_CODEC = RecordCodecBuilder.create(
            p_220544_ -> p_220544_.group(
                            Biome.CODEC.fieldOf("default_biome").forGetter(inst -> inst.defaultRange.biome()),
                            Codec.INT.fieldOf("order").forGetter(inst -> inst.order),
                            ResourceLocation.CODEC.fieldOf("hud").orElse(DEFAULT_HUD).forGetter(inst -> inst.hud),
                            BlockState.CODEC.fieldOf("walls").forGetter(inst -> inst.wall),
                            BlockState.CODEC.fieldOf("floor").forGetter(inst -> inst.floor),
                            BlockState.CODEC.fieldOf("ladder").forGetter(inst -> inst.ladder),
                            BlockState.CODEC.fieldOf("upper_portal").forGetter(inst -> inst.upper_portal),
                            BlockState.CODEC.fieldOf("lower_portal").forGetter(inst -> inst.lower_portal),
                            BlockState.CODEC.fieldOf("hole").forGetter(inst -> inst.hole),
                            NamedRange.CODEC.listOf().fieldOf("ranges").forGetter(inst -> inst.ranges),
                            WeightedRandomList.codec(Loot.CODEC).fieldOf("loots").forGetter(inst -> inst.loots),
                            Special.CODEC.listOf().fieldOf("special_loots").forGetter(inst -> inst.specials),
                            RoomSettings.CODEC.listOf().fieldOf("room_generators").forGetter(inst -> inst.generatorList)
                    )
                    .apply(p_220544_, MineTier::new)
    );

    private final NamedRange defaultRange;
    public static final Codec<Holder<MineTier>> CODEC = RegistryFileCodec.create(HFLevel.TIER_REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<MineTier>> LIST_CODEC = RegistryCodecs.homogeneousList(HFLevel.TIER_REGISTRY_KEY, DIRECT_CODEC);
    public static final RoomGenerator DEFAULT_GENERATOR = new CircleRoomGenerator(12);
    private final WeightedRandomList<Loot> loots;
    private final List<Special> specials;
    private final List<RoomSettings> generatorList;
    private final List<NamedRange> ranges;
    private final Random rand = new Random();
    public final int order;
    protected ResourceLocation hud;
    protected BlockState floor;
    protected BlockState wall;
    protected BlockState ladder;
    protected BlockState upper_portal;
    protected BlockState lower_portal;
    protected BlockState hole;

    // Cache Maps
    private final ConcurrentHashMap<Integer, NamedRange> namedRangeCache = new ConcurrentHashMap<>();
    public final Long2ObjectMap<Int2ObjectMap<BlockState[][][]>> terrainMap = new Long2ObjectOpenHashMap<>();
    private final EnumMap<RoomGenerator.Complexity, List<RoomGenerator>> generators = new EnumMap<>(RoomGenerator.Complexity.class);

    public MineTier(Holder<Biome> defaultBiome, int order, @Nullable ResourceLocation hud, BlockState wall, BlockState floor, BlockState ladder, BlockState upper_portal, BlockState lower_portal,
                    BlockState hole, List<NamedRange> ranges, WeightedRandomList<Loot> loots, List<Special> specials, List<RoomSettings> generators) {
        this.defaultRange = new NamedRange(defaultBiome, 1, Integer.MAX_VALUE);
        this.order = order;
        this.hud = hud == null ? DEFAULT_HUD : hud;
        this.wall = wall;
        this.floor = floor;
        this.ladder = ladder;
        this.upper_portal = upper_portal;
        this.lower_portal = lower_portal;
        this.hole = hole;
        this.loots = loots;
        this.specials = specials;
        this.ranges = ranges;

        generators.forEach(rl -> this.generators.computeIfAbsent(rl.generator().getComplexity(), c -> Lists.newArrayList()).add(rl.generator()));

        for (RoomGenerator generator : Lists.newArrayList(this.generators.get(RoomGenerator.Complexity.SIMPLE))) {
            if (generator instanceof TemplateRoomGenerator) {
                this.generators.get(RoomGenerator.Complexity.SIMPLE).addAll(((TemplateRoomGenerator) generator).extra());
            }
        }

        this.generatorList = generators; //Save the default list
    }

    private NamedRange getRangeFromFloor(int floor) {
        return namedRangeCache.computeIfAbsent(floor, flr -> {
            Optional<NamedRange> optionalRange = ranges.stream()
                    .filter(range -> flr >= range.min() && flr <= range.max())
                    .findFirst();

            return optionalRange.orElse(defaultRange);
        });
    }

    public Holder<Biome> getBiome(int floor) {
        return getRangeFromFloor(floor).biome();
    }

    public BlockState getFloor(int floor) {
        return getRangeFromFloor(floor).floor(this.floor);
    }

    public BlockState getWall(int floor) {
        return getRangeFromFloor(floor).wall(this.wall);
    }

    public BlockState getHole(int floor) {
        return getRangeFromFloor(floor).hole(this.hole);
    }

    public int getMaxEntities(int floor) {
        return getRangeFromFloor(floor).maxEntities();
    }

    public boolean isLoot(BlockState state) {
        return loots.unwrap().stream().anyMatch(loot -> loot.state() == state) || specials.stream().anyMatch(special -> special.state() == state);
    }

    @Nullable
    public Loot getLoot(RandomSource random, int actualFloor, int relativeFloor) {
        for (Special special: specials) {
            double bonus = ((double)(actualFloor - special.minimumFloor())) / special.divider();
            if (rand.nextDouble() <= bonus) return special.asLoot();
        }

        for (int i = 0; i < 1500; i++) {
            Optional<Loot> loot = loots.getRandom(random);
            if (loot.isPresent()) {
                Loot theLoot = loot.get();
                if (relativeFloor >= theLoot.minFloor() && relativeFloor <= theLoot.maxFloor()) {
                    return theLoot;
                }
            }
        }

        return null;
    }

    public ResourceLocation getHUD() {
        return hud;
    }

    public void build(MineSettings settings, int height, long generationID) {
        if (!terrainMap.containsKey(generationID))
            terrainMap.put(generationID, buildDecorationMap(settings, height, generationID));
    }

    private void setBlockState(ChunkAccess access, Heightmap heightmap, Heightmap heightmap1, BlockPos pos, BlockState state) {
        access.setBlockState(pos, state, false);
        heightmap.update(pos.getX(), pos.getY(), pos.getZ(), state);
        heightmap1.update(pos.getX(), pos.getY(), pos.getZ(), state);
    }

    public void fillFromNoise(ChunkAccess access, Heightmap heightmap, Heightmap heightmap1, BlockPos.MutableBlockPos blockpos$mutableblockpos, long generationID, MineSettings settings) {
        build(settings, access.getHeight(), generationID);
        Int2ObjectMap<BlockState[][][]> states = terrainMap.get(generationID);
        int sectionX = access.getPos().x % settings.chunksPerMine();
        int sectionZ = access.getPos().z % settings.chunksPerMine();
        //Set the blocks for these sections
        for (int floor = 0; floor < settings.floorsPerMine(access.getHeight()); floor++) {
            BlockState[][][] blockStates = states.get(floor);
            for (BlockPos pos: BlockPos.betweenClosed(0, 0, 0, 15, settings.floorHeight() - 1, 15)) {
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                BlockState state = blockStates[(sectionX * 16) + x][(sectionZ * 16) + z][y];
                if (state == Blocks.BEDROCK.defaultBlockState()) {
                    setBlockState(access, heightmap, heightmap1, blockpos$mutableblockpos.set(x, access.getMinBuildHeight() + (floor * settings.floorHeight()) + y + 1, z), Blocks.AIR.defaultBlockState());
                } else setBlockState(access, heightmap, heightmap1, blockpos$mutableblockpos.set(x, access.getMinBuildHeight() + (floor * settings.floorHeight()) + y + 1, z), state);
                setBlockState(access, heightmap, heightmap1, blockpos$mutableblockpos.set(x, access.getMinBuildHeight(), z), getWall(settings.floorsPerMine(access.getHeight())));
            }
        }
    }

    public BlockState getPortal(int floor) {
        return floor == 1 ? upper_portal : lower_portal;
    }

    public BlockState getLadder() {
        return ladder;
    }

    public RoomGenerator getValidGeneratorFromList(RoomGenerator.Complexity complexity, MineSettings settings, RandomSource rand, BlockPos target) {
        List<RoomGenerator> generators = this.generators.get(complexity);
        RoomGenerator generator = generators.get(rand.nextInt(generators.size()));
        int attempts = 0;
        while (!generator.canGenerate(settings, target)) {
            generator = generators.get(rand.nextInt(generators.size()));
            attempts++;

            if (attempts >= 32) {
                if (complexity == RoomGenerator.Complexity.COMPLEX) {
                    //Having failed complex generation, try simple
                    generators = this.generators.get(RoomGenerator.Complexity.SIMPLE);
                    while (!generator.canGenerate(settings, target)) {
                        generator = generators.get(rand.nextInt(generators.size()));
                        attempts++;
                        if (attempts >= 64)
                            return DEFAULT_GENERATOR;
                    }

                    return generator;
                }

                return DEFAULT_GENERATOR;
            }
        }

        return generator;
    }

    @SuppressWarnings("ConstantConditions, deprecation")
    public Int2ObjectMap<BlockState[][][]> buildDecorationMap(MineSettings settings, int height, long generationID) {
        RandomSource rand = RandomSource.create(generationID);
        Int2ObjectMap<BlockState[][][]> states = new Int2ObjectOpenHashMap<>();
        int floorCounter = 0;
        BlockPos start = new BlockPos(settings.blocksPerMine() / 2, 0, settings.blocksPerMine() / 2);
        BlockPos prevLadder = null;
        BlockState prevLadderState = getLadder();
        while(floorCounter < settings.floorsPerMine(height)) {
            BlockState[][][] blockStateMap = new BlockState[settings.blocksPerMine()][settings.blocksPerMine()][settings.floorHeight()];
            int floor = settings.floorsPerMine(height) - floorCounter;
            //Fill the chunk with the default blocks
            for (BlockPos pos: BlockPos.betweenClosed(0, 0, 0, blockStateMap.length - 1,  (settings.floorHeight() - 1),  blockStateMap.length - 1)) {
                blockStateMap[pos.getX()][pos.getZ()][pos.getY()] = getWall(floor);
            }

            DecoratorWrapper wrapper = new BlockStateMapWrapper(blockStateMap, settings, this, rand, floor);
            BlockPos pos = prevLadder == null ? start : prevLadder;
            RoomGenerator generator = floor == 1 || floor == settings.floorsPerMine(height) ? DEFAULT_GENERATOR : getValidGeneratorFromList(RoomGenerator.Complexity.COMPLEX, settings, rand, pos);
            BlockPos result = generator.generate(wrapper, pos);

            Rotation rotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
            BlockState ladder = getLadder().rotate(rotation);
            //Set the ladder blocks after generating the room
            if (floor != settings.floorsPerMine(height) && prevLadder != null) {
                wrapper.setBlockState(prevLadder, prevLadderState);
                for (int i = 1; i <= 2; i++) {
                    if (wrapper.getBlockState(prevLadder.above(i)) != ladder) {
                        wrapper.setBlockState(prevLadder.above(i), Blocks.AIR.defaultBlockState());
                    }
                }
            }

            if (floor != 1) {
                for (int y = 1; y < settings.floorHeight(); y++) {
                    wrapper.setBlockState(result.above(y), ladder);
                }

                //Set the area around the ladder at the bottom (on the x and z axis to air but not the ladder pos itself)
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        for (int y = 1; y <= 2; y++) {
                            BlockPos pos1 = result.offset(x, y, z);
                            if (wrapper.getBlockState(pos1) != ladder) {
                                wrapper.setBlockState(pos1, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            }

            prevLadder = result;
            prevLadderState = ladder;

            states.put(floorCounter, blockStateMap);
            floorCounter++;
        }

        return states;
    }

    public boolean canDecorate(RandomSource rand, int actualFloor, int relativeFloor) {
        NamedRange range = getRangeFromFloor(relativeFloor);
        return rand.nextDouble() <= Math.min(range.lootChanceMax(), range.lootChanceMin() + ((float) actualFloor / range.lootChanceDivisor()));
    }

    public HolderSet<Biome> getBiomes() {
        return HolderSet.direct(ranges.stream().map(NamedRange::biome).toList());
    }
}
