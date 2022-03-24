package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents an equipped or unequipped helmet in the backend world
 */
public class Helmet extends Equipment {
    /**
     * constructor for Helmet
     * 
     * @param x
     * @param y
     */
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Helmet";
    }

    public int getIncrease() {
        return 2;
    }

    public int decreaseAttack() {
        return 2;
    }

}
