package test;

import unsw.loopmania.Character;
import unsw.loopmania.Item;
import unsw.loopmania.LoopManiaWorld;

import org.json.JSONArray;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

/**
 * Tests for all item types weapons: sword, stake, staff equipment: armour,
 * shield, helmet consumables: health potion, one ring
 */
public class ItemTest {
        /**
         * test the equipping and unequipping of items
         */
        @Test
        public void testAddRemoveUnequipItem() {
                Helper helper = new Helper();
                // First create world based on map in helper
                LoopManiaWorld world = helper.createWorld(1);
                // Then create a character and place at a path index. For this e.g. I set it to
                // be 0
                helper.createCharacterSetup(0, world);

                // add 21 items (only 16 slots exist)
                for (int i = -1; i < 7; i++) {
                        world.addUnequippedItem(i);
                }
                for (int i = -1; i < 7; i++) {
                        world.addUnequippedItem(i);
                }
                for (int i = -1; i < 7; i++) {
                        world.addUnequippedItem(i);
                }

                // assert items have been added to the unequipment inventory
                assertEquals(world.getUnequippedInventoryItems().size(), 16);
                world.removeUnequippedInventoryItemByCoordinates(0, 0);
                assertEquals(world.getUnequippedInventoryItems().size(), 15);
                assertEquals(world.checkItemInEquippedInventory("Armour"), false);
        }

        /**
         * test the if the one ring item revives the player
         */
        @Test
        public void testOneRingItem() {
                Helper helper = new Helper();
                // First create world based on map in helper
                LoopManiaWorld world = helper.createWorld(1);
                // Then create a character and place at a path index. For this e.g. I set it to
                // be 0
                Character character = helper.createCharacterSetup(0, world);
                helper.createGoalsSetup(1, world);

                assertEquals(world.checkTheOneRingInUnequippedItems(), false);

                JSONArray rareItems = new JSONArray();
                rareItems.put("the_one_ring");
                world.setRareItems(rareItems);

                world.addUnequippedItem(7);

                // check one ring is in inventory
                assertEquals(world.getUnequippedInventoryItems().size(), 1);
                assertEquals(world.checkTheOneRingInUnequippedItems(), true);

                assertEquals(character.getHealth(), 100);

                // character is fighting battles
                helper.createEnemySetup(2, 1, world);
                helper.createEnemySetup(2, 1, world);

                world.runBattles();

                // one ring is now destroyed
                assertEquals(world.checkTheOneRingInUnequippedItems(), false);
                assertEquals(character.getHealth(), 0);

                helper.createEnemySetup(2, 1, world);

                world.runBattles();
                assertEquals(character.getHealth(), 0);

                assertEquals(world.isGameWon(), false);
                assertEquals(world.isGameOver(), true);
        }

        /**
         * test if the health potion heals the player character
         */
        @Test
        public void testHealthPotion() {
                Helper helper = new Helper();
                // First create world based on map in helper
                LoopManiaWorld world = helper.createWorld(1);
                // Then create a character and place at a path index. For this e.g. I set it to
                // be 0
                Character character = helper.createCharacterSetup(0, world);
                helper.createGoalsSetup(1, world);

                helper.createEnemySetup(1, 1, world);
                world.runBattles();

                world.possiblySpawnEnemies();

                // character's health after battling and killing the slug
                assertEquals(character.getHealth(), 84);

                // spawn health potion
                world.addUnequippedItem(5);
                assertEquals(world.getUnequippedInventoryItems().size(), 1);
                List<Item> unequippedItems = world.getUnequippedInventoryItems();
                assertEquals(unequippedItems.get(0).getType(), "HealthPotion");

                // use health potion and assert change
                world.usePotion(unequippedItems.get(0));
                assertEquals(character.getHealth(), 100);

                // spawn more enemies such that after the battle, character's health is 84
                helper.createEnemySetup(1, 1, world);
                helper.createEnemySetup(1, 1, world);
                world.runBattles();

                // assert that the character's new health after fighting two slugs is 68
                assertEquals(character.getHealth(), 68);
                world.addUnequippedItem(5);
                assertEquals(world.getUnequippedInventoryItems().size(), 1);
                List<Item> unequippedItemsPotion = world.getUnequippedInventoryItems();
                assertEquals(unequippedItemsPotion.get(0).getType(), "HealthPotion");

                // assert character's new health is 88 after using the potion
                world.usePotion(unequippedItems.get(0));
                assertEquals(character.getHealth(), 88);
        }
}
