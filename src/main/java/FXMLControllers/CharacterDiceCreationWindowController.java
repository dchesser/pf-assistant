package FXMLControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.AbilityScores;
import network.cardboard.crystallogic.Die;
import network.cardboard.crystallogic.PlayerCharacter;
import network.cardboard.crystallogic.PlayerSkill;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Written by David Hagerty
 * Written on 2019-04-17
 */
public class CharacterDiceCreationWindowController {

    public CharacterDiceCreationWindowController() {
        // empty because reasons
    }

    public void setCharacter(CharacterCreationSB.CreationMethod method)
    {
        switch (method) {
            case HEROIC:
                scoreRolls = rollHeroicAbilityScores();
                break;
            case MODERN:
                scoreRolls = rollModernAbilityScores();
                break;
            case CLASSIC:
                scoreRolls = rollClassicAbilityScores();
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

        scoreRollText.setText(" " + scoreRolls);

        errorMessage.setText(" ");

        // set up spinners
        pcStrScoreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(scoreOptions));
        pcDexScoreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(scoreOptions));
        pcConScoreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(scoreOptions));
        pcIntScoreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(scoreOptions));
        pcWisScoreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(scoreOptions));
        pcChaScoreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(scoreOptions));

        pcStrScoreSpinner.getValueFactory().setWrapAround(true);
        pcDexScoreSpinner.getValueFactory().setWrapAround(true);
        pcConScoreSpinner.getValueFactory().setWrapAround(true);
        pcIntScoreSpinner.getValueFactory().setWrapAround(true);
        pcWisScoreSpinner.getValueFactory().setWrapAround(true);
        pcChaScoreSpinner.getValueFactory().setWrapAround(true);

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
                scoreRolls.get(pcStrScoreSpinner.getValue()),
                scoreRolls.get(pcDexScoreSpinner.getValue()),
                scoreRolls.get(pcConScoreSpinner.getValue()),
                scoreRolls.get(pcIntScoreSpinner.getValue()),
                scoreRolls.get(pcWisScoreSpinner.getValue()),
                scoreRolls.get(pcChaScoreSpinner.getValue())
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

    private HashMap<String, Integer> rollClassicAbilityScores() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("A", rollClassic());
        result.put("B", rollClassic());
        result.put("C", rollClassic());
        result.put("D", rollClassic());
        result.put("E", rollClassic());
        result.put("F", rollClassic());
        return result;
    }

    private HashMap<String, Integer> rollModernAbilityScores() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("A", rollModern());
        result.put("B", rollModern());
        result.put("C", rollModern());
        result.put("D", rollModern());
        result.put("E", rollModern());
        result.put("F", rollModern());
        return result;
    }

    private HashMap<String, Integer> rollHeroicAbilityScores() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("A", rollHeroic());
        result.put("B", rollHeroic());
        result.put("C", rollHeroic());
        result.put("D", rollHeroic());
        result.put("E", rollHeroic());
        result.put("F", rollHeroic());
        return result;
    }

    private int rollClassic() {
        System.out.println("rollClassic method");
        int roll1 = Die.d6.roll();
        int roll2 = Die.d6.roll();
        int roll3 = Die.d6.roll();
        System.out.println("roll 1: " + roll1 + ", roll 2: " + roll2 + ", roll 3: " + roll3);
        int sum = roll1 + roll2 + roll3;
        System.out.println("Sum: " + sum);
        return sum;
    }

    private int rollModern() {
        System.out.println("rollModern method");
        ArrayList<Integer> rolls = new ArrayList<>();
        int roll1 = Die.d6.roll();
        int roll2 = Die.d6.roll();
        int roll3 = Die.d6.roll();
        int roll4 = Die.d6.roll();
        System.out.println("roll 1: " + roll1 + ", roll 2: " + roll2 + ", roll 3: " + roll3 + ", roll 4: " + roll4);
        rolls.add(roll1);
        rolls.add(roll2);
        rolls.add(roll3);
        rolls.add(roll4);

        Integer lowest = rolls.get(0);
        for (int roll : rolls) {
            if (roll < lowest) {
                lowest = roll;
            }
        }
        System.out.println("Lowest roll: " + lowest);
        rolls.remove(lowest);

        int sum = 0;
        for (int roll : rolls) {
            sum += roll;
        }
        System.out.println("Sum: " + sum);
        return sum;
    }

    private int rollHeroic() {
        System.out.println("rollHeroic Method");
        int roll1 = Die.d6.roll();
        int roll2 = Die.d6.roll();
        System.out.println("roll 1: " + roll1 + ", roll 2: " + roll2);
        int sum = 6 + roll1 + roll2;
        System.out.println("sum of rolls: " + sum);
        return sum;
    }

    private boolean validAbilityScores() {
        String strength = pcStrScoreSpinner.getValue();
        String dexterity = pcDexScoreSpinner.getValue();
        String intelligence = pcIntScoreSpinner.getValue();
        String constitution = pcConScoreSpinner.getValue();
        String wisdom = pcWisScoreSpinner.getValue();
        String charisma = pcChaScoreSpinner.getValue();

        boolean sameAsStr = strength.equalsIgnoreCase(dexterity) && strength.equalsIgnoreCase(constitution) && strength.equalsIgnoreCase(intelligence) && strength.equalsIgnoreCase(wisdom) && strength.equalsIgnoreCase(charisma);
        boolean sameAsDex = dexterity.equalsIgnoreCase(constitution) && dexterity.equalsIgnoreCase(intelligence) && dexterity.equalsIgnoreCase(wisdom) && dexterity.equalsIgnoreCase(charisma);
        boolean sameAsCon = constitution.equalsIgnoreCase(intelligence) && constitution.equalsIgnoreCase(wisdom) && constitution.equalsIgnoreCase(charisma);
        boolean sameAsInt = intelligence.equalsIgnoreCase(wisdom) && intelligence.equalsIgnoreCase(charisma);
        boolean sameAsWis = wisdom.equalsIgnoreCase(charisma);

        return !(sameAsStr || sameAsDex || sameAsCon || sameAsInt || sameAsWis);
    }

    private void updateError() {
        if (errorMessage.getText().isEmpty())
            errorMessage.setText("Make sure your abilities all have unique score options");
    }

    private PlayerCharacter playerCharacter = new PlayerCharacter("Default Character");
    private File saveLocation = new File("saves");
    private ObservableList<String> scoreOptions = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F");

    @FXML
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
    private Spinner<String> pcStrScoreSpinner;

    @FXML
    private Spinner<String> pcIntScoreSpinner;

    @FXML
    private Spinner<String> pcDexScoreSpinner;

    @FXML
    private Spinner<String> pcWisScoreSpinner;

    @FXML
    private Spinner<String> pcConScoreSpinner;

    @FXML
    private Spinner<String> pcChaScoreSpinner;

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

    @FXML
    private Label scoreRollText;

    @FXML
    private Label errorMessage;


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
            if (validAbilityScores()) {
                saveFile();
            } else {
                updateError();
            }
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
            if (validAbilityScores()) {
                saveFile();
            } else {
                updateError();
            }
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
