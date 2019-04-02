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
        calculatePointsChangeForDecrement(steps);
    }

    @Override
    public void increment(int steps) {
        calculatePointsChangeForIncrement(steps);
    }

    private void calculatePointsChangeForDecrement(int steps) {
        int startValue = this.getValue();
        if (startValue + 1 > 10) {
            int targetValue = startValue + 1;
            int cost = (int) Math.ceil((targetValue - 10) / 2.);
            setNewPointsValueForDecrement(startValue, cost, steps);
        } else if (startValue - 1 < 10) {
            int targetValue = startValue - 1;
            int cost = (int) Math.floor((targetValue - 10) / 2.);
            setNewPointsValueForDecrement(startValue, cost, steps);
        } else {
            int pointsChange = 0;
            pointsLeft.setPointPool(pointsLeft.getPointPool() + Math.abs(pointsChange));
        }
    }

    private void calculatePointsChangeForIncrement(int steps) {
        int startValue = this.getValue();
        if (startValue + 1 > 10) {
            int targetValue = startValue + 1;
            int cost = (int) Math.ceil((targetValue - 10) / 2.);
            System.out.println("Points change: " + cost);
            setNewPointsValueForIncrement(startValue, cost, steps);
        } else if (startValue - 1 < 10) {
            int targetValue = startValue - 1;
            int cost = (int) Math.floor((targetValue - 10) / 2.);
            System.out.println("Cost: " + cost);
            setNewPointsValueForIncrement(startValue, cost, steps);
        } else {
            int cost = 0;
            System.out.println("Points change: " + cost);
            pointsLeft.setPointPool(pointsLeft.getPointPool() - Math.abs(cost));
        }
    }

    private void setNewPointsValueForDecrement(int startValue, int cost, int steps) {
        int newPointsValue = pointsLeft.getPointPool() + Math.abs(cost);
        if (newPointsValue <= 0) {
            this.setValue(startValue);
        } else {
            pointsLeft.setPointPool(newPointsValue);
            super.decrement(steps);
        }
    }

    private void setNewPointsValueForIncrement(int startValue, int cost, int steps) {
        int newPointsValue = pointsLeft.getPointPool() - Math.abs(cost);
        if (newPointsValue <= 0) {
            this.setValue(startValue);
        } else {
            pointsLeft.setPointPool(newPointsValue);
            super.increment(steps);
        }
    }
}
