import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import network.cardboard.crystallogic.PlayerCharacter;

public class CharacterEditWindow {

    PlayerCharacter playerCharacter;
    /**
     * This method is the default constructor, which will assume that you are making a new character.
     */
    public CharacterEditWindow()
    {
        // playerCharacter = new PlayerCharacter();

        StackPane layout = new StackPane();

        HBox nameHBox = new HBox();
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField("New Character");

        nameHBox.getChildren().add(nameLabel);
        nameHBox.getChildren().add(nameField);

        layout.getChildren().add(nameHBox);

        Stage stage = new Stage();
        stage.setTitle("New Character");
        stage.setScene(new Scene(layout, 450, 450));

        stage.show();
    }
}
