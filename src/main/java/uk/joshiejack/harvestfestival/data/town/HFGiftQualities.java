package uk.joshiejack.harvestfestival.data.town;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import uk.joshiejack.penguinlib.data.generator.AbstractPenguinRegistryProvider;
import uk.joshiejack.settlements.Settlements;
import uk.joshiejack.settlements.world.entity.npc.gifts.GiftCategory;
import uk.joshiejack.settlements.world.entity.npc.gifts.GiftQuality;

import java.util.Map;

public class HFGiftQualities extends AbstractPenguinRegistryProvider<GiftQuality> {
    public static final GiftQuality AWESOME = new GiftQuality("awesome", GiftCategory.DefaultValues.AWESOME);
    public static final GiftQuality GOOD = new GiftQuality("good", GiftCategory.DefaultValues.GOOD);
    public static final GiftQuality DECENT = new GiftQuality("decent", GiftCategory.DefaultValues.DECENT);
    public static final GiftQuality DISLIKE = new GiftQuality("dislike", GiftCategory.DefaultValues.DISLIKE);
    public static final GiftQuality BAD = new GiftQuality("bad", GiftCategory.DefaultValues.BAD);
    public static final GiftQuality TERRIBLE = new GiftQuality("terrible", GiftCategory.DefaultValues.TERRIBLE);
    public HFGiftQualities(PackOutput output) {
        super(output, Settlements.Registries.GIFT_QUALITIES);
    }

    @Override
    protected void buildRegistry(Map<ResourceLocation, GiftQuality> map) {

    }
}
