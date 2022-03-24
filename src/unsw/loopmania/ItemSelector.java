package unsw.loopmania;

import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class ItemSelector {
    private static final int SWORD = 0;
    private static final int STAKE = 1;
    private static final int ARMOUR = 2;
    private static final int HELMET = 3;
    private static final int SHIELD = 4;
    private static final int POTION = 5;
    private static final int STAFF = 6;
    private static final int ONERING = 7;

    public Item getItem(int itemSelection, List<String> allRareItems, SimpleIntegerProperty x, SimpleIntegerProperty y,
            boolean oneRing) {
        Item item;
        if (itemSelection == ONERING && (!allRareItems.contains("the_one_ring") || oneRing)) {
            Random randomItem = new Random();
            itemSelection = randomItem.nextInt(7);
        }
        switch (itemSelection) {
            case SWORD:
                item = new Sword(x, y);
                return item;
            case STAKE:
                item = new Stake(x, y);
                return item;
            case ARMOUR:
                item = new Armour(x, y);
                return item;
            case HELMET:
                item = new Helmet(x, y);
                return item;
            case SHIELD:
                item = new Shield(x, y);
                return item;
            case POTION:
                item = new HealthPotion(x, y);
                return item;
            case STAFF:
                item = new Staff(x, y);
                return item;
            case ONERING:
                item = new TheOneRing(x, y);
                return item;
        }
        return null;
    }
}
