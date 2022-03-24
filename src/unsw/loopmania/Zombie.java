package unsw.loopmania;

import java.util.Random;

public class Zombie extends Enemy {
    private final String type = "Zombie";
    // stepcounter used to ensure zombie only moves once for every 3 times the
    // character moves
    private int stepCounter;
    private final int battleRadius = 3;
    private final int supportRadius = 4;

    /**
     * Constructor for zombie
     * 
     * @param position
     */
    public Zombie(PathPosition position) {
        super(position, new Statistics(20, 10, 2, 15, 10));
        stepCounter = 0;
    }

    /**
     * Method used by zombie to move along the game path.
     */
    @Override
    public void move() {
        // Zombie moves one step for every 3 steps the character takes
        if (stepCounter == 3) {
            moveUpPath();
            stepCounter = 0;
        } else {
            stepCounter++;
        }
    }

    /**
     * method used to possible turn a soldier into a zombie
     * 
     * @param soldier
     */
    public void possiblyTurnSoldier(AlliedSoldier soldier) {
        Random randomItem = new Random();
        int randItem = randomItem.nextInt(5);
        if (randItem == 0) {
            soldier.setIsZombie();
            System.out.println("allied has turned into a zombie");
        }
    }

    /**
     * Getter for the zombie type
     */
    public String getType() {
        return type;
    }

    @Override
    public void reverseDirection() {
    }

    /**
     * getter for zombie's battle radius
     */
    public int getBattleRadius() {
        return battleRadius;
    }

    /**
     * getter for zombie's support radius
     */
    public int getSupportRadius() {
        return supportRadius;
    }

}
