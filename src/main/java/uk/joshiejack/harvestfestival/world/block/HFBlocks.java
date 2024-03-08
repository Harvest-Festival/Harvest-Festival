package uk.joshiejack.harvestfestival.world.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.PlantType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.world.block.ShapedBlock;

import java.util.function.Supplier;

public class HFBlocks {
    private static final ResourceKey<Level> MINE_WORLD = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(HarvestFestival.MODID, "mine"));
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(HarvestFestival.MODID);
    //Deferred Blocks of Mine Wall, Mine Floor, Frozen Mine Wall, Frozen Mine Floor, Hell Mine Wall, Hell Mine Floor
    public static final DeferredBlock<Block> MINE_WALL = registerWithItem("mine_wall", () -> new Block(unbreakable().isValidSpawn((w,x,y,z)->false)));
    public static final DeferredBlock<Block> MINE_FLOOR = registerWithItem("mine_floor", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final DeferredBlock<Block> MINE_LADDER = registerWithItem("mine_ladder", () -> new LadderBlock(unbreakable().sound(SoundType.LADDER).noOcclusion().noLootTable()));
    public static final DeferredHolder<Block, ElevatorBlock> ELEVATOR = BLOCKS.register("elevator", () -> new ElevatorBlock(unbreakable().sound(SoundType.METAL).noOcclusion().noLootTable()));
    public static final DeferredBlock<Block> LOWER_PORTAL = registerWithItem("lower_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> UPPER_PORTAL = registerWithItem("upper_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> FROZEN_MINE_WALL = registerWithItem("frozen_mine_wall", () -> new Block(unbreakable()));
    public static final DeferredBlock<Block> FROZEN_MINE_BRICKS = registerWithItem("frozen_mine_bricks", () -> new Block(unbreakable()));
    public static final DeferredBlock<Block> FROZEN_MINE_FLOOR = registerWithItem("frozen_mine_floor", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final DeferredBlock<Block> FROZEN_MINE_LADDER = registerWithItem("frozen_mine_ladder", () -> new LadderBlock(unbreakable().sound(SoundType.LADDER).noOcclusion().noLootTable().friction(0.95F)));
    public static final DeferredBlock<Block> FROZEN_UPPER_PORTAL = registerWithItem("frozen_upper_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> FROZEN_LOWER_PORTAL = registerWithItem("frozen_lower_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> HELL_MINE_WALL = registerWithItem("hell_mine_wall", () -> new Block(unbreakable()));
    public static final DeferredBlock<Block> HELL_MINE_FLOOR = registerWithItem("hell_mine_floor", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final DeferredBlock<Block> HELL_MINE_BRICKS = registerWithItem("hell_mine_bricks", () -> new Block(unbreakable()));
    public static final DeferredBlock<Block> HELL_MINE_LADDER = registerWithItem("hell_mine_ladder", () -> new LadderBlock(unbreakable().sound(SoundType.LADDER).noOcclusion().noLootTable()));
    public static final DeferredBlock<Block> HELL_UPPER_PORTAL = registerWithItem("hell_upper_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> HELL_LOWER_PORTAL = registerWithItem("hell_lower_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> MINE_PORTAL = registerWithItem("mine_portal", () -> new PortalBlock(unbreakable(), MINE_WORLD));
    //Nodes: Amethyst, Copper, Diamond, Emerald, Gem, Gold, Iron, Jade, Mystril, Ruby, Silver, Topaz // Rocks: 1, 2, 3, 4, 5 // Barrel and Crate
    public static final DeferredBlock<Block> AMETHYST_NODE = registerWithItem("amethyst_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.EMERALD_ORE)));
    public static final DeferredBlock<Block> COPPER_NODE = registerWithItem("copper_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.COPPER_ORE)));
    public static final DeferredBlock<Block> DIAMOND_NODE = registerWithItem("diamond_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.DIAMOND_ORE)));
    public static final DeferredBlock<Block> EMERALD_NODE = registerWithItem("emerald_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.EMERALD_ORE)));
    public static final DeferredBlock<Block> GEM_NODE = registerWithItem("gem_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.EMERALD_ORE)));
    public static final DeferredBlock<Block> GOLD_NODE = registerWithItem("gold_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> IRON_NODE = registerWithItem("iron_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> JADE_NODE = registerWithItem("jade_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.EMERALD_ORE)));
    public static final DeferredBlock<Block> MYSTRIL_NODE = registerWithItem("mystril_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.DIAMOND_ORE)));
    public static final DeferredBlock<Block> RUBY_NODE = registerWithItem("ruby_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.EMERALD_ORE)));
    public static final DeferredBlock<Block> SILVER_NODE = registerWithItem("silver_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> TOPAZ_NODE = registerWithItem("topaz_node", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.EMERALD_ORE)));
    public static final DeferredBlock<Block> ROCK = registerWithItem("rock", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> ICE_ROCK = registerWithItem("ice_rock", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> ICE_CRYSTAL = registerWithItem("ice_crystal", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.ICE)));
    public static final DeferredBlock<Block> HELL_ROCK = registerWithItem("hell_rock", () -> new NodeBlock(Block.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> BARREL = registerWithItem("barrel", () -> new Block(Block.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().strength(0.75F)));
    public static final DeferredBlock<Block> FROZEN_BARREL = registerWithItem("frozen_barrel", () -> new Block(Block.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().strength(0.75F)));
    public static final DeferredBlock<Block> CRATE = registerWithItem("crate", () -> new Block(Block.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().strength(1F)));
    public static final DeferredBlock<Block> HELL_CHEST = registerWithItem("hell_chest", () -> new Block(Block.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().strength(1F)));
    //Decorations
    public static final DeferredBlock<Block> SMALL_OAK_BRANCH = registerWithItem("small_oak_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_OAK_BRANCH = registerWithItem("medium_oak_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_OAK_BRANCH = registerWithItem("large_oak_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_OAK_STUMP = registerWithItem("small_oak_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_OAK_STUMP = registerWithItem("medium_oak_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_OAK_STUMP = registerWithItem("large_oak_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_SPRUCE_BRANCH = registerWithItem("small_spruce_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_SPRUCE_BRANCH = registerWithItem("medium_spruce_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_SPRUCE_BRANCH = registerWithItem("large_spruce_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_SPRUCE_STUMP = registerWithItem("small_spruce_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_SPRUCE_STUMP = registerWithItem("medium_spruce_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_SPRUCE_STUMP = registerWithItem("large_spruce_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_BIRCH_BRANCH = registerWithItem("small_birch_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_BIRCH_BRANCH = registerWithItem("medium_birch_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_BIRCH_BRANCH = registerWithItem("large_birch_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_BIRCH_STUMP = registerWithItem("small_birch_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_BIRCH_STUMP = registerWithItem("medium_birch_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_BIRCH_STUMP = registerWithItem("large_birch_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_JUNGLE_BRANCH = registerWithItem("small_jungle_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_JUNGLE_BRANCH = registerWithItem("medium_jungle_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_JUNGLE_BRANCH = registerWithItem("large_jungle_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_JUNGLE_STUMP = registerWithItem("small_jungle_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_JUNGLE_STUMP = registerWithItem("medium_jungle_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_JUNGLE_STUMP = registerWithItem("large_jungle_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_ACACIA_BRANCH = registerWithItem("small_acacia_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_ACACIA_BRANCH = registerWithItem("medium_acacia_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_ACACIA_BRANCH = registerWithItem("large_acacia_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_ACACIA_STUMP = registerWithItem("small_acacia_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_ACACIA_STUMP = registerWithItem("medium_acacia_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_ACACIA_STUMP = registerWithItem("large_acacia_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_DARK_OAK_BRANCH = registerWithItem("small_dark_oak_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_DARK_OAK_BRANCH = registerWithItem("medium_dark_oak_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_DARK_OAK_BRANCH = registerWithItem("large_dark_oak_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_DARK_OAK_STUMP = registerWithItem("small_dark_oak_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_DARK_OAK_STUMP = registerWithItem("medium_dark_oak_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_DARK_OAK_STUMP = registerWithItem("large_dark_oak_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_MANGROVE_BRANCH = registerWithItem("small_mangrove_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_MANGROVE_BRANCH = registerWithItem("medium_mangrove_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_MANGROVE_BRANCH = registerWithItem("large_mangrove_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_MANGROVE_STUMP = registerWithItem("small_mangrove_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_MANGROVE_STUMP = registerWithItem("medium_mangrove_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_MANGROVE_STUMP = registerWithItem("large_mangrove_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_CHERRY_BRANCH = registerWithItem("small_cherry_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_CHERRY_BRANCH = registerWithItem("medium_cherry_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_CHERRY_BRANCH = registerWithItem("large_cherry_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_CHERRY_STUMP = registerWithItem("small_cherry_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_CHERRY_STUMP = registerWithItem("medium_cherry_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_CHERRY_STUMP = registerWithItem("large_cherry_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_CRIMSON_BRANCH = registerWithItem("small_crimson_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_CRIMSON_BRANCH = registerWithItem("medium_crimson_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_CRIMSON_BRANCH = registerWithItem("large_crimson_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_CRIMSON_STUMP = registerWithItem("small_crimson_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_CRIMSON_STUMP = registerWithItem("medium_crimson_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_CRIMSON_STUMP = registerWithItem("large_crimson_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_WARPED_BRANCH = registerWithItem("small_warped_branch", SmallBranchBlock::new);
    public static final DeferredBlock<Block> MEDIUM_WARPED_BRANCH = registerWithItem("medium_warped_branch", MediumBranchBlock::new);
    public static final DeferredBlock<Block> LARGE_WARPED_BRANCH = registerWithItem("large_warped_branch", LargeBranchBlock::new);
    public static final DeferredBlock<Block> SMALL_WARPED_STUMP = registerWithItem("small_warped_stump", SmallStumpBlock::new);
    public static final DeferredBlock<Block> MEDIUM_WARPED_STUMP = registerWithItem("medium_warped_stump", MediumStumpBlock::new);
    public static final DeferredBlock<Block> LARGE_WARPED_STUMP = registerWithItem("large_warped_stump", LargeStumpBlock::new);
    public static final DeferredBlock<Block> SMALL_STONES = registerWithItem("small_stones",
            () -> new StoneBlock(BlockBehaviour.Properties.of(),
                    Shapes.create(0.15F, 0F, 0.15F, 0.85F, 0.15F, 0.85F)));
    public static final DeferredBlock<Block> MEDIUM_STONES = registerWithItem("medium_stones",
            () -> new StoneBlock(BlockBehaviour.Properties.of(),
                    Shapes.create(0F, 0F, 0F, 1F, 0.2F, 1F)));
    public static final DeferredBlock<Block> LARGE_STONES = registerWithItem("large_stones",
            () -> new StoneBlock(BlockBehaviour.Properties.of(),
                    Shapes.create(0F, 0F, 0F, 1F, 0.3F, 1F)));
    public static final DeferredBlock<Block> SMALL_BOULDER = registerWithItem("small_boulder",
            () -> new StoneBlock(BlockBehaviour.Properties.of(),
                    Shapes.create(0.225F, 0F, 0.225F, 0.775F, 0.25F, 0.775F)));
    public static final DeferredBlock<Block> MEDIUM_BOULDER = registerWithItem("medium_boulder",
            () -> new StoneBlock(BlockBehaviour.Properties.of(),
                    Shapes.create(0F, 0F, 0F, 1F, 0.35F, 1F)));
    public static final DeferredBlock<Block> LARGE_BOULDER = registerWithItem("large_boulder",
            () -> new StoneBlock(BlockBehaviour.Properties.of(),
                    Shapes.create(0F, 0F, 0F, 1F, 0.6F, 1F)));
    //Add the plants: Dwarfen Shroom, Goddess Flower, Blue Magic Flower, Moondrop Flower, Pink Cat Flower, Red Magic Flower, Toy Flower, Weed
    public static final DeferredBlock<Block> DWARFEN_SHROOM = registerWithItem("dwarfen_shroom", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_BROWN)).withPlantType(PlantType.CAVE));
    public static final DeferredBlock<Block> GODDESS_FLOWER = BLOCKS.register("goddess_flower", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> BLUE_MAGIC_FLOWER = registerWithItem("blue_magic_flower", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.BLUE_ORCHID).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<Block> MOONDROP_FLOWER = registerWithItem("moondrop_flower", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> PINK_CAT_FLOWER = registerWithItem("pink_cat_flower", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<Block> RED_MAGIC_FLOWER = registerWithItem("red_magic_flower", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> TOY_FLOWER = registerWithItem("toy_flower", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> WEEDS = registerWithItem("weeds", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.FERN).mapColor(MapColor.COLOR_GREEN)).withPlantType(PlantType.CAVE));
    //Wild grapes and toadstool
    public static final DeferredBlock<Block> WILD_GRAPES = registerWithItem("wild_grapes", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).mapColor(MapColor.COLOR_PURPLE)).withPlantType(PlantType.CAVE));
    public static final DeferredBlock<Block> TOADSTOOL = registerWithItem("toadstool", () -> new HFBushBlock(Block.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_BROWN)).withPlantType(PlantType.CAVE));
    //Goddess Water
    public static final DeferredHolder<Block, LiquidBlock> GODDESS_WATER = BLOCKS.register("goddess_water", () -> new LiquidBlock(HFFluids.GODDESS_WATER_FLOWING, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> ICY_WATER = BLOCKS.register("icy_water", () -> new LiquidBlock(HFFluids.ICY_WATER_FLOWING, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    private static final VoxelShape FLOOR = Block.box(0D, 0D, 0D, 16D, 1D, 16D);
    public static final DeferredBlock<Block> HELL_COBBLE_BRICKS = registerWithItem("hell_cobble_bricks", () -> new ShapedBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE).noCollission().noOcclusion(), FLOOR));
    public static final DeferredBlock<Block> HELL_COBBLESTONE = registerWithItem("hell_cobblestone", () -> new ShapedBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE).noCollission().noOcclusion(), FLOOR));
    public static final DeferredBlock<Block> FROZEN_COBBLE_BRICKS = registerWithItem("frozen_cobble_bricks", () -> new ShapedBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE).noCollission().noOcclusion(), FLOOR));
    public static final DeferredBlock<Block> FROZEN_COBBLESTONE = registerWithItem("frozen_cobblestone", () -> new ShapedBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE).noCollission().noOcclusion(), FLOOR));
    //Deferred Blocks of Mine Wall, Mine Floor, Frozen Mine Wall, Frozen Mine Floor, Hell Mine Wall, Hell Mine Floor
    public static final DeferredBlock<Block> COBBLESTONE = registerWithItem("cobblestone", () -> new ShapedBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE).noCollission().noOcclusion(), FLOOR));
    //Holes
    public static final DeferredBlock<Block> MINE_HOLE = registerWithItem("mine_hole", () -> new HoleBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> FROZEN_MINE_HOLE = registerWithItem("frozen_mine_hole", () -> new HoleBlock(unbreakable(), MINE_WORLD));
    public static final DeferredBlock<Block> HELL_MINE_HOLE = registerWithItem("hell_mine_hole", () -> new HoleBlock(unbreakable(), MINE_WORLD));
    //Mailboxes
    public static final DeferredBlock<Block> MAILBOX = registerWithItem("mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> OAK_MAILBOX = registerWithItem("oak_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> SPRUCE_MAILBOX = registerWithItem("spruce_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> BIRCH_MAILBOX = registerWithItem("birch_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> JUNGLE_MAILBOX = registerWithItem("jungle_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> ACACIA_MAILBOX = registerWithItem("acacia_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> DARK_OAK_MAILBOX = registerWithItem("dark_oak_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> NETHER_BRICK_MAILBOX = registerWithItem("nether_brick_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> CHERRY_MAILBOX = registerWithItem("cherry_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> MANGROVE_MAILBOX = registerWithItem("mangrove_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> CRIMSON_MAILBOX = registerWithItem("crimson_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> WARPED_MAILBOX = registerWithItem("warped_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));
    public static final DeferredBlock<Block> BAMBOO_MAILBOX = registerWithItem("bamboo_mailbox", () -> new MailboxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.5F)));

    public static final DeferredBlock<Block> OAK_SEEDLING = BLOCKS.register("oak_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> SPRUCE_SEEDLING = BLOCKS.register("spruce_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> BIRCH_SEEDLING = BLOCKS.register("birch_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> JUNGLE_SEEDLING = BLOCKS.register("jungle_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> ACACIA_SEEDLING = BLOCKS.register("acacia_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> DARK_OAK_SEEDLING = BLOCKS.register("dark_oak_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> CHERRY_SEEDLING = BLOCKS.register("cherry_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> MANGROVE_SEEDLING = BLOCKS.register("mangrove_seedling", () -> new HFBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PROPAGULE).noLootTable()));

    public static final DeferredBlock<Block> OAK_JUVENILE = BLOCKS.register("oak_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> SPRUCE_JUVENILE = BLOCKS.register("spruce_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> BIRCH_JUVENILE = BLOCKS.register("birch_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> JUNGLE_JUVENILE = BLOCKS.register("jungle_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> ACACIA_JUVENILE = BLOCKS.register("acacia_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> DARK_OAK_JUVENILE = BLOCKS.register("dark_oak_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> CHERRY_JUVENILE = BLOCKS.register("cherry_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).noLootTable()));
    public static final DeferredBlock<Block> MANGROVE_JUVENILE = BLOCKS.register("mangrove_juvenile", () -> new HFDoubleBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PROPAGULE).noLootTable()));

    public static final DeferredBlock<Block> TELEVISION = registerWithItem("television", TelevisionBlock::new);
    private static BlockBehaviour.Properties unbreakable() {
        return BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).noLootTable();
    }
    
    private static DeferredBlock<Block> registerWithItem(String name, Supplier<Block> block) {
        DeferredBlock<Block> blockDeferred = BLOCKS.register(name, block);
        HFItems.ITEMS.register(name, () -> new BlockItem(blockDeferred.get(), new Item.Properties()));
        return blockDeferred;
    }
}