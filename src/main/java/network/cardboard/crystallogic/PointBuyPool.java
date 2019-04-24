package network.cardboard.crystallogic;

/**
 * Written by David Hagerty
 * Written on 2019-04-01
 */
public class PointBuyPool {

    private int pointPool;

    public PointBuyPool() {
        this(15);
    }

    public PointBuyPool(int value) {
        this.pointPool = value;
    }

    public int getPointPool() {
        return pointPool;
    }

    public void setPointPool(int pointPool) {
        this.pointPool = pointPool;
    }
}
