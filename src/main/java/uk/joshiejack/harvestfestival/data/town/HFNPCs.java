package uk.joshiejack.harvestfestival.data.town;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.horticulture.world.item.HorticultureTags;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.penguinlib.util.registry.ReloadableRegistry;
import uk.joshiejack.settlements.world.entity.npc.Age;
import uk.joshiejack.settlements.world.entity.npc.NPC;
import uk.joshiejack.settlements.world.entity.npc.NPCClass;

import java.util.List;
import java.util.Map;

public class HFNPCs extends AbstractPenguinRegistryProvider<NPC> {
    public HFNPCs(PackOutput output, ReloadableRegistry<NPC> registry) {
        super(output, registry);
    }

    /*
    cropStrawberry,awesome
cropPineapple,good
harvestfestival:food#strawberry_jam,decent
strawberry_milk,decent
cat#flower,decent
cat#plant,decent
cat#mushroom,dislike
cat#cooking,dislike
cat#knowledge,dislike
cat#gem,dislike
cat#mineral,bad
cat#meat,bad
cat#fish,bad
cat#art,bad
cat#money,bad
rabbit_foot,bad
wine,bad
bones,terrible
can,terrible
boot,terrible
     */
    @Override
    protected void buildRegistry(Map<ResourceLocation, NPC> map) {
        NPCClass goddess = new NPCClass(Age.ADULT, false, 1.25F, 0.25F, true, true, true, true, false, 400, true);
        NPCClass dwarf = new NPCClass(Age.ADULT, false, 0.75F, 05F, false, false, true, false, true, 0, true);
        map.put(HarvestFestival.prefix("harvest_goddess"), new NPC(
                HarvestFestival.prefix("harvest_goddess"), //Loot Table
                HarvestFestival.prefix("npcs/harvest_goddess"), //Custom Script
                null,   //Custom Texture
                "goddess", //Occupation
                goddess, //Class
                0x8CEED3, //Inside Color
                0x4EC485, //Outside Color
                List.of(new NPC.ItemData(Ingredient.of(HorticultureTags.STRAWBERRY), HFGiftQualities.AWESOME.value()),
                        new NPC.ItemData(Ingredient.of(HorticultureTags.PINEAPPLE), HFGiftQualities.GOOD.value()),
                        //TODO: Strawberry Jam
                        //TODO: Strawberry Milk,
                        new NPC.ItemData(Ingredient.of(Items.RABBIT_FOOT), HFGiftQualities.BAD.value()),
                        //TODO: WINE
                        new NPC.ItemData(Ingredient.of(Items.BONE), HFGiftQualities.TERRIBLE.value())
                        //TODO: Can
                        //TODO: Boot
                        ), //Item Gift Overrides
                List.of() //Category Gift Overrides
                //TODO: Gift Category Flower
                //TODO: Gift Category Plant
                //TODO: Gift Category Mushroom
                //TODO: Gift Category Cooking
                //TODO: Gift Category Knowledge
                //TODO: Gift Category Gem
                //TODO: Gift Category Mineral
                //TODO: Gift Category Meat
                //TODO: Gift Category Fish
                //TODO: Gift Category Art
                //TODO: Gift Category Money
                )
        );
    }
}
