package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Statistics for each entity
 */
public class Statistics {
    /**
     * attributes for Statistics
     */
    private int health;
    private int attack;
    private int defense;
    private int exp;
    private int gold;
    private SimpleIntegerProperty healthValue = new SimpleIntegerProperty(this, "healthValue");
    private SimpleIntegerProperty goldValue = new SimpleIntegerProperty(this, "goldValue");
    private SimpleIntegerProperty expValue = new SimpleIntegerProperty(this, "expValue");
    private SimpleIntegerProperty attackValue = new SimpleIntegerProperty(this, "attackValue");
    private SimpleIntegerProperty defenseValue = new SimpleIntegerProperty(this, "defenseValue");

    // Default constructor
    public Statistics() {
        this.health = 0;
        this.defense = 0;
        this.exp = 0;
        this.gold = 0;
    }

    /**
     * entity constructor for Statistics
     * 
     * @param health
     * @param attack
     * @param defense
     * @param exp
     * @param gold
     */
    public Statistics(int health, int attack, int defense, int exp, int gold) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.exp = exp;
        this.gold = gold;
        this.healthValue.set(health);
        this.goldValue.set(gold);
        this.expValue.set(exp);
        this.attackValue.set(attack);
        this.defenseValue.set(defense);
    }

    /**
     * getter for Health
     * 
     * @return int
     */
    public int getHealth() {
        getHealthProperty();
        return health;
    }

    /**
     * getter for Attack
     * 
     * @return int
     */

    public int getAttack() {
        return attack;
    }

    /**
     * getter for Gold
     * 
     * @return int
     */
    public int getGold() {
        return gold;
    }

    /**
     * getter for exp
     * 
     * @return int
     */
    public int getExp() {
        return exp;
    }

    /**
     * setter for Health
     * 
     * @param healthPoints
     */
    public void setHealth(int healthPoints) {
        this.health = healthPoints;
        if (this.health > 100) {
            this.health = 100;
        }
        setHealthProperty(health);
    }

    /**
     * setter for Attack
     * 
     * @param attack
     */
    public void setAttack(int attack) {
        setAttackProperty(attack);
        this.attack = attack;
    }

    /**
     * setter for Defense
     * 
     * @param defense
     */
    public void setDefense(int defense) {
        setDefenseProperty(defense);
        this.defense = defense;
    }

    /**
     * setter for Gold
     * 
     * @param gold
     */
    public void setGold(int gold) {
        setGoldProperty(gold);
        this.gold = gold;
    }

    /**
     * setter for Exp
     * 
     * @param exp
     */
    public void setExp(int exp) {
        setExpProperty(exp);
        this.exp = exp;
    }

    /**
     * getter for Defense
     * 
     * @return int
     */
    public int getDefense() {
        return defense;
    }

    /**
     * method for reduce Health
     * 
     * @param value
     */
    public void reduceHealth(int value) {
        if (value < 0) {
            value = 0;
        }
        health = health - value;
        if (health < 0) {
            health = 0;
        }

        // bindings for JavaFX
        setHealthProperty(getHealthProperty() - value);
        if (getHealthProperty() < 0) {
            setHealthProperty(0);
        }
    }

    /**
     * method to get HealthValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty HealthValueProperty() {
        return healthValue;
    }

    /**
     * getter for healthValue
     * 
     * @return int
     */
    public int getHealthProperty() {
        return healthValue.get();
    }

    /**
     * setter for Health Property
     * 
     * @param health
     */
    public void setHealthProperty(int health) {
        this.healthValue.set(health);
    }

    /**
     * method to get GoldValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty goldValueProperty() {
        return goldValue;
    }

    /**
     * getter for GoldProperty
     * 
     * @return int
     */
    public int getGoldProperty() {
        return goldValue.get();
    }

    /**
     * setter for GoldProperty
     * 
     * @param gold
     */
    public void setGoldProperty(int gold) {
        this.goldValue.set(gold);
    }

    /**
     * method to get expValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty expValueProperty() {
        return expValue;
    }

    /**
     * getter for ExpProperty
     * 
     * @return int
     */
    public int getExpProperty() {
        return expValue.get();
    }

    /**
     * setter for ExpProperty
     * 
     * @param exp
     */
    public void setExpProperty(int exp) {
        this.expValue.set(exp);
    }

    /**
     * method to get attackValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty attackValueProperty() {
        return attackValue;
    }

    /**
     * getter for AttackProperty
     * 
     * @return int
     */
    public int getattackProperty() {
        return attackValue.get();
    }

    /**
     * setter for AttackProperty
     * 
     * @param attack
     */
    public void setAttackProperty(int attack) {
        this.attackValue.set(attack);
    }

    /**
     * method to get defenseValueProperty
     * 
     * @return IntegerProperty
     */
    public IntegerProperty defenseValueProperty() {
        return defenseValue;
    }

    /**
     * getter for DefenseProperty
     * 
     * @return int
     */
    public int getDefenseProperty() {
        return defenseValue.get();
    }

    /**
     * setter for DefenseProperty
     * 
     * @param defense
     */
    public void setDefenseProperty(int defense) {
        this.defenseValue.set(defense);
    }

}
