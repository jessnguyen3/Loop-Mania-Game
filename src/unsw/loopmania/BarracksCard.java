package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a barracks card in the backend game world
 */
public class BarracksCard extends Card {
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "BarracksCard";
    }

}
