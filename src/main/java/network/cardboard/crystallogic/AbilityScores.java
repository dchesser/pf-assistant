package network.cardboard.crystallogic;

public class AbilityScores
{
    public AbilityScore strength;
    public AbilityScore dexterity;
    public AbilityScore constitution;
    public AbilityScore intelligence;
    public AbilityScore wisdom;
    public AbilityScore charisma;

    public AbilityScores()
    {
	this.strength = new AbilityScore(0);
	this.dexterity = new AbilityScore(0);
	this.constitution = new AbilityScore(0);
	this.intelligence = new AbilityScore(0);
	this.wisdom = new AbilityScore(0);
	this.charisma = new AbilityScore(0);
    }

    public AbilityScores(int defaultValue)
    {
	this.strength = new AbilityScore(defaultValue);
	this.dexterity = new AbilityScore(defaultValue);
	this.constitution = new AbilityScore(defaultValue);
	this.intelligence = new AbilityScore(defaultValue);
	this.wisdom = new AbilityScore(defaultValue);
	this.charisma = new AbilityScore(defaultValue);
    }

    public AbilityScores(int st, int dx, int cn,
			 int in, int ws, int ch)
    {
	this.strength = new AbilityScore(st);
	this.dexterity = new AbilityScore(dx);
	this.constitution = new AbilityScore(cn);
	this.intelligence = new AbilityScore(in);
	this.wisdom = new AbilityScore(ws);
	this.charisma = new AbilityScore(ch);
    }

    @Override
    public String toString()
    {
	return String.format("STR %s\tDEX %s\tCON %s\t" +
			     "INT %s\tWIS %s\tCHA %s",
			     this.strength.toString(),
			     this.dexterity.toString(),
			     this.constitution.toString(),
			     this.intelligence.toString(),
			     this.wisdom.toString(),
			     this.charisma.toString());
    }

    public class AbilityScore
    {
	int score;

	private AbilityScore(int value)
	{
	    this.score = value;
	}

	public int getValue()
	{
	    return this.score;
	}

	public int setValue(int value)
	{
	    return this.score = value;
	}

	public int getModifier()
	{
	    // Thanks to William for correcting the formula.
	    return (this.score / 2) - 5;
	}

	@Override
	public String toString()
	{
	    return String.format("%d (%+d)",
				 this.getValue(), this.getModifier());
	}
    }
}
