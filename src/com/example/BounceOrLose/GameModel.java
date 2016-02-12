package com.example.BounceOrLose;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bernardot on 2/10/16.
 */
public class GameModel implements Serializable {
    private static double worldWidth = 10;
    private static double worldHeight;
    private static int screenHeight, screenWidth;
    private Constants.GameStates gameState;
    private double ballScaleFactor = 20;
    private double wallScaleFactor = 16;
    private int clicks;
    private double differenceFictionalReal;
    
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

    public GameModel(int width, int height, boolean portrait) {
        screenWidth = width;
        screenHeight = height;
        if (!portrait) {
            double fictionalWidth = worldWidth * ((double) screenWidth / (double) screenHeight);
            double fictionalHeight = screenHeight * (worldWidth / screenWidth);
            double fictionalBallScaleFactor = ballScaleFactor * fictionalWidth / 10;
            double fictionalWallScaleFactor = wallScaleFactor * fictionalWidth / 10;
            double fictionalLeftWallX = fictionalWidth / fictionalWallScaleFactor;
            double fictionalRightWallX = fictionalWidth * (1 - (1 / fictionalWallScaleFactor));
            differenceFictionalReal = (fictionalRightWallX - fictionalLeftWallX) - (worldWidth * (1 - (1 / wallScaleFactor)) - worldWidth / wallScaleFactor);
            System.out.println(differenceFictionalReal);

            worldWidth = worldWidth * ((double) screenWidth / (double) screenHeight);
            ballScaleFactor = ballScaleFactor * worldWidth / 10;
            wallScaleFactor = wallScaleFactor * worldWidth / 10;
        } else {
            worldWidth = 10;
            differenceFictionalReal = 0;
        }
        worldHeight = screenHeight * (worldWidth / screenWidth);

        System.out.println(screenWidth + ":" + screenHeight);
        System.out.println(worldWidth + ":" + worldHeight);
        System.out.println(ballScaleFactor);
        System.out.println(wallScaleFactor);
        gameState = Constants.GameStates.PAUSED;
        gameInit();
    }

    public void gameInit() {
        clicks = 0;
        ball = new Ball(worldWidth / 2, worldHeight / 2 + 1, 10, 0, worldWidth / ballScaleFactor, paintBall);
        walls = new ArrayList<>();
        walls.add(new Wall(worldWidth / wallScaleFactor + differenceFictionalReal / 2, worldHeight, worldWidth / wallScaleFactor + differenceFictionalReal / 2, 0, paintWall));
        walls.add(new Wall(worldWidth * (1 - (1 / wallScaleFactor)) - differenceFictionalReal / 2, 0, worldWidth * (1 - (1 / wallScaleFactor)) - differenceFictionalReal / 2, worldHeight, paintWall));
        System.out.println(worldWidth / wallScaleFactor + differenceFictionalReal / 2);
        System.out.println(worldWidth * (1 - (1 / wallScaleFactor)) - differenceFictionalReal / 2);
    }

    public void update(int delay) {
        if (gameState.equals(Constants.GameStates.MOVING)) {
            ball.updatePosition();
            for (Wall wall : walls) {
                if (wall.isBallCollidingWall(ball.getPosition(), ball.getRadius())) {
                    ball.setVelocity(wall.calculateVelocityAfterACollision(ball.getVelocity()));
                    gameState = Constants.GameStates.COLLISION;
                }
            }
            checkBallTouchingTwoWalls();
        } else if (gameState.equals(Constants.GameStates.COLLISION)) {
            ball.increaseRadius();
            checkBallTouchingTwoWalls();
        } else if (gameState.equals(Constants.GameStates.CLICK)) {
            boolean touching = false;
            ball.updatePosition();
            for (Wall wall : walls) {
                if (wall.isBallCollidingWall(ball.getPosition(), ball.getRadius())) {
                    touching = true;
                    break;
                }
            }
            if (!touching) {
                gameState = Constants.GameStates.MOVING;
            }
            checkBallTouchingTwoWalls();
        }
    }

    public void checkBallTouchingTwoWalls() {
        if (walls.get(0).isBallCollidingWall(ball.getPosition(), ball.getRadius())
                && walls.get(1).isBallCollidingWall(ball.getPosition(), ball.getRadius())) {
            gameState = Constants.GameStates.END;
        }
    }

    public Ball getBall() {
        return ball;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setGameState(Constants.GameStates gameState) {
        this.gameState = gameState;
    }

    public Constants.GameStates getGameState() {
        return gameState;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
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
