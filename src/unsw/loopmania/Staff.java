package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped Staff in the backend world
 */
public class Staff extends Weapon {
    /**
     * constructor for Staff
     * 
     * @param x
     * @param y
     */
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Staff";
    }

    public int getIncrease() {
        return 2;
    }
}
