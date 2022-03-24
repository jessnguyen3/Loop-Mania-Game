package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class CardSelector {
    private static final int CAMPFIRECARD = 0;
    private static final int TRAPCARD = 1;
    private static final int VILLAGECARD = 2;
    private static final int ZOMBIEPITCARD = 3;
    private static final int VAMPIRECASTLECARD = 4;
    private static final int TOWERCARD = 5;
    private static final int BARRACKSCARD = 6;

    public Card getCard(int cardSelection, int cardSize) {
        Card card;
        switch(cardSelection) {
            case CAMPFIRECARD:
                card = new CampfireCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case TRAPCARD:
                card = new TrapCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case VILLAGECARD:
                card = new VillageCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case ZOMBIEPITCARD:
                card = new ZombiePitCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case VAMPIRECASTLECARD:
                card = new VampireCastleCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case TOWERCARD:
                card = new TowerCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case BARRACKSCARD:
                card = new BarracksCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
        }
        return null;
    }

    public Card getCard(String cardSelection, int cardSize) {
        Card card;
        switch(cardSelection) {
            case "CampfireCard":
                card = new CampfireCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case "TrapCard":
                card = new TrapCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case "VillageCard":
                card = new VillageCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case "ZombiePitCard":
                card = new ZombiePitCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case "VampireCastleCard":
                card = new VampireCastleCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case "TowerCard":
                card = new TowerCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
            case "BarracksCard":
                card = new BarracksCard(new SimpleIntegerProperty(cardSize), new SimpleIntegerProperty(0));
                return card;
        }
        return null;
    }
}
