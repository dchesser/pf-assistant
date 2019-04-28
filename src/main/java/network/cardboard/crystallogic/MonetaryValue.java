package network.cardboard.crystallogic;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MonetaryValue
{
    private int value; 		// In Cuprous Pieces (cp), base unit.

    // Avoid. Convert from known units instead of something arbitrary.
    private MonetaryValue(int value)
    {
	this.value = value;
    }

    public static MonetaryValue inCopper(int cp)
    {
	return new MonetaryValue(cp);
    }

    public static MonetaryValue inSilver(int sp)
    {
	return new MonetaryValue(sp * 10);
    }

    public static MonetaryValue inGold(int gp)
    {
	return new MonetaryValue(gp * 100);
    }

    public static MonetaryValue inPlatinum(int pp)
    {
	return new MonetaryValue(pp * 1000);
    }

    /* Could have sworn electrum was a Pathfinder denomination.
     * Can't find it in the SRD, core rulebook, or on-line wikis.
     * Must be a D&D specialty. 🤷
     */

    // For display purposes only!
    // To compare, refer to base values.

    public int toCopper()
    {
	return new Integer(this.value);
    }

    public float toSilver()
    {
	return new Integer(this.value).floatValue() / 10;
    }

    public float toGold()
    {
	return new Integer(this.value).floatValue() / 100;
    }

    public float toPlatinum()
    {
	return new Integer(this.value).floatValue() / 1000;
    }

    public ObservableValue<Integer> getValue() {
        return new ObservableValue<Integer>() {

            @Override
            public void addListener(ChangeListener<? super Integer> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Integer> listener) {

            }

            @Override
            public Integer getValue() {
                return toCopper();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }

    @Override
    public String toString()
    {
	return String.format("%d", this.value);
    }
}
