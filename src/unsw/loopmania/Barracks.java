package unsw.loopmania;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Barracks building in the backend game world
 */
public class Barracks extends Building {
    public Barracks(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Barracks";
    }

    /**
     * Perform action on character when it steps on the Barracks
     * 
     * @param character
     */
    @Override
    public void performActionOnCharacter(Character character) {
        spawnAlliedSoldier(character.getAllies(), character.getPathPosition(), character);
        character.incrementAlliesNumValueProperty();
    }

    /**
     * Create a new AlliedSoldier and add it to the character's list of allies
     * 
     * @param allies
     */
    public void spawnAlliedSoldier(ArrayList<AlliedSoldier> allies, PathPosition currentPosition, Character character) {
        PathPosition position = new PathPosition(currentPosition.getCurrentPositionInPath(),
                currentPosition.getOrderedPath());
        AlliedSoldier newAlly = new AlliedSoldier(position);
        character.addAlliedSoldier(newAlly);
    }
}