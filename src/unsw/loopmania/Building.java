package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Buildings that can be placed on the map.
 */
public abstract class Building extends StaticEntity {
    /**
     * attribute for building name/type
     */
    protected String type;

    /**
     * constructor for building
     * 
     * @param x
     * @param y
     */
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for building type
     * 
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * void method for when a character is ontop of a building
     * 
     * @param character
     */
    public void performActionOnCharacter(Character character) {
    }

    /**
     * method for when a enemy is ontop of a building
     * 
     * @param enemy
     * @return boolean
     */
    public boolean performActionOnEnemy(Enemy enemy) {
        return false;
    }
}