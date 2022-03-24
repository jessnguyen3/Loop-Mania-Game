package unsw.loopmania;

import java.util.List;
import java.util.Random;

public class Vampire extends Enemy {
    private final String type = "Vampire";
    private boolean direction;
    private final int battleRadius = 3;
    private final int supportRadius = 5;
    private int remainingCriticalBites;
    private int criticalBiteDamage;

    public Vampire(PathPosition position) {
        super(position, new Statistics(50, 25, 5, 25, 20));
        direction = true;
        this.remainingCriticalBites = 0;
        this.criticalBiteDamage = 0;
    }

    // Assumption: the vampire can deal a critical bite the first time it attacks
    // the allied
    // soldier or character

    /**
     * Attack takes into account the vampire's critical bite. Attack method reduces
     * the health of the opponent (which is the allied soldier or character)
     */
    @Override
    public void attack(MovingEntity opponent, List<Item> equippedItems) {
        if (opponent instanceof Character) {
            // inflict additional damage to the vampire if the character
            // is currently equipping a stake
            int additionalCharacterAttack = updateAttackToVampire(equippedItems.get(0));
            additionalCharacterAttack += updateAttackToVampire(equippedItems.get(1));
            super.setHealth(super.getHealth() - additionalCharacterAttack);
            if (super.getHealth() == 0) {
                // the vampire can no longer attack due to additional damage from stake(s)
                return;
            }
        }
        this.checkCriticalBites(equippedItems);
        remainingCriticalBites--; // critical bite will be dealt. Therefore, subtract 1.
        int attack = super.getAttack();
        attack = super.updateAttack(attack, equippedItems.get(2));
        attack = super.updateAttack(attack, equippedItems.get(3));
        attack += criticalBiteDamage;
        opponent.reduceHealth(attack);
    }

    /**
     * Checks if the character has equipped a stake. If so, it deals additional
     * damage to the vampire.
     * 
     * @param weapon
     * @return
     */
    public int updateAttackToVampire(Item weapon) {
        int additionalCharacterAttack = 0;
        if (weapon != null && weapon instanceof Stake) {
            additionalCharacterAttack += weapon.getIncrease();
        }
        return additionalCharacterAttack;
    }

    /**
     * Check if the vampire is current dealing critical bites. If not (criticalBites
     * <= 0), then it randomly determines if the vampire will deal critical bites.
     * Initially, the chance of a critical bite is 15%.
     * 
     * @param equippedItems
     */
    public void checkCriticalBites(List<Item> equippedItems) {
        if (remainingCriticalBites <= 0) {
            // reset the criticalBiteDamage
            criticalBiteDamage = 0;
            // Get the chance the vampire provides a critical bite
            Random random = new Random();
            int biteChance = random.nextInt(100);
            // current chance of a critical bite is 15%
            int biteChanceRequired = 15;
            biteChanceRequired = this.getBiteChanceRequired(equippedItems, biteChanceRequired);
            // 15% chance of inflicting biteChance
            if (biteChance < biteChanceRequired) {
                remainingCriticalBites = random.nextInt(5) + 1; // the number of critical bites
                criticalBiteDamage = random.nextInt(10) + 1; // the extra damage the critical bite inflicts
            }
        }
    }

    /**
     * Update the current chance of a critical bite depending on whether the
     * character has equipped armour.
     * 
     * @param equippedItems
     * @param currentBiteChance
     * @return
     */
    public int getBiteChanceRequired(List<Item> equippedItems, int currentBiteChance) {
        currentBiteChance = this.reducedBiteChance(equippedItems.get(2), currentBiteChance);
        currentBiteChance = this.reducedBiteChance(equippedItems.get(3), currentBiteChance);
        return currentBiteChance;
    }

    /**
     * if armour is equipped, reduce the chance of a critical bite by 60%.
     * 
     * @param equipment
     * @param currentBiteChance
     * @return
     */
    public int reducedBiteChance(Item equipment, int currentBiteChance) {
        if (equipment != null && equipment instanceof Armour) {
            // Armour reduces chance of critical bite by 60%
            currentBiteChance *= 0.4;
        }
        return currentBiteChance;
    }

    /*
     * Movement for the Vampire
     */
    @Override
    public void move() {
        if (direction) {
            moveUpPath();
            moveUpPath();
        } else {
            moveDownPath();
            moveDownPath();
        }
    }

    /**
     * getter for type of enemy
     */
    public String getType() {
        return type;
    }

    /**
     * getter fir vampire's battle radius
     */
    @Override
    public int getBattleRadius() {
        return battleRadius;
    }

    /**
     * getter for vampire's support radius
     */
    @Override
    public int getSupportRadius() {
        return supportRadius;
    }

    /**
     * Used to reverse vampire's direction when in proximity of a campfire
     */
    public void reverseDirection() {
        if (direction) {
            direction = false;
        } else {
            direction = true;
        }
    }
}
