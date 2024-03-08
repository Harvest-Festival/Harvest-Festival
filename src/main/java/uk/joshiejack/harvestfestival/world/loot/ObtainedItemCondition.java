package uk.joshiejack.harvestfestival.world.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFData;


public record ObtainedItemCondition(Item item) implements LootItemCondition {
    public static final Codec<ObtainedItemCondition> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ObtainedItemCondition::item))
            .apply(inst, ObtainedItemCondition::new));

    @Override
    public @NotNull LootItemConditionType getType() {
        return HFLoot.OBTAINED_ITEM.value();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY);
        return entity instanceof Player player && player.getData(HFData.PLAYER_DATA).hasObtained(item);
    }

    public static ObtainedItemCondition.Builder item(ItemLike item) {
        return () -> new ObtainedItemCondition(item.asItem());
    }
}
