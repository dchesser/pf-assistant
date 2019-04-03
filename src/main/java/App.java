import FXMLControllers.CharacterEditWindowSB;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App extends Application {
    private final FileChooser fileChooser = new FileChooser();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PathFinder Assistant");

        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);

        MenuItem dieRollHeroic = new MenuItem("Dice Roll - Heroic");
        dieRollHeroic.setOnAction(event -> {
            System.out.println("Create Heroic Method button pressed");
            new CharacterCreateWindow(CharacterCreateWindow.Method.HEROIC);
        });

        MenuItem dieRollModern = new MenuItem("Dice Roll - Modern");
        dieRollModern.setOnAction(event -> {
            System.out.println("Create Modern Method button pressed");
            new CharacterCreateWindow(CharacterCreateWindow.Method.MODERN);
        });

        MenuItem dieRollClassic = new MenuItem("Dice Roll - Classic");
        dieRollClassic.setOnAction(event -> {
            System.out.println("Create Classic Method button pressed");
            new CharacterCreateWindow(CharacterCreateWindow.Method.CLASSIC);
        });

        MenuItem pointBuy = new MenuItem("Point Buy");
        pointBuy.setOnAction(event -> {
            System.out.println("Point buy method used");
            new CharacterCreateWindow(CharacterCreateWindow.Method.POINT_BUY);
        });

        MenuItem customCreation = new MenuItem("Custom");
        customCreation.setOnAction(event -> {
            System.out.println("Custom method chosen");
            new CharacterCreateWindow(CharacterCreateWindow.Method.CUSTOM);
        });

        MenuItem loadCharacter = new MenuItem("Load Character File");
        loadCharacter.setOnAction(event -> {
            System.out.println("Load Character Button Pressed");
            // if the default save directory doesn't exist, make it.
            File saveDirectory = new File("saves");
            if(!saveDirectory.exists()) {
                saveDirectory.mkdir();
            }

            FileChooser saveSelector = new FileChooser();
            saveSelector.setTitle("Select Your Save");
            File defaultDirectory = new File("saves" + File.separator);
            saveSelector.setInitialDirectory(defaultDirectory);
            saveSelector.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
            );

            File saveLocation = saveSelector.showOpenDialog(primaryStage);

            if (saveLocation != null) {
                CharacterEditWindowSB cewsb = new CharacterEditWindowSB(saveLocation);
            }
        });

        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> primaryStage.close());

        Menu fileMenu = new Menu("File");

        Menu characterMenu = new Menu("Create Character");
        characterMenu.getItems().addAll(dieRollHeroic, dieRollModern, dieRollClassic, pointBuy, customCreation);

        fileMenu.getItems().addAll(characterMenu, loadCharacter, quit);
        menuBar.getMenus().addAll(fileMenu);

        Text applicationName = new Text();
        applicationName.setText("PathFinder Assistant v" + ApplicationConfig.VERSION_STRING);

        VBox rootView = new VBox();

        rootView.getChildren().add(applicationName);
        rootView.getChildren().add(rollDieWindowButton());
        rootView.getChildren().add(menuBar);

        rootView.setAlignment(Pos.CENTER);
        rootView.setSpacing(ApplicationConfig.DEFAULT_SPACING);
        rootView.setPadding(new Insets(ApplicationConfig.DEFAULT_PADDING));

        primaryStage.setScene(new Scene(rootView, 640, 480));

        primaryStage.show();
    }

    private void characterWindow(File file) {
        CharacterEditWindow newWindow;
        if (file != null) {
            newWindow = new CharacterEditWindow(file);
        } else {
            newWindow = new CharacterEditWindow();
        }
    }

    private Button rollDieWindowButton() {
        Button btn = new Button("Roll Dice");
        btn.setOnAction(event -> new DieRollWindow());
        return btn;
    }
}
