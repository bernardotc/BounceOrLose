package com.example.BounceOrLose;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BounceOrLoseActivity extends Activity {

    GameModel model;
    GameView view;
    GameThread game;
    boolean portrait = true;

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
        portrait = true;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            portrait = false;
        }
        model = new GameModel(metrics.widthPixels, metrics.heightPixels, portrait);

        // Create view object
        view = new GameView(this);

        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGame();
        game = new GameThread();
        game.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!model.getGameState().equals(Constants.GameStates.END))
            model.setGameState(Constants.GameStates.PAUSED);
        saveGame();
    }

    public void saveGame() {
        try {
            FileOutputStream out = openFileOutput(Constants.saveFile, Context.MODE_PRIVATE);
            ObjectOutputStream obj = new ObjectOutputStream(out);
            obj.writeObject(getModel());
            obj.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try {
            FileInputStream in = openFileInput(Constants.saveFile);
            ObjectInputStream obj = new ObjectInputStream(in);
            GameModel game = (GameModel) obj.readObject();
            loadGameDataToModel(game);
            obj.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.modelKey, getModel());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GameModel game = (GameModel) savedInstanceState.getSerializable(Constants.modelKey);
        loadGameDataToModel(game);
    }

    public GameModel getModel() {
        return model;
    }
    
    public void loadGameDataToModel(GameModel game) {
        System.out.println(game.toString());
        if (!game.isPortrait() && portrait) {
            model.adjustGameToFitScreen(game.getBall(), game.getDifferenceFictionalReal(), game.getClicks(), game.getGameState());
        } else if (game.isPortrait() && !portrait) {
            model.adjustGameToFitScreen(game.getBall(), null, game.getClicks(), game.getGameState());
        } else {
            model = game;
        }
        System.out.println(getModel().toString());
    }

    class GameThread extends Thread {
        boolean running = true;

        public void run() {
            while (running) {
                try {
                    getModel().update(50);
                    view.postInvalidate();
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
