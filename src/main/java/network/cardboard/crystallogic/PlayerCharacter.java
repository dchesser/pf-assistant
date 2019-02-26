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

    public String getName()
    {
	return this.name;
    }
}
