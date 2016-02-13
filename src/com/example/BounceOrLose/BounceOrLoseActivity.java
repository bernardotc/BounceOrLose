package com.example.BounceOrLose;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BounceOrLoseActivity extends Activity {

    GameModel model;
    GameView view;
    GameThread game;
    DisplayMetrics metrics;
    boolean portrait = true;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get screen resolution
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Create model object
        portrait = true;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            portrait = false;
        }
        model = new GameModel(metrics.widthPixels, metrics.heightPixels, portrait);

        // Set String and size constants of the app
        setStringSizeConstants();

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
        if (!getModel().getGameState().equals(Constants.GameStates.END) && !getModel().getGameState().equals(Constants.GameStates.START))
            getModel().setGameState(Constants.GameStates.PAUSED);
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
        if (!game.isPortrait() && portrait) {
            getModel().adjustGameToFitScreen(game.getBall(), game.getDifferenceFictionalReal(), game.getClicks(), game.getGameState());
        } else if (game.isPortrait() && !portrait) {
            getModel().adjustGameToFitScreen(game.getBall(), null, game.getClicks(), game.getGameState());
        } else {
            model = game;
        }
    }

    class GameThread extends Thread {
        boolean running = true;

        public void run() {
            while (running) {
                try {
                    getModel().update();
                    view.postInvalidate();
                    Thread.sleep(Constants.delay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Pause the game
            if (!getModel().getGameState().equals(Constants.GameStates.END) && !getModel().getGameState().equals(Constants.GameStates.START))
                getModel().setGameState(Constants.GameStates.PAUSED);

            // Then, continue with the dialog.
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(Constants.backTitleDialog);
            alertDialog.setMessage(Constants.backMessageDialog);

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    Constants.backMessagePositive, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    Constants.backMessageNegative, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing dialog will dismiss
                        }
                    });
            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setStringSizeConstants() {
        Constants.modelKey = getResources().getString(R.string.modelKey);
        Constants.saveFile = getResources().getString(R.string.saveFile);
        Constants.backTitleDialog = getResources().getString(R.string.backTitleDialog);
        Constants.backMessageDialog = getResources().getString(R.string.backMessageDialog);
        Constants.backMessagePositive = getResources().getString(R.string.backMessagePositive);
        Constants.backMessageNegative = getResources().getString(R.string.backMessageNegative);
        Constants.pauseMessage = getResources().getString(R.string.pausedMessage);
        Constants.endMessage = getResources().getString(R.string.endMessage);
        Constants.titleMessage = getResources().getString(R.string.titleMessage);
        Constants.resumeMessage = getResources().getString(R.string.resumeMessage);
        Constants.restartMessage = getResources().getString(R.string.restartMessage);
        Constants.startMessage = getResources().getString(R.string.startMessage);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Constants.scorePaint.setTextSize(GameModel.getScreenWidthStatic() / 30);
            Constants.infoPaint.setTextSize(GameModel.getScreenWidthStatic() / 15);
        } else {
            Constants.scorePaint.setTextSize(GameModel.getScreenWidthStatic() / 20);
            Constants.infoPaint.setTextSize(GameModel.getScreenWidthStatic() / 10);
        }
    }
}
