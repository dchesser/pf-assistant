import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.cardboard.crystallogic.Die;
import network.cardboard.crystallogic.PlayerCharacter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Written by David Hagerty
 * Written on 3/18/19
 */
public class CharacterCreateWindow {

    protected enum Method {
        HEROIC,
        MODERN,
        CLASSIC
    }

    private PlayerCharacter playerCharacter;

    private Stage stage;
    private TextField characterName;

    private HashMap<String, Integer> scoreRolls;

    private ObservableList<String> scoreOptions = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F");

    public CharacterCreateWindow(Method method) {
        switch (method) {
            case HEROIC:
                scoreRolls = rollHeroicAbilityScores();
            case MODERN:
                scoreRolls = rollModernAbilityScores();
            case CLASSIC:
                scoreRolls = rollClassicAbilityScores();
        }
        createWindow();
    }

    private void createWindow() {
        StackPane layout = new StackPane();
        VBox container = new VBox();

        // Character name information
        HBox nameBox = new HBox();
        Label nameLabel = new Label("Name: ");
        characterName = new TextField();
        nameBox.getChildren().addAll(nameLabel, characterName);
        nameBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);

        // Die rolls for scores
        HBox scores = new HBox();
        Label scoreLabel = new Label(scoreRolls.toString());
        scores.getChildren().addAll(scoreLabel);


        // Show the window
        container.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        container.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));
        container.setAlignment(Pos.TOP_LEFT);
        container.getChildren().addAll(nameBox, scores);
        layout.getChildren().add(container);
        stage = new Stage();
        stage.setTitle("Create a character");
        stage.setScene(new Scene(layout));
        stage.show();
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
        return Die.d6.roll() + Die.d6.roll() + Die.d6.roll();
    }

    private int rollModern() {
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(Die.d6.roll());
        rolls.add(Die.d6.roll());
        rolls.add(Die.d6.roll());
        rolls.add(Die.d6.roll());

        Integer lowest = rolls.get(0);
        for (int roll : rolls) {
            if (roll < lowest) {
                lowest = roll;
            }
        }
        rolls.remove(lowest);

        int sum = 0;
        for (int roll : rolls) {
            sum += roll;
        }

        return sum;
    }

    private int rollHeroic() {
        return 6 + Die.d6.roll() + Die.d6.roll();
    }
}
