package network.cardboard.crystallogic;

import javafx.scene.control.SpinnerValueFactory;

/**
 * Written by David Hagerty
 * Written on 2019-04-01
 */
public class PointBuySpinnerValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

    public PointBuyPool pointsLeft;

    public PointBuySpinnerValueFactory(int min, int max, int initialValue, PointBuyPool pointsLeft) {
        super(min, max, initialValue);
        this.pointsLeft = pointsLeft;
    }

    @Override
    public void decrement(int steps) {
        calculatePointsChangeDecrement();
        super.decrement(steps);
    }

    @Override
    public void increment(int steps) {
        calculatePointsChangeIncrement();
        super.increment(steps);
    }

    private void calculatePointsChangeDecrement() {
        int value = this.getValue();
        if (value + 1 > 10) {
            value += 1;
            int pointsChange = (int) Math.ceil((value - 10) / 2.);
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(pointsChange));
        } else if (value - 1 < 10) {
            value -= 1;
            int pointsChange = (int) Math.floor((value - 10) / 2.);
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(pointsChange));
        } else {
            int pointsChange = 0;
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(pointsChange));
        }
    }

    private void calculatePointsChangeIncrement() {
        int value = this.getValue();
        if (value + 1 > 10) {
            value += 1;
            int pointsChange = (int) Math.ceil((value - 10) / 2.);
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(pointsChange));
        } else if (value - 1 < 10) {
            value -= 1;
            int pointsChange = (int) Math.floor((value - 10) / 2.);
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(pointsChange));
        } else if (value - 1 == 10 || value + 1 == 10) {
            int pointsChange = 0;
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(pointsChange));
        }
    }
}
