package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.json.JSONArray;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one entity
 * can occupy the same square.
 */
public class LoopManiaWorld {

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;
    public static final int equippedInventoryLength = 4;
    private static final int SLUG = 0;
    private static final int ZOMBIE = 1;
    private static final int VAMPIRE = 2;

    /**
     * Randomisers control the spawn rate of gold, items and cards.
     * ITEMRANDOMISER >= 7 will spawn items more rarely after battles
     * CARDRANDOMISER >= 8 will spawn cards more rarely after battles
     * GOLDRANDOMISER >= 0 will spawn gold more rarely as the character moves
     */
    private static final int GOLDRANDOMISER = 50;
    private static final int ITEMRANDOMISER = 30;
    private static final int CARDRANDOMISER = 20;
    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * number of loops the character has completed
     */
    static int numLoops;

    /**
     * Number of loops for when the vampire will respawn
     */
    private int vampireRespawnLoop = 5;

    /**
     * Number of loops for when the zombie will respawn
     */
    private int zombieRespawnLoop = 1;
    /**
     * number of loops the character has completed
     */
    private Goals worldGoals;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    private List<Enemy> enemies;

    private List<Card> cardEntities;

    private List<Item> unequippedInventoryItems;

    private List<Item> equippedInventoryItems;

    private List<Building> buildingEntities;

    private List<Gold> goldEntities;

    private List<String> allRareItems;

    private boolean gameOver = false;

    Pair<Integer, Integer> goldPosition;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private boolean gameWon = false;

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    private Shop shop = new Shop();

    public Shop getShop() {
        return shop;
    }

    private boolean inShop = false;

    public boolean isInShop() {
        return inShop;
    }

    public void setInShop(boolean inShop) {
        this.inShop = inShop;
    }

    private SimpleIntegerProperty loopsValue = new SimpleIntegerProperty(this, "loopsValue");

    public IntegerProperty LoopsValueProperty() {
        return loopsValue;
    }

    public int getLoopsProperty() {
        return loopsValue.get();
    }

    public void incrementLoopsProperty() {
        this.loopsValue.set(getLoopsProperty() + 1);
    }

    public List<Item> getUnequippedInventoryItems() {
        return unequippedInventoryItems;
    }

    public List<Item> getEquippedInventoryItems() {
        return equippedInventoryItems;
    }

    public List<Card> getCardEntities() {
        return cardEntities;
    }

    // * list of x,y coordinate pairs in the order by which moving entities traverse
    // * them
    // */
    private List<Pair<Integer, Integer>> orderedPath;

    private int shopCounter = 0;
    private int shopIncrement = 1;

    private int castleX;
    private int castleY;

    /**
     * create the world (constructor)
     * 
     * @param width       width of world in number of cells
     * @param height      height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing
     *                    position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        equippedInventoryItems = new ArrayList<>();
        for (int i = 0; i < equippedInventoryLength; i += 1) {
            equippedInventoryItems.add(null);
        }
        numLoops = 0;
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        goldEntities = new ArrayList<>();
        allRareItems = new ArrayList<>();
    }

    public List<Pair<Integer, Integer>> getOrderedPath() {
        return orderedPath;
    }

    public void increaseShopLoops() {
        shopCounter += shopIncrement;
        shopIncrement += 1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity
     * out of the file
     * 
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public Goals getWorldGoals() {
        return worldGoals;
    }

    public void setWorldGoals(Goals goal) {
        worldGoals = goal;
    }

    public void incrementLoops() {
        numLoops++;
        incrementLoopsProperty();
    }

    public List<Building> getBuildingEntities() {
        return buildingEntities;
    }

    public int getNumLoops() {
        return numLoops;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the
     * world)
     * 
     * @param entity
     */
    public void addEntity(Entity entity) {
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world. It currently
     * spawns only slugs
     * 
     * @return list of the enemies to be displayed on screen
     */

    /**
     * Thoughts: 1. Create possiblyGetEnemySpawnPosition() for all types of enemies
     * e.g. possiblyGetSlugSpawnPosition() 2. Create
     * possiblySpawnVampire(),possiblySpawnSlug(), possiblySpawnZombie(). All 3
     * methods are called in the possibleSpawnEnemies. If condition moved into
     * possibleSpawn_____() methods created. Each method returns an enemy that is
     * added to the spawningEnemies list.
     * 
     * @return
     */
    private boolean zombieSpawned = false;
    private boolean vampireSpawned = false;
    private boolean specialEnemySpawned = false;

    public List<Enemy> possiblySpawnEnemies() {
        int enemySelection = SLUG;
        if (numLoops % 5 == 0 && !checkVampireSpawned() && numLoops == vampireRespawnLoop && checkVampireBuilding()) {
            enemySelection = VAMPIRE;
            vampireRespawnLoop = numLoops + 5;
            vampireSpawned = true;
            specialEnemySpawned = true;
        } else if (!checkZombieSpawned() && numLoops == zombieRespawnLoop && checkZombiePit()) {
            enemySelection = ZOMBIE;
            zombieRespawnLoop = numLoops + 1;
            zombieSpawned = true;
            specialEnemySpawned = true;
        }
        List<Pair<Integer, Integer>> positions = possiblyGetEnemySpawnPosition(enemySelection);
        List<Enemy> spawningEnemies = new ArrayList<>();
        if (positions != null) {
            for (Pair<Integer, Integer> pos : positions) {
                int indexInPath = orderedPath.indexOf(pos);
                PathPosition pathPosition = new PathPosition(indexInPath, orderedPath);
                EnemySelector enemySelector = new EnemySelector();
                Enemy enemy = enemySelector.getEnemy(enemySelection, pathPosition);
                enemies.add(enemy);
                spawningEnemies.add(enemy);
            }
        }
        return spawningEnemies;
    }

    /**
     * Checks if vampire building is in the world
     * @return  State if vampire building is in the world
     */
    public boolean checkVampireBuilding() {
        for (Building b : buildingEntities) {
            if (b instanceof VampireCastle) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if zombie pit is in the world
     * @return  State if zombie pit is in the world
     */
    public boolean checkZombiePit() {
        for (Building b : buildingEntities) {
            if (b instanceof ZombiePit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a vampire has spawned already. Only 1 spawn allowed per loop
     * @return  State if a vampire is already in the world.
     */
    public boolean checkVampireSpawned() {
        for (Enemy e : enemies) {
            if (e instanceof Vampire) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a zombie has spawned already. Only 1 spawn allowed per loop
     * @return  State if a zombie is already in the world.
     */
    public boolean checkZombieSpawned() {
        for (Enemy e : enemies) {
            if (e instanceof Zombie) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kill an enemy
     * 
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy) {
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * Determines behaviour for allied soldier. Soldier will keep fighting enemy unless turned into a zombie.
     * @param soldier   Soldier fighting in battle
     * @param enemy     Enemy fought by soldier
     */
    public void soldierEnemyBattle(AlliedSoldier soldier, Enemy enemy) {
        while (soldier.getHealth() > 0) {
            System.out.println("Soldier and enemy battle");
            soldier.attack(enemy, equippedInventoryItems);
            if (enemy.getHealth() == 0) {
                break;
            }
            if (enemy instanceof Zombie) {
                Zombie zombie = (Zombie) enemy;
                zombie.possiblyTurnSoldier(soldier);
            }

            if (soldier.getIsZombie()) {
                // soldier turned into a zombie, it can no longer battle
                break;
            }
            enemy.attack(soldier, equippedInventoryItems);
        }
    }

    /**
     * For the character and the enemy, play out the battle that will occur
     * @param enemy             Enemy for which the character fights
     * @param defeatedEnemies   List of enemies defeated already
     */
    public void characterEnemyBattle(MovingEntity enemy, List<Enemy> defeatedEnemies) {
        while (character.getHealth() > 0) {
            System.out.println("Character attack: " + character.getAttack());
            character.attack(enemy, equippedInventoryItems);
            System.out.println("Enemy health: " + enemy.getHealth());
            if (enemy.getHealth() == 0) {
                break;
            }
            this.determineEnemyTrance(enemy);
            // an allied soldier can not be "in-trance"
            if (enemy.getInTrance() && enemy instanceof Enemy) {
                // enemy in trance fights the supporting enemies
                fightSupportingEnemies(defeatedEnemies, enemy);
                if (enemy.getHealth() == 0) {
                    // if the enemy has died whilst in trance,
                    // remove the enemy
                    defeatedEnemies.add((Enemy) enemy);
                    return;
                }
            }
            enemy.attack(character, equippedInventoryItems);
            System.out.println("Enemy attack: " + enemy.getAttack());
        }
    }

    /**
     * Determines if supporting enemies will join the fight
     * @param defeatedEnemies   List of enemies defeated so far
     * @param player            The character player in the world
     */
    public void fightSupportingEnemies(List<Enemy> defeatedEnemies, MovingEntity player) {
        int numAttacks = 0;
        for (Enemy enemy : enemies) {
            // 1. Check the supporting enemy is alive and is in range
            // Note: The supporting enemy will never be the current enemy as either they
            // would have died (or the character has died - game ended).
            // Therefore, the if condition will fail.
            // 2. Check the character is within the supporting enemy's supprorting radius
            if (enemy.getHealth() > 0 && this.isInSupportRange(enemy)) {
                if (player instanceof Character) {
                    characterEnemyBattle(enemy, defeatedEnemies);
                    this.checkCharacterHealth();
                    // enemy has died, destroy the enemy and collect the rewards
                    if (enemy.getHealth() == 0) {
                        character.collectRewards(enemy);
                        defeatedEnemies.add(enemy);
                    }
                } else { // enemy in trance
                    while (numAttacks < player.getNumTranceAttacks()) {
                        player.attack(enemy, equippedInventoryItems);
                        if (enemy.getHealth() == 0) {
                            // the enemy (not in trance) has died
                            // break to iterate the enemies list and get the next supporting enemy
                            break;
                        }
                        enemy.attack(player, equippedInventoryItems);
                        if (player.getHealth() == 0) {
                            // the enemy in trance has died
                            return;
                        }
                        numAttacks++;
                    }
                    // the enemy has attacked for the character numAttacks time
                    // it is now no longer in trance
                    if (numAttacks == player.getNumTranceAttacks()) {
                        return; // the enemy is no longer in trance
                    }
                }
            }
        }
    }

    /**
     * Checks if soldier can attack the enemy
     * @param enemy             Enemy to defeat
     * @param defeatedEnemies   List of enemies defeated already
     */
    public void soldierPossiblyAttacksEnemy(Enemy enemy, List<Enemy> defeatedEnemies) {
        // allied soldier exists - fights the enemy first
        if (character.alliedSoldierExists()) {
            AlliedSoldier soldier = character.getAnAlliedSoldier();
            // allied soldier and enemy battle
            soldierEnemyBattle(soldier, enemy);

            if (soldier.getIsZombie()) {
                // allied soldier - turned zombie - battles the character
                System.out.println("Allied soldier is a zombie!");
                characterEnemyBattle(soldier, defeatedEnemies);
                this.checkCharacterHealth();
            }

            // soldier has died, it can no longer fight for the character
            if (soldier.getHealth() == 0) {
                character.removeSoldier(soldier);
                System.out.println("Allied soldier has died!");
            }
        }
    }

    /**
     * Determine if the enemy will be transitioned into the trance stage
     * 
     * @param enemy
     */
    public void determineEnemyTrance(MovingEntity enemy) {
        Item weapon1 = equippedInventoryItems.get(0);
        Item weapon2 = equippedInventoryItems.get(1);
        if ((weapon1 != null && weapon1 instanceof Staff) || (weapon2 != null && weapon2 instanceof Staff)) {
            System.out.println("In trance");
            Random random = new Random();
            int tranceChance = random.nextInt(100);
            int tranceThreshold = random.nextInt(20);
            // the chance of a trance is random (as seen by the tranceThreshold)
            if (tranceChance < tranceThreshold) {
                enemy.setInTrance(true);
                // set the number of attacks the enemy will undergo in trance
                int numAttacks = random.nextInt(10);
                enemy.setNumTranceAttacks(numAttacks);
            }
        }
    }

    /**
     * Checks if enemy is in battle radius
     * @param enemy     Enemy to attack
     * @return          State if enemy is within battle radius
     */
    public boolean isInBattleRange(Enemy enemy) {
        return Math.pow((character.getX() - enemy.getX()), 2) + Math.pow((character.getY() - enemy.getY()), 2) < enemy
                .getBattleRadius();
    }

    /**
     * Checks if enemy is in support radius
     * @param enemy     Enemy to attack
     * @return          State if enemy is within support radius
     */
    public boolean isInSupportRange(Enemy enemy) {
        return Math.pow((character.getX() - enemy.getX()), 2) + Math.pow((character.getY() - enemy.getY()), 2) < enemy
                .getSupportRadius();
    }

    /**
     * run the expected battles in the world, based on current world state
     * 
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        for (Enemy e : enemies) {
            // 1. check enemy's health as they may have died from supporting a previous
            // enemy
            // 2. check the character is in the enemy's battle radius
            if (e.getHealth() > 0 && this.isInBattleRange(e)) {
                // if an allied soldier exists, it fights the enemy first
                this.soldierPossiblyAttacksEnemy(e, defeatedEnemies);
                // enemy and character battle
                characterEnemyBattle(e, defeatedEnemies);
                this.checkCharacterHealth();
                character.collectRewards(e);
                // by this point, either the enemy died or the game ended (the character died)
                defeatedEnemies.add(e);

                // character fights all the supporting enemies
                // character that was in trance (and turned back to an enemy)
                // will fight character again here
                this.fightSupportingEnemies(defeatedEnemies, e);
            }
        }

        for (Enemy e : defeatedEnemies) {
            killEnemy(e);
        }
        return defeatedEnemies;

    }

    /**
     * Checks for current health of character and applies OneRing in case of death
     */
    public void checkCharacterHealth() {
        if (character.getHealth() == 0) {
            if (checkTheOneRingInUnequippedItems()) {
                for (Item i : unequippedInventoryItems) {
                    if (i instanceof TheOneRing) {
                        removeUnequippedInventoryItem(i);
                        break;
                    }
                }
                character.setHealth(100);
            } else {
                triggerGameOver();
            }
        }
    }

    /**
     * Trigger game over for LoopManiaWorldController to trigger game over screen
     */
    private void triggerGameOver() {
        // signal to loop mania world controller to end the game
        setGameOver(true);
    }

    /**
     * Load a card into the character's card entities
     * @param cardSelection     Specific card selection. Negative numbers will generate random cards
     */
    public void loadCard(int cardSelection) {
        if (cardEntities.size() >= getWidth()) {
            Card card = cardEntities.get(0);
            card.removeCardAward(character);
            addUnequippedItem(-1);
            removeCard(0);
        }
        Random randomCard = new Random();
        int randCard = cardSelection;
        // If card selection is < 0, then we generate a random card
        if (cardSelection < 0) {
            randCard = randomCard.nextInt(CARDRANDOMISER);
        } 
        CardSelector cardSelector = new CardSelector();
        Card card = cardSelector.getCard(randCard, cardEntities.size());
        if (card != null) {
            cardEntities.add(card);
        }
    }

    /**
     * Grabs a card depending on its location in the world
     * @param cardNodeX     x coordinate of card
     * @param cardNodeY     y coordinate of card
     * @return              Card at said location
     */
    public Card getCard(int cardNodeX, int cardNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        return card;
    }

    /**
     * Checks if One Ring is in inventory for unequipped items
     * @return  State that One Ring is in unequipped items
     */
    public boolean checkTheOneRingInUnequippedItems() {
        boolean state = false;
        for (Item i : unequippedInventoryItems) {
            if (i != null && i instanceof TheOneRing) {
                return true;
            }
        }
        return state;
    }

    /**
     * Adds unequipped items to unequippedItemSlots
     * @param itemSelection     Specific item selection. If < 0, will choose a random item
     */
    public void addUnequippedItem(int itemSelection) {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // Give some reward for removing the card
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        int randItem;
        ItemSelector itemSelector = new ItemSelector();
        if (itemSelection < 0) {
            Random randomItem = new Random();
            randItem = randomItem.nextInt(ITEMRANDOMISER);
        } else {
            randItem = itemSelection;
        }
        Item item = itemSelector.getItem(randItem, allRareItems,
                new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()), checkTheOneRingInUnequippedItems());
        if (item != null) {
            unequippedInventoryItems.add(item);
        }
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed
     * cards)
     * 
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index) {
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * remove an item by x,y coordinates
     * 
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y) {
        Item item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * Checks if item is in equipped inventory
     * @param itemType  String for item type
     * @return          State that item is in equipped inventory
     */
    public boolean checkItemInEquippedInventory(String itemType) {
        boolean state = false;
        for (Item i : equippedInventoryItems) {
            if (i != null && i.getType().equals(itemType)) {
                return true;
            }
        }
        return state;
    }

    /**
     * Converts card into items by coordinates
     * @param nodeX
     * @param nodeY
     * @param x
     * @param y
     * @return      Card retrieved based on coordinates on UI
     */
    public Item convertCardToItemByCoordinates(int nodeX, int nodeY, int x, int y) {
        Item item = null;
        for (Item i : unequippedInventoryItems) {
            if ((i.getX() == nodeX) && (i.getY() == nodeY)) {
                item = i;
                break;
            }
        }
        System.out.println(item.getItemType());
        if (item != null) {
            switch (x) {
                case 0:
                    if (item.getItemType().equals("Weapon") && !checkItemInEquippedInventory(item.getType())) {
                        equippedInventoryItems.set(x, item);
                        System.out.println("Weapon: " + item.getType() + " has been added");
                    } else {
                        item = null;
                    }
                    break;
                case 1:
                    if (item.getItemType().equals("Weapon") && !checkItemInEquippedInventory(item.getType())) {
                        equippedInventoryItems.set(x, item);
                        System.out.println("Weapon: " + item.getType() + " has been added");
                    } else {
                        item = null;
                    }
                    break;
                case 2:
                    if (item.getItemType().equals("Equipment") && !checkItemInEquippedInventory(item.getType())) {
                        equippedInventoryItems.set(x, item);
                        System.out.println("Equipment: " + item.getType() + " has been added");
                    } else {
                        item = null;
                    }
                    break;
                case 3:
                    if (item.getItemType().equals("Equipment") && !checkItemInEquippedInventory(item.getType())) {
                        equippedInventoryItems.set(x, item);
                        System.out.println("Equipment: " + item.getType() + " has been added");
                    } else {
                        item = null;
                    }
                    break;
            }

        }
        return item;
    }

    /**
     * run moves which occur with every tick without needing to spawn anything
     * immediately
     */
    public void runTickMoves() {
        this.updateCharacterBuff();
        character.updateStatistics(equippedInventoryItems);
        character.move();
        moveEnemies();
        detectCharacterisOnTile();
        detectEnemyisOnTile();
        detectEnemyInRadius();
        // Everytime the character moves, check if the character has acheieved the world
        // goals
        if (worldGoals.checkGoalsMet(character.getStats(), numLoops)) {
            setGameWon(true);
        }
    }

    /**
     * Checks for character buffs depending on location
     */
    public void updateCharacterBuff() {
        for (Building b : buildingEntities) {
            if (b instanceof Campfire) {
                if (Math.pow((character.getX() - b.getX()), 2) + Math.pow((character.getY() - b.getY()), 2) < 4) {
                    character.setIsBuffed(true);
                    return;
                }
            }
        }
        character.setIsBuffed(false);
    }

    /**
     * loop through all buildings and gold entities and see if Character is onTile
     */
    public void detectCharacterisOnTile() {
        List<Gold> destroyedGoldEntities = new ArrayList<>();
        for (Building b : buildingEntities) {
            // if building X coordinate == character X coordinate &&
            // building Y coordinate == character Y coordinate
            if ((b.getX() == character.getX()) && (b.getY() == character.getY())) {
                b.performActionOnCharacter(this.character);

                if (b instanceof HerosCastle) {
                    if (!zombieSpawned && numLoops != 0) {
                        zombieRespawnLoop += 1;
                    }

                    if (!vampireSpawned && numLoops % 5 == 0 && numLoops != 0) {
                        vampireRespawnLoop += 5;
                    }
                    // Check if shop is opened
                    if (numLoops == shopCounter) {
                        System.out.println("Shop is opened");
                        increaseShopLoops();
                        setInShop(true);
                    }
                    incrementLoops();
                }
            }
        }
        for (Gold g : goldEntities) {
            if (g.getX() == (character.getX()) && g.getY() == (character.getY())) {
                Statistics stats = character.getStats();
                stats.setGold(stats.getGold() + g.getGold());
                System.out.println("Character's new gold: " + stats.getGold());
                destroyedGoldEntities.add(g);
            }
        }

        for (Gold g : destroyedGoldEntities) {
            destroyGold(g);
        }
    }

    /**
     * Loop through all buildings and see if Enemy is onTile
     */
    public void detectEnemyisOnTile() {

        ArrayList<Building> destroyedBuildings = new ArrayList<Building>();
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();

        for (Building b : buildingEntities) {
            for (Enemy e : enemies) {
                if (b.getX() == (e.getX()) && b.getY() == (e.getY()) && b instanceof Trap) {
                    boolean enemyKilledByTrap = b.performActionOnEnemy(e);
                    destroyedBuildings.add(b);

                    if (enemyKilledByTrap) {
                        defeatedEnemies.add(e);
                    }
                }
            }
        }

        for (Building b : destroyedBuildings) {
            destroyBuilding(b);
        }
        for (Enemy e : defeatedEnemies) {
            killEnemy(e);
        }
    }

    /**
     * Destroys gold from the world
     * @param gold 
     */
    public void destroyGold(Gold gold) {
        gold.destroy();
        goldEntities.remove(gold);
    }

    /**
     * Detects if enemy is within radius of the buildings in the world
     */
    public void detectEnemyInRadius() {

        ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
        ArrayList<Enemy> killedEnemies = new ArrayList<Enemy>();

        ArrayList<Building> campfires = new ArrayList<Building>();

        for (Building b : buildingEntities) {

            if (b instanceof Campfire) {
                campfires.add(b);
            }

            for (Enemy e : enemies) {

                // TOWER
                // Pythagoras calculation to see if enemy in range.
                if (b instanceof Tower && Math.pow((b.getX() - e.getX()), 2) + Math.pow((b.getY() - e.getY()), 2) < e
                        .getBattleRadius()) {

                    enemiesInRange.add(e);

                    boolean enemyKilledByTower = b.performActionOnEnemy(e);

                    if (enemyKilledByTower) {
                        killedEnemies.add(e);
                    }
                }

                // Campfire
                if (b instanceof Campfire && e.getType().equals("Vampire") && Math.pow((b.getX() - e.getX()), 2)
                        + Math.pow((b.getY() - e.getY()), 2) < e.getBattleRadius()) {
                    e.reverseDirection();
                }
            }
        }

        // Loop through enemies killed by tower and
        for (Enemy e : killedEnemies) {
            killEnemy(e);
        }
    }

    /**
     * Destroys building from world
     * @param building
     */
    private void destroyBuilding(Building building) {
        building.destroy();
        buildingEntities.remove(building);
    }

    /**
     * remove an item from the unequipped inventory
     * 
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item) {
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates assumes that no 2
     * unequipped inventory items share x and y coordinates
     * 
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Item getUnequippedInventoryItemEntityByCoordinates(int x, int y) {
        for (Item i : unequippedInventoryItems) {
            if ((i.getX() == x) && (i.getY() == y)) {
                return i;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list
     * (this is ordered based on age in the starter code)
     * 
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index) {
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the
     * unequipped inventory
     * 
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available
        // slot defined by looking row by row
        for (int y = 0; y < unequippedInventoryHeight; y++) {
            for (int x = 0; x < unequippedInventoryWidth; x++) {
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null) {
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * 
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c : cardEntities) {
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveEnemies() {
        for (Enemy e : enemies) {
            e.move();
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * 
     * @return null if random choice is that wont be spawning an enemy or it isn't
     *         possible, or random coordinate pair if should go ahead
     */
    private List<Pair<Integer, Integer>> possiblyGetEnemySpawnPosition(int enemySelection) {
        Random rand = new Random();
        int choice = rand.nextInt(2);
        // spawn 4 enemies
        if (specialEnemySpawned) {
            choice = 0;
        }
        List<Pair<Integer, Integer>> spawnPositions = new ArrayList<>();
        if ((choice == 0) && ((enemies.size() < 4) || specialEnemySpawned)) {
            if (specialEnemySpawned) {
                specialEnemySpawned = false;
            }
            Pair<Integer, Integer> spawnPosition = null;
            if (enemySelection == 0) {
                List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
                int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
                // inclusive start and exclusive end of range of positions not allowed
                int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
                int endNotAllowed = (indexPosition + 3) % orderedPath.size();
                // note terminating condition has to be != rather than < since wrap around...
                for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
                    orderedPathSpawnCandidates.add(orderedPath.get(i));
                }

                // choose random choice
                spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
                goldPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
                spawnPositions.add(spawnPosition);
            }
            if (enemySelection == 1) {
                spawnPositions = getZombieSpawn();
            }

            if (enemySelection == 2) {
                spawnPositions = getVampireSpawn();
            }
            return spawnPositions;
        }
        return null;
    }

    /**
     * Gets the zombie spawn location
     * @return  Coordinates for zombie spawn
     */
    public List<Pair<Integer, Integer>> getZombieSpawn() {
        List<Pair<Integer, Integer>> zombieSpawns = new ArrayList<>();
        List<Pair<Integer, Integer>> allZombieBuildings = new ArrayList<>();
        for (Building b : buildingEntities) {
            if (b instanceof ZombiePit) {
                allZombieBuildings.add(new Pair<Integer, Integer>(b.getX(), b.getY()));
            }
        }
        for (Pair<Integer, Integer> building : allZombieBuildings) {
            for (int i = 0; i < orderedPath.size(); i++) {
                Pair<Integer, Integer> cell = orderedPath.get(i);
                if ((cell.getValue0() == building.getValue0() + 1 && cell.getValue1() == building.getValue1())
                        || (cell.getValue0() == building.getValue0() - 1 && cell.getValue1() == building.getValue1())
                        || (cell.getValue0() == building.getValue0() && cell.getValue1() == building.getValue1() + 1)
                        || (cell.getValue0() == building.getValue0() && cell.getValue1() == building.getValue1() - 1)) {
                    zombieSpawns.add(cell);
                    break;
                }
            }
        }
        return zombieSpawns;
    }

    /**
     * Gets the vampire spawn location
     * @return  Coordinates for vampire spawn
     */
    public List<Pair<Integer, Integer>> getVampireSpawn() {
        List<Pair<Integer, Integer>> vampireSpawns = new ArrayList<>();
        List<Pair<Integer, Integer>> allVampireBuildings = new ArrayList<>();
        for (Building b : buildingEntities) {
            if (b instanceof VampireCastle) {
                allVampireBuildings.add(new Pair<Integer, Integer>(b.getX(), b.getY()));
                System.out.println("Vampire pit building at (" + b.getX() + "," + b.getY() + ")");
            }
        }
        for (Pair<Integer, Integer> building : allVampireBuildings) {
            for (int i = 0; i < orderedPath.size(); i++) {
                Pair<Integer, Integer> cell = orderedPath.get(i);
                if ((cell.getValue0() == building.getValue0() + 1 && cell.getValue1() == building.getValue1())
                        || (cell.getValue0() == building.getValue0() - 1 && cell.getValue1() == building.getValue1())
                        || (cell.getValue0() == building.getValue0() && cell.getValue1() == building.getValue1() + 1)
                        || (cell.getValue0() == building.getValue0() && cell.getValue1() == building.getValue1() - 1)) {
                    System.out.println("Vampire spawned at (" + cell.getValue0() + "," + cell.getValue1() + ")");
                    vampireSpawns.add(cell);
                    break;
                }
            }
        }
        return vampireSpawns;
    }

    /**
     * remove a card by its x, y coordinates
     * 
     * @param cardNodeX     x index from 0 to width-1 of card to be removed
     * @param cardNodeY     y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        Building building = null;
        BuildingSelector buildingSelector = new BuildingSelector();
        building = buildingSelector.getBuilding(card.getCardType(), new SimpleIntegerProperty(buildingNodeX),
                new SimpleIntegerProperty(buildingNodeY), checkBuildingOnPath(buildingNodeX, buildingNodeY),
                checkBuildingNextToPath(buildingNodeX, buildingNodeY));
        if (building != null) {
            buildingEntities.add(building);
            card.destroy();
            cardEntities.remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
        }
        return building;

    }

    /**
     * Checks if building is on path based on coordinates
     * @param x
     * @param y
     * @return  state if building is on path
     */
    public boolean checkBuildingOnPath(int x, int y) {
        for (int i = 0; i < orderedPath.size(); i++) {
            Pair<Integer, Integer> cell = orderedPath.get(i);
            if (cell.getValue0() == x && cell.getValue1() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if building is next to path based on coordinates
     * @param x
     * @param y
     * @return  state if building is next to path
     */
    public boolean checkBuildingNextToPath(int x, int y) {
        for (int i = 0; i < orderedPath.size(); i++) {
            Pair<Integer, Integer> cell = orderedPath.get(i);
            if ((cell.getValue0() == x + 1 && cell.getValue1() == y)
                    || (cell.getValue0() == x - 1 && cell.getValue1() == y)
                    || (cell.getValue0() == x && cell.getValue1() == y + 1)
                    || (cell.getValue0() == x && cell.getValue1() == y - 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets all rare items that can be spawned in the world
     * @param jsonRareItems     JSONArray passed from JSON files
     */
    public void setRareItems(JSONArray jsonRareItems) {
        for (int i = 0; i < jsonRareItems.length(); i++) {
            allRareItems.add(jsonRareItems.getString(i));
        }
    }

    public void setCastleCoordinates(int x, int y) {
        castleX = x;
        castleY = y;
    }

    public int getCastleX() {
        return castleX;
    }

    public int getCastleY() {
        return castleY;
    }

    /**
     * Create gold randomly to spawn in world
     * @return  Gold object
     */
    public Gold possiblySpawnGold() {
        Random randSpawn = new Random();
        int goldSpawn = randSpawn.nextInt(GOLDRANDOMISER);
        if (goldPosition != null && goldSpawn == 0) {
            int indexInPath = orderedPath.indexOf(goldPosition);
            PathPosition pathPosition = new PathPosition(indexInPath, orderedPath);
            Statistics stats = new Statistics(0, 0, 0, 0, 100);
            Gold gold = new Gold(pathPosition, stats);
            goldEntities.add(gold);
            return gold;
        }
        return null;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addBuilding(Building building) {
        buildingEntities.add(building);
    }

    /**
     * Get item based on coordinates
     * @param x
     * @param y
     * @return  Returns item from said coordinates
     */
    public Item getItem(int x, int y) {
        Item item = null;
        for (Item i : unequippedInventoryItems) {
            if ((i.getX() == x) && (i.getY() == y)) {
                item = i;
                break;
            }
        }
        return item;
    }

    /**
     * Use a potion to heal the character
     * @param item  Potion used to heal character
     */
    public void usePotion(Item item) {
        unequippedInventoryItems.remove(item);
        item.destroy();
        Statistics stats = character.getStats();
        int characterHealth = stats.getHealth();
        stats.setHealth(characterHealth + 20);
    }

}
