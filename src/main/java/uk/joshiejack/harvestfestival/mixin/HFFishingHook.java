package uk.joshiejack.harvestfestival.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.joshiejack.harvestfestival.world.entity.player.Skill;

@Mixin(FishingHook.class)
public class HFFishingHook {
    @Mutable
    @Final
    @Shadow
    private int luck;

    @Mutable
    @Shadow
    @Final
    private int lureSpeed;

    //Target the constructor
    @Inject(method = "<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;II)V", at = @At("RETURN"))
    public void init(Player player, Level level, int luckIn, int speedIn, CallbackInfo ci) {
        int skill = Skill.getLevel(player, Skill.FISHING, Skill.LevelType.EFFECTIVE);
        luck += (skill/4);
        lureSpeed += (skill/2);
    }
}