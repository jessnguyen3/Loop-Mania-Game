package test;

import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Slug;
import unsw.loopmania.Zombie;
import unsw.loopmania.Vampire;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests for all enemy types (slugs, zombies, and vampires)
 */  
public class EnemyTest {

    private static final int MAP2 = 2;

    /**
     * test slug movement through the world
     */
    @Test
    public void slugMovementTest() {

        Helper helper = new Helper();
        Slug slug = helper.testSlugSetup(0, MAP2);

        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 0);
        slug.moveDownPath();
        assertEquals(slug.getX(), 1);
        assertEquals(slug.getY(), 0);
        slug.moveDownPath();
        assertEquals(slug.getX(), 2);
        assertEquals(slug.getY(), 0);
        slug.moveDownPath();
        assertEquals(slug.getX(), 2);
        assertEquals(slug.getY(), 1);
        slug.moveDownPath();
        assertEquals(slug.getX(), 2);
        assertEquals(slug.getY(), 2);
        slug.moveDownPath();
        assertEquals(slug.getX(), 1);
        assertEquals(slug.getY(), 2);
        slug.moveDownPath();
        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 2);
        slug.moveDownPath();
        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 1);
        slug.moveDownPath();
        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 0);
    }


    /**
     * tests for zombie movement through the world
     */
    @Test
    public void zombieMovementTest() {

        Helper helper = new Helper();
        Zombie zombie = helper.testZombieSetup(0, MAP2);

        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 0);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 1);
        assertEquals(zombie.getY(), 0);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 2);
        assertEquals(zombie.getY(), 0);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 2);
        assertEquals(zombie.getY(), 1);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 2);
        assertEquals(zombie.getY(), 2);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 1);
        assertEquals(zombie.getY(), 2);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 2);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 1);
        zombie.moveDownPath();
        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 0);
    }

    /**
     * Tests for vampire movement through the world
     */
    @Test
    public void vampireMovementTest() {

        Helper helper = new Helper();
        Vampire vampire = helper.testVampireSetup(0, MAP2);

        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 0);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 1);
        assertEquals(vampire.getY(), 0);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 2);
        assertEquals(vampire.getY(), 0);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 2);
        assertEquals(vampire.getY(), 1);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 2);
        assertEquals(vampire.getY(), 2);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 1);
        assertEquals(vampire.getY(), 2);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 2);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 1);
        vampire.moveDownPath();
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 0);
    }   

    /**
     * test if vampire entities move backwards when in the radius of the campfire
     */
    @Test
    public void vampireBackwardsMovementTest() {
        Helper helper = new Helper();
        //  First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to be 0
        helper.createCharacterSetup(1, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        helper.createBuildingSetup("CampfireCard", 7, world, 1);

        Enemy enemy = helper.createEnemySetup(2, 6, world);

        assertEquals(world.getEnemies().size(), 1);

        // vampire is intially at (4,0)
        assertEquals(enemy.getX(), 4);
        assertEquals(enemy.getY(), 0);

        // after world tick, it is at (3, 1). it is now in range of the campfire
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();

        // since in range of campfire, vampire moves back to (4, 0)
        world.runTickMoves();
        assertEquals(enemy.getX(), 0);
        assertEquals(enemy.getY(), 2);

        world.runTickMoves();
    }
}