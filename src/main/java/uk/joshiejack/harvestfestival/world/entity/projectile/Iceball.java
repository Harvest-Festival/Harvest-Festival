package uk.joshiejack.harvestfestival.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.world.entity.HFEntities;

import java.util.Objects;
import java.util.UUID;

public class Iceball extends AbstractBall {
    private static final AttributeModifier REDUCE_SPEED = new AttributeModifier(
            UUID.fromString("cff87596-e814-474e-83f7-c36947df113e"), "Iceball Speed reduction", -0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL
    );

    public Iceball(EntityType<? extends Iceball> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Iceball(Level pLevel, LivingEntity pShooter) {
        super(HFEntities.ICEBALL.get(), pShooter, pLevel);
    }

    protected ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? ParticleTypes.SNOWFLAKE : new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.PACKED_ICE;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (random.nextInt(3) == 0 && level().isClientSide && pResult.getEntity() instanceof Player player) {
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(REDUCE_SPEED);
            NeoForge.EVENT_BUS.register(new InSnow());
        }
    }

    public static class InSnow {
        private int counter = 0;
        @SubscribeEvent
        public void onTick(TickEvent.PlayerTickEvent event) {
            counter++;
            if (counter > 500) {
                Objects.requireNonNull(event.player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(REDUCE_SPEED.getId());
                NeoForge.EVENT_BUS.unregister(this);
            }
        }

        @SubscribeEvent
        public void onFog(ViewportEvent.RenderFog event) {
            event.setNearPlaneDistance(0.0F);
            event.setFarPlaneDistance(2.0F);
        }

        @SubscribeEvent
        public void onFogColor(ViewportEvent.ComputeFogColor event) {
            event.setRed(0.623F);
            event.setGreen(0.734F);
            event.setBlue(0.785F);
        }
    }
}
