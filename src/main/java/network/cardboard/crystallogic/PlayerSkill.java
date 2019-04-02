package network.cardboard.crystallogic;

import java.util.HashMap;
import network.cardboard.crystallogic.AbilityScores.Ability;

/**
 * Skills in Pathfinder are important to get anything done.
 * Rather than other RPG systems that would rely on a simple
 * "ability check", skills go beyond that by specifying
 * further details in these checks, known as "skill checks".
 * <p>
 * A skill when used without training is rolled for by a
 * simple d20 plus the ability score modifier the skill is
 * based on plus racial bonuses.
 * To train a skill, skill ranks must go into them.
 * Then the bonus stacks up to untrained use plus ranks.
 * Finally, there's the class skill.
 * Class skills only add a bonus if that skill is marked as
 * a class skill by the player character's class <em>and</em> if
 * there is one or more ranks put into it.
 * Once ranks are put in, the total roll adds up to the use
 * of a trained skill + 3.
 *
 * @author Dave Chesser
 */
public class PlayerSkill
{
    public static enum GameSkill {
	// SkillName(Ability.XXX, trainingRequired)
	Acrobatics(Ability.DEX, false),
	Appraise(Ability.INT, false),
	Bluff(Ability.CHA, false),
	Climb(Ability.STR, false),
	Craft(Ability.INT, false),
	Diplomacy(Ability.CHA, false),
	DisableDevice(Ability.DEX, true),
	Disguise(Ability.CHA, false),
	EscapeArtist(Ability.DEX, false),
	Fly(Ability.DEX, false),
	HandleAnimal(Ability.CHA, true),
	Heal(Ability.WIS, false),
	Intimidate(Ability.CHA, false),
	KnowledgeArcana(Ability.INT, true),
	KnowledgeDungeoneering(Ability.INT, true),
	KnowledgeEngineering(Ability.INT, true),
	KnowledgeGeography(Ability.INT, true),
	KnowledgeHistory(Ability.INT, true),
	KnowledgeLocal(Ability.INT, true),
	KnowledgeNature(Ability.INT, true),
	KnowledgeNobility(Ability.INT, true),
	KnowledgePlanes(Ability.INT, true),
	KnowledgeReligion(Ability.INT, true),
	Linguistics(Ability.INT, true),
	Perception(Ability.WIS, false),
	Perform(Ability.CHA, false),
	Profession(Ability.WIS, true),
	Ride(Ability.DEX, false),
	SenseMotive(Ability.WIS, false),
	SleightOfHand(Ability.DEX, true),
	Spellcraft(Ability.INT, true),
	Stealth(Ability.DEX, false),
	Survival(Ability.WIS, false),
	Swim(Ability.STR, false),
	UseMagicDevice(Ability.CHA, true);

	private final Ability abilityUsed;
	private final boolean isTrainingRequired;

	GameSkill(Ability abilityUsed, boolean isTrainingRequired)
	{
	    this.abilityUsed = abilityUsed;
	    this.isTrainingRequired = isTrainingRequired;
	}

	private Ability basedOnAbility()
	{
	    return this.abilityUsed;
	}

	private boolean isTrainingRequired()
	{
	    return this.isTrainingRequired;
	}

	public String getName()
	{
	    return this.toString().replaceAll("(\\p{Upper})", " $1").trim();
	}
    }

    GameSkill skill;
    int ranks;
    boolean isClassSkill;

    public PlayerSkill(GameSkill skill)
    {
	this.skill = skill;
	this.ranks = 0;
	this.isClassSkill = false;
    }

    public static HashMap<GameSkill, PlayerSkill> skillList()
    {
	HashMap skillSet = new HashMap<GameSkill, PlayerSkill>();

	for (GameSkill s : GameSkill.values()) {
	    skillSet.put(s, new PlayerSkill(s));
	}

	return skillSet;
    }

    public String getName()
    {
	return this.skill.getName();
    }

    public boolean isTrainingRequired()
    {
	return this.skill.isTrainingRequired();
    }

    public boolean isClassSkill()
    {
	return this.isClassSkill;
    }

    public int addRanks(int ranks)
    {
	this.ranks += ranks;
	return this.getRanks();
    }

    public int getRanks()
    {
	return this.ranks;
    }

    public boolean isBasedOnStrength()
    {
	return this.skill.basedOnAbility() == Ability.STR;
    }

    public boolean isBasedOnDexterity()
    {
	return this.skill.basedOnAbility() == Ability.DEX;
    }

    public boolean isBasedOnConstitution()
    {
	return this.skill.basedOnAbility() == Ability.CON;
    }

    public boolean isBasedOnIntelligence()
    {
	return this.skill.basedOnAbility() == Ability.INT;
    }

    public boolean isBasedOnWisdom()
    {
	return this.skill.basedOnAbility() == Ability.WIS;
    }

    public boolean isBasedOnCharisma()
    {
	return this.skill.basedOnAbility() == Ability.CHA;
    }
}