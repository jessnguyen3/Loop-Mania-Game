package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Item;
import unsw.loopmania.LoopManiaWorld;

public class CardTest {
    /**
     * Tests conversion of a card into item from LoopManiaWorld and placing into equipped inventory.
     * The first two slots are reserved for weapons only and the next two slots will be reserved for 
     * equipment.
     */
    @Test
    public void testCardToItem() {
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(1);
        // All equipped items initially has 4 null values. Equipped weapons will replace these
        // null values for use in battle.
        assertEquals(4, world.getEquippedInventoryItems().size());

        // Create a sword and slot it into slot 1
        world.addUnequippedItem(0);
        Item item = world.convertCardToItemByCoordinates(0, 0, 0, 0);
        assertEquals("Sword", item.getType());

        // Create a stake and slot it into slot 2
        world.addUnequippedItem(1);
        item = world.convertCardToItemByCoordinates(1, 0, 1, 0);
        assertEquals("Stake", item.getType());

        // Create a shield and slot it into slot 3
        world.addUnequippedItem(4);
        item = world.convertCardToItemByCoordinates(2, 0, 2, 0);
        assertEquals("Shield", item.getType());

        // Create a shield and slot it into slot 4
        world.addUnequippedItem(2);
        item = world.convertCardToItemByCoordinates(3, 0, 3, 0);
        assertEquals("Armour", item.getType());

        // Assert that each item was placed correctly into the equipped inventory slot
        List<Item> equippedItems = world.getEquippedInventoryItems();
        Item firstEquippedItem = equippedItems.get(0);
        assertEquals(firstEquippedItem.getType(), "Sword");

        Item secondEquippedItem = equippedItems.get(1);
        assertEquals(secondEquippedItem.getType(), "Stake");

        Item thirdEquippedItem = equippedItems.get(2);
        assertEquals(thirdEquippedItem.getType(), "Shield");

        Item fourthEquippedItem = equippedItems.get(3);
        assertEquals(fourthEquippedItem.getType(), "Armour");

        // Ensure that only weapons can be slotted in slots 1 and 2, and equipment is only
        // slotted in slots 3 and 4. First reset all unequippedItems
        world.removeUnequippedInventoryItem(firstEquippedItem);
        world.removeUnequippedInventoryItem(secondEquippedItem);
        world.removeUnequippedInventoryItem(thirdEquippedItem);
        world.removeUnequippedInventoryItem(fourthEquippedItem);

        // Create a shield and try to slot it into slot 1 and 2. Should return null as only equipment
        // items can be sent into that specified equipment slot.
        world.addUnequippedItem(3);
        item = world.convertCardToItemByCoordinates(0, 0, 0, 0);
        assertEquals(null, item);

        world.addUnequippedItem(3);
        item = world.convertCardToItemByCoordinates(1, 0, 1, 0);
        assertEquals(null, item);

        // Create a sword and try to slot it into slot 3 and 4. Should return null as only equipment
        // items can be sent into that specified equipment slot.
        world.addUnequippedItem(0);
        item = world.convertCardToItemByCoordinates(2, 0, 2, 0);
        assertEquals(null, item);

        world.addUnequippedItem(0);
        item = world.convertCardToItemByCoordinates(3, 0, 3, 0);
        assertEquals(null, item);

        // Test for getItem()
        item = world.getItem(0, 0);
        assertEquals(item.getType(), "Helmet");
        
        // Give an invalid location for item to return null
        item = world.getItem(0, 1);
        assertEquals(item, null);
    }
    
    /**
     * Tests conversion of a card into building from LoopManiaWorld and placing into equipped inventory.
     * The first two slots are reserved for weapons only and the next two slots will be reserved for 
     * equipment.
     */
    @Test
    public void testCardToBuilding() {
        Helper helper = new Helper();
        LoopManiaWorld world = helper.createWorld(1);
        helper.createCharacterSetup(0, world);
        // Load in a campfire and place it right next to Ordered Path. Since location is valid
        // building should be returned and card is destroyed.
        world.loadCard(0);   
        Building building = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);    
        assertEquals(building.getType(), "Campfire");
        assertEquals(world.getBuildingEntities().size(), 1);
        assertEquals(world.getCardEntities().size(), 0);

        // Load in a campfire and place it on the Ordered Path. Since location is invalid
        // building will be null.
        world.loadCard(0);   
        building = world.convertCardToBuildingByCoordinates(0, 0, 0, 0);    
        assertEquals(building, null);

        // Test for getCard()
        Card card = world.getCard(0, 0);
        assertEquals(card.getCardType(), "CampfireCard");

        // Give an invalid location for card to return null
        card = world.getCard(0, 1);
        assertEquals(card, null);

        // Check that loadCard removes oldest entity when inventory is full
        for (int i = 0; i < 10; i++) {
            world.loadCard(0);   
        }
        assertEquals(world.getCardEntities().size(), 8);
        world.loadCard(-1); 

    }
}
