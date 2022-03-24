package test;

import unsw.loopmania.Character;
import unsw.loopmania.Enemy;
import unsw.loopmania.Barracks;
import unsw.loopmania.Building;
import unsw.loopmania.HerosCastle;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Trap;
import unsw.loopmania.Vampire;

import org.junit.Test;
import javafx.beans.property.SimpleIntegerProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests for all building types (barracks, campfire, heroscastle, tower, trap,
 * vampirecastle, village, zombiepit)
 */
public class BuildingTest {
    private static final int CURRENT = 0;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;
    private static final int MAP1 = 1;

    /**
     * test the creation and placement of buildings onto the world
     */
    @Test
    public void testHelperBuilding() {
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(1);
        // Build a Barracks. Must pass in the card to make the barracks card and perform
        // checks
        Building building = helper.createBuildingSetup("BarracksCard", 30, world, CURRENT);
        // Check that building is placed in the correct CURRENT position.
        assertEquals(3, building.getX());
        assertEquals(3, building.getY());
        assertEquals("Barracks", building.getType());

        assertEquals(world.checkVampireBuilding(), false);
        building = helper.createBuildingSetup("VampireCastleCard", 30, world, NORTH);
        // Check that building is placed in the correct NORTH position.
        assertEquals(3, building.getX());
        assertEquals(2, building.getY());
        assertEquals("VampireCastle", building.getType());
        assertEquals(world.checkVampireBuilding(), true);

        assertEquals(world.checkZombiePit(), false);
        building = helper.createBuildingSetup("ZombiePitCard", 30, world, SOUTH);
        // Check that building is placed in the correct SOUTH position.
        assertEquals(3, building.getX());
        assertEquals(4, building.getY());
        assertEquals("ZombiePit", building.getType());
        assertEquals(world.checkZombiePit(), true);

        building = helper.createBuildingSetup("VillageCard", 30, world, EAST);
        // Check that building is placed in the correct EAST position.
        assertEquals(4, building.getX());
        assertEquals(3, building.getY());
        assertEquals("Village", building.getType());

        building = helper.createBuildingSetup("TrapCard", 30, world, WEST);
        // Check that building is placed in the correct WEST position.
        assertEquals(2, building.getX());
        assertEquals(3, building.getY());
        assertEquals("Trap", building.getType());

        building = helper.createBuildingSetup("CampfireCard", 30, world, WEST);
        // Campfire card cannot be placed on the pathway, so the building will return
        // null
        assertEquals(building, null);
    }

    /**
     * test if barracks spawn allies
     */
    @Test
    public void barracksSpawnAllyTest() {
        Helper helper = new Helper();
        Character character = helper.testCharacterSetup(0, MAP1);

        LoopManiaWorld world = helper.createWorld(MAP1);
        helper.createGoalsSetup(1, world);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();

        Barracks barracks = new Barracks(x, y);

        PathPosition pathPosition = new PathPosition(0, world.getOrderedPath());

        // assert character has no current allies
        assertEquals(character.getAllies().size(), 0);

        barracks.spawnAlliedSoldier(character.getAllies(), pathPosition, character);

        // assert character has one new ally
        assertEquals(character.getAllies().size(), 1);

        // spawn ally using performActionOnCharacter method
        barracks.performActionOnCharacter(character);

        // assert character has two allies
        assertEquals(character.getAllies().size(), 2);
    }

    /**
     * test if zombiePit and vampireCastle spawn the correct enemy
     */
    @Test
    public void testSpawningEnemies() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        helper.createCharacterSetup(0, world);
        helper.createGoalsSetup(1, world);
        helper.createEnemySetup(0, 1, world);

        world.possiblySpawnGold();

        // spawn enemies
        world.possiblySpawnEnemies();

        // assert, no zombies or vampires should exist
        assertEquals(world.checkZombieSpawned(), false);
        assertEquals(world.checkVampireSpawned(), false);

        // increment loops and run world to clear conditions to spawn a zombie
        world.incrementLoops();
        world.runTickMoves();

        // spawn zombie and check if a new zombie exists
        assertEquals(world.checkZombiePit(), false);
        helper.createBuildingSetup("ZombiePitCard", 30, world, 2);
        world.possiblySpawnEnemies();
        assertEquals(world.checkZombieSpawned(), true);

        // increment loops and run world to clear conditions to spawn a vampire
        for (int i = 1; i < 5; i++) {
            world.incrementLoops();
        }

        // a vampire should have spawned from the castle
        assertEquals(world.checkVampireBuilding(), false);
        helper.createBuildingSetup("VampireCastleCard", 30, world, 1);
        world.possiblySpawnEnemies();
        assertEquals(world.checkVampireSpawned(), true);
    }

    /**
     * test if hero'sCastle detects if player is ontop of it
     */
    @Test
    public void herosCastleShopTest() {
        Helper helper = new Helper();
        Character character = helper.testCharacterSetup(0, MAP1);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();

        HerosCastle herosCastle = new HerosCastle(x, y);

        // as player spawns ontop of the hero's castle, assert true
        assertEquals(herosCastle.atShop(character.getX(), character.getY()), true);

        character.moveDownPath();

        // as player is moving away from hero's castle, assert false
        assertEquals(herosCastle.atShop(character.getX(), character.getY()), false);
    }

    /**
     * test if trap inflicts damage on enemy
     */
    @Test
    public void trapDamageTest() {
        // create enemies and the trap
        Helper helper = new Helper();
        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();

        Trap trap = new Trap(x, y);

        Slug slug = helper.testSlugSetup(0, MAP1);
        Vampire vampire = helper.testVampireSetup(0, MAP1);

        // as trap kills slugs, assert true
        assertEquals(trap.inflictDamage(slug, slug.getStats()), true);

        // as vampires survive the trap, assert false and the remaining health should be
        // 25
        assertEquals(vampire.getHealth(), 50);
        assertEquals(trap.inflictDamage(vampire, vampire.getStats()), false);
        assertEquals(vampire.getHealth(), 25);
    }

    /**
     * test to dectect if enemy is ontop and is killed by a trap
     */
    @Test
    public void detectIfEnemyIsOnTrap() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        helper.createCharacterSetup(0, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        helper.createBuildingSetup("TrapCard", 3, world, NORTH);

        helper.createEnemySetup(0, 5, world);

        assertEquals(world.getEnemies().size(), 1);

        // iterate through world until enemy walks onto trap
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();

        // as enemy is killed by trap, getEnemies.size() should be 0
        assertEquals(world.getEnemies().size(), 0);
    }

    /**
     * test the tower will kill enemy entites within its radius
     */
    @Test
    public void towerKillTest() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        helper.createCharacterSetup(1, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        // each tower deals 5 damage
        // slug has health level of 25
        helper.createBuildingSetup("TowerCard", 6, world, WEST);
        helper.createBuildingSetup("TowerCard", 5, world, SOUTH);
        helper.createBuildingSetup("TowerCard", 4, world, SOUTH);
        helper.createBuildingSetup("TowerCard", 3, world, SOUTH);
        helper.createBuildingSetup("TowerCard", 2, world, EAST);

        helper.createEnemySetup(0, 8, world);

        assertEquals(world.getEnemies().size(), 1);

        // iterate through world until the towers kills the enemies
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();
        world.runTickMoves();

        assertEquals(world.getEnemies().size(), 0);
    }

    /**
     * test to check if tower will damage enemies within its radius
     */
    @Test
    public void towerAttackTest() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        helper.createCharacterSetup(0, world);

        // Create a enemy and place at a path index
        helper.createEnemySetup(0, 4, world);
        helper.createEnemySetup(0, 10, world);
        helper.createEnemySetup(0, 12, world);
        helper.createEnemySetup(2, 28, world);

        helper.createBuildingSetup("CampfireCard", 30, world, 2);
        helper.createBuildingSetup("TowerCard", 10, world, 3);

        helper.createGoalsSetup(1, world);

        assertEquals(world.getEnemies().size(), 4);

        world.runTickMoves();

        // no enemies died to the tower
        assertEquals(world.getEnemies().size(), 4);
    }

    /**
     * test to check if vampire is affected by the campfire when in its radius
     */
    @Test
    public void vampireInCampfireRadius() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        helper.createCharacterSetup(1, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        helper.createBuildingSetup("CampfireCard", 5, world, SOUTH);

        Enemy enemy = helper.createEnemySetup(2, 6, world);

        assertEquals(world.getEnemies().size(), 1);

        // vampire is intially at (4,0)
        assertEquals(enemy.getX(), 4);
        assertEquals(enemy.getY(), 0);

        // after world tick, it is at (3, 1). it is now in range of the campfire
        world.runTickMoves();
        assertEquals(enemy.getX(), 3);
        assertEquals(enemy.getY(), 1);

        // since in range of campfire, vampire moves back to (4, 0)
        world.runTickMoves();
        assertEquals(enemy.getX(), 4);
        assertEquals(enemy.getY(), 0);
    }

    /**
     * test if the player is able to interact with the hero's castle
     */
    @Test
    public void herosCastleTest() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        Character character = helper.createCharacterSetup(65, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        SimpleIntegerProperty xCoord = new SimpleIntegerProperty(0);
        SimpleIntegerProperty yCoord = new SimpleIntegerProperty(0);
        HerosCastle building = new HerosCastle(xCoord, yCoord);
        world.addBuilding(building);

        world.runTickMoves();

        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);

        world.runTickMoves();
    }

    /**
     * test if the zombiePits spawn enemies given the player walks over the hero's
     * castle
     */
    @Test
    public void herosCastleRespawnZombieLoopTest() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        Character character = helper.createCharacterSetup(64, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        SimpleIntegerProperty xCoord = new SimpleIntegerProperty(0);
        SimpleIntegerProperty yCoord = new SimpleIntegerProperty(0);
        HerosCastle building = new HerosCastle(xCoord, yCoord);
        world.addBuilding(building);

        // increment world loops to 1 and move player to hero's castle
        world.incrementLoops();
        world.runTickMoves();
        world.runTickMoves();

        // spawn zombie and check if a new zombie exists
        assertEquals(world.checkZombiePit(), false);
        helper.createBuildingSetup("ZombiePitCard", 30, world, 2);
        world.possiblySpawnEnemies();

        // player is ontop of castle
        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);

        // zombie should have spawned as conditions have been met
        assertEquals(world.checkZombieSpawned(), true);
    }

    /**
     * test if the vampireRespawnLoop increments after a vampire is spawned
     */
    @Test
    public void herosCastleRespawnVampireLoopTest() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        Character character = helper.createCharacterSetup(65, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        SimpleIntegerProperty xCoord = new SimpleIntegerProperty(0);
        SimpleIntegerProperty yCoord = new SimpleIntegerProperty(0);
        HerosCastle building = new HerosCastle(xCoord, yCoord);
        world.addBuilding(building);

        assertEquals(world.checkVampireBuilding(), false);
        helper.createBuildingSetup("VampireCastleCard", 30, world, 1);
        // world.possiblySpawnEnemies();
        assertEquals(world.checkVampireSpawned(), false);

        // increment world loops
        for (int i = 0; i < 4; i++) {
            world.incrementLoops();
        }
        assertEquals(world.getNumLoops(), 4);

        // move player character ontop of hero's castle
        world.runTickMoves();
        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);

        assertEquals(world.getNumLoops(), 5);

        assertEquals(world.checkVampireSpawned(), false);

        // move player through world
        for (int i = 0; i < 66; i++) {
            world.runTickMoves();
        }

        // spawn any possible vampires
        world.possiblySpawnEnemies();

        // assert the lops have incremented
        assertEquals(world.getNumLoops(), 6);

        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);
    }

    /**
     * test if vampireEnemy is spawned from vampireCastle
     */
    @Test
    public void testEnemySpawnPositions() {
        Helper helper = new Helper();
        // First create world based on map in helper
        LoopManiaWorld world = helper.createWorld(1);
        // Then create a character and place at a path index. For this e.g. I set it to
        // be 0
        Character character = helper.createCharacterSetup(65, world);
        // Create a enemy and place at a path index. For this e.g. I set it to be 1 and
        // 0 is the enemy selector which selects slug
        helper.createGoalsSetup(1, world);

        SimpleIntegerProperty xCoord = new SimpleIntegerProperty(0);
        SimpleIntegerProperty yCoord = new SimpleIntegerProperty(0);
        HerosCastle building = new HerosCastle(xCoord, yCoord);
        world.addBuilding(building);

        // spawn vampire building
        assertEquals(world.checkVampireBuilding(), false);
        helper.createBuildingSetup("VampireCastleCard", 30, world, 1);
        world.possiblySpawnEnemies();
        assertEquals(world.checkVampireSpawned(), false);

        // iterate through world to get to loop 5
        for (int i = 0; i < 4; i++) {
            world.incrementLoops();
        }
        assertEquals(world.getNumLoops(), 4);
        world.runTickMoves();
        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);
        assertEquals(world.getNumLoops(), 5);

        // spawn vampire entity
        world.possiblySpawnEnemies();
        assertEquals(world.checkVampireSpawned(), true);

        // iterate through world until character is at hero's castle again
        for (int i = 0; i < 66; i++) {
            world.runTickMoves();
        }

        world.possiblySpawnEnemies();

        assertEquals(world.getNumLoops(), 6);

        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);
    }
}