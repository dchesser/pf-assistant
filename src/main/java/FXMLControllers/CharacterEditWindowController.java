package FXMLControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.AbilityScores;
import network.cardboard.crystallogic.PlayerCharacter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CharacterEditWindowController
{
    // General Variables
    private PlayerCharacter playerCharacter;
    private File saveLocation;

    // FXML Field Variables
    @FXML
    private TextField pcNameField;

    @FXML
    private TextField pcAlignmentField;

    @FXML
    private TextField pcRaceField;

    @FXML
    private TextField pcDeityField;

    @FXML
    private TextField pcHeightField;

    @FXML
    private TextField pcWeightField;

    @FXML
    private TextField pcHomelandField;

    @FXML
    private TextField pcHairColorField;

    @FXML
    private TextField pcEyeColorField;

    @FXML
    private TextField pcGenderField;

    @FXML
    private TextField pcAgeField;

    @FXML
    private TextField pcSizeField;

    @FXML
    private Spinner<Integer> pcStrScoreSpinner;

    @FXML
    private Spinner<Integer> pcIntScoreSpinner;

    @FXML
    private Spinner<Integer> pcDexScoreSpinner;

    @FXML
    private Spinner<Integer> pcWisScoreSpinner;

    @FXML
    private Spinner<Integer> pcConScoreSpinner;

    @FXML
    private Spinner<Integer> pcChaScoreSpinner;

    @FXML
    private Spinner<Integer> PPField;

    @FXML
    private Spinner<Integer> GPField;

    @FXML
    private Spinner<Integer> SPField;

    @FXML
    private Spinner<Integer> CPField;

    @FXML
    private TextField OtherMoneyField;

    @FXML
    private Spinner<Integer> pcCurrentHPSpinner;

    @FXML
    private Spinner<Integer> pcMaxHPSpinner;

    @FXML
    private MenuBar sbMenuBar;

    // General Methods
    public CharacterEditWindowController()
    {
        //Runs automatically before the player character can be set, so it's not that useful
    }

    public void setCharacter(PlayerCharacter pc)
    {
        playerCharacter = pc;

        // Lots and lots of null checks to see if I can even load the data (makes things more reverse compatible save-wise(
        if(pc.getName() != null) {
            pcNameField.setText(pc.getName());
        } else {
            pcNameField.setText("Default Character");
        }

        if(pc.abilityScores != null) {
            pcStrScoreSpinner.increment(pc.abilityScores.strength.getValue());

            pcDexScoreSpinner.increment(pc.abilityScores.dexterity.getValue());

            pcConScoreSpinner.increment(pc.abilityScores.constitution.getValue());

            pcIntScoreSpinner.increment(pc.abilityScores.intelligence.getValue());

            pcWisScoreSpinner.increment(pc.abilityScores.wisdom.getValue());

            pcChaScoreSpinner.increment(pc.abilityScores.charisma.getValue());
        } else {
            pcStrScoreSpinner.increment(10);

            pcDexScoreSpinner.increment(10);

            pcConScoreSpinner.increment(10);

            pcIntScoreSpinner.increment(10);

            pcWisScoreSpinner.increment(10);

            pcChaScoreSpinner.increment(10);
        }

        if(pc.getAlignment() != null) {
            pcAlignmentField.setText(pc.getAlignment());
        } else {
            pcAlignmentField.setText("N (default)");
        }

        if(pc.getDeity() != null) {
            pcDeityField.setText(pc.getDeity());
        } else {
            pcDeityField.setText("Some Popular God(ess) (default)");
        }

        if(pc.getGender() != null) {
            pcGenderField.setText(pc.getGender());
        } else {
            pcGenderField.setText("Person (default)");
        }

        if(pc.getSize() != null) {
            pcSizeField.setText(pc.getSize());
        } else {
            pcSizeField.setText("M (default)");
        }

        if(pc.getHeight() != null) {
            pcHeightField.setText(pc.getHeight());
        } else {
            pcHeightField.setText("1'1\" (default)");
        }

        if(pc.getWeight() != null) {
            pcWeightField.setText(pc.getWeight());
        } else {
            pcWeightField.setText(">1 (default)");
        }

        if(pc.getHomeland() != null) {
            pcHomelandField.setText(pc.getHomeland());
        } else {
            pcHomelandField.setText("Home (default)");
        }

        if(pc.getHairColor() != null) {
            pcHairColorField.setText(pc.getHairColor());
        } else {
            pcHairColorField.setText("Brown-ish (default)");
        }

        if(pc.getEyeColor() != null) {
            pcEyeColorField.setText(pc.getEyeColor());
        } else {
            pcEyeColorField.setText("Brown (default)");
        }

        if(pc.getRace() != null) {
            pcRaceField.setText(pc.getRace());
        } else {
            pcRaceField.setText("Human (default)");
        }

        if(pc.getAge() != null) {
            pcAgeField.setText(pc.getAge());
        } else {
            pcAgeField.setText(">1 (default)");
        }

        // These default to 0 anyways.
        if(pc.getPlatinumCoins() != null) {
            PPField.increment(pc.getPlatinumCoins());
        }

        if(pc.getGoldCoins() != null) {
            GPField.increment(pc.getGoldCoins());
        }

        if(pc.getSilverCoins() != null) {
            SPField.increment(pc.getSilverCoins());
        }

        if(pc.getCopperCoins() != null) {
            CPField.increment(pc.getCopperCoins());
        }

        if(pc.getOtherValuables() != null) {
            OtherMoneyField.setText(pc.getOtherValuables());
        } else {
            OtherMoneyField.setText("Nothing (default)");
        }

        if(pc.getCurrHealth() != null) {
            pcCurrentHPSpinner.increment(pc.getCurrHealth());
        }

        if(pc.getMaxHealth() != null) {
            pcMaxHPSpinner.increment(pc.getMaxHealth());
        }

        sbMenuBar.setUseSystemMenuBar(true);
    }

    public void setSaveLocation(File saveLocation)
    {
        this.saveLocation = saveLocation;
    }

    /**
     * @TODO Expand this file as we add more things to the PlayerCharacter class!  It is very short on data right now!
     * The purpose of this method is to take the contents of the window and prepare them into a PlayerCharacter object
     * It returns the new PlayerCharacter object that has been prepared for saving
     * @return the updated PlayerCharacter object.
     */
    private void updateCharacterForSave() {

        AbilityScores newAbilities = new AbilityScores(
                pcStrScoreSpinner.getValue(),
                pcDexScoreSpinner.getValue(),
                pcConScoreSpinner.getValue(),
                pcIntScoreSpinner.getValue(),
                pcWisScoreSpinner.getValue(),
                pcChaScoreSpinner.getValue()
        );

        playerCharacter = new PlayerCharacter(pcNameField.getText(),
                newAbilities,
                pcAlignmentField.getText(),
                pcRaceField.getText(),
                pcDeityField.getText(),
                pcHeightField.getText(),
                pcWeightField.getText(),
                pcHomelandField.getText(),
                pcHairColorField.getText(),
                pcEyeColorField.getText(),
                pcGenderField.getText(),
                pcAgeField.getText(),
                pcSizeField.getText(),
                PPField.getValue(),
                GPField.getValue(),
                SPField.getValue(),
                CPField.getValue(),
                OtherMoneyField.getText(),
                pcCurrentHPSpinner.getValue(),
                pcMaxHPSpinner.getValue()
        );


    }

    /**
     * This method is used to update the saveLocation for use in SaveAs as well as first time Saves
     */
    private void updateSaveLocation() {
        FileChooser folderSelector = new FileChooser();
        folderSelector.setTitle("Select Save Location");
        File defaultDirectory = new File("saves" + File.separator);
        folderSelector.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON files (*.json)","*.json")
        );
        folderSelector.setInitialDirectory(defaultDirectory);

        Stage stage = new Stage();
        File tempSaveLocation = folderSelector.showSaveDialog(stage);

        if(tempSaveLocation != null) {
            saveLocation = tempSaveLocation;
        } else {
            System.out.println("Save Canceled.");
        }

    }

    /**
     * This method will save the character to the save location
     * @TODO Probably need better naming for methods
     * @TODO Refactor saving functionality into an interface the PlayerCharacter class implements
     */
    private void saveFile() {
        try{
            updateCharacterForSave();

            PrintWriter pw = new PrintWriter(saveLocation);

            // The JSON file is created in the Character object's toString
            pw.print(playerCharacter.toString());
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // FXML Actions
    @FXML
    void openRollWindowAction(ActionEvent event) {
        System.out.println("Open Roll Window Request Recieved.");
    }

    @FXML
    void rollD20Action(ActionEvent event) {
        System.out.println("Request to roll a d20 recieved.");
    }

    /**
     * This method is used to save the currently open character.
     * It will, by default, remember where you last saved, or loaded from,
     * and save to that location.
     */
    @FXML
    void saveCharAction(ActionEvent event) {
        // Check the existence of the default save directory
        File saveDirectory = new File("saves");
        if(!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }

        // If there isn't a defined saveLocation, get one.
        if(saveLocation == null) {
            updateSaveLocation();
        }

        if(saveLocation != null) {
            // Now just save to save Location
            saveFile();
        } else {
            System.out.println("Cancel Save Successful.");
        }
    }

    /**
     * This method is used to save the currently open character under a name that you specify.
     */
    @FXML
    void saveCharAsAction(ActionEvent event) {
        // Check the existence of the default save directory
        File saveDirectory = new File("saves");
        if(!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }

        updateSaveLocation();

        if(saveLocation != null) {
            // Now just save to save Location
            saveFile();
        } else {
            System.out.println("Cancel Save Successful.");
        }
    }

    @FXML
    void refillHPMax(ActionEvent event) {
        int difference = pcMaxHPSpinner.getValue() - pcCurrentHPSpinner.getValue();
        pcCurrentHPSpinner.increment(difference);

        System.out.println(event.getEventType());
    }
}
