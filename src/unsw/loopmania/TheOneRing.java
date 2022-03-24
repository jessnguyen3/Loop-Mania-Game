package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TheOneRing extends Consumable {
    // TODO
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "OneRing";
    }
}
