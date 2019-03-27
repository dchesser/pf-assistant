import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import static argo.format.JsonNumberUtils.asInteger;
import network.cardboard.crystallogic.PlayerCharacter;
import network.cardboard.crystallogic.AbilityScores;

public class CharacterJSONTest {
    /**
     * Brother Crunk was my half-orc monk for D&D 5e.
     * Despite the game aiming for a typical Asian monastery,
     * I went for the traditional European monastery approach.
     * We'll be using his statistics as a base as we
     * convert him over to Pathfinder.
     */
    private static String brotherCrunk =
	"{ \"name\": \"Brother Crunk\"," +
	"\"abilityScores\": {" +
	"\"STR\":  16," +
	"\"DEX\":  16," +
	"\"CON\":  13," +
	"\"INT\":  12," +
	"\"WIS\":  15," +
	"\"CHA\":  10 }}";

    private static final JdomParser PARSER = new JdomParser();

    @Test
    public void readPlayerCharacter()
    {
	try {
	    JsonNode result = PARSER.parse(brotherCrunk);
	    PlayerCharacter crunk = new PlayerCharacter(result);
	    JsonNode abilities = result.getNode("abilityScores");
	    int st = asInteger(abilities.getNumberValue("STR"));
	    int dx = asInteger(abilities.getNumberValue("DEX"));
	    int cn = asInteger(abilities.getNumberValue("CON"));
	    int in = asInteger(abilities.getNumberValue("INT"));
	    int ws = asInteger(abilities.getNumberValue("WIS"));
	    int ch = asInteger(abilities.getNumberValue("CHA"));

	    crunk.abilityScores = new AbilityScores(st, dx, cn, in, ws, ch);

	    assertEquals(crunk.abilityScores.strength.getValue(), 16);
	    assertEquals(crunk.abilityScores.dexterity.getValue(), 16);
	    assertEquals(crunk.abilityScores.constitution.getValue(), 13);
	    assertEquals(crunk.abilityScores.intelligence.getValue(), 12);
	    assertEquals(crunk.abilityScores.wisdom.getValue(), 15);
	    assertEquals(crunk.abilityScores.charisma.getValue(), 10);
	    assertTrue("Brother Crunk".equals(crunk.getName()));
	} catch (argo.saj.InvalidSyntaxException ex) {
	    Assert.fail("Test JSON was not proper.");
	}
    }
}
