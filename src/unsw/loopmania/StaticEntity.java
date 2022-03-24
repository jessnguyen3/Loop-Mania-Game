package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a non-moving entity unlike the moving entities, this can be placed
 * anywhere on the game map
 */
public abstract class StaticEntity extends Entity {
    /**
     * x and y coordinates represented by IntegerProperty, so ChangeListeners can be
     * added
     */
    private IntegerProperty x, y;

    /**
     * constructor for StaticEntity
     * 
     * @param x
     * @param y
     */
    public StaticEntity(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * function to return entity's x integerProperty
     */
    public IntegerProperty x() {
        return x;
    }

    /**
     * function to return entity's y integerProperty
     */
    public IntegerProperty y() {
        return y;
    }

    /**
     * getter for x entity coordinate
     */
    public int getX() {
        return x().get();
    }

    /**
     * getter for x entity coordinate
     */
    public int getY() {
        return y().get();
    }
}
