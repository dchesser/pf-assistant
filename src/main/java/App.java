import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PathFinder Assistant");

        // Create character button
        Button createCharacterButton = new Button();
        createCharacterButton.setDefaultButton(true);
        createCharacterButton.setText("Create Character");

        /* Open new window for editing a character */
        createCharacterButton.setOnAction(event -> {

            System.out.println("Create Character Button Pressed");

            /*
            The window is created in the CharacterEditWindow class.
            It will also handle the creation of the character object.
             */
            CharacterEditWindow newEditWindow = new CharacterEditWindow();

            /* Un-comment this if you want the main menu to hide when opening the new window.
            primaryStage.hide();
            */
        });

        // Load character button
        Button loadCharacterButton = new Button();
        loadCharacterButton.setText("Load Character");
        /* Open new window for loading character */
        loadCharacterButton.setOnAction(event -> {
            System.out.println("Load Character Button Pressed");

            /*
            Might want to use javafx.stage.FileChooser here to select the character file.
             */
        });

        // Quit button
        Button quitButton = new Button();
        quitButton.setText("Quit");
        quitButton.setOnAction(event -> primaryStage.close());

        VBox rootView = new VBox();

        rootView.getChildren().add(createCharacterButton);
        rootView.getChildren().add(loadCharacterButton);
        rootView.getChildren().add(quitButton);
        rootView.setAlignment(Pos.CENTER);
        rootView.setSpacing(25);
        primaryStage.setScene(new Scene(rootView, 200, 200));



        primaryStage.show();
    }
}
