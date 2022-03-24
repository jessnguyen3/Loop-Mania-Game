package unsw.loopmania;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.beans.binding.Bindings;


public class ShopController {
    
    private LoopManiaWorld world;

    private Shop shop;

    private MenuSwitcher gameSwitcher;

    private Character character;


    public ShopController(LoopManiaWorld world) {
        this.world = world;
        this.shop = world.getShop();
        this.character = world.getCharacter();
    }
    
    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    public Label availableGold = new Label(); 


    @FXML
    public Button minusConsumableButton = new Button();
    @FXML
    public Button plusConsumableButton = new Button();
    @FXML
    public TextField consumableNumberField = new TextField();
    
    @FXML
    public Label consumableLabel = new Label();


    @FXML
    public Button minusSwordButton = new Button();
    @FXML
    public Button plusSwordButton = new Button();
    @FXML
    public TextField swordNumberField = new TextField();
    
    @FXML
    public Label swordLabel = new Label();


    @FXML
    public Button minusStakeButton = new Button();
    @FXML
    public Button plusStakeutton = new Button();
    @FXML
    public TextField stakeNumberField = new TextField(); 

    @FXML Label stakeLabel = new Label();

    @FXML
    public Button minusStaffButton = new Button();
    @FXML
    public Button plusStaffButton = new Button();
    @FXML
    public TextField staffNumberField = new TextField(); 

    @FXML
    public Label staffLabel = new Label();

    @FXML
    public Button minusHelmetButton = new Button();
    @FXML
    public Button plusHelmetButton = new Button();
    @FXML
    public TextField helmetNumberField = new TextField(); 

    @FXML
    public Label helmetLabel = new Label();

    @FXML
    public Button minusArmourButton = new Button();
    @FXML
    public Button plusArmourButton = new Button();
    @FXML
    public TextField armourNumberField = new TextField(); 

    @FXML
    public Label armourLabel = new Label();


    @FXML
    public Button minusShieldButton = new Button();
    @FXML
    public Button plusShieldButton = new Button();
    @FXML
    public TextField shieldNumberField = new TextField(); 

    @FXML
    public Label shieldLabel = new Label();


    @FXML 
    public Label totalNumberField = new Label(); 
    @FXML
    public Button purchaseButton = new Button(); 


    @FXML
    public Label notEnoughGold = new Label(); 
    @FXML
    public Label purchaseSuccessful = new Label(); 


    @FXML 
    public Button leaveShopButton = new Button(); 
    




    @FXML 
    public void handleConsumableMinus(ActionEvent event) { 
        shop.subtractPotion();
    }

    @FXML 
    public void handleConsumablePlus(ActionEvent event) { 

        shop.addPotion();
    }


    @FXML 
    public void handleSwordMinus(ActionEvent event) {

        shop.subtractSword();
    }

    @FXML 
    public void handleSwordPlus(ActionEvent event) {

        shop.addSword();
    }

    @FXML 
    public void handleStakeMinus(ActionEvent event) { 

        shop.subtractStake();
    }

    @FXML 
    public void handleStakePlus(ActionEvent event) { 

        shop.addStake();
    }

    @FXML 
    public void handleStaffMinus(ActionEvent event) { 

        shop.subtractStaff();
    }

    @FXML 
    public void handleStaffPlus(ActionEvent event) { 

        shop.addStaff();
    }

    @FXML 
    public void handleHelmetMinus(ActionEvent event) { 

        shop.subtractHelmet();
    }

    @FXML 
    public void handleHelmetPlus(ActionEvent event) { 

        shop.addHelmet();
    }

    @FXML 
    public void handleArmourMinus(ActionEvent event) { 

        shop.subtractArmour();
    }

    @FXML 
    public void handleArmourPlus(ActionEvent event) { 

        shop.addArmour();
    }

    @FXML 
    public void handleShieldMinus(ActionEvent event) { 

        shop.subtractShield();
    }

    @FXML 
    public void handleShieldPlus(ActionEvent event) { 

        shop.addShield();
    }

     /**
    * Display whether the purchase is successful or if there is not enough gold 
    * to complete the transaction after pressing the purchase button. 
    * @param event
    */
    @FXML
    public void handlePurchase(ActionEvent event) {

        if (shop.sufficientFunds(character.getStats())) {
            shop.finaliseTransaction(character.getStats(), world);
            notEnoughGold.setVisible(false);
            purchaseSuccessful.setVisible(true);
        }        

        else {

            purchaseSuccessful.setVisible(false);
            notEnoughGold.setVisible(true);
        }

    }

   

    @FXML
    public void handleLeaveShop(){
        gameSwitcher.switchMenu();

        // Set both labels to not visible whenever the shop is left. 
        notEnoughGold.setVisible(false);
        purchaseSuccessful.setVisible(false);

        shop.setShopping(false);
    }


    @FXML
    public void initialize() {

        totalNumberField.setText("0");
        availableGold.setText("0");

        
        availableGold.textProperty().bind(Bindings.convert(character.getStats().goldValueProperty()));
        totalNumberField.textProperty().bind(Bindings.convert(shop.totalValue()));

        consumableLabel.textProperty().bind(Bindings.convert(shop.potionsValueProperty()));
        swordLabel.textProperty().bind(Bindings.convert(shop.swordsValueProperty()));
        stakeLabel.textProperty().bind(Bindings.convert(shop.stakesValueProperty()));
        staffLabel.textProperty().bind(Bindings.convert(shop.staffsValueProperty()));
        helmetLabel.textProperty().bind(Bindings.convert(shop.helmetsValueProperty()));
        armourLabel.textProperty().bind(Bindings.convert(shop.armoursValueProperty()));
        shieldLabel.textProperty().bind(Bindings.convert(shop.shieldsValueProperty()));

    }



}
