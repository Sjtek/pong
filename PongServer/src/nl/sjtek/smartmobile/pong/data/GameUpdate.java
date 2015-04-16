package nl.sjtek.smartmobile.pong.data;

import java.io.Serializable;
import java.util.UUID;


public class GameUpdate extends ClientUpdate implements Serializable {

    private long tick = 0;

    private final int ballSize = 10;
    private int ballX, ballY;
    private final int batLength = 75;
    private final int batHeight = 10;
    private int topBatX, topBatY;
    private int bottomBatX, bottomBatY;
    private boolean doClose = false;

    public GameUpdate() {
        super(null);
    }

    public GameUpdate(UUID uuid) {
        super(uuid);
    }

    public GameUpdate(boolean doClose) {
        super(null);
        doClose = true;
    }

    public GameUpdate(long tick, int ballX, int ballY, int topBatX, int topBatY, int bottomBatX, int bottomBatY) {
        super(null);
        this.tick = tick;
        this.ballX = ballX;
        this.ballY = ballY;
        this.topBatX = topBatX;
        this.topBatY = topBatY;
        this.bottomBatX = bottomBatX;
        this.bottomBatY = bottomBatY;
    }

    public long getTick() {
        return tick;
    }

    public int getBallSize() {
        return ballSize;
    }

    public int getBallX() {
        return ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public int getBatLength() {
        return batLength;
    }

    public int getBatHeight() {
        return batHeight;
    }

    public int getTopBatX() {
        return topBatX;
    }

    public int getTopBatY() {
        return topBatY;
    }

    public int getBottomBatX() {
        return bottomBatX;
    }

    public int getBottomBatY() {
        return bottomBatY;
    }

    public boolean isDoClose() {
        return doClose;
    }
}
