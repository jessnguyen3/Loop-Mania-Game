package test;

import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Statistics;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests for basic character functionality
 */
public class CharacterTest {
  private static final int MAP1 = 1;
  private static final int MAP2 = 2;

  /**
   * have the character move around MAP 1 and assert that the character travels
   * down the path correctly
   */
  @Test
  public void test_character_movement_MAP1() {
    Helper helper = new Helper();
    Character c = helper.testCharacterSetup(0, MAP1);
    assertEquals(c.getX(), 0);
    assertEquals(c.getY(), 0);
    c.moveDownPath();
    assertEquals(c.getX(), 1);
    assertEquals(c.getY(), 0);
    c.moveDownPath();
    assertEquals(c.getX(), 2);
    assertEquals(c.getY(), 0);
    c.moveDownPath();
    c.moveDownPath();
    c.moveDownPath();
    assertEquals(c.getX(), 4);
    assertEquals(c.getY(), 1);
  }

  /**
   * have the character move around MAP 2 and assert that the character travels
   * down the path correctly
   */
  @Test
  public void test_character_movement_MAP2() {
    Helper helper = new Helper();
    Character c = helper.testCharacterSetup(0, MAP2);
    assertEquals(c.getX(), 0);
    assertEquals(c.getY(), 0);
    c.moveDownPath();
    assertEquals(c.getX(), 1);
    assertEquals(c.getY(), 0);
    c.moveDownPath();
    assertEquals(c.getX(), 2);
    assertEquals(c.getY(), 0);
    c.moveDownPath();
    assertEquals(c.getX(), 2);
    assertEquals(c.getY(), 1);
    c.moveDownPath();
    assertEquals(c.getX(), 2);
    assertEquals(c.getY(), 2);
    c.moveDownPath();
    assertEquals(c.getX(), 1);
    assertEquals(c.getY(), 2);
    c.moveDownPath();
    assertEquals(c.getX(), 0);
    assertEquals(c.getY(), 2);
    c.moveDownPath();
    assertEquals(c.getX(), 0);
    assertEquals(c.getY(), 1);
    c.moveDownPath();
    assertEquals(c.getX(), 0);
    assertEquals(c.getY(), 0);
  }

  /**
   * assert that the character's stats when using the constructor are expected
   * values
   */
  @Test
  public void test_character_stats() {
    Helper helper = new Helper();
    Character c = helper.testCharacterSetup(0, MAP1);
    Statistics characterStats = c.getStats();
    assertEquals(characterStats.getHealth(), 100);
    assertEquals(characterStats.getAttack(), 10);
    assertEquals(characterStats.getDefense(), 2);
    assertEquals(characterStats.getGold(), 0);
    assertEquals(characterStats.getExp(), 0);
  }

  /**
   * test if character is ontop of a particular tile
   */
  @Test
  public void testCharacterOnTile() {
    Helper helper = new Helper();
    LoopManiaWorld world = helper.createWorld(1);
    // Then create a character and place at a path index.
    Character character = helper.createCharacterSetup(64, world);
    helper.createGoalsSetup(1, world);

    helper.createBuildingSetup("BarracksCard", 1, world, 0);
    helper.createBuildingSetup("ZombiePitCard", 30, world, 2);
    helper.createBuildingSetup("VampireCastleCard", 30, world, 1);

    // Move character such that it moves onto the hero's castle
    world.runTickMoves();
    world.runTickMoves();
    assertEquals(0, character.getX());
    assertEquals(0, character.getY());

    // character should be ontop of Barracks
    world.runTickMoves();

    world.runTickMoves();
  }

  /**
   * test if character gains gold when spawned in the world
   */
  @Test
  public void testGold() {
    Helper helper = new Helper();
    // First create world based on map in helper
    LoopManiaWorld world = helper.createWorld(1);
    // Then create a character and place at a path index. For this e.g. I set it to
    // be 0
    Character character = helper.createCharacterSetup(0, world);
    helper.createGoalsSetup(1, world);

    for (int i = 0; i < 66; i++) {
      world.runTickMoves();
      world.possiblySpawnGold();
    }

    assertEquals(character.getStats().getGold(), 0);
  }
}
