package com.example.BounceOrLose;

/**
 * Created by bernardot on 2/10/16.
 */
public class GameModel {
    private static double worldWidth = 10;
    private static double worldHeight;
    private static int screenHeight, screenWidth;

    public GameModel(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        worldHeight = screenHeight * (worldWidth / screenWidth);
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
