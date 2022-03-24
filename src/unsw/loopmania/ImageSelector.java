package unsw.loopmania;

import java.util.List;

import javafx.scene.image.Image;

public class ImageSelector {
    public Image getImage(Card card, List<Image> allImages) {
        Image image;
        String cardType = card.getCardType();
        switch (cardType) {
            case "VampireCastleCard":
                image = allImages.get(0);
                return image;
            case "ZombiePitCard":
                image = allImages.get(1);
                return image;
            case "TowerCard":
                image = allImages.get(2);
                return image;
            case "VillageCard":
                image = allImages.get(3);
                return image;
            case "BarracksCard":
                image = allImages.get(4);
                return image;
            case "TrapCard":
                image = allImages.get(5);
                return image;
            case "CampfireCard":
                image = allImages.get(6);
                return image;
        }
        return null;
    }

    public Image getImage(Building building, List<Image> allImages) {
        Image image;
        String buildingType = building.getType();
        switch (buildingType) {
            case "VampireCastle":
                image = allImages.get(0);
                return image;
            case "ZombiePit":
                image = allImages.get(1);
                return image;
            case "Tower":
                image = allImages.get(2);
                return image;
            case "Village":
                image = allImages.get(3);
                return image;
            case "Barracks":
                image = allImages.get(4);
                return image;
            case "Trap":
                image = allImages.get(5);
                return image;
            case "Campfire":
                image = allImages.get(6);
                return image;
        }
        return null;
    }

    public Image getImage(Item item, List<Image> allImages) {
        Image image;
        String itemType = item.getType();
        switch (itemType) {
            case "Sword":
                image = allImages.get(0);
                return image;
            case "Stake":
                image = allImages.get(1);
                return image;
            case "Staff":
                image = allImages.get(2);
                return image;
            case "Shield":
                image = allImages.get(3);
                return image;
            case "Armour":
                image = allImages.get(4);
                return image;
            case "Helmet":
                image = allImages.get(5);
                return image;
            case "HealthPotion":
                image = allImages.get(6);
                return image;
            case "OneRing":
                image = allImages.get(7);
                return image;
        }
        return null;
    }

    public Image getImage(Enemy enemy, List<Image> allImages) {
        Image image;
        String enemyType = enemy.getType();
        switch (enemyType) {
            case "Slug":
                image = allImages.get(0);
                return image;
            case "Zombie":
                image = allImages.get(1);
                return image;
            case "Vampire":
                image = allImages.get(2);
                return image;
        }
        return null;
    }
}
