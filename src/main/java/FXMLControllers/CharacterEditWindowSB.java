package FXMLControllers;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.saj.InvalidSyntaxException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.cardboard.crystallogic.PlayerCharacter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CharacterEditWindowSB {

    public CharacterEditWindowSB()
    {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/CharacterEditWindowLayout.fxml"));
            Parent root = fxmlLoader.load();

            CharacterEditWindowController controller = fxmlLoader.getController();

            controller.setCharacter(new PlayerCharacter("Default"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CharacterEditWindowSB(File save)
    {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/CharacterEditWindowLayout.fxml"));
            Parent root = fxmlLoader.load();

            CharacterEditWindowController controller = fxmlLoader.getController();

            controller.setSaveLocation(save);

            PlayerCharacter playerCharacter = readPlayerSaveFile(save);
            controller.setCharacter(playerCharacter);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(playerCharacter.getName());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: readPlayerSaveFile(File)
     * This is only to be called if you are loading a character from a file
     * @return the character that was saved in the specified file. (null for errored)
     */
    private PlayerCharacter readPlayerSaveFile(File saveFile)
    {
        try {
            FileReader fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);

            String contents = "";

            String line;
            while((line = br.readLine()) != null) {
                contents += line;
            }

            // The PlayerCharacter class will return a new instance of itself after parsing the JSON file
            JsonNode data = new JdomParser().parse(contents);
            return new PlayerCharacter(data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
        }
        return null;
    }
}
