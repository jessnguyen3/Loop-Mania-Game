package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Consumable HealthPotions that can be used by the character.
 */
public class HealthPotion extends Consumable {
    /**
     * constructor for HealthPotion
     * @param x
     * @param y
     */ 
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "HealthPotion";
    }    
}
