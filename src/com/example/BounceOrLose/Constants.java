package com.example.BounceOrLose;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bernardot on 2/10/16.
 */
public class Constants {

    // BounceOrLoseActivty Constants
    static String modelKey;
    static String saveFile;
    static int delay = 20;

    static String backTitleDialog, backMessageDialog, backMessagePositive, backMessageNegative;

    // GameView Constants
    static String pauseMessage, endMessage, titleMessage, resumeMessage, restartMessage, startMessage;

    static Paint paintBall, paintWall, scorePaint, infoPaint;

    static {
        paintBall = new Paint();
        paintBall.setColor(Color.BLUE);
        paintBall.setStyle(Paint.Style.FILL);
        paintBall.setAntiAlias(true);

        paintWall = new Paint();
        paintWall.setColor(Color.WHITE);
        paintWall.setStyle(Paint.Style.FILL);
        paintWall.setAntiAlias(true);

        scorePaint = new Paint();
        scorePaint.setStyle(Paint.Style.FILL);
        scorePaint.setColor(Color.YELLOW);
        scorePaint.setAntiAlias(true);

        infoPaint = new Paint();
        infoPaint.setStyle(Paint.Style.FILL);
        infoPaint.setColor(Color.CYAN);
        infoPaint.setAntiAlias(true);
    }

    // GameModel Constants
    enum GameStates {
        COLLISION, MOVING, CLICK, END, PAUSED, START
    }

    static double worldWidth = 10;
    static double ballScaleFactor = 20;
    static double wallScaleFactor = 16;
    static double ballInitialVelocityX = 5;

    // Ball Constants
    static double increaseRadiusFactor = 0.01;
    static double deltaTime = 0.01;

    // Wall Constatns
    static double coefficientOfRestitution = 1.001;
}
