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
			"}";

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
