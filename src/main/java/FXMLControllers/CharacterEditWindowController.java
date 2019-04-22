package FXMLControllers;

import NonFXMLWindows.DieRollWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
import java.util.Map;

public class CharacterEditWindowController
{
    // General Variables
    private PlayerCharacter playerCharacter;
    private File saveLocation;

    // Display Assisting variables
    private ArrayList<SkillDisplay> strengthSkills;
    private ArrayList<SkillDisplay> dexteritySkills;
    private ArrayList<SkillDisplay> constitutionSkills;
    private ArrayList<SkillDisplay> intelligenceSkills;
    private ArrayList<SkillDisplay> wisdomSkills;
    private ArrayList<SkillDisplay> charismaSkills;


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

    // General Methods
    public CharacterEditWindowController()
    {
        //Runs automatically before the player character can be set, so it's not that useful
    }

    public void setCharacter(PlayerCharacter pc)
    {
        playerCharacter = pc;

        sbMenuBar.setUseSystemMenuBar(true);

        strengthSkills = new ArrayList<>();
        dexteritySkills = new ArrayList<>();
        constitutionSkills = new ArrayList<>();
        intelligenceSkills = new ArrayList<>();
        wisdomSkills = new ArrayList<>();
        charismaSkills = new ArrayList<>();

        // Lots and lots of null checks to see if I can even load the data (makes things more reverse compatible save-wise(
        if(pc.getName() != null) {
            pcNameField.setText(pc.getName());
        } else {
            pcNameField.setText("Default Character");
        }

        // Set the ability scores to their correct values if there are any
        if(pc.abilityScores != null) {
            pcStrScoreSpinner.increment(pc.abilityScores.strength.getValue());

            pcDexScoreSpinner.increment(pc.abilityScores.dexterity.getValue());

            pcConScoreSpinner.increment(pc.abilityScores.constitution.getValue());

            pcIntScoreSpinner.increment(pc.abilityScores.intelligence.getValue());

            pcWisScoreSpinner.increment(pc.abilityScores.wisdom.getValue());

            pcChaScoreSpinner.increment(pc.abilityScores.charisma.getValue());
        } else { // Otherwise set the defaults (10)
            pcStrScoreSpinner.increment(10);

            pcDexScoreSpinner.increment(10);

            pcConScoreSpinner.increment(10);

            pcIntScoreSpinner.increment(10);

            pcWisScoreSpinner.increment(10);

            pcChaScoreSpinner.increment(10);
        }

        // Set the onUpdate for the spinners so that they update the skill rows.
        pcStrScoreSpinner.valueProperty().addListener(
                observable -> {
                    updateAll(AbilityScores.Ability.STR);
                }
        );
        pcDexScoreSpinner.valueProperty().addListener(
                observable -> {
                    updateAll(AbilityScores.Ability.DEX);
                }
        );
        pcConScoreSpinner.valueProperty().addListener(
                observable -> {
                    updateAll(AbilityScores.Ability.CON);
                }
        );
        pcIntScoreSpinner.valueProperty().addListener(
                observable -> {
                    updateAll(AbilityScores.Ability.INT);
                }
        );
        pcWisScoreSpinner.valueProperty().addListener(
                observable -> {
                    updateAll(AbilityScores.Ability.WIS);
                }
        );
        pcChaScoreSpinner.valueProperty().addListener(
                observable -> {
                    updateAll(AbilityScores.Ability.CHA);
                }
        );

        // Detect these traits for nulls, and set to default if they are null.
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

        // Not 0 default, but still in the valuables section.
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


        // Retrieve and setup Skills from the player for display
        int currRowCount = 1;
        for(Map.Entry<PlayerSkill.GameSkill, PlayerSkill> entry : pc.getSkillSet().entrySet())
        {
            // add a skill rank display row for the given name and rank.
            addSkillDisplayRow(entry, currRowCount);
            currRowCount++;
        }
    }

    /**
     * Method: updateAll(Ability)
     * This method causes all skills of the designated ability to be updated on their displays.
     * Most often due to a change in an ability score associated with the skill.
     * @param ability - The ability type to be updated.
     */
    private void updateAll(AbilityScores.Ability ability)
    {
        if(ability.equals(AbilityScores.Ability.STR))
        {
            for(SkillDisplay skill : strengthSkills)
            {
                if(skill.classSkill.isSelected() && skill.ranks.getValue() > 0) {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + 3 + ( pcStrScoreSpinner.getValue() / 2 ) - 5 ) );
                }
                else {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + ( pcStrScoreSpinner.getValue() / 2 ) - 5 ) );
                }
            }
        }
        else if(ability.equals(AbilityScores.Ability.DEX))
        {
            for(SkillDisplay skill : dexteritySkills)
            {
                if(skill.classSkill.isSelected() && skill.ranks.getValue() > 0) {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + 3 + ( pcDexScoreSpinner.getValue() / 2 ) - 5 ) );
                }
                else {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + ( pcDexScoreSpinner.getValue() / 2 ) - 5 ) );
                }
            }
        }
        else if(ability.equals(AbilityScores.Ability.CON))
        {
            for(SkillDisplay skill : constitutionSkills)
            {
                if(skill.classSkill.isSelected() && skill.ranks.getValue() > 0) {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + 3 + ( pcConScoreSpinner.getValue() / 2 ) - 5 ) );
                }
                else {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + ( pcConScoreSpinner.getValue() / 2 ) - 5 ) );
                }
            }
        }
        else if(ability.equals(AbilityScores.Ability.INT))
        {
            for(SkillDisplay skill : intelligenceSkills)
            {
                if(skill.classSkill.isSelected() && skill.ranks.getValue() > 0) {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + 3 + ( pcIntScoreSpinner.getValue() / 2 ) - 5 ) );
                }
                else {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + ( pcIntScoreSpinner.getValue() / 2 ) - 5 ) );
                }
            }
        }
        else if(ability.equals(AbilityScores.Ability.WIS))
        {
            for(SkillDisplay skill : wisdomSkills)
            {
                if(skill.classSkill.isSelected() && skill.ranks.getValue() > 0) {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + 3 + ( pcWisScoreSpinner.getValue() / 2 ) - 5 ) );
                }
                else {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + ( pcWisScoreSpinner.getValue() / 2 ) - 5 ) );
                }
            }
        }
        else {
            for(SkillDisplay skill : charismaSkills)
            {
                if(skill.classSkill.isSelected() && skill.ranks.getValue() > 0) {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + 3 + ( pcChaScoreSpinner.getValue() / 2 ) - 5 ) );
                }
                else {
                    skill.modDisplay.setText("" + ( skill.ranks.getValue() + ( pcChaScoreSpinner.getValue() / 2 ) - 5 ) );
                }
            }
        }
    }

    /** @TODO Possibly relocate to a new object?  Also finish all parts' functions.
     * Method: addSkillDisplayRow()
     * This method adds a row to the GridPane that is used to display Player Skills.
     * It is required to generate these programmatically,
     * as we need them to each have unique functions based on their assigned skills.
     */
    private void addSkillDisplayRow(Map.Entry<PlayerSkill.GameSkill, PlayerSkill> skill, int currRowCount)
    {
        // Build the new row's contents (the row will be added at the end in case something errors)

        // Total Mod
        Label totalMod = new Label("Default Text, it broke?");
        // retrieve the total modifier
        totalMod.setText(playerCharacter.getTotalSkillBonus(skill.getKey()) + "");


        // Build the spinner for the ranks of the skill
        Spinner<Integer> ranks = new Spinner<>();

        // Define the values allowed in spinners using this factory
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);

        // add a converter to allow people to edit the spinner directly
        // Do I need this?

        // set the factory and increment to the current player ranks.
        // current ranks SHOULD default to 0 if there are none, leaving the default at 0
        ranks.setValueFactory(valueFactory);
        ranks.increment(skill.getValue().getRanks());


        // Add  the class skill checkbox
        CheckBox classSkillCB = new CheckBox();
        classSkillCB.setSelected(skill.getValue().isClassSkill()); //@TODO Figure out wtf is going wrong with this line.


        // Button for opening the roll window.
        Button rollSkillBtn = new Button(skill.getKey().getName());
        rollSkillBtn.setMinWidth(100);

        rollSkillBtn.setOnAction(event ->
        {
            new DieRollWindow(Die.d20, 1, 0,Integer.parseInt(totalMod.getText()));
        });

        // Create the listeners for the objects
        // Set the function that occurs on update of the spinner
        ranks.valueProperty().addListener(((observable, oldValue, newValue) ->
        {
            totalMod.setText("" +
                    getTotalMod(ranks.getValue(),
                            classSkillCB.isSelected(),
                            skill.getKey().basedOnAbility()
                    )
            );
            /*
            if(classSkillCB.isSelected())
            {
                if(oldValue == 0 && newValue > 0)
                {
                    totalMod.setText("" + (Integer.parseInt(totalMod.getText()) + 3 + newValue));
                }
                else if(newValue == 0 && oldValue > 0)
                {
                    totalMod.setText("" + (Integer.parseInt(totalMod.getText()) - 3 - oldValue));
                }
                else
                {
                    totalMod.setText("" + (Integer.parseInt(totalMod.getText()) + (newValue - oldValue)));
                }
            }
            else {
                totalMod.setText("" + (Integer.parseInt(totalMod.getText()) + (newValue - oldValue)));
            }
            */
        }));

        classSkillCB.setOnAction(event ->
        {
            totalMod.setText("" + getTotalMod(ranks.getValue(), classSkillCB.isSelected(), skill.getKey().basedOnAbility()));
            /*
            if(ranks.getValue() > 0) // Class bonus only applies if you have at least one rank
            {
                if(classSkillCB.isSelected())
                {
                    totalMod.setText("" + (Integer.parseInt(totalMod.getText()) + 3));
                }
                else {
                    totalMod.setText("" + (Integer.parseInt(totalMod.getText()) - 3));
                }
            }
            */

        });

        saveSkillDisplay(
                new SkillDisplay(totalMod, ranks, classSkillCB),
                skill.getKey().basedOnAbility()
        );
        // add onto the GridPane
        skillGridPane.addRow(currRowCount, rollSkillBtn, classSkillCB, totalMod, ranks);
    }

    /**
     * Method: saveSkillDisplay(SkillDisplay, Ability)
     * This method is used to save the display controls of each skill to their respective lists for updating.
     * This is primarily for the use with the Ability Score spinners update functions to interact with the
     * dynamically created skill display rows.
     * @param display - The object storing references to the display controls
     * @param ability - The associated ability for the skill being saved.
     */
    private void saveSkillDisplay(SkillDisplay display, AbilityScores.Ability ability) 
    {
        if(ability.equals(AbilityScores.Ability.STR))
        {
            strengthSkills.add(display);
        }
        else if(ability.equals(AbilityScores.Ability.DEX))
        {
            dexteritySkills.add(display);
        }
        else if(ability.equals(AbilityScores.Ability.CON))
        {
            constitutionSkills.add(display);
        }
        else if(ability.equals(AbilityScores.Ability.INT))
        {
            intelligenceSkills.add(display);
        }
        else if(ability.equals(AbilityScores.Ability.WIS))
        {
            wisdomSkills.add(display);
        }
        else
        {
            charismaSkills.add(display);
        }
    }

    /**
     * Method: getTotalMod()
     * This method retrieves the totalMod value for the purposes of calculating skills.
     * It requires the identifier for the score involved with the calculation.
     * @return - int TotalMod - This is the total calculated modifier for the skill selected.
     */
    private int getTotalMod(int ranks, boolean classSkill, AbilityScores.Ability basedOn)
    {
        if(basedOn.equals(AbilityScores.Ability.STR))
        {
            if(ranks > 0 && classSkill)
            {
                return ranks + 3 + ((pcStrScoreSpinner.getValue()/2)-5);
            }
            else {
                return ranks + ((pcStrScoreSpinner.getValue()/2)-5);
            }
        }
        else if(basedOn.equals(AbilityScores.Ability.DEX))
        {
            if(ranks > 0 && classSkill)
            {
                return ranks + 3 + ((pcDexScoreSpinner.getValue()/2)-5);
            }
            else {
                return ranks + ((pcDexScoreSpinner.getValue()/2)-5);
            }
        }
        else if(basedOn.equals(AbilityScores.Ability.CON))
        {
            if(ranks > 0 && classSkill)
            {
                return ranks + 3 + ((pcConScoreSpinner.getValue()/2)-5);
            }
            else {
                return ranks + ((pcConScoreSpinner.getValue()/2)-5);
            }
        }
        else if(basedOn.equals(AbilityScores.Ability.INT))
        {
            if(ranks > 0 && classSkill)
            {
                return ranks + 3 + ((pcIntScoreSpinner.getValue()/2)-5);
            }
            else {
                return ranks + ((pcIntScoreSpinner.getValue()/2)-5);
            }
        }
        else if(basedOn.equals(AbilityScores.Ability.WIS))
        {
            if(ranks > 0 && classSkill)
            {
                return ranks + 3 + ((pcWisScoreSpinner.getValue()/2)-5);
            }
            else {
                return ranks + ((pcWisScoreSpinner.getValue()/2)-5);
            }
        }
        else
        {
            if(ranks > 0 && classSkill)
            {
                return ranks + 3 + ((pcChaScoreSpinner.getValue()/2)-5);
            }
            else {
                return ranks + ((pcChaScoreSpinner.getValue()/2)-5);
            }
        }
    }

    /**
     * Method: setSaveLocation(File)
     * This method sets the file location where the program is meant to save the character.
     * It is necessary so that the save vs save as functions can maintain their proper function.
     * @param saveLocation - The location that the program is meant to save to.
     */
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

        // Create the new player with the given information.
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

    /**
     * Class: SkillDisplay
     * This sub-class is meant to handle interactions from the hard-coded display to the
     * dynamically generated Skills list.  As the objects in question do not have a permanent name that is
     * reference-able outside of the specific instance that they are created, this object is necessary for outside reference
     */
    private class SkillDisplay
    {
        // The references to the display methods, which hopefully stay up to date
        private Label modDisplay;
        private Spinner<Integer> ranks;
        private CheckBox classSkill;

        /**
         * Constructor:
         * This method will create the SkillDisplay object using the required Label that displays
         * the total, the spinner for the ranks, and the checkbox for class skill status.
         * @param modDisplay - The label that displays the total mod of the skill
         * @param ranks - The spinner that contains the number of ranks that a particular skill has
         * @param classSkill - The checkbox that determines whether or not a skill is a class skill
         */
        public SkillDisplay(Label modDisplay, Spinner<Integer> ranks, CheckBox classSkill)
        {
            this.classSkill = classSkill;
            this.modDisplay = modDisplay;
            this.ranks = ranks;
        }

        /**
         * Method: updateDisplay(int)
         * This method updates the modDisplay label using the information available.
         * It was built for use with the ability score spinner updates.
         * @param scoreMod - The modifier for the associated skill.
         */
        public void updateDisplay(int scoreMod)
        {
            if(ranks.getValue() > 0 && classSkill.isSelected())
            {
                modDisplay.setText("" + (ranks.getValue() + 3 + scoreMod));
            } else {
                modDisplay.setText("" + (ranks.getValue() + scoreMod));
            }
        }
    }
}
