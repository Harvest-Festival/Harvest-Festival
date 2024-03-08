package uk.joshiejack.harvestfestival.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFTags;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.world.block.HFBlocks;
import uk.joshiejack.harvestfestival.world.collections.Collections;
import uk.joshiejack.harvestfestival.world.item.HFItems;
import uk.joshiejack.penguinlib.data.generator.PenguinItemTags;
import uk.joshiejack.penguinlib.util.PenguinTags;

import java.util.concurrent.CompletableFuture;

public class HFItemTags extends PenguinItemTags {
    public HFItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockLookup, ExistingFileHelper existingFileHelper) {
        super(output, provider, blockLookup, existingFileHelper, HarvestFestival.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //Generate penguin tags too?
        super.addTags(provider);//TODO REMOVE THIS SOMEHOW
        tag(ItemTags.AXES).add(HFItems.BASIC_AXE.get(), HFItems.COPPER_AXE.get(), HFItems.SILVER_AXE.get(), HFItems.GOLD_AXE.get(), HFItems.MYSTRIL_AXE.get(), HFItems.CURSED_AXE.get(), HFItems.BLESSED_AXE.get(), HFItems.MYTHIC_AXE.get());
        tag(ItemTags.HOES).add(HFItems.BASIC_HOE.get(), HFItems.COPPER_HOE.get(), HFItems.SILVER_HOE.get(), HFItems.GOLD_HOE.get(), HFItems.MYSTRIL_HOE.get(), HFItems.CURSED_HOE.get(), HFItems.BLESSED_HOE.get(), HFItems.MYTHIC_HOE.get());
        //tag(ItemTags.PICKAXES).add(HFItems.BASIC_PICKAXE.get(), HFItems.COPPER_PICKAXE.get(), HFItems.SILVER_PICKAXE.get(), HFItems.GOLD_PICKAXE.get(), HFItems.MYSTRIL_PICKAXE.get(), HFItems.CURSED_PICKAXE.get(), HFItems.BLESSED_PICKAXE.get(), HFItems.MYTHIC_PICKAXE.get());
        //tag(ItemTags.SHOVELS).add(HFItems.BASIC_SHOVEL.get(), HFItems.COPPER_SHOVEL.get(), HFItems.SILVER_SHOVEL.get(), HFItems.GOLD_SHOVEL.get(), HFItems.MYSTRIL_SHOVEL.get(), HFItems.CURSED_SHOVEL.get(), HFItems.BLESSED_SHOVEL.get(), HFItems.MYTHIC_SHOVEL.get());
        tag(ItemTags.SWORDS).add(HFItems.BASIC_SWORD.get(), HFItems.COPPER_SWORD.get(), HFItems.SILVER_SWORD.get(), HFItems.GOLD_SWORD.get(), HFItems.MYSTRIL_SWORD.get(), HFItems.CURSED_SWORD.get(), HFItems.BLESSED_SWORD.get(), HFItems.MYTHIC_SWORD.get());
        tag(PenguinTags.WATERING_CANS).add(HFItems.COPPER_WATERING_CAN.get(), HFItems.SILVER_WATERING_CAN.get(), HFItems.GOLD_WATERING_CAN.get(), HFItems.MYSTRIL_WATERING_CAN.get(), HFItems.CURSED_WATERING_CAN.get(), HFItems.BLESSED_WATERING_CAN.get(), HFItems.MYTHIC_WATERING_CAN.get());
        //tag(PenguinTags.SICKLES).add(HFItems.BASIC_SICKLE.get(), HFItems.COPPER_SICKLE.get(), HFItems.SILVER_SICKLE.get(), HFItems.GOLD_SICKLE.get(), HFItems.MYSTRIL_SICKLE.get(), HFItems.CURSED_SICKLE.get(), HFItems.BLESSED_SICKLE.get(), HFItems.MYTHIC_SICKLE.get());
        tag(PenguinTags.HAMMERS).add(HFItems.BASIC_HAMMER.get(), HFItems.COPPER_HAMMER.get(), HFItems.SILVER_HAMMER.get(), HFItems.GOLD_HAMMER.get(), HFItems.MYSTRIL_HAMMER.get(), HFItems.CURSED_HAMMER.get(), HFItems.BLESSED_HAMMER.get(), HFItems.MYTHIC_HAMMER.get());
        addEnergyTags();
        //Tag all the gems
        tag(HFTags.Items.ADAMANTITE).add(HFItems.ADAMANTITE.get());
        tag(HFTags.Items.AGATE).add(HFItems.AGATE.get());
        tag(HFTags.Items.ALEXANDRITE).add(HFItems.ALEXANDRITE.get());
        tag(HFTags.Items.CHAROITE).add(HFItems.CHAROITE.get());
        tag(HFTags.Items.FLUORITE).add(HFItems.FLUORITE.get());
        tag(HFTags.Items.JADE).add(HFItems.JADE.get());
        tag(HFTags.Items.MOONSTONE).add(HFItems.MOONSTONE.get());
        tag(HFTags.Items.OPAL).add(HFItems.OPAL.get());
        tag(HFTags.Items.PERIDOT).add(HFItems.PERIDOT.get());
        tag(HFTags.Items.PINK_DIAMOND).add(HFItems.PINK_DIAMOND.get());
        tag(Tags.Items.GEMS_DIAMOND).add(HFItems.PINK_DIAMOND.get());
        tag(HFTags.Items.RUBY).add(HFItems.RUBY.get());
        tag(HFTags.Items.SAND_ROSE).add(HFItems.SAND_ROSE.get());
        tag(HFTags.Items.SAPPHIRE).add(HFItems.SAPPHIRE.get());
        tag(HFTags.Items.TOPAZ).add(HFItems.TOPAZ.get());
        //Add all gem tags to gems itemTag
        tag(Tags.Items.GEMS).addTags(HFTags.Items.ADAMANTITE, HFTags.Items.AGATE, HFTags.Items.ALEXANDRITE, HFTags.Items.CHAROITE, HFTags.Items.FLUORITE, HFTags.Items.JADE, HFTags.Items.MOONSTONE, HFTags.Items.OPAL, HFTags.Items.PERIDOT, HFTags.Items.PINK_DIAMOND, HFTags.Items.RUBY, HFTags.Items.SAND_ROSE, HFTags.Items.SAPPHIRE, HFTags.Items.TOPAZ);
        //Add items tagged as tools to the ungiftable itemTag
        tag(HFTags.Items.UNGIFTABLE).addTag(Tags.Items.TOOLS);
        tag(ItemTags.SMALL_FLOWERS).add(HFItems.GODDESS_FLOWER.get(), HFBlocks.BLUE_MAGIC_FLOWER.asItem(), HFBlocks.MOONDROP_FLOWER.asItem(), HFBlocks.PINK_CAT_FLOWER.asItem(), HFBlocks.RED_MAGIC_FLOWER.asItem(), HFBlocks.TOY_FLOWER.asItem());

        //Tag the hammers
        tag(PenguinTags.HAMMERS).add(HFItems.BASIC_HAMMER.get(), HFItems.COPPER_HAMMER.get(), HFItems.SILVER_HAMMER.get(), HFItems.GOLD_HAMMER.get(), HFItems.MYSTRIL_HAMMER.get(), HFItems.CURSED_HAMMER.get(), HFItems.BLESSED_HAMMER.get(), HFItems.MYTHIC_HAMMER.get());
        //Add all the tool tags to their respective tags
        tag(HFTags.Items.FARMING_TOOLS).addTags(/*TODO PenguinTags.WATERING_CANS, PenguinTags.SICKLES, */ItemTags.HOES);
        tag(HFTags.Items.MINING_TOOLS).addTags(ItemTags.PICKAXES, ItemTags.SHOVELS, PenguinTags.HAMMERS);
        tag(HFTags.Items.GATHERING_TOOLS).addTags(ItemTags.AXES);
        tag(HFTags.Items.FISHING_TOOLS).addTags(Tags.Items.TOOLS_FISHING_RODS);
        tag(HFTags.Items.COMBAT_TOOLS).addTags(ItemTags.SWORDS);
        tag(HFTags.Items.COINS).add(HFItems.COPPER_COIN.get(), HFItems.SILVER_COIN.get(), HFItems.GOLD_COIN.get());

        //Collections Tags
        //Add Raw Silver and Mystril to the raw materials itemTag
        tag(Tags.Items.RAW_MATERIALS).add(HFItems.SILVER_ORE.get(), HFItems.MYSTRIL_ORE.get());
        tag(Collections.FISHING_COLLECTION).addTags(PenguinTags.RAW_FISHES);
        tag(Collections.MINING_COLLECTION).add(HFItems.JUNK_ORE.get(), HFItems.MYTHIC_STONE.get()).addTags(Tags.Items.RAW_MATERIALS, Tags.Items.GEMS, Tags.Items.DUSTS_REDSTONE, HFTags.Items.COINS);
        tag(Collections.COOKING_COLLECTION).add(Items.BREAD);
        tag(Collections.SHIPPING_COLLECTION).addTags(Tags.Items.CROPS);

        //Quality Tags
        tag(HFTags.Items.HAS_QUALITY_LEVELS).addTags(Tags.Items.CROPS);
    }

    private void addEnergyTags() {
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> required = tag(HFTags.Items.REQUIRES_ENERGY);
        required.add(Items.CACTUS, Items.SHEARS, Items.BOW, Items.CROSSBOW, Items.TRIDENT, Items.SHEARS, Items.SUGAR_CANE); //TODO
        required.addTags(/*PenguinTags.WATERING_CANS, */ItemTags.HOES, Tags.Items.SEEDS,
                Tags.Items.CROPS, ItemTags.SAPLINGS);
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof BucketItem)
                .forEach(required::add);
    }
}
