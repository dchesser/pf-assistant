import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.AbilityScores;
import network.cardboard.crystallogic.PlayerCharacter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * This class is used for displaying the window that is used to edit characters.
 * It will take a file and load an existing character from it,
 * Or it will create a new character if given nothing (for use with NewCharacter button in main menu)
 *
 * @author William Bullock
 * @version 0.2
 * @since 03-14-2019
 */
public class CharacterEditWindow {

    private PlayerCharacter playerCharacter;
    private File saveLocation;

    // Window Objects
    private Stage stage;

    // Character Stats and Values
    private TextField nameField;

    // Ability Score fields.
    private Map<String, Spinner<Integer>> abilityScoresSpinners = new HashMap<>();

    /**
     * This method is the default constructor, which will assume that you are making a new character.
     */
    public CharacterEditWindow()
    {
        playerCharacter = new PlayerCharacter("New Character", false);

        createWindow(); // This goes at the end, as it requires playerCharacter to have a name
    }

    /**
     * This constructor is meant to recieve a file location to load a character from
     * This is best used with LoadCharacter functions, as it bases everything off of that file
     * @param characterSaveFile the save location of the character file.
     */
    public CharacterEditWindow(File characterSaveFile) {

        // Handle the character save file in here, and create the window as normal.

        saveLocation = characterSaveFile;

        parseSaveFile();
        createWindow(); // This goes at the end, as it requires playerCharacter to have a name
    }

    /**
     * Method: Character Edit Window
     *
     * This constructor is meant to accept newly generated player characters that come from
     * the AbilityScore selection process.  It will generate the window off of the accepted Data.
     * @param pc The Generated Player Character
     */
    public CharacterEditWindow(PlayerCharacter pc) {
        playerCharacter = pc;

        createWindow();
    }

    /**
     * This method creates the window for the CharacterEditWindow.
     * It was created so that there would be less repeated code within this class.
     */
    private void createWindow() {

        // Layout creation:
        StackPane layout = new StackPane();
        VBox overallVBox = new VBox();


        // Name Stuff Prepared and Combined
        HBox nameHBox = new HBox();

        Label nameLabel = new Label("Name:");
        nameField = new TextField(playerCharacter.getName());

        nameHBox.getChildren().add(nameLabel);
        nameHBox.getChildren().add(nameField);

        // S p a c i n g
        nameHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);


        // Ability Scores display Creation, Preparation, and Combination.

        // Layout of Section
        HBox abilityScoresHBox = new HBox();
        VBox strIntScoresVBox = new VBox();
        VBox dexWisScoresVBox = new VBox();
        VBox conChaScoresVBox = new VBox();

        // Create the Ability Score fields and fill them (max 100 for now)
        abilityScoresSpinners.put("str", new Spinner<>(1,100, playerCharacter.abilityScores.strength.getValue()));
        abilityScoresSpinners.put("dex", new Spinner<>(1,100, playerCharacter.abilityScores.dexterity.getValue()));
        abilityScoresSpinners.put("con", new Spinner<>(1,100, playerCharacter.abilityScores.constitution.getValue()));
        abilityScoresSpinners.put("int", new Spinner<>(1,100, playerCharacter.abilityScores.intelligence.getValue()));
        abilityScoresSpinners.put("wis", new Spinner<>(1,100, playerCharacter.abilityScores.wisdom.getValue()));
        abilityScoresSpinners.put("cha", new Spinner<>(1,100, playerCharacter.abilityScores.charisma.getValue()));

        // Create Labels for Each
        Label strLabel = new Label("STR:");
        Label dexLabel = new Label("DEX:");
        Label conLabel = new Label("CON:");
        Label intLabel = new Label("INT:");
        Label wisLabel = new Label("WIS:");
        Label chaLabel = new Label("CHA:");

        // create minor pairs for each ability score
        //str
        HBox strHBox = new HBox();
        strHBox.getChildren().add(strLabel);
        strHBox.getChildren().add(abilityScoresSpinners.get("str"));
        //dex
        HBox dexHBox = new HBox();
        dexHBox.getChildren().add(dexLabel);
        dexHBox.getChildren().add(abilityScoresSpinners.get("dex"));
        //con
        HBox conHBox = new HBox();
        conHBox.getChildren().add(conLabel);
        conHBox.getChildren().add(abilityScoresSpinners.get("con"));
        //int
        HBox intHBox = new HBox();
        intHBox.getChildren().add(intLabel);
        intHBox.getChildren().add(abilityScoresSpinners.get("int"));
        //wis
        HBox wisHBox = new HBox();
        wisHBox.getChildren().add(wisLabel);
        wisHBox.getChildren().add(abilityScoresSpinners.get("wis"));
        //cha
        HBox chaHBox = new HBox();
        chaHBox.getChildren().add(chaLabel);
        chaHBox.getChildren().add(abilityScoresSpinners.get("cha"));

        //Combine everything
        strIntScoresVBox.getChildren().add(strHBox);
        strIntScoresVBox.getChildren().add(intHBox);

        dexWisScoresVBox.getChildren().add(dexHBox);
        dexWisScoresVBox.getChildren().add(wisHBox);

        conChaScoresVBox.getChildren().add(conHBox);
        conChaScoresVBox.getChildren().add(chaHBox);

        abilityScoresHBox.getChildren().add(strIntScoresVBox);
        abilityScoresHBox.getChildren().add(dexWisScoresVBox);
        abilityScoresHBox.getChildren().add(conChaScoresVBox);

        // Spacing
        strIntScoresVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        dexWisScoresVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        conChaScoresVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        abilityScoresHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);

        // Save Preparation and Combination
        HBox saveHBox = new HBox();

        // Button Creation
        Button saveCharacterButton = new Button();
        saveCharacterButton.setText("Save Character");
        saveCharacterButton.setOnAction(event -> saveCharacter());

        Button saveCharacterAsButton = new Button();
        saveCharacterAsButton.setText("Save As");
        saveCharacterAsButton.setOnAction(event -> saveCharacterAs());

        // Combination
        saveHBox.getChildren().add(saveCharacterButton);
        saveHBox.getChildren().add(saveCharacterAsButton);

        // Spacing
        saveHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);


        // Spacing Settings (Final)
        overallVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        overallVBox.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));
        overallVBox.setAlignment(Pos.TOP_LEFT);


        // Final Combination and Display
        overallVBox.getChildren().add(nameHBox);
        overallVBox.getChildren().add(abilityScoresHBox);
        overallVBox.getChildren().add(saveHBox);

        layout.getChildren().add(overallVBox);

        stage = new Stage();
        stage.setTitle(playerCharacter.getName().isEmpty() ?  playerCharacter.getName() : "Create a Character");
        stage.setScene(new Scene(layout));

        stage.show();
    }

    /**
     * This method is used to save the currently open character.
     * It will, by default, remember where you last saved, or loaded from,
     * and save to that location.
     */
    private void saveCharacter() {

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
    private void saveCharacterAs() {

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

    /**
     * @TODO Expand this file as we add more things to the PlayerCharacter class!  It is very short on data right now!
     * The purpose of this method is to take the contents of the window and prepare them into a PlayerCharacter object
     * It returns the new PlayerCharacter object that has been prepared for saving
     * @return the updated PlayerCharacter object.
     */
    private void updateCharacterForSave() {

        AbilityScores newAbilities = new AbilityScores(
                abilityScoresSpinners.get("str").getValue(),
                abilityScoresSpinners.get("dex").getValue(),
                abilityScoresSpinners.get("con").getValue(),
                abilityScoresSpinners.get("int").getValue(),
                abilityScoresSpinners.get("wis").getValue(),
                abilityScoresSpinners.get("cha").getValue()
        );

        playerCharacter = new PlayerCharacter(nameField.getText(), newAbilities);
    }

    /**
     * This is only to be called if you are loading a character from saveLocation
     * @return the character that was saved in saveLocation (null for errored)
     */
    private void parseSaveFile() {

        try {
            FileReader fr = new FileReader(saveLocation);
            BufferedReader br = new BufferedReader(fr);

            String contents = "";

            String line;
            while((line = br.readLine()) != null) {
                contents += line;
            }

            // The PlayerCharacter class will return a new instance of itself after parsing the JSON file
            playerCharacter = new PlayerCharacter(contents, true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
