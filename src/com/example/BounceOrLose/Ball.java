package com.example.BounceOrLose;

import android.graphics.Color;

/**
 * Created by bernardot on 2/10/16.
 */
public class Ball {
    private Vector2D position;
    private Vector2D velocity;
    private int color;
    private double radius;

    public Ball() {
        this(0, 0, 0, 0, Color.BLUE, 1);
    }

    public Ball(double x, double y, double vx, double vy, int col, double rad) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(vx, vy);
        color = col;
        radius = rad;
    }


}
