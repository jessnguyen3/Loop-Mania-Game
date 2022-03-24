package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public abstract class Weapon extends Item {
    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for Item Type
     */
    public String getItemType() {
        return "Weapon";
    }

    abstract public int getIncrease();
}
