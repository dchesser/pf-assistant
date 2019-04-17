package FXMLControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Written by David Hagerty
 * Written on 2019-04-17
 */
public class CharacterPointBuyCreationController {

    public CharacterPointBuyCreationController() {
        // empty because
    }

    public void setCharacter(CharacterCreationSB.CreationMethod method)
    {
        int spinnerMinimum = 0;
        int spinnerMaximum = 0;
        int spinnerStart = 10;

        switch (method) {
            case POINT_BUY:
                scoreRolls = pointBuyScores();
                spinnerMinimum = 7;
                spinnerMaximum = 18;
                break;
            case CUSTOM:
                scoreRolls = pointBuyScores();
                spinnerMinimum = 0;
                spinnerMaximum = 20;
                break;
        }

        pcNameField.setText(playerCharacter.getName());
        pcAlignmentField.setText("N (default)");
        pcDeityField.setText("Some Popular God(ess) (default)");
        pcGenderField.setText("Person (default)");
        pcSizeField.setText("M (default)");
        pcHeightField.setText("1'1\" (default)");
        pcWeightField.setText(">1 (default)");
        pcHomelandField.setText("Home (default)");
        pcHairColorField.setText("Brown-ish (default)");
        pcEyeColorField.setText("Brown (default)");
        pcRaceField.setText("Human (default)");
        pcAgeField.setText(">1 (default)");
        OtherMoneyField.setText("Nothing (default)");

        // set up spinners
        pcStrScoreSpinner.setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerStart, buyPool));
        pcDexScoreSpinner.setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerStart, buyPool));
        pcConScoreSpinner.setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerStart, buyPool));
        pcIntScoreSpinner.setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerStart, buyPool));
        pcWisScoreSpinner.setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerStart, buyPool));
        pcChaScoreSpinner.setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerStart, buyPool));

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

        // Setup the new SkillSet
        HashMap<PlayerSkill.GameSkill, PlayerSkill> newSkillSet = new HashMap<>();

        // Read through all of the GridPane's children and figure out wtf is going on
        for(int i = 5; i < skillGridPane.getChildren().size(); i+=4)
        {

            String skillName = ((Button)skillGridPane.getChildren().get(i)).getText();
            boolean isClassSkill = ((CheckBox)skillGridPane.getChildren().get(i+1)).isSelected();
            // totalMod is unnecessary ((Label)skillGridPane.getChildren().get(i+2)).getText();
            int ranks = ((Spinner<Integer>)skillGridPane.getChildren().get(i+3)).getValue();

            if(PlayerSkill.getGameSkill(skillName) != null) {
                PlayerSkill skill = new PlayerSkill(PlayerSkill.getGameSkill(skillName), ranks, isClassSkill);
                newSkillSet.put(PlayerSkill.getGameSkill(skillName), skill);
            }
        }

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
                pcMaxHPSpinner.getValue(),
                newSkillSet
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

    private HashMap<String, Integer> pointBuyScores() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("A", 10);
        result.put("B", 10);
        result.put("C", 10);
        result.put("D", 10);
        result.put("E", 10);
        result.put("F", 10);
        return result;
    }

    private PlayerCharacter playerCharacter = new PlayerCharacter("Default Character");
    private File saveLocation = new File("saves");
    private PointBuyPool buyPool = new PointBuyPool();
    private HashMap<String, Integer> scoreRolls;

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
    private GridPane skillGridPane;

    @FXML
    private MenuBar sbMenuBar;

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
