import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.cardboard.crystallogic.PlayerCharacter;

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

    public HashMap<String, Integer> rollAbilityScores(Method method)
    {
        return new HashMap<>();
    }
}
