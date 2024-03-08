package uk.joshiejack.harvestfestival.world.loot.global;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.world.farming.HFFarming;
import uk.joshiejack.harvestfestival.world.farming.Quality;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;
import uk.joshiejack.harvestfestival.world.level.ticker.HasQuality;

import java.util.Objects;

public record ApplyQualityModifier() implements IGlobalLootModifier {
    public static final Codec<ApplyQualityModifier> CODEC = Codec.unit(ApplyQualityModifier::new);

    public static ApplyQualityModifier of() {
        return new ApplyQualityModifier();
    }

    @Override
    public @NotNull Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (context.hasParam(LootContextParams.BLOCK_STATE) && context.hasParam(LootContextParams.ORIGIN)) {
            Vec3 vec3 = context.getParam(LootContextParams.ORIGIN);
            BlockPos pos = new BlockPos(Mth.floor(vec3.x), Mth.floor(vec3.y), Mth.floor(vec3.z));
            DailyTicker<?> ticker = DailyTicker.get(context.getLevel(), pos);
            if (ticker instanceof HasQuality) {
                Quality quality = ((HasQuality) ticker).getQuality();
                if (quality != HFFarming.QualityLevels.NORMAL.get()) {
                    generatedLoot.forEach(stack -> {
                        CompoundTag tag = stack.getOrCreateTag();
                        tag.putString("Quality", Objects.requireNonNull(HFRegistries.QUALITY.getKey(quality)).toString());
                    });
                }
            }
        }

        return generatedLoot;
    }
}
