package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a basic form of building in the world
 */
public class VampireCastle extends Building {
    public VampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "VampireCastle";
    }
}
