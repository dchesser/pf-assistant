import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.PlayerCharacter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
        createWindow(); // This goes at the end, as it requires playerCharacter to have a name
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

        Button saveCharacterButton = new Button();
        saveCharacterButton.setText("Save Character");
        saveCharacterButton.setOnAction(event -> {

            System.out.println("Save Button Pressed");
            /* Inside this, we need to have it output the file of the playerCharacter */
            saveCharacter();
        });

        overallVBox.getChildren().add(nameHBox);
        overallVBox.getChildren().add(saveCharacterButton);

        layout.getChildren().add(overallVBox);

        stage = new Stage();
        stage.setTitle(playerCharacter.getName());
        stage.setScene(new Scene(layout, 450, 450));

        stage.show();
    }

    /**
     * This method is used to save the currently open character.
     * It will, by default, remember where you last saved, or loaded from,
     * and save to that location.
     */
    private void saveCharacter() {

        // Check the location of the default save directory
        File saveDirectory = new File("saves");
        if(!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }


        if(saveLocation == null) {
            FileChooser folderSelector = new FileChooser();
            folderSelector.setTitle("Select Save Location");
            File defaultDirectory = new File("saves" + File.separator);
            folderSelector.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON files (*.json)","*.json")
            );
            folderSelector.setInitialDirectory(defaultDirectory);

            saveLocation = folderSelector.showSaveDialog(stage);
        }

        // Now just save to save Location
        try{

            playerCharacter = updateCharacterForSave();

            PrintWriter pw = new PrintWriter(saveLocation);
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
}
