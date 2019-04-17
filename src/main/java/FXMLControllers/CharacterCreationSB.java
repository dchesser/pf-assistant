package FXMLControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.cardboard.crystallogic.PlayerCharacter;

import java.io.IOException;

/**
 * Written by David Hagerty
 * Written on 2019-04-17
 */
public class CharacterCreationSB {

    public enum CreationMethod {
        HEROIC,
        MODERN,
        CLASSIC,
        POINT_BUY,
        CUSTOM
    }

    public CharacterCreationSB(CreationMethod method) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            CharacterDiceCreationWindowController diceController = null;
            CharacterPointBuyCreationController pointController = null;
            Parent root;

            if (method == CreationMethod.MODERN || method == CreationMethod.CLASSIC || method == CreationMethod.HEROIC) {
                fxmlLoader.setLocation(getClass().getResource("/CharacterDiceCreationWindow.fxml"));
                root = fxmlLoader.load();
                diceController = fxmlLoader.getController();
            } else {
                fxmlLoader.setLocation(getClass().getResource("/CharacterPointBuyCreationLayout.fxml"));
                root = fxmlLoader.load();
                pointController = fxmlLoader.getController();
            }

            if (diceController != null) {
                diceController.setCharacter(method);
            }

            if (pointController != null) {
                pointController.setCharacter(method);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
