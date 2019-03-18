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
import javafx.scene.text.Text;
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

    private HashMap<String, Integer> scoreRolls;

    private ObservableList<String> scoreOptions = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F");

    public CharacterCreateWindow(Method method) {
        if (method == Method.HEROIC) {
            System.out.println("rolling heroic");
            scoreRolls = rollHeroicAbilityScores();
        } else if (method == Method.MODERN) {
            System.out.println("rolling modern");
            scoreRolls = rollModernAbilityScores();
        } else if (method == Method.CLASSIC) {
            System.out.println("rolling classic");
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
        TextField characterName = new TextField();
        nameBox.getChildren().addAll(nameLabel, characterName);
        nameBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);

        // Die rolls for scores
        HBox scores = new HBox();
        Label scoreLabel = new Label("Your ability score options: ");
        Text scoreRollString = new Text(scoreRolls.toString());
        scoreLabel.setLabelFor(scoreRollString);

        scores.getChildren().addAll(scoreLabel, scoreRollString);


        // Show the window
        container.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        container.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));
        container.setAlignment(Pos.TOP_LEFT);
        container.getChildren().addAll(nameBox, scores);
        layout.getChildren().add(container);
        Stage stage = new Stage();
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
}
