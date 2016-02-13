package com.example.BounceOrLose;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

/**
 * Created by bernardot on 2/10/16.
 */
public class Ball implements Serializable {
    private Vector2D position;
    private Vector2D velocity;
    private double radius;
    private int SCREEN_RADIUS;

    public Ball() {
        this(0, 0, 0, 0, 1);
    }

    public Ball(double x, double y, double vx, double vy, double rad) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(vx, vy);
        radius = rad;
        SCREEN_RADIUS = Math.max(GameModel.convertWorldLengthToScreenLength(radius), 1);
    }

    public double getRadius() {
        return radius;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void draw(Canvas c) {
        int x = GameModel.convertWorldXtoScreenX(position.getX());
        int y = GameModel.convertWorldYtoScreenY(position.getY());
        c.drawCircle(x, y, (float) SCREEN_RADIUS, Constants.paintBall);
    }

    public void updatePosition() {
        position.addScaled(velocity, 0.01);
    }

    public void increaseRadius() {
        radius += .01;
        SCREEN_RADIUS = Math.max(GameModel.convertWorldLengthToScreenLength(radius), 1);
    }
}
