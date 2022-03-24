package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Trap building in the backend game world
 */
public class Trap extends Building implements EnemyTriggerBuilding {
    public Trap(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.type = "Trap";
    }

    /**
     * Perform action on the enemy Return true if enemy is killed, false if it still
     * survives
     */
    // @Override
    public boolean performActionOnEnemy(Enemy e) {
        // Deal damage to the enemy
        return inflictDamage(e, e.getStats());

    }

    /**
     * Inflict damage to the enemy that steps on the trap Return true if the enemy
     * is killed, false if it still survives
     */
    public boolean inflictDamage(Enemy enemy, Statistics enemyStats) {

        // Kill the enemy if it has less than 25 health
        if (enemyStats.getHealth() <= 25) {
            enemy.destroy();

            System.out.println("The enemy stepped on the trap and was killed!");
            System.out.println("The trap was destroyed ...");
            return true;
        }

        // Otherwise deduct 25 damage from the enemies
        else {
            enemyStats.setHealth(enemyStats.getHealth() - 25);

            System.out.println("The enemy stepped on the trap and took some damage!");
            System.out.println("The enemy's health is now " + enemyStats.getHealth());

            return false;
        }
    }

}
