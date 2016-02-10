package com.example.BounceOrLose;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class BounceOrLoseActivity extends Activity {

    GameModel model;
    GameView view;
    GameThread game;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get screen resolution
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Create model object
        model = new GameModel(metrics.widthPixels, metrics.heightPixels);

        // Create view object
        view = new GameView(this);

        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        game = new GameThread();
        game.start();
    }

    public GameModel getModel() {
        return model;
    }

    class GameThread extends Thread {
        boolean running = true;

        public void run() {
            while (running) {
                try {
                    getModel().update(50);
                    view.postInvalidate();
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
