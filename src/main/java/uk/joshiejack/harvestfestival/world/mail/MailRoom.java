package uk.joshiejack.harvestfestival.world.mail;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import uk.joshiejack.harvestfestival.HFRegistries;
import uk.joshiejack.harvestfestival.network.mail.RemoveLetterPacket;
import uk.joshiejack.harvestfestival.network.mail.SendLetterPacket;
import uk.joshiejack.harvestfestival.network.mail.SyncLettersPacket;
import uk.joshiejack.penguinlib.network.PenguinNetwork;
import uk.joshiejack.penguinlib.network.packet.PenguinPacket;
import uk.joshiejack.penguinlib.util.PenguinGroup;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

public class MailRoom implements INBTSerializable<CompoundTag> {
    private final NonNullList<AbstractLetter<?>> unreadLetters = NonNullList.create();
    private final Object2IntMap<ResourceLocation> expiryTimer = new Object2IntOpenHashMap<>();
    private final Object2IntMap<ResourceLocation> deliverTimer = new Object2IntOpenHashMap<>();
    private WeakReference<Player> player;
    private final UUID uuid;

    public MailRoom(UUID uuid) {
        this.uuid = uuid;
        //Add default letters
        HFRegistries.LETTERS.stream()
                .filter(AbstractLetter::startsWith)
                .forEach(unreadLetters::add);
    }

    public MailRoom(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        deserializeNBT(tag);
    }

    public boolean contains(AbstractLetter<?> letter) {
        return unreadLetters.contains(letter);
    }

    public List<AbstractLetter<?>> getLetters() {
        return unreadLetters;
    }

    @Nullable
    public Player getPlayer(Level world) {
        if (player == null || player.get() == null) {
            player = new WeakReference<>(world.getPlayerByUUID(uuid));
        }

        return player.get();
    }

    public void copyLetters(Level world, MailRoom theirmailroom) {
        unreadLetters.removeIf(letter -> letter.getGroup() == PenguinGroup.TEAM);
        theirmailroom.unreadLetters.stream()
                .filter((letter -> letter.getGroup() == PenguinGroup.TEAM))
                .forEach(unreadLetters::add);
        sendPacket(world, new SyncLettersPacket(unreadLetters));
    }

    public void synchronize(Level world) {
        sendPacket(world, new SyncLettersPacket(unreadLetters));
    }

    public void onNewDay(Level world) {
        unreadLetters.add(HFRegistries.LETTERS.stream().findAny().get());
        for (ResourceLocation resource : Sets.newHashSet(expiryTimer.keySet())) {
            AbstractLetter<?> letter = HFRegistries.LETTERS.get(resource);
            if (expiryTimer.compute(resource, (r, v) -> v == null ? 1 : v + 1) >= letter.getExpiry()) {
                if (letter.isRepeatable()) expiryTimer.removeInt(letter.id());
                if (letter.expires()) unreadLetters.remove(letter);
            }
        }

        //YES MATE
        for (ResourceLocation letter : Sets.newHashSet(deliverTimer.keySet())) {
            deliverTimer.mergeInt(letter, -1, Integer::sum);
            if (deliverTimer.getInt(letter) <= 0) {
                deliverTimer.removeInt(letter);
                deliverLetter(world, HFRegistries.LETTERS.get(letter)); //Yes this is how we roll byatch!!
            }
        }

        synchronize(world);
    }

    public void removeLetter(Level world, AbstractLetter<?> letter) {
        unreadLetters.remove(letter);
        if (letter.isRepeatable()) {
            expiryTimer.removeInt(letter.id());
        }

        sendPacket(world, new RemoveLetterPacket(letter));
    }

    public void addLetter(Level world, AbstractLetter<?> letter) {
        if (letter.getDeliveryTime() > 0) {
            deliverTimer.put(letter.id(), letter.getDeliveryTime());
        } else deliverLetter(world, letter);
    }

    private void deliverLetter(Level world, AbstractLetter<?> letter) {
        //Don't receive the letter if we already have it
        if (!expiryTimer.containsKey(letter.id())) {
            unreadLetters.add(letter);
            expiryTimer.put(letter.id(), 0);
            sendPacket(world, new SendLetterPacket(letter));
        }
    }

    private void sendPacket(Level world, PenguinPacket packet) {
        Player player = getPlayer(world);
        if (player instanceof ServerPlayer serverPlayer)
            PenguinNetwork.sendToClient(serverPlayer, packet);
    }

    @Override
    public @NotNull CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag letters = new ListTag();
        unreadLetters.forEach(letter -> letters.add(StringTag.valueOf(letter.id().toString())));
        tag.put("Letters", letters);

        ListTag expiryTimer = new ListTag();
        this.expiryTimer.object2IntEntrySet().forEach((entry) -> {
            CompoundTag data = new CompoundTag();
            data.putString("Letter", entry.getKey().toString());
            data.putInt("Timer", entry.getIntValue());
            expiryTimer.add(data);
        });

        tag.put("ExpiryTimer", expiryTimer);

        ListTag deliverTimer = new ListTag();
        this.deliverTimer.object2IntEntrySet().forEach((entry) -> {
            CompoundTag data = new CompoundTag();
            data.putString("Letter", entry.getKey().toString());
            data.putInt("Timer", entry.getIntValue());
            deliverTimer.add(data);
        });

        tag.put("DeliverTimer", deliverTimer);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag unreadLetters = nbt.getList("Letters", 8);
        this.unreadLetters.clear();
        for (int i = 0; i < unreadLetters.size(); i++) {
            this.unreadLetters.add(HFRegistries.LETTERS.get(new ResourceLocation(unreadLetters.getString(i))));
        }

        ListTag expiryTimer = nbt.getList("ExpiryTimer", 10);
        this.expiryTimer.clear();
        for (int i = 0; i < expiryTimer.size(); i++) {
            CompoundTag data = expiryTimer.getCompound(i);
            this.expiryTimer.put(new ResourceLocation(data.getString("Letter")), data.getInt("Timer"));
        }

        ListTag deliverTimer = nbt.getList("DeliverTimer", 10);
        this.deliverTimer.clear();
        for (int i = 0; i < deliverTimer.size(); i++) {
            CompoundTag data = deliverTimer.getCompound(i);
            this.deliverTimer.put(new ResourceLocation(data.getString("Letter")), data.getInt("Timer"));
        }
    }
}
