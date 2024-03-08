package uk.joshiejack.harvestfestival.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.joshiejack.harvestfestival.world.level.ticker.DailyTickLevelListener;

@Mixin(Level.class)
public abstract class HFLevel {
    @Inject(method = "markAndNotifyBlock", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.onBlockStateChange (Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private void onBlockChanged(BlockPos pos, LevelChunk levelchunk, BlockState oldState, BlockState newState, int flags, int recursion, CallbackInfo ci) {
        if ((Object)this instanceof ServerLevel sl)
            DailyTickLevelListener.onBlockChanged(sl, levelchunk, pos, oldState, newState);
    }
}