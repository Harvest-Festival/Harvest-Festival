package uk.joshiejack.harvestfestival.world.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.penguinlib.util.helper.PlayerHelper;
import uk.joshiejack.penguinlib.util.helper.TimeHelper;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class MobScaling {
    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        //Find the closest player to this mob
        if (event.getEntity() instanceof Enemy) {
            Mob monster = event.getEntity();
            Player player = event.getLevel().getNearestPlayer(monster, 256D);
            if (player != null) {
                long time = PlayerHelper.getPenguinStatuses(player).getLong("Birthday");
                int days = TimeHelper.getElapsedDays(time);
                int difficulty = days / 28; //Increase the difficulty every 28 days
                //Every month, increase the difficulty by 1
                float baseDifficulty = (difficulty + (event.getDifficulty().getDifficulty().getId() * 3)) - 12;
                float modifier = baseDifficulty * 0.1F;
                monster.setHealth(monster.getMaxHealth() + (monster.getMaxHealth() * difficulty));
                Objects.requireNonNull(monster.getAttribute(Attributes.ATTACK_DAMAGE)).addPermanentModifier(new AttributeModifier("Difficulty adjustment", modifier, AttributeModifier.Operation.ADDITION));
                Objects.requireNonNull(monster.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(new AttributeModifier("Difficulty adjustment", modifier / 2, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
