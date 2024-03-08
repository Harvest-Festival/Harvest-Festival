package uk.joshiejack.harvestfestival.world.mail;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HarvestFestival;
import uk.joshiejack.harvestfestival.client.Inbox;
import uk.joshiejack.penguinlib.event.NewDayEvent;
import uk.joshiejack.penguinlib.event.TeamChangedEvent;
import uk.joshiejack.penguinlib.world.team.PenguinTeams;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Mod.EventBusSubscriber(modid = HarvestFestival.MODID)
public class PostalOffice extends SavedData {
    private final Map<UUID, MailRoom> MAILROOMS = new Object2ObjectOpenHashMap<>();

    public static PostalOffice get(ServerLevel world) {
        return world.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(PostalOffice::new, PostalOffice::new), "mail");
    }

    public PostalOffice() {}
    public PostalOffice(CompoundTag tag) {
        CompoundTag mailrooms = tag.getCompound("Mailrooms");
        mailrooms.getAllKeys().forEach((uuid) -> MAILROOMS.put(UUID.fromString(uuid), new MailRoom(UUID.fromString(uuid), mailrooms.getCompound(uuid))));
    }

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        ServerLevel level = event.getLevel();
        PostalOffice office = get(level);
        level.players().forEach((p) -> office.MAILROOMS.computeIfAbsent(p.getUUID(), MailRoom::new));
        office.MAILROOMS.forEach((uuid, mailroom) -> mailroom.onNewDay(level));
        office.setDirty();
    }

    @SubscribeEvent
    public static void onTeamChanged(TeamChangedEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            PostalOffice office = get(level);
            MailRoom mymailroom = office.MAILROOMS.computeIfAbsent(event.getPlayer(), MailRoom::new);
            for (UUID uuid : PenguinTeams.get(level).getTeamMembers(event.getNewTeam())) {
                if (!uuid.equals(event.getPlayer())) {
                    MailRoom theirmailroom = office.MAILROOMS.computeIfAbsent(uuid, MailRoom::new);
                    mymailroom.copyLetters(level, theirmailroom);
                    office.setDirty();
                    break;
                }
            }
        }
    }

    public static List<AbstractLetter<?>> getLetters(Player player) {
        return player.level() instanceof ServerLevel serverLevel ? get(serverLevel).MAILROOMS.getOrDefault(player.getUUID(), new MailRoom(player.getUUID())).getLetters() : Inbox.PLAYER.getLetters();
    }

    public void remove(Level world, Player player, AbstractLetter<?> letter) {
        if (!world.isClientSide) {
            MAILROOMS.getOrDefault(player.getUUID(), new MailRoom(player.getUUID())).removeLetter(world, letter);
            setDirty();
        }
    }

    public void send(Level world, Player player, @Nonnull AbstractLetter<?> letter) {
        if (world instanceof ServerLevel level) {
            switch (letter.getGroup()) {
                case PLAYER:
                    MAILROOMS.computeIfAbsent(player.getUUID(), MailRoom::new).addLetter(world, letter);
                    break;
                case TEAM:
                    UUID team = PenguinTeams.getTeamUUIDForPlayer(player);
                    for (UUID uuid : PenguinTeams.get(level).getTeamMembers(team)) {
                        MAILROOMS.computeIfAbsent(uuid, MailRoom::new).addLetter(world, letter);
                    }
                    break;
                case GLOBAL:
                    level.players().forEach((p) -> MAILROOMS.computeIfAbsent(p.getUUID(), MailRoom::new));
                    MAILROOMS.forEach((uuid, mailroom) -> mailroom.addLetter(world, letter));
                    break;
            }
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        CompoundTag mailrooms = new CompoundTag();
        MAILROOMS.forEach((uuid, mailroom) -> mailrooms.put(uuid.toString(), mailroom.serializeNBT()));
        tag.put("Mailrooms", mailrooms);
        return tag;
    }

    public boolean contains(Player player, AbstractLetter<?> letter) {
        return MAILROOMS.getOrDefault(player.getUUID(), new MailRoom(player.getUUID())).contains(letter);
    }
}
