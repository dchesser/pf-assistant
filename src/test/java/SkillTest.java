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
	private static String brotherCrunk ="{ " +
				"\"name\": \"Brother Crunk\"," +
				"\"abilityScores\": {" +
					"\"STR\":  16," +
					"\"DEX\":  16," +
					"\"CON\":  13," +
					"\"INT\":  12," +
					"\"WIS\":  15," +
					"\"CHA\":  10 }," +
				"\"alignment\":  \"NE\"," +
				"\"race\": \"Half-Orc\"," +
				"\"deity\": \"unknown\"," +
				"\"height\": \"tall?\"," +
				"\"weight\": \"Bulky and Stronk\"," +
				"\"homeland\": \"Orcland\"," +
				"\"hairColor\": \"Dark Brown?\"," +
				"\"eyeColor\": \"Dark Brown\"," +
				"\"gender\": \"M\"," +
				"\"age\": \"25\"," +
				"\"size\": \"M\"," +
				"\"platinumCoins\": 100," +
				"\"goldCoins\": 200," +
				"\"silverCoins\": 3," +
				"\"copperCoins\": 0," +
				"\"otherValuables\": \"Religious Symbol\"," +
				"\"currentHealth\": 256," +
				"\"maxHealth\": 512," +
			"\"skills\": [" +
				"{" +
					"\"name\": \"Linguistics\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"3\"" +
				"}," +
				"{" +
					"\"name\": \"Stealth\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Disable Device\"," +
					"\"classSkill\": true," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Local\"," +
					"\"classSkill\": true," +
					"\"ranks\": \"1\"" +
				"}," +
				"{" +
					"\"name\": \"Use Magic Device\"," +
					"\"classSkill\": true," +
					"\"ranks\": \"3\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Engineering\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Handle Animal\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Planes\"," +
					"\"classSkill\": true," +
					"\"ranks\": \"10\"" +
				"}," +
				"{" +
					"\"name\": \"Sense Motive\"," +
					"\"classSkill\": true," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Perception\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Survival\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Profession\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"7\"" +
				"}," +
				"{" +
					"\"name\": \"Heal\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Escape Artist\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Spellcraft\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Diplomacy\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Climb\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Arcana\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Appraise\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Disguise\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Dungeoneering\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Craft\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Geography\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Fly\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Intimidate\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Nobility\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Perform\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Nature\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Swim\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge Religion\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Ride\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Acrobatics\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Bluff\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Knowledge History\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}," +
				"{" +
					"\"name\": \"Sleight Of Hand\"," +
					"\"classSkill\": false," +
					"\"ranks\": \"0\"" +
				"}" +
			"]," +
			"inventory: []" +
			"}";

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
