package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Village building in the backend game world
 */
public class Village extends Building implements CharacterTriggerBuilding {
    public Village(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Village";
    }

    /**
     * Perform action on character when character steps on Village
     * 
     */
    // @Override

    public void performActionOnCharacter(Character character) {
        healCharacter(character.getStats());
    }

    /**
     * Heal character for up to 20 health
     */
    public void healCharacter(Statistics stats) {

        // if heal amount is greater than 100, set health to 100 (max health)
        if (stats.getHealth() >= 90) {
            stats.setHealth(100);
        }

        else {
            // Heal the character for 10 points
            stats.setHealth(stats.getHealth() + 10);
        }
    }
}
