package network.cardboard.crystallogic;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

/**
 * Ability Scores represent a Player Character's ability to interact
 * with the world around them. These ability scores will affect feats
 * and skills for the player to accomplish. The six ability scores are
 * strength (lifting and swinging swords), dexterity (armo rand
 * shooting bows),constitution (general health and hit points),
 * intelligence (knowledge and magic), wisdom (willpower and
 * awareness), and charisma (speechcraft animals).
 */
public class AbilityScores
{
    public static enum Ability {
	NUL, STR, DEX, CON, INT, WIS, CHA
    }

    public AbilityScore strength;
    public AbilityScore dexterity;
    public AbilityScore constitution;
    public AbilityScore intelligence;
    public AbilityScore wisdom;
    public AbilityScore charisma;

    public static enum BuildMethod {
        CLASSICAL,              // 3d6
        MODERN,                 // 4d6 - minimum value
        HEROIC,                 // 2d6 + 6
    }

    public AbilityScores()
    {
        this.strength = new AbilityScore(1);
        this.dexterity = new AbilityScore(1);
        this.constitution = new AbilityScore(1);
        this.intelligence = new AbilityScore(1);
        this.wisdom = new AbilityScore(1);
        this.charisma = new AbilityScore(1);
    }

    public AbilityScores(BuildMethod method)
    {
        int[] scores = new int[6];

	// Makes me wish there was Haskell's pattern matching here.
        switch (method) {
        case CLASSICAL:
            scores = IntStream
                .generate(AbilityScores::rollClassicalMethod)
                .limit(6)
                .toArray();
            break;
        case MODERN:
            scores = IntStream
                .generate(AbilityScores::rollModernMethod)
                .limit(6)
                .toArray();
            break;
        case HEROIC:
            scores = IntStream
                .generate(AbilityScores::rollHeroicMethod)
                .limit(6)
                .toArray();
            break;
        }

        this.strength = new AbilityScore(scores[0]);
        this.dexterity = new AbilityScore(scores[1]);
        this.constitution = new AbilityScore(scores[2]);
        this.intelligence = new AbilityScore(scores[3]);
        this.wisdom = new AbilityScore(scores[4]);
        this.charisma = new AbilityScore(scores[5]);
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

    private static int rollClassicalMethod()
    {
        int sum = IntStream
            .generate(Die.d6::roll)
            .limit(3)
            .sum();

        return sum;
    }

    private static int rollModernMethod()
    {
        IntSummaryStatistics rolls = IntStream
            .generate(Die.d6::roll)
            .limit(4)
            .summaryStatistics();
        int sum = Math.toIntExact(rolls.getSum());
        int min = rolls.getMin();

        return (sum - min);
    }

    private static int rollHeroicMethod()
    {
        int sum = IntStream
            .generate(Die.d6::roll)
            .limit(2)
            .sum();

        return (sum + 6);
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
