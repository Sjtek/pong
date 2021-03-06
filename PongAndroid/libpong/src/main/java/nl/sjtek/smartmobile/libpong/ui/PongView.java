package nl.sjtek.smartmobile.libpong.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nl.sjtek.smartmobile.libpong.game.PongState;
import nl.sjtek.smartmobile.libpong.game.GameUpdater;

/**
 * A view to display pong
 */
public class PongView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread = new GameThread();
    private final SurfaceHolder surfaceHolder;
    private PongState pongState = new PongState(0, 0);

    private boolean delayedStart = true;
    private boolean multiplayer;
    private boolean host;

    public PongView(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    /**
     * Start the PongView in singleplayer mode.
     */
    public void setSingleplayer() {
        multiplayer = host = false;
        delayedStart = false;
    }

    /**
     * Start the PongView in multiplayer mode
     * @param isHost Host switch. (Will disable game calculations if not host)
     */
    public void setMultiplayer(boolean isHost) {
        multiplayer = true;
        host = isHost;
        delayedStart = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        pongState = new PongState(this.getWidth(), this.getHeight());
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Change the X position of the bottom bat.
     *
     * @param x New X position of the bottom bat
     */
    public void setBottomBatX(int x) {
        thread.setBottomBatX(x);
    }

    /**
     * Change the X position of the top bat.
     * <p>Only for multiplayer.</p>
     *
     * @param x New X position of the top bat
     */
    public void setTopBatX(int x) {
        thread.setTopBatX(x);
    }

    /**
     * Get the current {@link nl.sjtek.smartmobile.libpong.game.PongState}.
     *
     * @return The PongState
     */
    public PongState getPongState() {
        return pongState;
    }

    public void setPongState(PongState pongState) {
        this.pongState = pongState;
    }


    private class GameThread extends Thread {

        private int bottomBatX = 0;
        private int topBatX = 0;

        private boolean running = true;

        public void setBottomBatX(int x) {
            this.bottomBatX = x;
        }

        public void setTopBatX(int x) {
            topBatX = x;
        }

        @Override
        public void run() {

            while (delayedStart) ;

            Canvas canvas = null;
            while (running) {
                try {
                    canvas = getHolder().lockCanvas();
                    synchronized (surfaceHolder) {
                        if ((multiplayer && host)) {
                            GameUpdater.update(pongState, bottomBatX, topBatX);
                        } else if (!multiplayer) {
                            GameUpdater.update(pongState, bottomBatX);
                        }

                        if (multiplayer && !host) {
                            GameUpdater.draw(canvas, pongState, true);
                        } else {
                            GameUpdater.draw(canvas, pongState, false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    running = false;
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}