package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a zombie pit card in the backend game world
 */
public class ZombiePitCard extends Card {
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "ZombiePitCard";
    }    
}
