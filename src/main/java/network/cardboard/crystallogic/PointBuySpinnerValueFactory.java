package network.cardboard.crystallogic;

import javafx.scene.control.SpinnerValueFactory;

/**
 * Written by David Hagerty
 * Written on 2019-04-01
 */
public class PointBuySpinnerValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

    private int pointsLeft;

    public PointBuySpinnerValueFactory(int min, int max) {
        super(min, max);
    }

    public PointBuySpinnerValueFactory(int min, int max, int initialValue) {
        super(min, max, initialValue);
    }

    public PointBuySpinnerValueFactory(int min, int max, int initialValue, int pointsLeft) {
        super(min, max, initialValue);
        this.pointsLeft = pointsLeft;
    }

    @Override
    public void decrement(int steps) {
        calculatePointsChange();
        super.decrement(steps);
    }

    @Override
    public void increment(int steps) {
        calculatePointsChange();
        super.increment(steps);
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

    public void setPointsLeft(int pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    private void calculatePointsChange() {
        int value = this.getValue();
        if (value >= 10) {
            int pointsChange = (int) Math.ceil((value - 10) / 2.);
            pointsLeft -= pointsChange;
        } else if (value < 10) {
            int pointsChange = (int) Math.floor((value - 10) / 2.);
            pointsLeft -= pointsChange;
        }
    }
}
