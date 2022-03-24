package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "VampireCastleCard";
    }
}
