package com.example.BounceOrLose;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bernardot on 2/10/16.
 */
public class Ball {
    private Vector2D position;
    private Vector2D velocity;
    private Paint color;
    private double radius;
    private int SCREEN_RADIUS;

    public Ball() {
        this(0, 0, 0, 0, 1, null);
    }

    public Ball(double x, double y, double vx, double vy, double rad, Paint col) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(vx, vy);
        color = col;
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
        c.drawCircle(x, y, (float) SCREEN_RADIUS, color);
    }

    public void updatePosition() {
        position.addScaled(velocity, 0.01);
    }

    public void increaseRadius() {
        radius += .01;
        SCREEN_RADIUS = Math.max(GameModel.convertWorldLengthToScreenLength(radius), 1);
    }
}
