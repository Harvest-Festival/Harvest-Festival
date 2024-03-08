package uk.joshiejack.harvestfestival.data;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.ElevatorBlock;
import uk.joshiejack.harvestfestival.world.block.PortalBlock;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;

public class HFBlockStates extends BlockStateProvider {
    public HFBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, HarvestFestival.MODID, existingFileHelper);
    }

    private void crossBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(), models().cross(block.getId().toString(), modLoc("block/" + block.getId().getPath())).renderType("cutout"));
    }

    private void treeBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(), models().cross(block.getId().toString(), modLoc("block/tree/" + block.getId().getPath())).renderType("cutout"));
    }

    @Override
    protected void registerStatesAndModels() {
        registerFloorVariants(HFBlocks.MINE_FLOOR.get());
        registerWallVariants(HFBlocks.MINE_WALL.get());
        registerPortalBasic(HFBlocks.UPPER_PORTAL.get(), "upper", "wall_blank");
        registerPortalBasic(HFBlocks.LOWER_PORTAL.get(), "lower", "wall_blank");
        registerPortalBasic(HFBlocks.MINE_PORTAL.get(), "stone", "minecraft:block/stone");
        registerLadder(HFBlocks.MINE_LADDER.get());

        //Elevator
        ModelFile upper = models().getExistingFile(modLoc("block/elevator_upper"));
        ModelFile lower = models().getExistingFile(modLoc("block/elevator_lower"));
        VariantBlockStateBuilder builder = getVariantBuilder(HFBlocks.ELEVATOR.get());
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.UPPER).with(ElevatorBlock.FACING, Direction.WEST).modelForState().modelFile(upper).rotationY(90).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.UPPER).with(ElevatorBlock.FACING, Direction.EAST).modelForState().modelFile(upper).rotationY(270).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.UPPER).with(ElevatorBlock.FACING, Direction.SOUTH).modelForState().modelFile(upper).rotationY(0).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.UPPER).with(ElevatorBlock.FACING, Direction.NORTH).modelForState().modelFile(upper).rotationY(180).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.LOWER).with(ElevatorBlock.FACING, Direction.WEST).modelForState().modelFile(lower).rotationY(90).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.LOWER).with(ElevatorBlock.FACING, Direction.EAST).modelForState().modelFile(lower).rotationY(270).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.LOWER).with(ElevatorBlock.FACING, Direction.SOUTH).modelForState().modelFile(lower).rotationY(0).addModel();
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.LOWER).with(ElevatorBlock.FACING, Direction.NORTH).modelForState().modelFile(lower).rotationY(180).addModel();

        //Block textures are in block/wilderness
        crossBlock(HFBlocks.DWARFEN_SHROOM);
        crossBlock(HFBlocks.GODDESS_FLOWER);
        crossBlock(HFBlocks.BLUE_MAGIC_FLOWER);
        crossBlock(HFBlocks.MOONDROP_FLOWER);
        crossBlock(HFBlocks.PINK_CAT_FLOWER);
        crossBlock(HFBlocks.RED_MAGIC_FLOWER);
        crossBlock(HFBlocks.TOY_FLOWER);
        crossBlock(HFBlocks.WILD_GRAPES);
        crossBlock(HFBlocks.TOADSTOOL);
        //Saplings
        treeBlock(HFBlocks.OAK_SEEDLING);
        treeBlock(HFBlocks.SPRUCE_SEEDLING);
        treeBlock(HFBlocks.BIRCH_SEEDLING);
        treeBlock(HFBlocks.JUNGLE_SEEDLING);
        treeBlock(HFBlocks.ACACIA_SEEDLING);
        treeBlock(HFBlocks.DARK_OAK_SEEDLING);
        //treeBlock(HFBlocks.MANGROVE_SEEDLING);
        //treeBlock(HFBlocks.CHERRY_SEEDLING);
        //Juvenile
        //doubleCrossBlock(HFBlocks.OAK_JUVENILE);
        //doubleCrossBlock(HFBlocks.SPRUCE_JUVENILE);
        doubleCrossBlock(HFBlocks.BIRCH_JUVENILE);
        //doubleCrossBlock(HFBlocks.JUNGLE_JUVENILE);
        doubleCrossBlock(HFBlocks.ACACIA_JUVENILE);
        //doubleCrossBlock(HFBlocks.DARK_OAK_JUVENILE);
        //doubleCrossBlock(HFBlocks.MANGROVE_JUVENILE);
        //doubleCrossBlock(HFBlocks.CHERRY_JUVENILE);


        simpleBlock(HFBlocks.FROZEN_BARREL.get(), models().withExistingParent("frozen_barrel", modLoc("block/barrel")).texture("lid", modLoc("block/mine/crates/frozen_barrel_lid")).texture("particle", modLoc("block/mine/crates/frozen_barrel")));
        simpleBlock(HFBlocks.CRATE.get(), models().cubeAll("crate", modLoc("block/mine/crates/square")));
        simpleBlock(HFBlocks.BARREL.get(), models().getExistingFile(modLoc("block/barrel")));

        ModelFile hellChest = models().getExistingFile(modLoc("block/hell_chest"));
        getVariantBuilder(HFBlocks.HELL_CHEST.get())
                .partialState().setModels(ConfiguredModel.builder()
                        .modelFile(hellChest).rotationY(0).nextModel()
                        .modelFile(hellChest).rotationY(90)
                        .build());

        cobblestoneBlock(HFBlocks.HELL_COBBLESTONE.get(), "hell");
        cobblestoneBlock(HFBlocks.FROZEN_COBBLESTONE.get(), "frozen");
        cobblestoneBlock(HFBlocks.COBBLESTONE.get(), "standard");
        cobblestoneBlock(HFBlocks.FROZEN_COBBLE_BRICKS.get(), "frozen");
        cobblestoneBlock(HFBlocks.HELL_COBBLE_BRICKS.get(), "hell");

        //Register ladders
        ladderBlock(HFBlocks.FROZEN_MINE_LADDER.get(), "frozen");
        ladderBlock(HFBlocks.HELL_MINE_LADDER.get(), "hell");
        registerPortal(HFBlocks.FROZEN_UPPER_PORTAL.get(), "upper", "frozen");
        registerPortal(HFBlocks.FROZEN_LOWER_PORTAL.get(), "lower", "frozen");
        registerPortal(HFBlocks.HELL_UPPER_PORTAL.get(), "upper", "hell");
        registerPortal(HFBlocks.HELL_LOWER_PORTAL.get(), "lower", "hell");

        //Weeds have multiple textures so can be weeds1, weeds2, weeds3, weeds4
        VariantBlockStateBuilder weedsBuilder = getVariantBuilder(HFBlocks.WEEDS.get());
        weedsBuilder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(models().singleTexture("weeds_1", mcLoc("block/tinted_cross"), "cross", modLoc("block/weeds_1")).renderType("cutout")).nextModel()
                .modelFile(models().singleTexture("weeds_2", mcLoc("block/tinted_cross"), "cross", modLoc("block/weeds_2")).renderType("cutout")).nextModel()
                .modelFile(models().singleTexture("weeds_3", mcLoc("block/tinted_cross"), "cross", modLoc("block/weeds_3")).renderType("cutout")).nextModel()
                .modelFile(models().singleTexture("weeds_4", mcLoc("block/tinted_cross"), "cross", modLoc("block/weeds_4")).renderType("cutout")).build());

        wallBlock(HFBlocks.FROZEN_MINE_WALL.get(), "frozen", "frozen_mine_wall");
        wallBlock(HFBlocks.HELL_MINE_WALL.get(), "hell", "hell_mine_wall");
        simpleBlock(HFBlocks.HELL_MINE_BRICKS.get(), models().cubeAll("hell_mine_bricks", modLoc("block/mine/interior/hell/brick")));
        simpleBlock(HFBlocks.FROZEN_MINE_BRICKS.get(), models().cubeAll("frozen_mine_bricks", modLoc("block/mine/interior/frozen/brick")));
        nodeBlock(HFBlocks.AMETHYST_NODE.get());
        nodeBlock(HFBlocks.COPPER_NODE.get());
        nodeBlock(HFBlocks.DIAMOND_NODE.get());
        nodeBlock(HFBlocks.EMERALD_NODE.get());
        nodeBlock(HFBlocks.GEM_NODE.get());
        nodeBlock(HFBlocks.GOLD_NODE.get());
        nodeBlock(HFBlocks.IRON_NODE.get());
        nodeBlock(HFBlocks.JADE_NODE.get());
        nodeBlock(HFBlocks.MYSTRIL_NODE.get());
        nodeBlock(HFBlocks.RUBY_NODE.get());
        nodeBlock(HFBlocks.SILVER_NODE.get());
        nodeBlock(HFBlocks.TOPAZ_NODE.get());

        rockBlock(HFBlocks.ROCK.get(), "rock");
        rockBlock(HFBlocks.ICE_ROCK.get(), "rock");
        rockBlock(HFBlocks.ICE_CRYSTAL.get(), "ice_crystal");
        rockBlock(HFBlocks.HELL_ROCK.get(), "rock");

        //Register models for the branch and stump blocks
        for (String size : new String[]{"small", "medium", "large"}) {
            for (String type : new String[]{"branch", "stump"}) {
                registerBranchOrStump(size + "_" + type, size + "_oak_" + type, "oak_log_top", "oak_log");
                registerBranchOrStump(size + "_" + type, size + "_birch_" + type, "birch_log_top", "birch_log");
                registerBranchOrStump(size + "_" + type, size + "_spruce_" + type, "spruce_log_top", "spruce_log");
                registerBranchOrStump(size + "_" + type, size + "_jungle_" + type, "jungle_log_top", "jungle_log");
                registerBranchOrStump(size + "_" + type, size + "_acacia_" + type, "acacia_log_top", "acacia_log");
                registerBranchOrStump(size + "_" + type, size + "_dark_oak_" + type, "dark_oak_log_top", "dark_oak_log");
                registerBranchOrStump(size + "_" + type, size + "_mangrove_" + type, "mangrove_log_top", "mangrove_log");
                registerBranchOrStump(size + "_" + type, size + "_cherry_" + type, "cherry_log_top", "cherry_log");
                registerBranchOrStump(size + "_" + type, size + "_crimson_" + type, "crimson_stem_top", "crimson_stem");
                registerBranchOrStump(size + "_" + type, size + "_warped_" + type, "warped_stem_top", "warped_stem");
            }
        }

        stoneBlock(HFBlocks.SMALL_STONES.get());
        stoneBlock(HFBlocks.MEDIUM_STONES.get());
        stoneBlock(HFBlocks.LARGE_STONES.get());
        boulderBlock(HFBlocks.SMALL_BOULDER.get());
        boulderBlock(HFBlocks.MEDIUM_BOULDER.get());
        boulderBlock(HFBlocks.LARGE_BOULDER.get());

        floorBlock(HFBlocks.FROZEN_MINE_FLOOR.get(), "frozen");
        floorBlock(HFBlocks.HELL_MINE_FLOOR.get(), "hell");
        holeBlock(HFBlocks.MINE_HOLE.get(), "standard");
        holeBlock(HFBlocks.FROZEN_MINE_HOLE.get(), "frozen");
        holeBlock(HFBlocks.HELL_MINE_HOLE.get(), "hell");

        mailboxBlock(HFBlocks.MAILBOX.get());
        mailboxBlock(HFBlocks.OAK_MAILBOX.get());
        mailboxBlock(HFBlocks.SPRUCE_MAILBOX.get());
        mailboxBlock(HFBlocks.BIRCH_MAILBOX.get());
        mailboxBlock(HFBlocks.JUNGLE_MAILBOX.get());
        mailboxBlock(HFBlocks.ACACIA_MAILBOX.get());
        mailboxBlock(HFBlocks.DARK_OAK_MAILBOX.get());
        mailboxBlock(HFBlocks.NETHER_BRICK_MAILBOX.get());
//        mailboxBlock(HFBlocks.CHERRY_MAILBOX.get());
//        mailboxBlock(HFBlocks.MANGROVE_MAILBOX.get());
//        mailboxBlock(HFBlocks.CRIMSON_MAILBOX.get());
//        mailboxBlock(HFBlocks.WARPED_MAILBOX.get());
//        mailboxBlock(HFBlocks.BAMBOO_MAILBOX.get());
    }

    private void doubleCrossBlock(DeferredBlock<Block> block) {
        //Upper and lower
        VariantBlockStateBuilder builder = getVariantBuilder(block.get());
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.UPPER).setModels(ConfiguredModel.builder()
                .modelFile(models().cross(block.getId() + "_upper", modLoc("block/tree/" + block.getId().getPath() + "_upper")).renderType("cutout")).build());
        builder.partialState().with(ElevatorBlock.HALF, DoubleBlockHalf.LOWER).setModels(ConfiguredModel.builder()
                .modelFile(models().cross(block.getId() + "_lower", modLoc("block/tree/" + block.getId().getPath() + "_lower")).renderType("cutout")).build());
    }

    private void mailboxBlock(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int rotation = direction == Direction.NORTH ? 0 : direction == Direction.SOUTH ? 180 : direction == Direction.WEST ? 270 : 90;
            builder.partialState().with(LadderBlock.FACING, direction).setModels(ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                                    new ResourceLocation(HarvestFestival.MODID, "block/mailbox_base"))
                            .texture("texture", new ResourceLocation(HarvestFestival.MODID, "block/mail/" + path + "_1"))
                            .texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mail/" + path + "_2")))
                    .rotationY(rotation).build());
        }
    }

    private void registerLadder(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int rotation = direction == Direction.NORTH ? 0 : direction == Direction.SOUTH ? 180 : direction == Direction.WEST ? 270 : 90;
            builder.partialState().with(LadderBlock.FACING, direction).setModels(ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/ladder")))
                    .rotationY(rotation).build());
        }
    }

    private void holeBlock(Block block, String dir) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(models().cubeTop(BuiltInRegistries.BLOCK.getKey(block).getPath(), mineDir(dir, "wall_blank"), mineDir(dir, "hole"))).build());
    }

    private ResourceLocation mineDir(String path) {
        return modLoc(String.format("block/mine/%s", path));
    }

    private void registerFloorVariants(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(models().cubeTop("mine_floor_blank", mineDir("wall_blank"), mineDir("floor_blank"))).weight(60).nextModel()
                .modelFile(models().cubeTop("mine_floor_bones_1", mineDir("wall_blank"), mineDir("floor_bones_1"))).weight(3).nextModel()
                .modelFile(models().cubeTop("mine_floor_bones_2", mineDir("wall_blank"), mineDir("floor_bones_2"))).weight(3).nextModel()
                .modelFile(models().cubeTop("mine_floor_bones_3", mineDir("wall_blank"), mineDir("floor_bones_3"))).weight(3).nextModel()
                .modelFile(models().cubeTop("mine_floor_bones_4", mineDir("wall_blank"), mineDir("floor_bones_4"))).weight(3).nextModel()
                .modelFile(models().cubeTop("mine_floor_leaves_1", mineDir("wall_blank"), mineDir("floor_leaves_1"))).weight(2).nextModel()
                .modelFile(models().cubeTop("mine_floor_leaves_2", mineDir("wall_blank"), mineDir("floor_leaves_2"))).weight(2).nextModel()
                .modelFile(models().cubeTop("mine_floor_leaves_3", mineDir("wall_blank"), mineDir("floor_leaves_3"))).weight(2).nextModel()
                .modelFile(models().cubeTop("mine_floor_leaves_4", mineDir("wall_blank"), mineDir("floor_leaves_4"))).weight(2).nextModel()
                .modelFile(models().cubeTop("mine_floor_pebble_1", mineDir("wall_blank"), mineDir("floor_pebble_1"))).weight(4).nextModel()
                .modelFile(models().cubeTop("mine_floor_pebble_2", mineDir("wall_blank"), mineDir("floor_pebble_2"))).weight(4).nextModel()
                .modelFile(models().cubeTop("mine_floor_pebble_3", mineDir("wall_blank"), mineDir("floor_pebble_3"))).weight(4).nextModel()
                .modelFile(models().cubeTop("mine_floor_pebble_4", mineDir("wall_blank"), mineDir("floor_pebble_4"))).weight(4).nextModel()
                .modelFile(models().cubeTop("mine_floor_pebble_5", mineDir("wall_blank"), mineDir("floor_pebble_5"))).weight(4).nextModel()
                .modelFile(models().cubeTop("mine_floor_pebble_6", mineDir("wall_blank"), mineDir("floor_pebble_6"))).weight(4).nextModel()
                .modelFile(models().cubeTop("mine_floor_rock_1", mineDir("wall_blank"), mineDir("floor_rock_1"))).weight(3).nextModel()
                .modelFile(models().cubeTop("mine_floor_rock_2", mineDir("wall_blank"), mineDir("floor_rock_2"))).weight(3).build());
    }

    private void registerWallVariants(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(models().cubeAll("mine_wall_blank", new ResourceLocation(HarvestFestival.MODID, "block/mine/wall_blank"))).weight(20).nextModel()
                .modelFile(models().cubeAll("mine_wall_gems_1", new ResourceLocation(HarvestFestival.MODID, "block/mine/wall_gems_1"))).weight(1).nextModel()
                .modelFile(models().cubeAll("mine_wall_gems_2", new ResourceLocation(HarvestFestival.MODID, "block/mine/wall_gems_2"))).weight(1).nextModel()
                .modelFile(models().cubeAll("mine_wall_gems_3", new ResourceLocation(HarvestFestival.MODID, "block/mine/wall_gems_3"))).weight(1).nextModel()
                .modelFile(models().cubeAll("mine_wall_gems_4", new ResourceLocation(HarvestFestival.MODID, "block/mine/wall_gems_4"))).weight(1).nextModel()
                .modelFile(models().cubeAll("mine_wall_gems_5", new ResourceLocation(HarvestFestival.MODID, "block/mine/wall_gems_5"))).weight(1).build());
    }

    private void registerPortalBasic(Block block, String particle, String wall) {
        //Add the wall texture key too!!
        ModelFile tl = getPortalModel(block, particle, wall, "tl");
        ModelFile tm = getPortalModel(block, particle, wall, "tm");
        ModelFile tr = getPortalModel(block, particle, wall, "tr");
        ModelFile bl = getPortalModel(block, particle, wall, "bl");
        ModelFile bm = getPortalModel(block, particle, wall, "bm");
        ModelFile br = getPortalModel(block, particle, wall, "br");
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.TL).modelForState().modelFile(tl).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.TM).modelForState().modelFile(tm).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.TR).modelForState().modelFile(tr).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.BL).modelForState().modelFile(bl).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.BM).modelForState().modelFile(bm).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.BR).modelForState().modelFile(br).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.TL).modelForState().modelFile(tl).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.TM).modelForState().modelFile(tm).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.TR).modelForState().modelFile(tr).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.BL).modelForState().modelFile(bl).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.BM).modelForState().modelFile(bm).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.BR).modelForState().modelFile(br).rotationY(270).addModel();
    }

    private ModelFile getPortalModel(Block block, String particle, String wall, String section) {
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ResourceLocation parent = modLoc("block/" + "portal");
        return models().withExistingParent(String.format("%s_%s", path, section), parent)
                .texture("wall", wall.contains("minecraft:") ? mcLoc(wall) : modLoc(String.format("block/mine/%s", wall)))
                .texture("particle", modLoc(String.format("block/mine/portal/%s/%s", particle, section)));
    }

    private ResourceLocation mineDir(String dir, String path) {
        return this.modLoc(String.format("block/mine/interior/%s/%s", dir, path));
    }


    private void floorBlock(Block block, String dir) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(this.models().cubeTop(name + "_blank", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_blank"))).weight(60).nextModel()
                .modelFile(this.models().cubeTop(name + "_bones_1", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_bones_1"))).weight(3).nextModel()
                .modelFile(this.models().cubeTop(name + "_bones_2", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_bones_2"))).weight(3).nextModel()
                .modelFile(this.models().cubeTop(name + "_bones_3", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_bones_3"))).weight(3).nextModel()
                .modelFile(this.models().cubeTop(name + "_bones_4", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_bones_4"))).weight(3).nextModel()
                .modelFile(this.models().cubeTop(name + "_leaves_1", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_leaves_1"))).weight(2).nextModel()
                .modelFile(this.models().cubeTop(name + "_leaves_2", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_leaves_2"))).weight(2).nextModel()
                .modelFile(this.models().cubeTop(name + "_leaves_3", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_leaves_3"))).weight(2).nextModel()
                .modelFile(this.models().cubeTop(name + "_leaves_4", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_leaves_4"))).weight(2).nextModel()
                .modelFile(this.models().cubeTop(name + "_pebble_1", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_pebble_1"))).weight(4).nextModel()
                .modelFile(this.models().cubeTop(name + "_pebble_2", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_pebble_2"))).weight(4).nextModel()
                .modelFile(this.models().cubeTop(name + "_pebble_3", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_pebble_3"))).weight(4).nextModel()
                .modelFile(this.models().cubeTop(name + "_pebble_4", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_pebble_4"))).weight(4).nextModel()
                .modelFile(this.models().cubeTop(name + "_pebble_5", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_pebble_5"))).weight(4).nextModel()
                .modelFile(this.models().cubeTop(name + "_pebble_6", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_pebble_6"))).weight(4).nextModel()
                .modelFile(this.models().cubeTop(name + "_rock_1", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_rock_1"))).weight(3).nextModel()
                .modelFile(this.models().cubeTop(name + "_rock_2", this.mineDir(dir, "wall_blank"), this.mineDir(dir, "floor_rock_2"))).weight(3).build());
    }

    private void cobblestoneBlock(Block block, String texture) {
        //Also add one rotated 90 degrees
        ResourceLocation parent = new ResourceLocation(HarvestFestival.MODID, "block/cobblestone");
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
        if (key.getPath().equals("cobblestone"))
            key = new ResourceLocation(HarvestFestival.MODID, "block/cobblestone_standard");
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(models().withExistingParent(key.getPath(), parent).texture("particle", modLoc("block/mine/interior/" + texture + "/wall_blank"))).rotationY(0).nextModel()
                .modelFile(models().withExistingParent(key.getPath() + "_rotated", parent).texture("particle", modLoc("block/mine/interior/" + texture + "/wall_blank"))).rotationY(90)
                .build());
    }

    //Create model variants using the existing model with 3 different textures (stone_1, stone_2, stone_3) and with 4 rotations
    private void stoneBlock(Block block) {
        VariantBlockStateBuilder stoneBuilder = getVariantBuilder(block);
        ResourceLocation parent = BuiltInRegistries.BLOCK.getKey(block);
        ModelFile stone_1 = models().withExistingParent(parent + "_1", parent).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/stone_1"));
        ModelFile stone_2 = models().withExistingParent(parent + "_2", parent).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/stone_2"));
        ModelFile stone_3 = models().withExistingParent(parent + "_3", parent).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/stone_3"));
        stoneBuilder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(stone_1).rotationY(0).nextModel()
                .modelFile(stone_1).rotationY(90).nextModel()
                .modelFile(stone_1).rotationY(-90).nextModel()
                .modelFile(stone_1).rotationY(180).nextModel()
                .modelFile(stone_2).rotationY(0).nextModel()
                .modelFile(stone_2).rotationY(90).nextModel()
                .modelFile(stone_2).rotationY(-90).nextModel()
                .modelFile(stone_2).rotationY(180).nextModel()
                .modelFile(stone_3).rotationY(0).nextModel()
                .modelFile(stone_3).rotationY(90).nextModel()
                .modelFile(stone_3).rotationY(-90).nextModel()
                .modelFile(stone_3).rotationY(180)
                .build());
    }

    private void boulderBlock(Block block) {
        VariantBlockStateBuilder boulderBuilder = getVariantBuilder(block);
        ResourceLocation parent = BuiltInRegistries.BLOCK.getKey(block);
        ModelFile stone_1 = models().withExistingParent(parent + "_1", parent).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/stone_1"));
        ModelFile stone_2 = models().withExistingParent(parent + "_2", parent).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/stone_2"));
        ModelFile stone_3 = models().withExistingParent(parent + "_3", parent).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/stone_3"));
        boulderBuilder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(stone_1).nextModel()
                .modelFile(stone_2).nextModel()
                .modelFile(stone_3)
                .build());
    }

    private void rockBlock(Block block, String parent) {
        VariantBlockStateBuilder rockBuilder = getVariantBuilder(block);
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        //Create model variants for all 5 rock models and 8 rock textures
        ModelFile rock_1_t1 = models().withExistingParent(path + "_1_t1", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "1"));
        ModelFile rock_1_t2 = models().withExistingParent(path + "_1_t2", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "2"));
        ModelFile rock_1_t3 = models().withExistingParent(path + "_1_t3", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "3"));
        ModelFile rock_1_t4 = models().withExistingParent(path + "_1_t4", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "4"));
        ModelFile rock_1_t5 = models().withExistingParent(path + "_1_t5", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "5"));
        ModelFile rock_1_t6 = models().withExistingParent(path + "_1_t6", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "6"));
        ModelFile rock_1_t7 = models().withExistingParent(path + "_1_t7", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "7"));
        ModelFile rock_1_t8 = models().withExistingParent(path + "_1_t8", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_1")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "8"));
        ModelFile rock_2_t1 = models().withExistingParent(path + "_2_t1", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "1"));
        ModelFile rock_2_t2 = models().withExistingParent(path + "_2_t2", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "2"));
        ModelFile rock_2_t3 = models().withExistingParent(path + "_2_t3", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "3"));
        ModelFile rock_2_t4 = models().withExistingParent(path + "_2_t4", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "4"));
        ModelFile rock_2_t5 = models().withExistingParent(path + "_2_t5", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "5"));
        ModelFile rock_2_t6 = models().withExistingParent(path + "_2_t6", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "6"));
        ModelFile rock_2_t7 = models().withExistingParent(path + "_2_t7", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "7"));
        ModelFile rock_2_t8 = models().withExistingParent(path + "_2_t8", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_2")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "8"));
        ModelFile rock_3_t1 = models().withExistingParent(path + "_3_t1", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "1"));
        ModelFile rock_3_t2 = models().withExistingParent(path + "_3_t2", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "2"));
        ModelFile rock_3_t3 = models().withExistingParent(path + "_3_t3", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "3"));
        ModelFile rock_3_t4 = models().withExistingParent(path + "_3_t4", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "4"));
        ModelFile rock_3_t5 = models().withExistingParent(path + "_3_t5", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "5"));
        ModelFile rock_3_t6 = models().withExistingParent(path + "_3_t6", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "6"));
        ModelFile rock_3_t7 = models().withExistingParent(path + "_3_t7", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "7"));
        ModelFile rock_3_t8 = models().withExistingParent(path + "_3_t8", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_3")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "8"));
        ModelFile rock_4_t1 = models().withExistingParent(path + "_4_t1", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "1"));
        ModelFile rock_4_t2 = models().withExistingParent(path + "_4_t2", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "2"));
        ModelFile rock_4_t3 = models().withExistingParent(path + "_4_t3", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "3"));
        ModelFile rock_4_t4 = models().withExistingParent(path + "_4_t4", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "4"));
        ModelFile rock_4_t5 = models().withExistingParent(path + "_4_t5", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "5"));
        ModelFile rock_4_t6 = models().withExistingParent(path + "_4_t6", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "6"));
        ModelFile rock_4_t7 = models().withExistingParent(path + "_4_t7", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "7"));
        ModelFile rock_4_t8 = models().withExistingParent(path + "_4_t8", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_4")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "8"));
        ModelFile rock_5_t1 = models().withExistingParent(path + "_5_t1", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "1"));
        ModelFile rock_5_t2 = models().withExistingParent(path + "_5_t2", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "2"));
        ModelFile rock_5_t3 = models().withExistingParent(path + "_5_t3", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "3"));
        ModelFile rock_5_t4 = models().withExistingParent(path + "_5_t4", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "4"));
        ModelFile rock_5_t5 = models().withExistingParent(path + "_5_t5", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "5"));
        ModelFile rock_5_t6 = models().withExistingParent(path + "_5_t6", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "6"));
        ModelFile rock_5_t7 = models().withExistingParent(path + "_5_t7", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "7"));
        ModelFile rock_5_t8 = models().withExistingParent(path + "_5_t8", new ResourceLocation(HarvestFestival.MODID, "block/" + parent + "_5")).texture("particle", new ResourceLocation(HarvestFestival.MODID, "block/mine/node/" + path + "8"));

        // Add all variants to builder as models with the rotations
        rockBuilder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(rock_1_t1).rotationY(0).nextModel()
                .modelFile(rock_1_t1).rotationY(90).nextModel()
                .modelFile(rock_1_t1).rotationY(-90).nextModel()
                .modelFile(rock_1_t1).rotationY(180).nextModel()
                .modelFile(rock_1_t2).rotationY(0).nextModel()
                .modelFile(rock_1_t2).rotationY(90).nextModel()
                .modelFile(rock_1_t2).rotationY(-90).nextModel()
                .modelFile(rock_1_t2).rotationY(180).nextModel()
                .modelFile(rock_1_t3).rotationY(0).nextModel()
                .modelFile(rock_1_t3).rotationY(90).nextModel()
                .modelFile(rock_1_t3).rotationY(-90).nextModel()
                .modelFile(rock_1_t3).rotationY(180).nextModel()
                .modelFile(rock_1_t4).rotationY(0).nextModel()
                .modelFile(rock_1_t4).rotationY(90).nextModel()
                .modelFile(rock_1_t4).rotationY(-90).nextModel()
                .modelFile(rock_1_t4).rotationY(180).nextModel()
                .modelFile(rock_1_t5).rotationY(0).nextModel()
                .modelFile(rock_1_t5).rotationY(90).nextModel()
                .modelFile(rock_1_t5).rotationY(-90).nextModel()
                .modelFile(rock_1_t5).rotationY(180).nextModel()
                .modelFile(rock_1_t6).rotationY(0).nextModel()
                .modelFile(rock_1_t6).rotationY(90).nextModel()
                .modelFile(rock_1_t6).rotationY(-90).nextModel()
                .modelFile(rock_1_t6).rotationY(180).nextModel()
                .modelFile(rock_1_t7).rotationY(0).nextModel()
                .modelFile(rock_1_t7).rotationY(90).nextModel()
                .modelFile(rock_1_t7).rotationY(-90).nextModel()
                .modelFile(rock_1_t7).rotationY(180).nextModel()
                .modelFile(rock_1_t8).rotationY(0).nextModel()
                .modelFile(rock_1_t8).rotationY(90).nextModel()
                .modelFile(rock_1_t8).rotationY(-90).nextModel()
                .modelFile(rock_1_t8).rotationY(180).nextModel()
                .modelFile(rock_2_t1).rotationY(0).nextModel()
                .modelFile(rock_2_t1).rotationY(90).nextModel()
                .modelFile(rock_2_t1).rotationY(-90).nextModel()
                .modelFile(rock_2_t1).rotationY(180).nextModel()
                .modelFile(rock_2_t2).rotationY(0).nextModel()
                .modelFile(rock_2_t2).rotationY(90).nextModel()
                .modelFile(rock_2_t2).rotationY(-90).nextModel()
                .modelFile(rock_2_t2).rotationY(180).nextModel()
                .modelFile(rock_2_t3).rotationY(0).nextModel()
                .modelFile(rock_2_t3).rotationY(90).nextModel()
                .modelFile(rock_2_t3).rotationY(-90).nextModel()
                .modelFile(rock_2_t3).rotationY(180).nextModel()
                .modelFile(rock_2_t4).rotationY(0).nextModel()
                .modelFile(rock_2_t4).rotationY(90).nextModel()
                .modelFile(rock_2_t4).rotationY(-90).nextModel()
                .modelFile(rock_2_t4).rotationY(180).nextModel()
                .modelFile(rock_2_t5).rotationY(0).nextModel()
                .modelFile(rock_2_t5).rotationY(90).nextModel()
                .modelFile(rock_2_t5).rotationY(-90).nextModel()
                .modelFile(rock_2_t5).rotationY(180).nextModel()
                .modelFile(rock_2_t6).rotationY(0).nextModel()
                .modelFile(rock_2_t6).rotationY(90).nextModel()
                .modelFile(rock_2_t6).rotationY(-90).nextModel()
                .modelFile(rock_2_t6).rotationY(180).nextModel()
                .modelFile(rock_2_t7).rotationY(0).nextModel()
                .modelFile(rock_2_t7).rotationY(90).nextModel()
                .modelFile(rock_2_t7).rotationY(-90).nextModel()
                .modelFile(rock_2_t7).rotationY(180).nextModel()
                .modelFile(rock_2_t8).rotationY(0).nextModel()
                .modelFile(rock_2_t8).rotationY(90).nextModel()
                .modelFile(rock_2_t8).rotationY(-90).nextModel()
                .modelFile(rock_2_t8).rotationY(180).nextModel()
                .modelFile(rock_3_t1).rotationY(0).nextModel()
                .modelFile(rock_3_t1).rotationY(90).nextModel()
                .modelFile(rock_3_t1).rotationY(-90).nextModel()
                .modelFile(rock_3_t1).rotationY(180).nextModel()
                .modelFile(rock_3_t2).rotationY(0).nextModel()
                .modelFile(rock_3_t2).rotationY(90).nextModel()
                .modelFile(rock_3_t2).rotationY(-90).nextModel()
                .modelFile(rock_3_t2).rotationY(180).nextModel()
                .modelFile(rock_3_t3).rotationY(0).nextModel()
                .modelFile(rock_3_t3).rotationY(90).nextModel()
                .modelFile(rock_3_t3).rotationY(-90).nextModel()
                .modelFile(rock_3_t3).rotationY(180).nextModel()
                .modelFile(rock_3_t4).rotationY(0).nextModel()
                .modelFile(rock_3_t4).rotationY(90).nextModel()
                .modelFile(rock_3_t4).rotationY(-90).nextModel()
                .modelFile(rock_3_t4).rotationY(180).nextModel()
                .modelFile(rock_3_t5).rotationY(0).nextModel()
                .modelFile(rock_3_t5).rotationY(90).nextModel()
                .modelFile(rock_3_t5).rotationY(-90).nextModel()
                .modelFile(rock_3_t5).rotationY(180).nextModel()
                .modelFile(rock_3_t6).rotationY(0).nextModel()
                .modelFile(rock_3_t6).rotationY(90).nextModel()
                .modelFile(rock_3_t6).rotationY(-90).nextModel()
                .modelFile(rock_3_t6).rotationY(180).nextModel()
                .modelFile(rock_3_t7).rotationY(0).nextModel()
                .modelFile(rock_3_t7).rotationY(90).nextModel()
                .modelFile(rock_3_t7).rotationY(-90).nextModel()
                .modelFile(rock_3_t7).rotationY(180).nextModel()
                .modelFile(rock_3_t8).rotationY(0).nextModel()
                .modelFile(rock_3_t8).rotationY(90).nextModel()
                .modelFile(rock_3_t8).rotationY(-90).nextModel()
                .modelFile(rock_3_t8).rotationY(180).nextModel()
                .modelFile(rock_4_t1).rotationY(0).nextModel()
                .modelFile(rock_4_t1).rotationY(90).nextModel()
                .modelFile(rock_4_t1).rotationY(-90).nextModel()
                .modelFile(rock_4_t1).rotationY(180).nextModel()
                .modelFile(rock_4_t2).rotationY(0).nextModel()
                .modelFile(rock_4_t2).rotationY(90).nextModel()
                .modelFile(rock_4_t2).rotationY(-90).nextModel()
                .modelFile(rock_4_t2).rotationY(180).nextModel()
                .modelFile(rock_4_t3).rotationY(0).nextModel()
                .modelFile(rock_4_t3).rotationY(90).nextModel()
                .modelFile(rock_4_t3).rotationY(-90).nextModel()
                .modelFile(rock_4_t3).rotationY(180).nextModel()
                .modelFile(rock_4_t4).rotationY(0).nextModel()
                .modelFile(rock_4_t4).rotationY(90).nextModel()
                .modelFile(rock_4_t4).rotationY(-90).nextModel()
                .modelFile(rock_4_t4).rotationY(180).nextModel()
                .modelFile(rock_4_t5).rotationY(0).nextModel()
                .modelFile(rock_4_t5).rotationY(90).nextModel()
                .modelFile(rock_4_t5).rotationY(-90).nextModel()
                .modelFile(rock_4_t5).rotationY(180).nextModel()
                .modelFile(rock_4_t6).rotationY(0).nextModel()
                .modelFile(rock_4_t6).rotationY(90).nextModel()
                .modelFile(rock_4_t6).rotationY(-90).nextModel()
                .modelFile(rock_4_t6).rotationY(180).nextModel()
                .modelFile(rock_4_t7).rotationY(0).nextModel()
                .modelFile(rock_4_t7).rotationY(90).nextModel()
                .modelFile(rock_4_t7).rotationY(-90).nextModel()
                .modelFile(rock_4_t7).rotationY(180).nextModel()
                .modelFile(rock_4_t8).rotationY(0).nextModel()
                .modelFile(rock_4_t8).rotationY(90).nextModel()
                .modelFile(rock_4_t8).rotationY(-90).nextModel()
                .modelFile(rock_4_t8).rotationY(180).nextModel()
                .modelFile(rock_5_t1).rotationY(0).nextModel()
                .modelFile(rock_5_t1).rotationY(90).nextModel()
                .modelFile(rock_5_t1).rotationY(-90).nextModel()
                .modelFile(rock_5_t1).rotationY(180).nextModel()
                .modelFile(rock_5_t2).rotationY(0).nextModel()
                .modelFile(rock_5_t2).rotationY(90).nextModel()
                .modelFile(rock_5_t2).rotationY(-90).nextModel()
                .modelFile(rock_5_t2).rotationY(180).nextModel()
                .modelFile(rock_5_t3).rotationY(0).nextModel()
                .modelFile(rock_5_t3).rotationY(90).nextModel()
                .modelFile(rock_5_t3).rotationY(-90).nextModel()
                .modelFile(rock_5_t3).rotationY(180).nextModel()
                .modelFile(rock_5_t4).rotationY(0).nextModel()
                .modelFile(rock_5_t4).rotationY(90).nextModel()
                .modelFile(rock_5_t4).rotationY(-90).nextModel()
                .modelFile(rock_5_t4).rotationY(180).nextModel()
                .modelFile(rock_5_t5).rotationY(0).nextModel()
                .modelFile(rock_5_t5).rotationY(90).nextModel()
                .modelFile(rock_5_t5).rotationY(-90).nextModel()
                .modelFile(rock_5_t5).rotationY(180).nextModel()
                .modelFile(rock_5_t6).rotationY(0).nextModel()
                .modelFile(rock_5_t6).rotationY(90).nextModel()
                .modelFile(rock_5_t6).rotationY(-90).nextModel()
                .modelFile(rock_5_t6).rotationY(180).nextModel()
                .modelFile(rock_5_t7).rotationY(0).nextModel()
                .modelFile(rock_5_t7).rotationY(90).nextModel()
                .modelFile(rock_5_t7).rotationY(-90).nextModel()
                .modelFile(rock_5_t7).rotationY(180).nextModel()
                .modelFile(rock_5_t8).rotationY(0).nextModel()
                .modelFile(rock_5_t8).rotationY(90).nextModel()
                .modelFile(rock_5_t8).rotationY(-90).nextModel()
                .modelFile(rock_5_t8).rotationY(180).build());
    }

    private void wallBlock(Block block, String interiorPath, String wallPrefix) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(models().cubeAll(String.format("%s_%s", wallPrefix, "blank"), new ResourceLocation(HarvestFestival.MODID, String.format("block/mine/interior/%s/wall_%s", interiorPath, "blank")))).weight(20).nextModel()
                .modelFile(models().cubeAll(String.format("%s_%s", wallPrefix, "gems_1"), new ResourceLocation(HarvestFestival.MODID, String.format("block/mine/interior/%s/wall_%s", interiorPath, "gems_1")))).weight(1).nextModel()
                .modelFile(models().cubeAll(String.format("%s_%s", wallPrefix, "gems_2"), new ResourceLocation(HarvestFestival.MODID, String.format("block/mine/interior/%s/wall_%s", interiorPath, "gems_2")))).weight(1).nextModel()
                .modelFile(models().cubeAll(String.format("%s_%s", wallPrefix, "gems_3"), new ResourceLocation(HarvestFestival.MODID, String.format("block/mine/interior/%s/wall_%s", interiorPath, "gems_3")))).weight(1).nextModel()
                .modelFile(models().cubeAll(String.format("%s_%s", wallPrefix, "gems_4"), new ResourceLocation(HarvestFestival.MODID, String.format("block/mine/interior/%s/wall_%s", interiorPath, "gems_4")))).weight(1).nextModel()
                .modelFile(models().cubeAll(String.format("%s_%s", wallPrefix, "gems_5"), new ResourceLocation(HarvestFestival.MODID, String.format("block/mine/interior/%s/wall_%s", interiorPath, "gems_5")))).weight(1).build());
    }

    private void registerBranchOrStump(String parent, String variant, String top, String side) {
        ModelFile model = models().withExistingParent(variant, new ResourceLocation(HarvestFestival.MODID, "block/" + parent))
                .texture("top", new ResourceLocation("block/" + top))
                .texture("side", new ResourceLocation("block/" + side));
        VariantBlockStateBuilder builder = getVariantBuilder(BuiltInRegistries.BLOCK.get(new ResourceLocation(HarvestFestival.MODID, variant)));
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(model).rotationY(0).nextModel()
                .modelFile(model).rotationY(90).nextModel()
                .modelFile(model).rotationY(-90).nextModel()
                .modelFile(model).rotationY(180).build());
    }

    private void nodeBlock(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        //In the mine subdir
        ModelFile m = models().getExistingFile(new ResourceLocation(HarvestFestival.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
        builder.partialState().setModels(ConfiguredModel.builder()
                .modelFile(m).rotationY(0).weight(1).nextModel()
                .modelFile(m).rotationY(90).weight(1).nextModel()
                .modelFile(m).rotationY(-90).weight(1).nextModel()
                .modelFile(m).rotationY(180).weight(1).build());
    }

    private void ladderBlock(Block block, String path) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int rotation = direction == Direction.NORTH ? 0 : direction == Direction.SOUTH ? 180 : direction == Direction.WEST ? 270 : 90;
            builder.partialState().with(LadderBlock.FACING, direction).setModels(ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                                    new ResourceLocation(HarvestFestival.MODID, "block/ladder"))
                            .texture("main", new ResourceLocation(HarvestFestival.MODID, "block/mine/interior/" + path + "/ladder")))
                    .rotationY(rotation).build());
        }
    }

    private void registerPortal(Block block, String level, String particle) {
        //Add the wall texture key too!!
        String wall = "wall_blank";
        ModelFile tl = getPortalModel(block, particle, level, wall, "tl");
        ModelFile tm = getPortalModel(block, particle, level, wall, "tm");
        ModelFile tr = getPortalModel(block, particle, level, wall, "tr");
        ModelFile bl = getPortalModel(block, particle, level, wall, "bl");
        ModelFile bm = getPortalModel(block, particle, level, wall, "bm");
        ModelFile br = getPortalModel(block, particle, level, wall, "br");
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.TL).modelForState().modelFile(tl).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.TM).modelForState().modelFile(tm).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.TR).modelForState().modelFile(tr).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.BL).modelForState().modelFile(bl).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.BM).modelForState().modelFile(bm).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, false).with(PortalBlock.SECTION, PortalBlock.Section.BR).modelForState().modelFile(br).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.TL).modelForState().modelFile(tl).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.TM).modelForState().modelFile(tm).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.TR).modelForState().modelFile(tr).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.BL).modelForState().modelFile(bl).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.BM).modelForState().modelFile(bm).rotationY(270).addModel();
        builder.partialState().with(PortalBlock.EAST_WEST, true).with(PortalBlock.SECTION, PortalBlock.Section.BR).modelForState().modelFile(br).rotationY(270).addModel();
    }

    private ModelFile getPortalModel(Block block, String subdir, String level, String wall, String section) {
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ResourceLocation parent = new ResourceLocation(HarvestFestival.MODID, "block/" + "portal");
        return models().withExistingParent(String.format("%s_%s", path, section), parent)
                .texture("wall", modLoc(String.format("block/mine/interior/%s/%s", subdir, wall)))
                .texture("particle", modLoc(String.format("block/mine/interior/%s/portal/%s/%s", subdir, level, section)));
    }
}
