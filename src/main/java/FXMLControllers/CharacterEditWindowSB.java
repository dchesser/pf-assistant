package FXMLControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CharacterEditWindowSB {

    public CharacterEditWindowSB()
    {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new CharacterEditWindowController().getURL());
            System.out.println(getClass().getClassLoader().getResource("/CharacterEditWindowLayout.fxml").toString());
            System.out.println(getClass().getClassLoader().getResource("CharacterEditWindowLayout.fxml").toString());
            System.out.println(getClass().getResource("/CharacterEditWindowLayout.fxml").toString());
            System.out.println(getClass().getResource("CharacterEditWindowLayout.fxml").toString());
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
