package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Campfire building in the backend game world
 */
public class Campfire extends Building {
    public Campfire(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Campfire";
    }

}
