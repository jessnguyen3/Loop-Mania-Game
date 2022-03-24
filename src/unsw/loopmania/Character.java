package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    // isBuffed used to determine if the character is in range of a campfire
    private boolean isBuffed;
    private final int initialAttack = 10;
    private final int initialDefense = 2;
    private ArrayList<AlliedSoldier> allies;
    private IntegerProperty alliesNumValue = new SimpleIntegerProperty(this, "alliesNumValue");

    /**
     * Constructor for the character class.
     * 
     * @param position
     */
    public Character(PathPosition position) {
        super(position, new Statistics(100, 10, 2, 0, 0));
        allies = new ArrayList<AlliedSoldier>();
        isBuffed = false;
    }

    /**
     * Move the character in the game path
     */
    public void move() {
        super.moveDownPath();
    }

    /**
     * Character collects gold and experience dropped by the enemy they just killed
     * 
     * @param e
     */
    public void collectRewards(Enemy e) {
        Statistics eStats = e.getStats();
        stats.setGold(eStats.getGold() + stats.getGold());
        stats.setExp(eStats.getExp() + stats.getExp());
    }

    /**
     * Set the isBuffed boolean to determine if the character is in range of a
     * campfire
     * 
     * @param isBuffed
     */
    public void setIsBuffed(boolean isBuffed) {
        this.isBuffed = isBuffed;
    }

    /**
     * Character's method to attack enemies
     */
    public void attack(MovingEntity opponent, List<Item> equippedItems) {
        updateStatistics(equippedItems);
        int attack = stats.getAttack();
        attack -= opponent.getDefense();
        opponent.reduceHealth(attack);
    }

    /**
     * Update the character's attack and defense based on the currently equipped
     * items
     * 
     * @param equippedItems
     */
    public void updateStatistics(List<Item> equippedItems) {
        super.setAttack(this.initialAttack);
        super.setDefense(this.initialDefense);
        int attack = super.getAttack();
        int defense = super.getDefense();
        for (Item equippedItem : equippedItems) {
            if (equippedItem != null && equippedItem instanceof Weapon) {
                attack += equippedItem.getIncrease();
            } else if (equippedItem != null && equippedItem instanceof Equipment) {
                defense += equippedItem.getIncrease();
                if (equippedItem instanceof Helmet) {
                    Helmet helmet = (Helmet) equippedItem;
                    attack -= helmet.decreaseAttack();
                }
            }
        }
        if (this.isBuffed) {
            attack = attack * 2;
        }
        super.setAttack(attack);
        super.setDefense(defense);
    }

    /**
     * Add a new allied soldier to the allies list
     * 
     * @return
     */
    public void addAlliedSoldier(AlliedSoldier newSoldier) {
        allies.add(newSoldier);
    }

    /**
     * Getter for the list of allies the character currently has
     */
    public ArrayList<AlliedSoldier> getAllies() {
        return allies;
    }

    /**
     * determine if the character has at least one allied soldier
     * 
     * @return
     */
    public boolean alliedSoldierExists() {
        if (allies.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * return one allied soldier of the character
     * 
     * @return
     */
    public AlliedSoldier getAnAlliedSoldier() {
        return allies.get(0);
    }

    /**
     * remove a soldier from the allied soldier array
     */
    public void removeSoldier(AlliedSoldier deadSoldier) {
        allies.remove(deadSoldier);
        this.decrementAlliesNumValueProperty();
    }

    public IntegerProperty alliesNumValueProperty() {
        return alliesNumValue;
    }

    public int getAlliesNumValueProperty() {
        return alliesNumValue.get();
    }

    public void incrementAlliesNumValueProperty() {
        this.alliesNumValue.set(getAlliesNumValueProperty() + 1);
    }

    public void decrementAlliesNumValueProperty() {
        this.alliesNumValue.set(getAlliesNumValueProperty() - 1);
    }

}
