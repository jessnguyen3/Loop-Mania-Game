package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Shop {

    private int total = 0;

    private LoopManiaWorld world;
    private Character character;
    private Statistics statistics;

    public Shop(LoopManiaWorld world) {
        this.world = world;
        this.character = world.getCharacter();
        this.statistics = character.getStats();
    }

    private int potions;
    private int swords;
    private int stakes;
    private int staffs;
    private int helmets;
    private int armours;
    private int shields;

    private IntegerProperty totalValue = new SimpleIntegerProperty(this, "totalValue");
    private IntegerProperty potionsValue = new SimpleIntegerProperty(this, "potionsValue");
    private IntegerProperty swordsValue = new SimpleIntegerProperty(this, "swordsValue");
    private IntegerProperty stakesValue = new SimpleIntegerProperty(this, "stakesValue");
    private IntegerProperty staffsValue = new SimpleIntegerProperty(this, "staffsValue");
    private IntegerProperty helmetsValue = new SimpleIntegerProperty(this, "helmetsValue");
    private IntegerProperty armoursValue = new SimpleIntegerProperty(this, "armoursValue");
    private IntegerProperty shieldsValue = new SimpleIntegerProperty(this, "shieldsValue");

    public IntegerProperty totalValue() {
        return totalValue;
    }

    public int getTotalValue() {
        return totalValue.get();
    }

    public void setTotalValue(int totalValue) {
        this.totalValue.set(totalValue);
    }

    public IntegerProperty potionsValueProperty() {
        return potionsValue;
    }

    public int getPotionsValue() {
        return potionsValue.get();
    }

    public void setPotionsValue(int potionsValue) {
        this.potionsValue.set(potionsValue);
    }

    public IntegerProperty swordsValueProperty() {
        return swordsValue;
    }

    public int getSwordsValue() {
        return swordsValue.get();
    }

    public void setSwordsValue(int swordsValue) {
        this.swordsValue.set(swordsValue);
    }

    public IntegerProperty stakesValueProperty() {
        return stakesValue;
    }

    public int getStakesValue() {
        return stakesValue.get();
    }

    public void setStakesValue(int stakesValue) {
        this.stakesValue.set(stakesValue);
    }

    public IntegerProperty staffsValueProperty() {
        return staffsValue;
    }

    public int getStaffsValue() {
        return staffsValue.get();
    }

    public void setStaffsValue(int staffsValue) {
        this.staffsValue.set(staffsValue);
    }

    public IntegerProperty helmetsValueProperty() {
        return helmetsValue;
    }

    public int getHelmetsValue() {
        return helmetsValue.get();
    }

    public void setHelmetsValue(int helmetsValue) {
        this.helmetsValue.set(helmetsValue);
    }

    public IntegerProperty armoursValueProperty() {
        return armoursValue;
    }

    public int getArmoursValue() {
        return armoursValue.get();
    }

    public void setArmoursValue(int armoursValue) {
        this.armoursValue.set(armoursValue);
    }

    public IntegerProperty shieldsValueProperty() {
        return shieldsValue;
    }

    public int getShieldsValue() {
        return shieldsValue.get();
    }

    public void setShieldsValue(int shieldsValue) {
        this.shieldsValue.set(shieldsValue);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        setTotalValue(total);
        this.total = total;
    }

    public Shop() {
        this.total = 0;
        this.shopping = false;
        this.potions = 0;
        this.swords = 0;
        this.stakes = 0;
        this.staffs = 0;
        this.helmets = 0;
        this.armours = 0;
        this.shields = 0;
    }

    private boolean shopping;

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public LoopManiaWorld getWorld() {
        return world;
    }

    public void setWorld(LoopManiaWorld world) {
        this.world = world;
    }

    public int getPotions() {
        return potions;
    }

    public void setPotions(int potions) {
        setPotionsValue(potions);
        this.potions = potions;
    }

    public int getSwords() {
        return swords;
    }

    public void setSwords(int swords) {
        setSwordsValue(swords);
        this.swords = swords;
    }

    public int getStakes() {
        return stakes;
    }

    public void setStakes(int stakes) {
        setStakesValue(stakes);
        this.stakes = stakes;
    }

    public int getStaffs() {
        return staffs;
    }

    public void setStaffs(int staffs) {
        setStaffsValue(staffs);
        this.staffs = staffs;
    }

    public int getHelmets() {
        return helmets;
    }

    public void setHelmets(int helmets) {
        setHelmetsValue(helmets);
        this.helmets = helmets;
    }

    public int getArmours() {
        return armours;
    }

    public void setArmours(int armours) {
        setArmoursValue(armours);
        this.armours = armours;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        setShieldsValue(shields);
        this.shields = shields;
    }

    public void addPotion() {
        setTotal(getTotal() + 10);
        setPotions(getPotions() + 1);

    }

    public void subtractPotion() {
        if (getPotions() > 0) {
            setTotal(getTotal() - 10);
            setPotions(getPotions() - 1);
        }
    }

    public void addSword() {
        setTotal(getTotal() + 50);
        setSwords(getSwords() + 1);

    }

    public void subtractSword() {

        if (getSwords() > 0) {
            setTotal(getTotal() - 50);
            setSwords(getSwords() - 1);
        }
    }

    public void addStaff() {

        setTotal(getTotal() + 80);
        setStaffs(getStaffs() + 1);
    }

    public void subtractStaff() {

        if (getStaffs() > 0) {
            setTotal(getTotal() - 80);
            setStaffs(getStaffs() - 1);
        }
    }

    public void addStake() {

        setTotal(getTotal() + 30);
        setStakes(getStakes() + 1);
    }

    public void subtractStake() {

        if (getStakes() > 0) {
            setTotal(getTotal() - 30);
            setStakes(getStakes() - 1);

        }
    }

    public void addHelmet() {

        setTotal(getTotal() + 20);
        setHelmets(getHelmets() + 1);

    }

    public void subtractHelmet() {

        if (getHelmets() > 0) {
            setTotal(getTotal() - 20);
            setHelmets(getHelmets() - 1);
        }
    }

    public void addArmour() {

        setTotal(getTotal() + 70);

        setArmours(getArmours() + 1);
    }

    public void subtractArmour() {

        if (getArmours() > 0) {

            setTotal(getTotal() - 70);
            setArmours(getArmours() - 1);

        }
    }

    public void addShield() {

        setTotal(getTotal() + 40);
        setShields(getShields() + 1);

    }

    public void subtractShield() {

        if (getShields() > 0) {
            setTotal(getTotal() - 70);
            setShields(getShields() - 1);
        }

    }

    /**
     * Determines whether there are sufficient funds to purchase the items requested
     * Return true if there are enough funds, else false.
     * 
     * @param world
     */
    public boolean sufficientFunds(Statistics statistics) {

        // Total of requested items exceeds characters' gold
        if (getTotal() > statistics.getGold()) {
            return false;
        }

        return true;
    }

    /**
     * Handles the finalising of the shop transaction. Decrements each of the
     * requested items from the shop and adds to the character's inventory.
     * Decreases the character's gold.
     * 
     * @param stats
     * @param world
     */
    public void finaliseTransaction(Statistics stats, LoopManiaWorld world) {

        while (getSwords() > 0) {
            world.addUnequippedItem(0);
            setSwords(getSwords() - 1);
        }

        while (getStakes() > 0) {
            world.addUnequippedItem(1);
            setStakes(getStakes() - 1);
        }

        while (getArmours() > 0) {
            world.addUnequippedItem(2);
            setArmours(getArmours() - 1);
        }

        while (getHelmets() > 0) {
            world.addUnequippedItem(3);
            setHelmets(getHelmets() - 1);
        }

        while (getShields() > 0) {
            world.addUnequippedItem(4);
            setShields(getShields() - 1);
        }

        while (getPotions() > 0) {
            world.addUnequippedItem(5);
            setPotions(getPotions() - 1);
        }

        while (getStaffs() > 0) {
            world.addUnequippedItem(6);
            setStaffs(getStaffs() - 1);
        }

        // Decrement the players gold.
        stats.setGold(stats.getGold() - getTotal());

        // Set total back to zero after each transaction
        setTotal(0);

    }
}
