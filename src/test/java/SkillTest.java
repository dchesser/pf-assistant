import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import network.cardboard.crystallogic.AbilityScores.Ability;
import network.cardboard.crystallogic.PlayerCharacter;
import network.cardboard.crystallogic.PlayerSkill;
import network.cardboard.crystallogic.PlayerSkill.GameSkill;

public class SkillTest {
    private static JdomParser parser = new JdomParser();
    private static String brotherCrunk =
	"{ \"name\": \"Brother Crunk\"," +
	"\"abilityScores\": {" +
	"\"STR\":  16," +
	"\"DEX\":  16," +
	"\"CON\":  13," +
	"\"INT\":  12," +
	"\"WIS\":  15," +
	"\"CHA\":  10 }}";

    @Test
    public void testSkills()
    {
	try {
	    JsonNode result = parser.parse(brotherCrunk);
	    PlayerCharacter crunk = new PlayerCharacter(result);

	    crunk.getSkill(GameSkill.Diplomacy).addRanks(2);
	    crunk.getSkill(GameSkill.Climb).addRanks(3);

	    System.out.printf("Sample roll for climb: %d\n",
			      crunk.rollForSkill(GameSkill.Climb));

	    assertEquals(2, crunk.getSkill(GameSkill.Diplomacy).getRanks());
	} catch (argo.saj.InvalidSyntaxException ex) {
	    Assert.fail("Test JSON was not proper.");
	}
    }
}
