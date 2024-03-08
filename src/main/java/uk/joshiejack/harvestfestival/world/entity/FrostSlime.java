package uk.joshiejack.harvestfestival.world.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;

public class FrostSlime extends Slime {
    public FrostSlime(EntityType<? extends FrostSlime> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes();
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected @NotNull SoundEvent getSquishSound() {
        return HarvestFestival.HFSounds.FROST_SLIME.get();
    }
}