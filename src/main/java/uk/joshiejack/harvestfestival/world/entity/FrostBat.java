package uk.joshiejack.harvestfestival.world.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import uk.joshiejack.harvestfestival.world.entity.projectile.Iceball;

public class FrostBat extends Bat implements RangedAttackMob {
    public FrostBat(EntityType<? extends FrostBat> type, Level level) {
        super(type, level);
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 20, 10.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 6.0D);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float partialTicks) {
        Iceball iceball = new Iceball(this.level(), this);
        double d0 = pTarget.getEyeY() - 1.1F;
        double d1 = pTarget.getX() - this.getX();
        double d2 = d0 - iceball.getY();
        double d3 = pTarget.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        iceball.shoot(d1, d2 + d4, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.POWDER_SNOW_HIT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(iceball);
    }
}
