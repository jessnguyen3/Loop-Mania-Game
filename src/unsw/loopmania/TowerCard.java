package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a tower card in the backend game world
 */
public class TowerCard extends Card {
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "TowerCard";
    }
}
