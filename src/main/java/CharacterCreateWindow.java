import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.cardboard.crystallogic.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
        CLASSIC,
        POINT_BUY,
        CUSTOM
    }

    private PlayerCharacter playerCharacter;
    private TextField characterName;
    private File saveLocation;

    private HashMap<String, Integer> scoreRolls;

    private ObservableList<String> scoreOptions = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F");
    private HashMap<String, Spinner<String>> abilityScoreRollSpinners = new HashMap<>();
    private HashMap<String, Spinner<Integer>> abilityScoreBuySpinners = new HashMap<>();

    private HBox errorHBox;
    private Stage stage;

    private Method creationMethod;

    private PointBuyPool buyPool = new PointBuyPool();

    public CharacterCreateWindow(Method method) {
        creationMethod = method;
        switch (creationMethod) {
            case HEROIC:
                scoreRolls = rollHeroicAbilityScores();
                createRollWindow();
                break;
            case MODERN:
                scoreRolls = rollModernAbilityScores();
                createRollWindow();
                break;
            case CLASSIC:
                scoreRolls = rollClassicAbilityScores();
                createRollWindow();
                break;
            case POINT_BUY:
                scoreRolls = pointBuyScores();
                createBuyWindow();
                break;
            case CUSTOM:
                scoreRolls = pointBuyScores();
                createBuyWindow();
                break;
        }
    }

    private void createBuyWindow() {
        StackPane layout = new StackPane();
        VBox container = new VBox();

        // Character name information
        HBox nameBox = nameHBox();

        HBox pointsLeftBox = new HBox();
        Text pointsLeftText = new Text("Points left: " + buyPool.getPointPool());
        pointsLeftBox.getChildren().add(pointsLeftText);

        Label strLabel = new Label("STR:");
        Label dexLabel = new Label("DEX:");
        Label conLabel = new Label("CON:");
        Label intLabel = new Label("INT:");
        Label wisLabel = new Label("WIS:");
        Label chaLabel = new Label("CHA:");

        abilityScoreBuySpinners.put("str", new Spinner<>());
        abilityScoreBuySpinners.put("dex", new Spinner<>());
        abilityScoreBuySpinners.put("con", new Spinner<>());
        abilityScoreBuySpinners.put("int", new Spinner<>());
        abilityScoreBuySpinners.put("wis", new Spinner<>());
        abilityScoreBuySpinners.put("cha", new Spinner<>());

        if (creationMethod == Method.POINT_BUY) {
            int spinnerMinimum = 7;
            int spinnerMaximum = 18;
            int spinnerInitialValue = 10;

            abilityScoreBuySpinners.get("str").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue, buyPool));
            abilityScoreBuySpinners.get("con").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue, buyPool));
            abilityScoreBuySpinners.get("dex").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue, buyPool));
            abilityScoreBuySpinners.get("int").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue, buyPool));
            abilityScoreBuySpinners.get("wis").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue, buyPool));
            abilityScoreBuySpinners.get("cha").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue, buyPool));
        } else if (creationMethod == Method.CUSTOM) {
            int spinnerMinimum = 0;
            int spinnerMaximum = 20;
            int spinnerInitialValue = 10;

            abilityScoreBuySpinners.get("str").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue));
            abilityScoreBuySpinners.get("con").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue));
            abilityScoreBuySpinners.get("dex").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue));
            abilityScoreBuySpinners.get("int").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue));
            abilityScoreBuySpinners.get("wis").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue));
            abilityScoreBuySpinners.get("cha").setValueFactory(new PointBuySpinnerValueFactory(spinnerMinimum, spinnerMaximum, spinnerInitialValue));
        }

        strLabel.setLabelFor(abilityScoreBuySpinners.get("str"));
        dexLabel.setLabelFor(abilityScoreBuySpinners.get("dex"));
        conLabel.setLabelFor(abilityScoreBuySpinners.get("con"));
        intLabel.setLabelFor(abilityScoreBuySpinners.get("int"));
        wisLabel.setLabelFor(abilityScoreBuySpinners.get("wis"));
        chaLabel.setLabelFor(abilityScoreBuySpinners.get("cha"));

        abilityScoreBuySpinners.get("str").setOnMouseClicked(event -> pointsLeftText.setText("Points left: " + buyPool.getPointPool()));
        abilityScoreBuySpinners.get("dex").setOnMouseClicked(event -> pointsLeftText.setText("Points left: " + buyPool.getPointPool()));
        abilityScoreBuySpinners.get("con").setOnMouseClicked(event -> pointsLeftText.setText("Points left: " + buyPool.getPointPool()));
        abilityScoreBuySpinners.get("int").setOnMouseClicked(event -> pointsLeftText.setText("Points left: " + buyPool.getPointPool()));
        abilityScoreBuySpinners.get("wis").setOnMouseClicked(event -> pointsLeftText.setText("Points left: " + buyPool.getPointPool()));
        abilityScoreBuySpinners.get("cha").setOnMouseClicked(event -> pointsLeftText.setText("Points left: " + buyPool.getPointPool()));

        HBox strHBox = new HBox();
        strHBox.getChildren().add(strLabel);
        strHBox.getChildren().add(abilityScoreBuySpinners.get("str"));
        //dex
        HBox dexHBox = new HBox();
        dexHBox.getChildren().add(dexLabel);
        dexHBox.getChildren().add(abilityScoreBuySpinners.get("dex"));
        //con
        HBox conHBox = new HBox();
        conHBox.getChildren().add(conLabel);
        conHBox.getChildren().add(abilityScoreBuySpinners.get("con"));
        //int
        HBox intHBox = new HBox();
        intHBox.getChildren().add(intLabel);
        intHBox.getChildren().add(abilityScoreBuySpinners.get("int"));
        //wis
        HBox wisHBox = new HBox();
        wisHBox.getChildren().add(wisLabel);
        wisHBox.getChildren().add(abilityScoreBuySpinners.get("wis"));
        //cha
        HBox chaHBox = new HBox();
        chaHBox.getChildren().add(chaLabel);
        chaHBox.getChildren().add(abilityScoreBuySpinners.get("cha"));

        //Combine everything
        VBox strIntScoresVBox = new VBox();
        VBox dexWisScoresVBox = new VBox();
        VBox conChaScoresVBox = new VBox();
        HBox abilityScoresHBox = new HBox();

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
        HBox saveHBox = saveHBox();

        // Show the window
        container.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        container.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));
        container.setAlignment(Pos.TOP_LEFT);
        if (creationMethod == Method.POINT_BUY) {
            container.getChildren().add(pointsLeftBox);
        }
        container.getChildren().addAll(nameBox, abilityScoresHBox, saveHBox);
        layout.getChildren().add(container);
        stage = new Stage();
        stage.setTitle("Create a character");
        stage.setScene(new Scene(layout));
        stage.show();
    }

    private void createRollWindow() {
        StackPane layout = new StackPane();
        VBox container = new VBox();

        // Character name information
        HBox nameBox = nameHBox();

        // Die rolls for scores
        HBox scores = new HBox();
        Label scoreLabel = new Label("Your ability score options: ");
        Text scoreRollString = new Text(scoreRolls.toString());
        scoreLabel.setLabelFor(scoreRollString);

        scores.getChildren().addAll(scoreLabel, scoreRollString);

        // Choose which roll goes to which ability
        abilityScoreRollSpinners.put("str", new Spinner<>(scoreOptions));
        abilityScoreRollSpinners.put("dex", new Spinner<>(scoreOptions));
        abilityScoreRollSpinners.put("con", new Spinner<>(scoreOptions));
        abilityScoreRollSpinners.put("int", new Spinner<>(scoreOptions));
        abilityScoreRollSpinners.put("wis", new Spinner<>(scoreOptions));
        abilityScoreRollSpinners.put("cha", new Spinner<>(scoreOptions));


        Label strLabel = new Label("STR:");
        Label dexLabel = new Label("DEX:");
        Label conLabel = new Label("CON:");
        Label intLabel = new Label("INT:");
        Label wisLabel = new Label("WIS:");
        Label chaLabel = new Label("CHA:");

        strLabel.setLabelFor(abilityScoreRollSpinners.get("str"));
        dexLabel.setLabelFor(abilityScoreRollSpinners.get("dex"));
        conLabel.setLabelFor(abilityScoreRollSpinners.get("con"));
        intLabel.setLabelFor(abilityScoreRollSpinners.get("int"));
        wisLabel.setLabelFor(abilityScoreRollSpinners.get("wis"));
        chaLabel.setLabelFor(abilityScoreRollSpinners.get("cha"));

        // create minor pairs for each ability score
        //str
        HBox strHBox = new HBox();
        strHBox.getChildren().add(strLabel);
        strHBox.getChildren().add(abilityScoreRollSpinners.get("str"));
        //dex
        HBox dexHBox = new HBox();
        dexHBox.getChildren().add(dexLabel);
        dexHBox.getChildren().add(abilityScoreRollSpinners.get("dex"));
        //con
        HBox conHBox = new HBox();
        conHBox.getChildren().add(conLabel);
        conHBox.getChildren().add(abilityScoreRollSpinners.get("con"));
        //int
        HBox intHBox = new HBox();
        intHBox.getChildren().add(intLabel);
        intHBox.getChildren().add(abilityScoreRollSpinners.get("int"));
        //wis
        HBox wisHBox = new HBox();
        wisHBox.getChildren().add(wisLabel);
        wisHBox.getChildren().add(abilityScoreRollSpinners.get("wis"));
        //cha
        HBox chaHBox = new HBox();
        chaHBox.getChildren().add(chaLabel);
        chaHBox.getChildren().add(abilityScoreRollSpinners.get("cha"));

        //Combine everything
        VBox strIntScoresVBox = new VBox();
        VBox dexWisScoresVBox = new VBox();
        VBox conChaScoresVBox = new VBox();
        HBox abilityScoresHBox = new HBox();
        errorHBox = new HBox();

        strIntScoresVBox.getChildren().add(strHBox);
        strIntScoresVBox.getChildren().add(intHBox);

        dexWisScoresVBox.getChildren().add(dexHBox);
        dexWisScoresVBox.getChildren().add(wisHBox);

        conChaScoresVBox.getChildren().add(conHBox);
        conChaScoresVBox.getChildren().add(chaHBox);

//        abilityScoresHBox.getChildren().add(errorHBox);
        abilityScoresHBox.getChildren().add(strIntScoresVBox);
        abilityScoresHBox.getChildren().add(dexWisScoresVBox);
        abilityScoresHBox.getChildren().add(conChaScoresVBox);

        // Spacing
        strIntScoresVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        dexWisScoresVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        conChaScoresVBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        abilityScoresHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        errorHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        errorHBox.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));

        // Save Preparation and Combination
        HBox saveHBox = saveHBox();

        // Show the window
        container.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        container.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));
        container.setAlignment(Pos.TOP_LEFT);
        container.getChildren().addAll(nameBox, scores, errorHBox, abilityScoresHBox, saveHBox);
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

    private HashMap<String, Integer> pointBuyScores() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("A", 10);
        result.put("B", 10);
        result.put("C", 10);
        result.put("D", 10);
        result.put("E", 10);
        result.put("F", 10);
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

    /**
     * This method is used to save the currently open character.
     * It will, by default, remember where you last saved, or loaded from,
     * and save to that location.
     */
    private void saveCharacter() {

        // Check the existence of the default save directory
        File saveDirectory = new File("saves");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }

        // If there isn't a defined saveLocation, get one.
        if (saveLocation == null) {
            updateSaveLocation();
        }

        if (saveLocation != null) {
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
        if (!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }

        updateSaveLocation();

        if (saveLocation != null) {
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
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );
        folderSelector.setInitialDirectory(defaultDirectory);

        File tempSaveLocation = folderSelector.showSaveDialog(stage);

        if (tempSaveLocation != null) {
            saveLocation = tempSaveLocation;
        } else {
            System.out.println("Save Canceled.");
        }

    }

    /**
     * This method will save the character to the save location
     *
     * @TODO Probably need better naming for methods
     * @TODO Refactor saving functionality into an interface the PlayerCharacter class implements
     */
    private void saveFile() {
        try {
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
     * @return the updated PlayerCharacter object.
     *
     * @TODO Expand this file as we add more things to the PlayerCharacter class!  It is very short on data right now!
     * The purpose of this method is to take the contents of the window and prepare them into a PlayerCharacter object
     * It returns the new PlayerCharacter object that has been prepared for saving
     */
    private void updateCharacterForSave() {
        AbilityScores newAbilities;
        if (!abilityScoreRollSpinners.isEmpty()) {
            newAbilities = new AbilityScores(
                scoreRolls.get(abilityScoreRollSpinners.get("str").getValue()),
                scoreRolls.get(abilityScoreRollSpinners.get("dex").getValue()),
                scoreRolls.get(abilityScoreRollSpinners.get("con").getValue()),
                scoreRolls.get(abilityScoreRollSpinners.get("int").getValue()),
                scoreRolls.get(abilityScoreRollSpinners.get("wis").getValue()),
                scoreRolls.get(abilityScoreRollSpinners.get("cha").getValue())
            );
        } else {
            newAbilities = new AbilityScores(
                abilityScoreBuySpinners.get("str").getValue(),
                abilityScoreBuySpinners.get("dex").getValue(),
                abilityScoreBuySpinners.get("con").getValue(),
                abilityScoreBuySpinners.get("int").getValue(),
                abilityScoreBuySpinners.get("wis").getValue(),
                abilityScoreBuySpinners.get("cha").getValue()
            );
        }
        playerCharacter = new PlayerCharacter(characterName.getText(), newAbilities);
    }

    private boolean validAbilityScores() {
        String strength = abilityScoreRollSpinners.get("str").getValue();
        String dexterity = abilityScoreRollSpinners.get("dex").getValue();
        String intelligence = abilityScoreRollSpinners.get("int").getValue();
        String constitution = abilityScoreRollSpinners.get("con").getValue();
        String wisdom = abilityScoreRollSpinners.get("wis").getValue();
        String charisma = abilityScoreRollSpinners.get("cha").getValue();

        boolean sameAsStr = strength.equalsIgnoreCase(dexterity) && strength.equalsIgnoreCase(constitution) && strength.equalsIgnoreCase(intelligence) && strength.equalsIgnoreCase(wisdom) && strength.equalsIgnoreCase(charisma);
        boolean sameAsDex = dexterity.equalsIgnoreCase(constitution) && dexterity.equalsIgnoreCase(intelligence) && dexterity.equalsIgnoreCase(wisdom) && dexterity.equalsIgnoreCase(charisma);
        boolean sameAsCon = constitution.equalsIgnoreCase(intelligence) && constitution.equalsIgnoreCase(wisdom) && constitution.equalsIgnoreCase(charisma);
        boolean sameAsInt = intelligence.equalsIgnoreCase(wisdom) && intelligence.equalsIgnoreCase(charisma);
        boolean sameAsWis = wisdom.equalsIgnoreCase(charisma);

        return !(sameAsStr || sameAsDex || sameAsCon || sameAsInt || sameAsWis);
    }

    private void displayValidationError() {
        Text error = new Text();
        error.setText("Make sure your abilities all have unique scores");
        error.setFill(Color.RED);
        errorHBox.getChildren().add(error);
    }

    private HBox nameHBox() {
        HBox output = new HBox();
        Label nameLabel = new Label("Name: ");
        characterName = new TextField();
        output.getChildren().addAll(nameLabel, characterName);
        output.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        return output;
    }

    private HBox saveHBox() {
        HBox output = new HBox();

        // Button Creation
        Button saveCharacterButton = new Button();
        saveCharacterButton.setText("Save Character");
        saveCharacterButton.setOnAction(event -> {
            if (creationMethod == Method.POINT_BUY || creationMethod == Method.CUSTOM || validAbilityScores()) {
                saveCharacter();
            } else {
                displayValidationError();
            }
        });

        Button saveCharacterAsButton = new Button();
        saveCharacterAsButton.setText("Save As");
        saveCharacterAsButton.setOnAction(event -> {
            if (creationMethod == Method.POINT_BUY || creationMethod == Method.CUSTOM || validAbilityScores()) {
                saveCharacterAs();
            } else {
                displayValidationError();
            }
        });

        // Combination
        output.getChildren().add(saveCharacterButton);
        output.getChildren().add(saveCharacterAsButton);

        // Spacing
        output.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        return output;
    }
}
