package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a campfire card in the backend game world
 */
public class CampfireCard extends Card {
    // TODO = add more types of card
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.cardType = "CampfireCard";
    }    
}