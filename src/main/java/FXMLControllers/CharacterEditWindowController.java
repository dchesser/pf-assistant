package FXMLControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;

public class CharacterEditWindowController
{

    public URL getURL() {
        System.out.println(this.getClass().getClassLoader().getResource("CharacterEditWindowLayout.fxml"));
        return this.getClass().getClassLoader().getResource("CharacterEditWindowLayout.fxml");
    }

    // FXML Field Variables
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

    // FXML Actions
    @FXML
    void openRollWindowAction(ActionEvent event) {
        System.out.println("Open Roll Window Request Recieved.");
    }

    @FXML
    void rollD20Action(ActionEvent event) {
        System.out.println("Request to roll a d20 recieved.");
    }

    @FXML
    void saveCharAction(ActionEvent event) {
        System.out.println("Request to save character received.");
    }

    @FXML
    void saveCharAsAction(ActionEvent event) {
        System.out.println("SaveCharAsAction received.");
    }
}
