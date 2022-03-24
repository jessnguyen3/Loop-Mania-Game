package unsw.loopmania;

import java.util.List;

public abstract class Enemy extends MovingEntity {
    /**
     * Constructor the Enemy class.
     * 
     * @param position
     * @param stats
     */
    public Enemy(PathPosition position, Statistics stats) {
        super(position, stats);
    }

    /**
     * Default attack method for all enemies
     */
    public void attack(MovingEntity opponent, List<Item> equippedItems) {
        int attack = super.getAttack();
        if (opponent instanceof Character) {
            // character's armour reduces the attack of the enemy
            attack = updateAttack(attack, equippedItems.get(2));
            attack = updateAttack(attack, equippedItems.get(3));
        }
        attack -= opponent.getDefense();
        opponent.reduceHealth(attack);
    }

    /**
     * Update the enemy's attack with regards to the character's armour (if
     * equipped)
     * 
     * @param attack
     * @param equipment
     * @return
     */
    public int updateAttack(int attack, Item equipment) {
        if (equipment != null && equipment instanceof Armour) {
            attack = attack / 2;
        }
        return attack;
    }

    // interface
    public abstract void reverseDirection();

    // method to allow the enemy to move through the game loop
    public abstract void move();

    // getter for the enemy's battle radius
    public abstract int getBattleRadius();

    // getter for the enemy's support radius
    public abstract int getSupportRadius();

    // getter for the enemy's type
    public abstract String getType();
}