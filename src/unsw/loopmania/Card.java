package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    /**
     * Static Final integers to represent 
     */
    private static final int EXPREWARD = 20;
    private static final int GOLDREWARD = 100;

    /**
     * cardType
     */
    protected String cardType;

    /**
     * constructor for Card
     * @param x
     * @param y
     */
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for card type
     * @return String
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * method to award the player when a card is removed
     * @param character
     */
    public void removeCardAward(Character character) {
        Statistics stats = character.getStats();
        stats.setExp(stats.getExp() + EXPREWARD);
        stats.setGold(stats.getGold() + GOLDREWARD);
    }
    
}
