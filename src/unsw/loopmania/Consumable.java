package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Consumable Items that can be used by the character.
 */
public class Consumable extends Item {
    /**
     * constructor for Consumable
     * 
     * @param x
     * @param y
     */
    public Consumable(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for Item Type
     */
    public String getItemType() {
        return "Consumable";
    }

    @Override
    public int getIncrease() {
        // TODO Auto-generated method stub
        return 0;
    }
}
