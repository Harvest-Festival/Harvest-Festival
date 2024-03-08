package uk.joshiejack.harvestfestival.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTicker;

@Mixin(BeetrootBlock.class)
public class HFBeetrootFix extends CropBlock {
    public HFBeetrootFix(Properties properties) {
        super(properties);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void fixRandomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (DailyTicker.has(pState)) {
            //Fix beetroot to ignore random.nextInt(3) == 0
            super.randomTick(pState, pLevel, pPos, pRandom);
            ci.cancel();
        }
    }
}