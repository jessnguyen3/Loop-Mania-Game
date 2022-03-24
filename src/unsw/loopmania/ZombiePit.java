package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a ZombiePit building in the backend game world
 */
public class ZombiePit extends Building {
    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "ZombiePit";

    }
}
