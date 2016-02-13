package com.example.BounceOrLose;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bernardot on 2/10/16.
 */
public class Constants {
    enum GameStates {
        COLLISION, MOVING, CLICK, END, PAUSED, START
    }
    static String modelKey = "Model";
    static String saveFile = "savedGameData";
    static String backTitleDialog = "Leaving to soon? Thought you were having fun.";
    static String backMessageDialog = "Are you sure?";

    static Paint paintBall, paintWall;

    static {
        paintBall = new Paint();
        paintBall.setColor(Color.BLUE);
        paintBall.setStyle(Paint.Style.FILL);
        paintBall.setAntiAlias(true);

        paintWall = new Paint();
        paintWall.setColor(Color.WHITE);
        paintWall.setStyle(Paint.Style.FILL);
        paintWall.setAntiAlias(true);
    }



}
