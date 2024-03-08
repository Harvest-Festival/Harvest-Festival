package uk.joshiejack.harvestfestival.client;

import com.google.common.collect.Lists;
import uk.joshiejack.harvestfestival.world.mail.AbstractLetter;
import uk.joshiejack.penguinlib.util.PenguinGroup;

import java.util.List;

public class Inbox {
    public static final Inbox PLAYER = new Inbox();
    private final List<AbstractLetter<?>> unread_letters = Lists.newArrayList();

    public List<AbstractLetter<?>> getLetters() {
        return PLAYER.unread_letters;
    }

    public void add(AbstractLetter<?> letter) {
        unread_letters.add(letter);
    }

    public void remove(AbstractLetter<?> letter) {
        unread_letters.remove(letter);
    }

    public void set(List<AbstractLetter<?>> letters, PenguinGroup... groups) {
        unread_letters.removeIf((letter -> {
            for (PenguinGroup group : groups) {
                if (letter.getGroup() == group) return true;
            }

            return false;
        }));

        unread_letters.addAll(letters);
    }
}
