package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents an equipped or unequipped armour in the backend world
 */
public class Armour extends Equipment {
    /**
     * constructor for Armour
     * 
     * @param x
     * @param y
     */
    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Armour";
    }

    public int getIncrease() {
        return 5;
    }
}
