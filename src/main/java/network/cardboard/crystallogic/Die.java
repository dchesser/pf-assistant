package network.cardboard.crystallogic;

import java.util.Random;

public class Die
{
    public static Die d4 = new Die(4);
    public static Die d6 = new Die(6);
    public static Die d8 = new Die(8);
    public static Die d10 = new Die(10);
    public static Die d12 = new Die(12);
    public static Die d20 = new Die(20);
    public static Die d100 = new Die(100);

    private final int sides;

    public Die(int sides)
    {
	this.sides = sides;
    }

    public int roll()
    {
	return new Random().nextInt(this.sides) + 1;
    }
}
