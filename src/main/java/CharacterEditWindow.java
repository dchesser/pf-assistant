import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import argo.saj.InvalidSyntaxException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.AbilityScores;
import network.cardboard.crystallogic.PlayerCharacter;

import java.io.*;

import static argo.jdom.JsonNodeBuilders.*;

/**
 * This class is used for displaying the window that is used to edit characters.
 * It will take a file and load an existing character from it,
 * Or it will create a new character if given nothing (for use with NewCharacter button in main menu)
 *
 * @author William
 * @version 0.1
 * @since 02-26-2019
 */
public class CharacterEditWindow {

    PlayerCharacter playerCharacter;
    File saveLocation;

    // Window Objects
    Stage stage;
    TextField nameField;

    /**
     * This method is the default constructor, which will assume that you are making a new character.
     */
    public CharacterEditWindow()
    {
        playerCharacter = new PlayerCharacter("New Character");

        createWindow(); // This goes at the end, as it requires playerCharacter to have a name
    }

    /**
     * This constructor is meant to recieve a file location to load a character from
     * This is best used with LoadCharacter functions, as it bases everything off of that file
     * @param characterSaveFile the save location of the character file.
     */
    public CharacterEditWindow(File characterSaveFile) {

        /*
        Handle the character save file in here, and create the window as normal.
         */

        saveLocation = characterSaveFile;

        try {
            playerCharacter = parseSaveFile();

            createWindow(); // This goes at the end, as it requires playerCharacter to have a name
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found, or was not readable.  There may have also been a parsing error.  Not really sure.");
            System.out.println("Here, have the stack trace, maybe you can figure it out...");
            e.printStackTrace();
        }
    }

    /**
     * This method creates the window for the CharacterEditWindow.
     * It was created so that there would be less repeated code within this class.
     */
    private void createWindow() {
        StackPane layout = new StackPane();

        VBox overallVBox = new VBox();

        HBox nameHBox = new HBox();
        Label nameLabel = new Label("Name:");
        nameField = new TextField(playerCharacter.getName());

        nameHBox.getChildren().add(nameLabel);
        nameHBox.getChildren().add(nameField);

        HBox saveHBox = new HBox();

        Button saveCharacterButton = new Button();
        saveCharacterButton.setText("Save Character");
        saveCharacterButton.setOnAction(event -> {

            System.out.println("Save Button Pressed");
            /* Inside this, we need to have it output the file of the playerCharacter */
            saveCharacter();
        });
        
        nameHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        overallVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        saveHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        overallVBox.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));
        overallVBox.setAlignment(Pos.TOP_LEFT);

        Button saveCharacterAsButton = new Button();
        saveCharacterAsButton.setText("Save As");
        saveCharacterAsButton.setOnAction(event -> {

            System.out.println("Save As Button Pressed");

            saveCharacterAs();
        });

        saveHBox.getChildren().add(saveCharacterButton);
        saveHBox.getChildren().add(saveCharacterAsButton);

        overallVBox.getChildren().add(nameHBox);
        overallVBox.getChildren().add(saveHBox);

        layout.getChildren().add(overallVBox);

        stage = new Stage();
        stage.setTitle(playerCharacter.getName().isEmpty() ?  playerCharacter.getName() : "Create a Character");
        stage.setScene(new Scene(layout));

        stage.show();
    }

    /**
     * @TODO This method is missing the parse to JSON function, and requires the playerCharacter.toString to get its JSON!
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

        // Now just save to save Location
        saveFile();
    }

    /**
     * @TODO This method is missing the parse to JSON function, and requires the playerCharacter.toString to get its JSON!
     * This method is used to save the currently open character under a name that you specify.
     */
    private void saveCharacterAs() {

        // Check the existence of the default save directory
        File saveDirectory = new File("saves");
        if(!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }

        updateSaveLocation();

        // Now just save to save Location
        saveFile();
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

        saveLocation = folderSelector.showSaveDialog(stage);
    }

    /**
     * This method will save the character to the save location
     * @TODO Probably need better naming for methods
     */
    private void saveFile() {
        try{
            playerCharacter = updateCharacterForSave();

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
    private PlayerCharacter updateCharacterForSave() {

        PlayerCharacter characterForSave = new PlayerCharacter(nameField.getText(), playerCharacter.abilityScores);

        return characterForSave;
    }

    /**
     * @TODO This method is extremely simple as is, and requires the json parser touch-up that we will be doing.
     * This is only to be called if you are loading a character from saveLocation
     * @return the character that was saved in saveLocation
     */
    private PlayerCharacter parseSaveFile() throws FileNotFoundException {

        try {
            FileReader fr = new FileReader(saveLocation);
            BufferedReader br = new BufferedReader(fr);

            String contents = "";

            String line;
            while((line = br.readLine()) != null) {
                contents += line;
            }

            // The PlayerCharacter class will return a new instance of itself after parsing the JSON file
            PlayerCharacter characterFromLoad = PlayerCharacter.parseJSON(contents);

            return characterFromLoad;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InvalidSyntaxException s) {
            s.printStackTrace();
        }

        throw new FileNotFoundException();
    }
}
