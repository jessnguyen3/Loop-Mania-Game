package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a trap card in the backend game world
 */
public class TrapCard extends Card {
    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "TrapCard";
    }    
}
