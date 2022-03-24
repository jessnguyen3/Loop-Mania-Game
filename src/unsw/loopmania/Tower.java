package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Tower building in the backend game world
 */
public class Tower extends Building implements EnemyTriggerBuilding {
    public Tower(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Tower";
    }

    /**
     * Perform action on enemy when it enters the tower's radius
     * 
     * @param enemy
     */
    public boolean performActionOnEnemy(Enemy enemy) {
        return towerInflictDamage(enemy.getStats());
    }

    /**
     * Inflict damage on to the enemy within the tower's radius Return true if the
     * enemy is killed, false if not.
     * 
     * @param statistics
     */
    public boolean towerInflictDamage(Statistics statistics) {

        System.out.println("Before being attacked by the tower, the enemy has health " + statistics.getHealth());

        // Tower kills the enermy
        if (statistics.getHealth() - 5 <= 0) {

            System.out.println("The tower killed the enemy!");
            return true;
        }

        // else deal 5 damage
        else {
            statistics.setHealth(statistics.getHealth() - 5);

            System.out.println("An enemy within the radius took damage from the tower and now has health "
                    + statistics.getHealth());

            return false;
        }
    }

}
