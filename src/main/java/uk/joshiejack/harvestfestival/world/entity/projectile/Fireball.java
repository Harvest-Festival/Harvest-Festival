package uk.joshiejack.harvestfestival.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;

public class Fireball extends AbstractBall {
    public Fireball(EntityType<? extends Fireball> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Fireball(Level pLevel, LivingEntity pShooter) {
        super(HFEntities.FIREBALL.get(), pShooter, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.MAGMA_BLOCK;
    }

    protected ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? ParticleTypes.SMALL_FLAME : new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (random.nextInt(5) == 0) {
            pResult.getEntity().setSecondsOnFire(5);
        }
    }
}
