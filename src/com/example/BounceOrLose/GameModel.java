package com.example.BounceOrLose;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bernardot on 2/10/16.
 */
public class GameModel {
    private static double worldWidth = 10;
    private static double worldHeight;
    private static int screenHeight, screenWidth;
    
    static Paint paintBall, paintWall;

    private Ball ball;
    private List<Wall> walls;
    
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

    public GameModel(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        worldHeight = screenHeight * (worldWidth / screenWidth);
        gameInit();
    }

    public void gameInit() {
        ball = new Ball(worldWidth / 2, worldHeight / 2, 0, 0, 1, paintBall);
        walls = new ArrayList<>();
        walls.add(new Wall(1, worldHeight, 1, 0, paintWall));
        walls.add(new Wall(worldWidth - 1, 0, worldWidth - 1, worldHeight, paintWall));
    }

    public Ball getBall() {
        return ball;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    // The 3 following methods were taken from the Physics Based Game module
    public static int convertWorldXtoScreenX(double worldX) {
        return (int) (worldX / worldWidth * screenWidth);
    }
    public static int convertWorldYtoScreenY(double worldY) {
        // minus sign in here is because screen coordinates are upside down.
        return (int) (screenHeight - (worldY / worldHeight * screenHeight));
    }
    public static int convertWorldLengthToScreenLength(double worldLength) {
        return (int) (worldLength / worldWidth * screenWidth);
    }
}
