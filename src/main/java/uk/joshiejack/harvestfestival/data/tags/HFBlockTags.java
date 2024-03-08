package uk.joshiejack.harvestfestival.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;

import java.util.concurrent.CompletableFuture;

public class HFBlockTags extends BlockTagsProvider {
    public HFBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, HarvestFestival.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.FEATURES_CANNOT_REPLACE).add(HFBlocks.HELL_MINE_WALL.get(), HFBlocks.HELL_MINE_BRICKS.get(), HFBlocks.HELL_MINE_FLOOR.get(), HFBlocks.HELL_MINE_LADDER.get(),
                HFBlocks.MINE_WALL.get(), HFBlocks.MINE_FLOOR.get(), HFBlocks.MINE_LADDER.get(), HFBlocks.UPPER_PORTAL.get(), HFBlocks.LOWER_PORTAL.get(), HFBlocks.MINE_PORTAL.get(),
                HFBlocks.FROZEN_MINE_WALL.get(), HFBlocks.FROZEN_MINE_BRICKS.get(), HFBlocks.FROZEN_MINE_FLOOR.get(), HFBlocks.FROZEN_MINE_LADDER.get(),
                HFBlocks.HELL_UPPER_PORTAL.get(), HFBlocks.HELL_LOWER_PORTAL.get(), HFBlocks.FROZEN_LOWER_PORTAL.get(), HFBlocks.FROZEN_UPPER_PORTAL.get(),
                HFBlocks.ELEVATOR.get());
        tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE).add(HFBlocks.HELL_MINE_WALL.get(), HFBlocks.HELL_MINE_BRICKS.get(), HFBlocks.HELL_MINE_FLOOR.get(), HFBlocks.HELL_MINE_LADDER.get(),
                HFBlocks.MINE_WALL.get(), HFBlocks.MINE_FLOOR.get(), HFBlocks.MINE_LADDER.get(), HFBlocks.UPPER_PORTAL.get(), HFBlocks.LOWER_PORTAL.get(), HFBlocks.MINE_PORTAL.get(),
                HFBlocks.FROZEN_MINE_WALL.get(), HFBlocks.FROZEN_MINE_BRICKS.get(), HFBlocks.FROZEN_MINE_FLOOR.get(), HFBlocks.FROZEN_MINE_LADDER.get(),
                HFBlocks.HELL_UPPER_PORTAL.get(), HFBlocks.HELL_LOWER_PORTAL.get(), HFBlocks.FROZEN_LOWER_PORTAL.get(), HFBlocks.FROZEN_UPPER_PORTAL.get(),
                HFBlocks.ELEVATOR.get());
        tag(BlockTags.CLIMBABLE).add(HFBlocks.MINE_LADDER.get());
        tag(BlockTags.DIRT).add(HFBlocks.MINE_FLOOR.get());
        //Add all the nodes and rocks to be mineable with a pickaxe
        tag(HFTags.Blocks.NODES).add(HFBlocks.AMETHYST_NODE.get(), HFBlocks.COPPER_NODE.get(), HFBlocks.DIAMOND_NODE.get(), HFBlocks.EMERALD_NODE.get(),
                HFBlocks.GEM_NODE.get(), HFBlocks.GOLD_NODE.get(), HFBlocks.IRON_NODE.get(), HFBlocks.JADE_NODE.get(),
                HFBlocks.MYSTRIL_NODE.get(), HFBlocks.RUBY_NODE.get(), HFBlocks.SILVER_NODE.get(), HFBlocks.TOPAZ_NODE.get());
        Block[] blocks = new Block[]{HFBlocks.AMETHYST_NODE.get(),
                HFBlocks.ROCK.get(), HFBlocks.HELL_ROCK.get(), HFBlocks.ICE_ROCK.get(), HFBlocks.SMALL_STONES.get(),
                HFBlocks.MEDIUM_STONES.get(), HFBlocks.LARGE_STONES.get(), HFBlocks.SMALL_BOULDER.get(),
                HFBlocks.MEDIUM_BOULDER.get(), HFBlocks.LARGE_BOULDER.get()};
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blocks).addTags(HFTags.Blocks.NODES);
        tag(HFTags.Blocks.MINEABLE_WITH_HAMMER).add(blocks).addTags(HFTags.Blocks.NODES);
        tag(HFTags.Blocks.SMASHABLE).add(blocks)
                .add(HFBlocks.SMALL_STONES.get(), HFBlocks.MEDIUM_STONES.get(), HFBlocks.LARGE_STONES.get(),
                        HFBlocks.SMALL_BOULDER.get(), HFBlocks.MEDIUM_BOULDER.get(), HFBlocks.LARGE_BOULDER.get());

        //Add Sword Efficient blockTag itemTag to crates, barrels and ice crystal
        tag(BlockTags.SWORD_EFFICIENT).add(HFBlocks.CRATE.get(), HFBlocks.BARREL.get(), HFBlocks.ICE_CRYSTAL.get());
        tag(BlockTags.CLIMBABLE).add(HFBlocks.FROZEN_MINE_LADDER.get(), HFBlocks.HELL_MINE_LADDER.get());

        //Tag the flowers as small flowers
        tag(BlockTags.SMALL_FLOWERS).add(HFBlocks.GODDESS_FLOWER.get(), HFBlocks.BLUE_MAGIC_FLOWER.get(), HFBlocks.MOONDROP_FLOWER.get(), HFBlocks.PINK_CAT_FLOWER.get(), HFBlocks.RED_MAGIC_FLOWER.get(), HFBlocks.TOY_FLOWER.get());
        tag(HFTags.Blocks.HIGH_ENERGY_CONSUMPTION).addTags(Tags.Blocks.ORES, HFTags.Blocks.NODES);
        tag(HFTags.Blocks.LOW_ENERGY_CONSUMPTION).addTags(BlockTags.DIRT, Tags.Blocks.SAND, Tags.Blocks.GRAVEL, Tags.Blocks.STONE);

        //Add the tags for the mining and gathering experience
        tag(HFTags.Blocks.AWARDS_MINING_EXPERIENCE).addTags(Tags.Blocks.ORES, HFTags.Blocks.NODES);
        tag(HFTags.Blocks.AWARDS_GATHERING_EXPERIENCE).add(HFBlocks.SMALL_STONES.get(), HFBlocks.MEDIUM_STONES.get(), HFBlocks.LARGE_STONES.get(),
                        HFBlocks.SMALL_BOULDER.get(), HFBlocks.MEDIUM_BOULDER.get(), HFBlocks.LARGE_BOULDER.get()).addTags(BlockTags.LOGS, BlockTags.SMALL_FLOWERS)
                .add(HFBlocks.TOADSTOOL.get(), HFBlocks.DWARFEN_SHROOM.get(), HFBlocks.WILD_GRAPES.get());

        //Add the experience tags
        tag(HFTags.Blocks.LOW_TOOL_EXPERIENCE).addTags(BlockTags.LOGS, Tags.Blocks.STONE, Tags.Blocks.SAND, BlockTags.DIRT).add(HFBlocks.ROCK.get(), HFBlocks.ICE_CRYSTAL.get(), HFBlocks.ICE_ROCK.get(), HFBlocks.HELL_ROCK.get());
        tag(HFTags.Blocks.HIGH_TOOL_EXPERIENCE).add(HFBlocks.MYSTRIL_NODE.get(), HFBlocks.GEM_NODE.get(), HFBlocks.DIAMOND_NODE.get(), HFBlocks.EMERALD_NODE.get(), Blocks.DIAMOND_ORE, Blocks.GOLD_ORE, Blocks.EMERALD_ORE);
        tag(HFTags.Blocks.AWARDS_FARMING_EXPERIENCE).addTags(BlockTags.CROPS);

        //Add the effective skill tags
        tag(HFTags.Blocks.MINING_SKILL_BONUS).addTags(HFTags.Blocks.AWARDS_MINING_EXPERIENCE);
        tag(HFTags.Blocks.GATHERING_SKILL_BONUS).add(HFBlocks.SMALL_STONES.get(), HFBlocks.MEDIUM_STONES.get(), HFBlocks.LARGE_STONES.get(),
                HFBlocks.SMALL_BOULDER.get(), HFBlocks.MEDIUM_BOULDER.get(), HFBlocks.LARGE_BOULDER.get(), Blocks.TALL_GRASS, Blocks.SHORT_GRASS);
        tag(HFTags.Blocks.FARMING_SKILL_BONUS).add(Blocks.WHEAT);
    }
}