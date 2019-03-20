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
import network.cardboard.crystallogic.AbilityScores;
import network.cardboard.crystallogic.Die;
import network.cardboard.crystallogic.PlayerCharacter;

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
        CLASSIC
    }

    private PlayerCharacter playerCharacter;
    private TextField characterName;
    private File saveLocation;

    private HashMap<String, Integer> scoreRolls;

    private ObservableList<String> scoreOptions = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F");
    private HashMap<String, Spinner<String>> abilityScores = new HashMap<>();

    private HBox errorHBox;
    private Stage stage;

    public CharacterCreateWindow(Method method) {
        switch (method) {
            case HEROIC:
                scoreRolls = rollHeroicAbilityScores();
                break;
            case MODERN:
                scoreRolls = rollModernAbilityScores();
                break;
            case CLASSIC:
                scoreRolls = rollClassicAbilityScores();
                break;
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
        Label scoreLabel = new Label("Your ability score options: ");
        Text scoreRollString = new Text(scoreRolls.toString());
        scoreLabel.setLabelFor(scoreRollString);

        scores.getChildren().addAll(scoreLabel, scoreRollString);

        // Choose which roll goes to which ability
        abilityScores.put("str", new Spinner<>(scoreOptions));
        abilityScores.put("dex", new Spinner<>(scoreOptions));
        abilityScores.put("con", new Spinner<>(scoreOptions));
        abilityScores.put("int", new Spinner<>(scoreOptions));
        abilityScores.put("wis", new Spinner<>(scoreOptions));
        abilityScores.put("cha", new Spinner<>(scoreOptions));

        Label strLabel = new Label("STR:");
        Label dexLabel = new Label("DEX:");
        Label conLabel = new Label("CON:");
        Label intLabel = new Label("INT:");
        Label wisLabel = new Label("WIS:");
        Label chaLabel = new Label("CHA:");

        strLabel.setLabelFor(abilityScores.get("str"));
        dexLabel.setLabelFor(abilityScores.get("dex"));
        conLabel.setLabelFor(abilityScores.get("con"));
        intLabel.setLabelFor(abilityScores.get("int"));
        wisLabel.setLabelFor(abilityScores.get("wis"));
        chaLabel.setLabelFor(abilityScores.get("cha"));

        // create minor pairs for each ability score
        //str
        HBox strHBox = new HBox();
        strHBox.getChildren().add(strLabel);
        strHBox.getChildren().add(abilityScores.get("str"));
        //dex
        HBox dexHBox = new HBox();
        dexHBox.getChildren().add(dexLabel);
        dexHBox.getChildren().add(abilityScores.get("dex"));
        //con
        HBox conHBox = new HBox();
        conHBox.getChildren().add(conLabel);
        conHBox.getChildren().add(abilityScores.get("con"));
        //int
        HBox intHBox = new HBox();
        intHBox.getChildren().add(intLabel);
        intHBox.getChildren().add(abilityScores.get("int"));
        //wis
        HBox wisHBox = new HBox();
        wisHBox.getChildren().add(wisLabel);
        wisHBox.getChildren().add(abilityScores.get("wis"));
        //cha
        HBox chaHBox = new HBox();
        chaHBox.getChildren().add(chaLabel);
        chaHBox.getChildren().add(abilityScores.get("cha"));

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
        HBox saveHBox = new HBox();

        // Button Creation
        Button saveCharacterButton = new Button();
        saveCharacterButton.setText("Save Character");
        saveCharacterButton.setOnAction(event -> {
            if (validAbilityScores()) {
                saveCharacter();
            } else {
                displayValidationError();
            }
        });

        Button saveCharacterAsButton = new Button();
        saveCharacterAsButton.setText("Save As");
        saveCharacterAsButton.setOnAction(event -> {
            if (validAbilityScores()) {
                saveCharacterAs();
            } else {
                displayValidationError();
            }
        });

        // Combination
        saveHBox.getChildren().add(saveCharacterButton);
        saveHBox.getChildren().add(saveCharacterAsButton);

        // Spacing
        saveHBox.setSpacing(ApplicationConfig.DEFAULT_SPACING);

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
        AbilityScores newAbilities = new AbilityScores(
            scoreRolls.get(abilityScores.get("str").getValue()),
            scoreRolls.get(abilityScores.get("dex").getValue()),
            scoreRolls.get(abilityScores.get("con").getValue()),
            scoreRolls.get(abilityScores.get("int").getValue()),
            scoreRolls.get(abilityScores.get("wis").getValue()),
            scoreRolls.get(abilityScores.get("cha").getValue())
        );
        playerCharacter = new PlayerCharacter(characterName.getText(), newAbilities);
    }

    // @TODO Clean up the logic in this method
    private boolean validAbilityScores() {
        String strength = abilityScores.get("str").getValue();
        String dexterity = abilityScores.get("dex").getValue();
        String intelligence = abilityScores.get("int").getValue();
        String constitution = abilityScores.get("con").getValue();
        String wisdom = abilityScores.get("wis").getValue();
        String charisma = abilityScores.get("cha").getValue();

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
}
