package uk.joshiejack.harvestfestival;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import uk.joshiejack.penguinlib.util.PenguinTags;

public class HFTags {
    public static class Blocks {
        public static final TagKey<Block> MINEABLE_WITH_HAMMER = BlockTags.create(new ResourceLocation("mineable/hammer"));
        public static final TagKey<Block> SMASHABLE = modBlockTag("smashable");
        public static final TagKey<Block> NODES = modBlockTag("nodes");
        public static final TagKey<Block> LOW_ENERGY_CONSUMPTION =  modBlockTag("low_energy_consumption");
        public static final TagKey<Block> HIGH_ENERGY_CONSUMPTION = modBlockTag("high_energy_consumption");
        public static final TagKey<Block> AWARDS_MINING_EXPERIENCE = modBlockTag("awards_mining_experience");
        public static final TagKey<Block> AWARDS_GATHERING_EXPERIENCE = modBlockTag("awards_gathering_experience");
        public static final TagKey<Block> AWARDS_FARMING_EXPERIENCE = modBlockTag("awards_farming_experience");
        public static final TagKey<Block> LOW_TOOL_EXPERIENCE = modBlockTag("awards_low_tool_experience");
        public static final TagKey<Block> HIGH_TOOL_EXPERIENCE = modBlockTag("awards_high_tool_experience");
        public static final TagKey<Block> FARM_LAND = modBlockTag("farm_land");
        public static final TagKey<Block> MINING_SKILL_BONUS = modBlockTag("mining_skill_bonus");
        public static final TagKey<Block> FARMING_SKILL_BONUS = modBlockTag("farming_skill_bonus");
        public static final TagKey<Block> GATHERING_SKILL_BONUS = modBlockTag("gathering_skill_bonus");
        public static final TagKey<EntityType<?>> COMBAT_SKILL_BONUS = modEntityTag("combat_skill_bonus");
    }

    public static class Items {
        public static final TagKey<Item> REQUIRES_ENERGY = ItemTags.create(new ResourceLocation(HarvestFestival.MODID, "requires_energy"));
        public static final TagKey<Item> ADAMANTITE = PenguinTags.forgeItemTag("gems/adamantite");
        public static final TagKey<Item> AGATE = PenguinTags.forgeItemTag("gems/agate");
        public static final TagKey<Item> ALEXANDRITE = PenguinTags.forgeItemTag("gems/alexandrite");
        public static final TagKey<Item> CHAROITE = PenguinTags.forgeItemTag("gems/charoite");
        public static final TagKey<Item> FLUORITE = PenguinTags.forgeItemTag("gems/fluorite");
        public static final TagKey<Item> JADE = PenguinTags.forgeItemTag("gems/jade");
        public static final TagKey<Item> MOONSTONE = PenguinTags.forgeItemTag("gems/moonstone");
        public static final TagKey<Item> OPAL = PenguinTags.forgeItemTag("gems/opal");
        public static final TagKey<Item> PERIDOT = PenguinTags.forgeItemTag("gems/peridot");
        public static final TagKey<Item> PINK_DIAMOND = PenguinTags.forgeItemTag("gems/pink_diamond");
        public static final TagKey<Item> RUBY = PenguinTags.forgeItemTag("gems/ruby");
        public static final TagKey<Item> SAND_ROSE = PenguinTags.forgeItemTag("gems/sand_rose");
        public static final TagKey<Item> SAPPHIRE = PenguinTags.forgeItemTag("gems/sapphire");
        public static final TagKey<Item> TOPAZ = PenguinTags.forgeItemTag("gems/topaz");
        public static final TagKey<Item> UNGIFTABLE = modItemTag("ungiftable");
        public static final TagKey<Item> INGOTS_SILVER = PenguinTags.forgeItemTag("ingots/silver");
        public static final TagKey<Item> INGOTS_MYSTRIL = PenguinTags.forgeItemTag("ingots/mystril");
        public static final TagKey<Item> MYTHIC_STONE = modItemTag("mythic_stone");
        public static final TagKey<Item> FARMING_TOOLS = modItemTag("tools/farming");
        public static final TagKey<Item> MINING_TOOLS = modItemTag("tools/mining");
        public static final TagKey<Item> GATHERING_TOOLS = modItemTag("tools/gathering");
        public static final TagKey<Item> FISHING_TOOLS = modItemTag("tools/fishing");
        public static final TagKey<Item> COMBAT_TOOLS = modItemTag("tools/combat");
        public static final TagKey<Item> COINS = PenguinTags.forgeItemTag("coin");
        public static final TagKey<Item> AWARDS_FISHING_EXPERIENCE = modItemTag("awards_fishing_experience");
        public static final TagKey<Item> AWARDS_COMBAT_EXPERIENCE = modItemTag("awards_combat_experience");
        public static final TagKey<Item> HAS_QUALITY_LEVELS = modItemTag("has_quality_levels");
    }

    public static final class Entities {
        public static final TagKey<EntityType<?>> AWARDS_LOW_COMBAT_EXPERIENCE = modEntityTag("awards_low_combat_experience");
        public static final TagKey<EntityType<?>> AWARDS_HIGH_COMBAT_EXPERIENCE = modEntityTag("awards_high_combat_experience");
        public static final TagKey<EntityType<?>> SLIMES = forgeEntityTag("slimes");

    }

    private static TagKey<Item> modItemTag(String tag) {
        return ItemTags.create(new ResourceLocation(HarvestFestival.MODID, tag));
    }

    private static TagKey<Block> modBlockTag(String tag) {
        return BlockTags.create(new ResourceLocation(HarvestFestival.MODID, tag));
    }

    private static TagKey<EntityType<?>> modEntityTag(String tag) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(HarvestFestival.MODID, tag));
    }


    private static TagKey<EntityType<?>> forgeEntityTag(String tag) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge", tag));
    }
}