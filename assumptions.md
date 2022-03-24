# COMP2511 Major Project: Loop Mania
### Assumptions

### UML Diagram
- The goals are associated with the character AND the application
- Vampire is associated with VampireCastle building
- Zombie is associated with ZombiePitt
- Character is associated with Shop


### Game world
- There is always one complete loop that has the hero’s castle on some tile along the path.
- No entity can spawn outside of the tiles provided for the game world.

### Goals
- Goals cannot have two AND goals and two other AND goals OR'ed together for e.g. (Exp && Gold) || (Exp && Loops)

### Character
- Character attacks after an (one) allied soldier has attacked. 
- Character attacks before the enemies. 
- The character will only travel clockwise around the world path.
- Characters can equip 2 weapons and 2 equipments at one time. The weapons must be different types and the equipments must be different types.


### Allied Soldier
- The allied soldier(s) will take all damage inflicted from enemies.
- Only when there are no allied soldiers, the character will be wounded in battle.
- A player character can attain multiple allied soldiers at a time.
- The allied soldier will always attack first, even before the player character.


### Items
- All items can be destroyed by the human player using the bin icon and gold is obtained in return.
- ‘The One Ring’ item can be sold for experience and gold
- Potions can be sold for experience and gold.
- When the character loses all its health, if the player has ‘The One Ring’ item, then that item will be removed from the inventory and the character will have full health.
- ‘The One Ring’ item can only be obtained after killing an enemy
- The character can only hold onto one ‘The One Ring’ item until it has been used.


### Enemies
- There can be one or more enemies in a single battle.
- Only one enemy can occupy a path tile
- Vampires can move the fastest amongst the slugs and zombies
- Vampires critical rate (A float value between 0 and 1)
- Zombies critical rate (A float value between 0 and 1)
- In a battle with more than one enemy, when the player wins the rewards distributed by each individual enemy are added together.
- Enemies attack after all allied soldiers and the character have attacked.
- Enemies travel anti-clockwise through the game loop (towards the character)


### Battle
- The sequence of battle is: the soldier and enemy attack then the character and enemy attack
- The soldier and enemy battle will end on 3 conditions: the soldier has died, the enemy has died or the soldier has turned into an allied soldier
- If the soldier has turned into a zombie: it immediately attacks the character and the two battle until either entity die. That is, the character will fight the allied zombie before it attacks the enemy that turned the soldier into an allied zombie.
- A soldier-turned-zombie will keep its original statistics (from being a soldier)
- In the character and enemy battle, the battle ends if either side die.
- If an enemy is in trance, it will fight the supporting enemies for the character for a given number of trance attacks (randomised).
- An enemy in trance will keep its original statistics. The only thing that changes is it battles for the character.
- The vampire can inflict a critical bite the first time it attacks the soldier/character.



### Weapon 
- The chance of a trance being inflicted by the staff is randomised (between 0 - 20%).
- When in a trance, the enemy deals up to 10 attacks against other enemies.
- Sword increases character's attack by 10
- Staff increases character's attack by 2
- Stake increases character's attack by 5. It inflicts double the damage (in total 10) on a vampire.


### Equipment
- The Helmet, Shield and Armour defensive items will increase the player’s defense statistic
- Each equipment piece has an endurance attribute that will cause the equipment to break after the character has participated in multiple battles (to be implemented in Milestone 3)
- Armour increases character's defense by 5
- Shield increases character's defense by 7
- Helmet increases character's defense by 2 but also decreases character's attack by 2


### Consumable
- Health potion increases character's health by 20

### Buffs
- A character can withhold multiple buffs such as increases in damage or defense.
- The ‘trance’ effect will disappear after a certain amount of turns


### Buildings 
- All building cards can be sold by the human player using the bin icon
- A building can exist without a card (the Hero’s castle) 
- Once a card is placed, it persists on the map and cannot be taken off (except for the trap). 
- Two cards cannot be placed on the same tile. 
- Traps will not harm the character if the character walks over it.
- Tower will harm the enemy if it is in range (even when the character is not in battle with the enemy)


### Inventory management 
- Any items dropped by the enemies will appear in the top-left most slot of the inventory slots that are available.
- If the inventory is full, then the incoming item will be sold for experience and gold.
- Only one item can be stored per slot.
- Any building card dropped by enemies will appear in the left-most slot in the card slots that is available.
- If the card slot is full, then the incoming card will be sold for experience and gold.
- Special items such as ‘The one ring’ and potions are stored separately from the inventory slot since they have a different user interaction.


### Shop
- The shop will always sell potions.
- Equipment sold in the shop will be randomised and only two will be available to the character every time they open the shop.
- Each item in the shop can be bought in bulk (0..* items can be bought at one time).


### Frontend
- The size of the game application is modelled for the game to be played on desktops.
- The human player is using a mouse and keyboard in order to interact with the user interface.
- The language of the game is in English and all text entities will be in English.

