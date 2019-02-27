package network.cardboard.crystallogic;

import network.cardboard.crystallogic.AbilityScores.BuildMethod;

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

    public PlayerCharacter(String name)
    {
        this.name = name;
        this.abilityScores = new AbilityScores(10);
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

    public String getName()
    {
	return this.name;
    }

    // This toString should probably be replaced when we finish figuring out what json library should be used.
    @Override
    public String toString() {

        String name = "\"name\":\"" + getName() + "\","; // It didn't like just putting name, no idea why
        String aScores = "\"abilityScores\":{" +
                "\"strength\":" + abilityScores.strength.getValue() + "," +
                "\"dexterity\":" + abilityScores.dexterity.getValue() + "," +
                "\"constitution\":" + abilityScores.constitution.getValue() + "," +
                "\"intelligence\":" + abilityScores.intelligence.getValue() + "," +
                "\"wisdom\":" + abilityScores.wisdom.getValue() + "," +
                "\"charisma\":" + abilityScores.charisma.getValue() + "}";

        return "{"+(name + aScores) + "}";
    }
}
