package network.cardboard.crystallogic;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import argo.saj.InvalidSyntaxException;
import network.cardboard.crystallogic.AbilityScores.BuildMethod;


import static argo.jdom.JsonNodeBuilders.aNumberBuilder;
import static argo.jdom.JsonNodeBuilders.aStringBuilder;
import static argo.jdom.JsonNodeBuilders.anObjectBuilder;

/**
 * Player Characters (or "PCs") are the very essence of Pathfinder or
 * any other roleplaying game that revovles around character
 * development for the sake of the plot. They revolve around seven
 * attributes: their race, their class/job, and their six traditional
 * ability scores: strength, dexterity, constitution, intelligence,
 * wisdom, and charisma.
 */
public class PlayerCharacter
{
    private String name;
    public AbilityScores abilityScores;

    public PlayerCharacter(String contents)
    {
	name = contents;
	this.abilityScores = new AbilityScores(10);
    }

    public PlayerCharacter(JsonNode json)
    {
	try {
	    parseJSON(json);
	} catch (InvalidSyntaxException ise) {
	    ise.printStackTrace();
	}
    }

    public PlayerCharacter(String name, BuildMethod rolls)
    {
        this.name = name;
        this.abilityScores = new AbilityScores(rolls);
    }

    public PlayerCharacter(String name, AbilityScores abilityScores)
    {
        this.name = name;
        this.abilityScores = abilityScores;
    }

    public void parseJSON(JsonNode node) throws InvalidSyntaxException
    {
        name = node.getStringValue("name");
	JsonNode abilities = node.getNode("abilityScores");
        int s= Integer.parseInt(abilities.getNumberValue("STR"));
        int d = Integer.parseInt(abilities.getNumberValue("DEX"));
        int con = Integer.parseInt(abilities.getNumberValue("CON"));
        int i = Integer.parseInt(abilities.getNumberValue("INT"));
        int w = Integer.parseInt(abilities.getNumberValue("WIS"));
        int c = Integer.parseInt(abilities.getNumberValue("CHA"));

        abilityScores = new AbilityScores(s, d, con, i, w, c);
    }

    public String getName()
    {
	    return this.name;
    }

    // This toString should probably be replaced when we finish figuring out what json library should be used.
    @Override
    public String toString() {

        // create json object
        JsonNodeBuilder builder = anObjectBuilder()
                .withField("name", aStringBuilder(name))
                .withField("abilityScores", anObjectBuilder()
                        .withField("STR", aNumberBuilder("" + abilityScores.strength.getValue()))
                        .withField("DEX", aNumberBuilder("" + abilityScores.dexterity.getValue()))
                        .withField("CON", aNumberBuilder("" + abilityScores.constitution.getValue()))
                        .withField("INT", aNumberBuilder("" + abilityScores.intelligence.getValue()))
                        .withField("WIS", aNumberBuilder("" + abilityScores.wisdom.getValue()))
                        .withField("CHA", aNumberBuilder("" + abilityScores.wisdom.getValue()))
                );

        JsonNode json = builder.build();

        JsonFormatter formatter = new PrettyJsonFormatter();

        return formatter.format(json);
    }
}
