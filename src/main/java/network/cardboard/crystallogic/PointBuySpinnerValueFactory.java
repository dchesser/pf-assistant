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
        int startValue = this.getValue();
        if (startValue + 1 > 10) {
            int targetValue = startValue + 1;
            int cost = (int) Math.ceil((targetValue - 10) / 2.);
            System.out.println("Points change: " + cost);
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(cost));
        } else if (startValue - 1 < 10) {
            int targetValue = startValue - 1;
            int cost = (int) Math.floor((targetValue - 10) / 2.);
            System.out.println("Cost: " + cost);
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(cost));
        } else {
            int pointsChange = 0;
            System.out.println("Points change: " + pointsChange);
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(pointsChange));
        }
    }

    private void calculatePointsChangeIncrement() {
        int startValue = this.getValue();
        if (startValue + 1 > 10) {
            int targetValue = startValue + 1;
            int cost = (int) Math.ceil((targetValue - 10) / 2.);
            System.out.println("Points change: " + cost);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(cost));
        } else if (startValue - 1 < 10) {
            int targetValue = startValue - 1;
            int cost = (int) Math.floor((targetValue - 10) / 2.);
            System.out.println("Cost: " + cost);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(cost));
        } else {
            int cost = 0;
            System.out.println("Points change: " + cost);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(cost));
        }
    }
}
