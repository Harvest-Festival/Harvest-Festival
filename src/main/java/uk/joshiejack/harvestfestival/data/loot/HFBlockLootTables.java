package uk.joshiejack.harvestfestival.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.harvestfestival.world.loot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HFBlockLootTables extends BlockLootSubProvider {
    protected HFBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return HFBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }

    @Override
    protected void generate() {
        //Flowers and shrooms drop self from HFBlocks
        dropSelf(HFBlocks.DWARFEN_SHROOM.get());
        dropSelf(HFBlocks.GODDESS_FLOWER.get());
        dropSelf(HFBlocks.BLUE_MAGIC_FLOWER.get());
        dropSelf(HFBlocks.MOONDROP_FLOWER.get());
        dropSelf(HFBlocks.PINK_CAT_FLOWER.get());
        dropSelf(HFBlocks.RED_MAGIC_FLOWER.get());
        dropSelf(HFBlocks.TOY_FLOWER.get());
        dropSelf(HFBlocks.WEEDS.get());
        dropSelf(HFBlocks.WILD_GRAPES.get());
        dropSelf(HFBlocks.TOADSTOOL.get());
        dropSelf(HFBlocks.COBBLESTONE.get());
        dropSelf(HFBlocks.FROZEN_COBBLESTONE.get());
        dropSelf(HFBlocks.HELL_COBBLESTONE.get());
        dropSelf(HFBlocks.FROZEN_COBBLE_BRICKS.get());
        dropSelf(HFBlocks.HELL_COBBLE_BRICKS.get());
        dropSelf(HFBlocks.MINE_FLOOR.get());
        dropSelf(HFBlocks.FROZEN_MINE_FLOOR.get());
        dropSelf(HFBlocks.HELL_MINE_FLOOR.get());

        dropSelf(HFBlocks.TELEVISION.get());
        dropSelf(HFBlocks.MAILBOX.get());
        dropSelf(HFBlocks.OAK_MAILBOX.get());
        dropSelf(HFBlocks.SPRUCE_MAILBOX.get());
        dropSelf(HFBlocks.BIRCH_MAILBOX.get());
        dropSelf(HFBlocks.JUNGLE_MAILBOX.get());
        dropSelf(HFBlocks.ACACIA_MAILBOX.get());
        dropSelf(HFBlocks.DARK_OAK_MAILBOX.get());
        dropSelf(HFBlocks.NETHER_BRICK_MAILBOX.get());
        dropSelf(HFBlocks.CHERRY_MAILBOX.get());
        dropSelf(HFBlocks.MANGROVE_MAILBOX.get());
        dropSelf(HFBlocks.CRIMSON_MAILBOX.get());
        dropSelf(HFBlocks.WARPED_MAILBOX.get());
        dropSelf(HFBlocks.BAMBOO_MAILBOX.get());


        dropNodeWithCount(HFBlocks.AMETHYST_NODE, Items.AMETHYST_SHARD, 3);
        dropNodeWithCount(HFBlocks.COPPER_NODE, HFItems.COPPER_ORE, 5);
        dropNodeWithCount(HFBlocks.EMERALD_NODE, Items.EMERALD, 4);
        dropNodeWithCount(HFBlocks.GOLD_NODE, HFItems.GOLD_ORE, 3);
        dropNodeWithCount(HFBlocks.IRON_NODE, HFItems.IRON_ORE, 4);
        dropNodeWithCount(HFBlocks.JADE_NODE, HFItems.JADE, 4);
        dropNodeWithCount(HFBlocks.MYSTRIL_NODE, HFItems.MYSTRIL_ORE, 3);
        dropNodeWithCount(HFBlocks.RUBY_NODE, HFItems.RUBY, 4);
        dropNodeWithCount(HFBlocks.SILVER_NODE, HFItems.SILVER_ORE, 4);
        dropNodeWithCount(HFBlocks.TOPAZ_NODE, HFItems.TOPAZ, 4);

        //Diamond node drops 1-3 diamonds with a weight of 511 or 1 pink diamond with a weight of 1,
        add(HFBlocks.DIAMOND_NODE.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).setWeight(511))
                .add(LootItem.lootTableItem(HFItems.PINK_DIAMOND).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).setWeight(1))));

        add(HFBlocks.GEM_NODE.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(5))
                                .add(LootItem.lootTableItem(HFItems.AGATE.get()).setWeight(190).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(1, 50)))
                                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(200).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(1, 40)))
                                .add(LootItem.lootTableItem(HFItems.FLUORITE.get()).setWeight(175).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(10, 60)))
                                .add(LootItem.lootTableItem(HFItems.PERIDOT.get()).setWeight(160).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(20, 70)))
                                .add(LootItem.lootTableItem(HFItems.RUBY.get()).setWeight(125).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(40, 100)))
                                .add(LootItem.lootTableItem(HFItems.TOPAZ.get()).setWeight(150).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(30, 80)))
                                .add(LootItem.lootTableItem(HFItems.ALEXANDRITE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorMultipleOfCondition.multipleOf(10).and(FloorFromCondition.from(110))))
                                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(80).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorMultipleOfCondition.multipleOf(3).and(FloorFromCondition.from(81))))
                                .add(LootItem.lootTableItem(HFItems.SAPPHIRE.get()).setWeight(100).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(30, 60)))
                                .add(LootItem.lootTableItem(Items.EMERALD).setWeight(90).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(50, 100)))
                                .add(LootItem.lootTableItem(HFItems.MOONSTONE.get()).setWeight(225).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(70, 100)))
                                .add(LootItem.lootTableItem(HFItems.SAND_ROSE.get()).setWeight(200).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(60, 100)))
                                .add(LootItem.lootTableItem(HFItems.PINK_DIAMOND.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorFromCondition.from(102).and(FloorMultipleOfCondition.multipleOf(3))))
                                .add(LootItem.lootTableItem(HFItems.JADE.get()).setWeight(110).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1))).when(FloorBetweenScaledCondition.betweenScaled(50, 100)))));


        //All three of these rocks have the same loot table so we can create a method to get the loot table
        //And then apply it to all three of them
        add(HFBlocks.ROCK.get(), rockLootTable());
        add(HFBlocks.ICE_ROCK.get(), rockLootTable());
        add(HFBlocks.HELL_ROCK.get(), rockLootTable());

        //Ice crystal has 2 pools, once with a weight of 1 and the other 50
        //One pool drops nothing, the other drops with a weight of 4: silver coin, 5: adamantite, 1: moonstone
        add(HFBlocks.ICE_CRYSTAL.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(HFItems.SILVER_COIN).setWeight(4))
                                .add(LootItem.lootTableItem(HFItems.ADAMANTITE).setWeight(5))
                                .add(LootItem.lootTableItem(HFItems.MOONSTONE).setWeight(1)))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.AIR).setWeight(140))));


        //TODO: Crates and Barrels drop random stuff
        LootBuilder.create(HFBlocks.BARREL, this)
                .withItem(Items.STICK, 125)
                .withItem(Items.OAK_SAPLING, 100)
                .withItem(Items.FLINT, 75)
                .withItem(Items.CHARCOAL, 50)
                .withItem(Items.CARROT, 40)
                .withItem(Items.CLAY_BALL, 35)
                .withItem(HFItems.COPPER_COIN.get(), 10)
                .withItem(Items.REDSTONE, 5)
                .withItem(Items.AMETHYST_SHARD, 3)
                .withItem(HFItems.TOPAZ.get(), 2)
                .withItem(Items.GOLDEN_CARROT, 1)
                .add();

        LootBuilder.create(HFBlocks.CRATE, this)
                .withItem(Items.COCOA_BEANS, 25)
                .withItem(Items.BRICK, 20)
                .withItem(Items.BOOK, 15)
                .withItem(HFItems.ESCAPE_ROPE.get(), 10)
                .withItem(Items.IRON_INGOT, 7)
                .withItem(Items.LAPIS_LAZULI, 5)
                .withItem(Items.GOLD_INGOT, 2)
                .withItem(Items.CAKE, 1)
                .add();

        LootBuilder.create(HFBlocks.FROZEN_BARREL, this)
                .withItem(Items.DEAD_BUSH, 25)
                .withItem(Items.SPRUCE_SAPLING, 20)
                .withItem(Items.COD, 15)
                .withItem(Items.LAPIS_LAZULI, 10)
                .withItem(HFItems.SILVER_COIN.get(), 7)
                .withItem(Items.PRISMARINE_SHARD, 2)
                .withItem(Items.PRISMARINE_CRYSTALS, 1)
                .add();

        LootBuilder.create(HFBlocks.HELL_CHEST, this).
                withItem(Items.QUARTZ, 250).
                withItem(Items.NETHER_BRICK, 200).
                withItem(Items.NETHER_WART, 150).
                withItem(Items.COAL, 100).
                withItem(HFItems.GOLD_COIN.get(), 20).
                withItem(Items.GHAST_TEAR, 10).
                withItem(Items.GOLDEN_APPLE, 1).
                add();


        //Small branche 1-4, medium 2-6, large 5-9, small stump 3-7, medium 4-8, large 6-10
        dropBranchStumpOrStone(HFBlocks.SMALL_OAK_BRANCH, Blocks.OAK_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_OAK_BRANCH, Blocks.OAK_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_OAK_BRANCH, Blocks.OAK_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_OAK_STUMP, Blocks.OAK_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_OAK_STUMP, Blocks.OAK_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_OAK_STUMP, Blocks.OAK_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_BIRCH_BRANCH, Blocks.BIRCH_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_BIRCH_BRANCH, Blocks.BIRCH_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_BIRCH_BRANCH, Blocks.BIRCH_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_BIRCH_STUMP, Blocks.BIRCH_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_BIRCH_STUMP, Blocks.BIRCH_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_BIRCH_STUMP, Blocks.BIRCH_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_SPRUCE_BRANCH, Blocks.SPRUCE_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_SPRUCE_BRANCH, Blocks.SPRUCE_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_SPRUCE_BRANCH, Blocks.SPRUCE_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_SPRUCE_STUMP, Blocks.SPRUCE_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_SPRUCE_STUMP, Blocks.SPRUCE_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_SPRUCE_STUMP, Blocks.SPRUCE_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_JUNGLE_BRANCH, Blocks.JUNGLE_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_JUNGLE_BRANCH, Blocks.JUNGLE_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_JUNGLE_BRANCH, Blocks.JUNGLE_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_JUNGLE_STUMP, Blocks.JUNGLE_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_JUNGLE_STUMP, Blocks.JUNGLE_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_JUNGLE_STUMP, Blocks.JUNGLE_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_DARK_OAK_BRANCH, Blocks.DARK_OAK_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_DARK_OAK_BRANCH, Blocks.DARK_OAK_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_DARK_OAK_BRANCH, Blocks.DARK_OAK_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_DARK_OAK_STUMP, Blocks.DARK_OAK_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_DARK_OAK_STUMP, Blocks.DARK_OAK_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_DARK_OAK_STUMP, Blocks.DARK_OAK_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_ACACIA_BRANCH, Blocks.ACACIA_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_ACACIA_BRANCH, Blocks.ACACIA_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_ACACIA_BRANCH, Blocks.ACACIA_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_ACACIA_STUMP, Blocks.ACACIA_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_ACACIA_STUMP, Blocks.ACACIA_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_ACACIA_STUMP, Blocks.ACACIA_LOG, 6, 10);

        //mangrove, cherry, crimson, warped
        dropBranchStumpOrStone(HFBlocks.SMALL_MANGROVE_BRANCH, Blocks.MANGROVE_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_MANGROVE_BRANCH, Blocks.MANGROVE_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_MANGROVE_BRANCH, Blocks.MANGROVE_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_MANGROVE_STUMP, Blocks.MANGROVE_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_MANGROVE_STUMP, Blocks.MANGROVE_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_MANGROVE_STUMP, Blocks.MANGROVE_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_CHERRY_BRANCH, Blocks.CHERRY_LOG, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_CHERRY_BRANCH, Blocks.CHERRY_LOG, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_CHERRY_BRANCH, Blocks.CHERRY_LOG, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_CHERRY_STUMP, Blocks.CHERRY_LOG, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_CHERRY_STUMP, Blocks.CHERRY_LOG, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_CHERRY_STUMP, Blocks.CHERRY_LOG, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_CRIMSON_BRANCH, Blocks.CRIMSON_STEM, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_CRIMSON_BRANCH, Blocks.CRIMSON_STEM, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_CRIMSON_BRANCH, Blocks.CRIMSON_STEM, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_CRIMSON_STUMP, Blocks.CRIMSON_STEM, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_CRIMSON_STUMP, Blocks.CRIMSON_STEM, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_CRIMSON_STUMP, Blocks.CRIMSON_STEM, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_WARPED_BRANCH, Blocks.WARPED_STEM, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_WARPED_BRANCH, Blocks.WARPED_STEM, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_WARPED_BRANCH, Blocks.WARPED_STEM, 5, 9);
        dropBranchStumpOrStone(HFBlocks.SMALL_WARPED_STUMP, Blocks.WARPED_STEM, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_WARPED_STUMP, Blocks.WARPED_STEM, 4, 8);
        dropBranchStumpOrStone(HFBlocks.LARGE_WARPED_STUMP, Blocks.WARPED_STEM, 6, 10);

        dropBranchStumpOrStone(HFBlocks.SMALL_STONES, HFBlocks.SMALL_STONES, 1, 4);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_STONES, HFBlocks.MEDIUM_STONES, 2, 6);
        dropBranchStumpOrStone(HFBlocks.LARGE_STONES, HFBlocks.LARGE_STONES, 4, 8);
        dropBranchStumpOrStone(HFBlocks.SMALL_BOULDER, HFBlocks.SMALL_BOULDER, 3, 7);
        dropBranchStumpOrStone(HFBlocks.MEDIUM_BOULDER, HFBlocks.MEDIUM_BOULDER, 5, 9);
        dropBranchStumpOrStone(HFBlocks.LARGE_BOULDER, HFBlocks.LARGE_BOULDER, 6, 10);
    }

    private LootTable.Builder rockLootTable() {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3))
                .add(LootItem.lootTableItem(HFItems.JUNK_ORE).setWeight(170).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                .add(LootItem.lootTableItem(HFItems.COPPER_COIN).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorFromCondition.from(1))))
                .add(LootItem.lootTableItem(HFItems.ADAMANTITE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorFromCondition.from(10))))
                .add(LootItem.lootTableItem(HFItems.ORICHALC).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorFromCondition.from(10))))
                .add(LootItem.lootTableItem(HFItems.SILVER_COIN).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorFromCondition.from(41))))
                .add(LootItem.lootTableItem(HFItems.GOLD_COIN).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorFromCondition.from(81))))
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorExactCondition.exact(30).and(ObtainedItemCondition.item(HFItems.BLESSED_AXE))))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorBetweenCondition.between(43, 84).and(FloorMultipleOfCondition.multipleOf(20)).and(ObtainedItemCondition.item(HFItems.BLESSED_AXE))))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorBetweenCondition.between(85, 126).and(FloorMultipleOfCondition.multipleOf(15)).and(ObtainedItemCondition.item(HFItems.BLESSED_AXE))))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorBetweenCondition.between(127, 168).and(FloorMultipleOfCondition.multipleOf(10)).and(ObtainedItemCondition.item(HFItems.BLESSED_AXE))))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorBetweenCondition.between(169, 211).and(FloorMultipleOfCondition.multipleOf(5)).and(ObtainedItemCondition.item(HFItems.BLESSED_AXE))))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(16).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorBetweenCondition.between(212, 254).and(FloorMultipleOfCondition.multipleOf(3)).and(ObtainedItemCondition.item(HFItems.BLESSED_AXE))))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.MYTHIC_STONE).setWeight(32).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(FloorFromCondition.from(255)))) //TODO: Add obtained blessed tools condition
                .add(LootItem.lootTableItem(HFItems.CURSED_HOE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(39)
                        .and(ObtainedItemCondition.item(HFItems.MYSTRIL_AXE).and(ObtainedItemCondition.item(HFItems.MYSTRIL_HAMMER).and(ObtainedItemCondition.item(HFItems.MYSTRIL_HOE)
                                .and(ObtainedItemCondition.item(HFItems.MYSTRIL_SWORD))))))) //TODO: Add obtained other mystril tools condition //TODO: Add when season winter
                .add(LootItem.lootTableItem(HFItems.CURSED_AXE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(49))) //TODO: Add obtained mystril tools condition //TODO: Add when season winter
                .add(LootItem.lootTableItem(HFItems.CURSED_HAMMER).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(59))) //TODO: Add obtained mystril tools condition //TODO: Add when season winter
                //TODO: CURSED WATERING CAN .add(LootItem.lootTableItem(HCItems.CURSED_WATERING_CAN).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(69)) //TODO: Add obtained mystril tools condition //TODO: Add when season winter
                //TODO.add(LootItem.lootTableItem(HCItems.CURSED_SICKLE).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(79)) //TODO: Add obtained mystril tools condition //TODO: Add when season winter
                //TODO.add(LootItem.lootTableItem(HCItems.CURSED_FISHING_ROD).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(29)) //TODO: Add obtained mystril tools condition //TODO: Add when season winter
                //TODO.add(LootItem.lootTableItem(HCItems.CURSED_SHOVEL).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).when(FloorExactCondition.exact(89)) //TODO: Add obtained mystril tools condition //TODO: Add when season winter
                .add(LootItem.lootTableItem(HFItems.CURSED_SWORD).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F)).when(FloorExactCondition.exact(99))))); //TODO: Add obtained mystril tools condition //TODO: Add when season winter
    }

    private void dropNodeWithCount(DeferredBlock<Block> node, ItemLike gem, int max) {
        add(node.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(gem).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, max))).apply(ApplySkillBonus.applySkill(Skill.MINING)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    //Drops a branch or stump by default drops 1-3 sticks for a small_branch, 2-4 for a medium_branch and 3-5 for a large_branch and 1 log for small_branch, 2 for medium_branch and 3 for large_branch
    //Stumps drop 2-3 logs for small_stump, 3-4 for medium_stump and 4-5 for large_stump
    //If the player has silk touch, it drops the blockTag instead
    private void dropBranchStumpOrStone(DeferredBlock<Block> block, ItemLike item, int min, int max) {
        add(block.get(), createSilkTouchDispatchTable(block.get(), this.applyExplosionCondition(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplySkillBonus.applySkill(Skill.GATHERING)))));
    }

    private static class LootBuilder {
        private final List<Pair<Item, Integer>> list = new ArrayList<>();
        private final HFBlockLootTables provider;
        private final Block block;

        private LootBuilder(HFBlockLootTables provider, Block block) {
            this.provider = provider;
            this.block = block;
        }

        public static LootBuilder create(DeferredBlock<Block> block, HFBlockLootTables provider) {
            return new LootBuilder(provider, block.get());
        }

        public LootBuilder withItem(Item item, int weight) {
            list.add(Pair.of(item, weight));
            return this;
        }

        public void add() {
            LootPool.Builder builder = LootPool.lootPool().setRolls(ConstantValue.exactly(3));
            for (Pair<Item, Integer> pair : list) {
                builder.add(LootItem.lootTableItem(pair.getLeft()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))).setWeight(pair.getRight()));
            }

            provider.add(block, LootTable.lootTable().withPool(builder));
        }
    }
}
