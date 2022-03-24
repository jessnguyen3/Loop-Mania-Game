package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {
    /**
     * object holding the statistics of a moving entity
     */
    Statistics stats;
    /**
     * Determines if the current entity is in a trance
     */
    private boolean inTrance;
    private int numTranceAttacks;
    /**
     * object holding position in the path
     */
    private PathPosition position;

    /**
     * Create a moving entity which moves up and down the path in position
     * 
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position, Statistics stats) {
        super();
        this.position = position;
        this.stats = stats;
        this.inTrance = false;
        this.numTranceAttacks = 0;
    }

    /**
     * Abstract method: All moving entities require the method move()
     */
    public abstract void move();

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    public SimpleIntegerProperty x() {
        return position.getX();
    }

    public SimpleIntegerProperty y() {
        return position.getY();
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }

    /**
     * Getter for the moving entity's statistics
     * 
     * @return
     */
    public Statistics getStats() {
        return stats;
    }

    /**
     * Getter for moving entity's health
     * 
     * @return
     */
    public int getHealth() {
        Statistics stats = this.getStats();
        return stats.getHealth();
    }

    /**
     * Set the entity's attack to a new value
     * 
     * @param attack
     */
    public void setAttack(int attack) {
        stats.setAttack(attack);
    }

    /**
     * Set the entity's defense to a new value
     * 
     * @param defense
     */
    public void setDefense(int defense) {
        stats.setDefense(defense);
    }

    /**
     * Getter for moving entity's attack
     * 
     * @return
     */
    public int getAttack() {
        Statistics stats = this.getStats();
        return stats.getAttack();
    }

    /**
     * Reduce the entity's health by a given value
     * 
     * @param value
     */
    public void reduceHealth(int value) {
        stats.reduceHealth(value);
    }

    /**
     * used to set the health of a character
     * 
     * @param value
     */
    public void setHealth(int value) {
        stats.setHealth(value);
    }

    /**
     * Getter for moving entity's defense
     * 
     * @return
     */
    public int getDefense() {
        Statistics stats = this.getStats();
        return stats.getDefense();
    }

    /**
     * Set a moving entity into trance
     * 
     * @param inTrance
     */
    public void setInTrance(boolean inTrance) {
        this.inTrance = inTrance;
    }

    /**
     * Getter to determine if the moving entity is in trance
     * 
     * @return
     */
    public boolean getInTrance() {
        return inTrance;
    }

    public void setNumTranceAttacks(int numAttacks) {
        this.numTranceAttacks = numAttacks;
    }

    public int getNumTranceAttacks() {
        return numTranceAttacks;
    }

    /**
     * Each moving entity must battle against each other.
     * 
     * @param opponentStats
     * @param equippedItems
     */
    public abstract void attack(MovingEntity opponent, List<Item> equippedItems);

    /**
     * Getter for the moving entity's current path position
     * 
     * @return
     */
    public PathPosition getPathPosition() {
        return position;
    }
}
