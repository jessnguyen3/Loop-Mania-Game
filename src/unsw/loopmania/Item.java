package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Items that can be attached to the character.
 */
public abstract class Item extends StaticEntity {
    /**
     * attributes for Item EXPREWARD and GOLDREWARD: integers for the gold and exp
     * rewards
     */
    private static final int EXPREWARD = 20;
    private static final int GOLDREWARD = 100;
    protected String type;

    /**
     * constructor for Item
     * 
     * @param x
     * @param y
     */
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * getter for Type
     * 
     * @return String
     */
    public String getType() {
        return type;
    }

    public void removeCardAward(Character character) {
        Statistics stats = character.getStats();
        stats.setExp(stats.getExp() + EXPREWARD);
        stats.setGold(stats.getGold() + GOLDREWARD);
    }

    public abstract int getIncrease();

    public abstract String getItemType();
}