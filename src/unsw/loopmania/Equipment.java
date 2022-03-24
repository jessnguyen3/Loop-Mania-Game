package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Equipment Items that can be attached to the character.
 */
public abstract class Equipment extends Item {
    /**
     * constructor for Equipment
     * 
     * @param x
     * @param y
     */
    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for Item Type
     */
    public String getItemType() {
        return "Equipment";
    }

    abstract public int getIncrease();
}