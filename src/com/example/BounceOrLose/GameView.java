package com.example.BounceOrLose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by bernardot on 2/10/16.
 */
public class GameView extends View implements View.OnClickListener, View.OnTouchListener {
    BounceOrLoseActivity controller;

    public GameView(Context context) {
        super(context);
        setup(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context);
    }

    public void setup(Context context) {
        controller = (BounceOrLoseActivity) context;
        setOnTouchListener(this);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint scorePaint = new Paint();
        scorePaint.setStyle(Paint.Style.FILL);
        scorePaint.setColor(Color.YELLOW);
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(GameModel.getScreenWidth() / 20);

        Ball b = controller.getModel().getBall();
        List<Wall> walls = controller.getModel().getWalls();
        b.draw(canvas);
        for (Wall w : walls) {
            w.draw(canvas);
        }

        Rect bounds = new Rect();
        scorePaint.getTextBounds(String.valueOf(controller.getModel().getClicks()),0,String.valueOf(controller.getModel().getClicks()).length(),bounds);
        int width = bounds.width();
        canvas.drawText(String.valueOf(controller.getModel().getClicks()), GameModel.getScreenWidth() / 2 - width / 2, GameModel.getScreenHeight() / 20, scorePaint);
    }

    @Override
    public void onClick(View v) {
        GameModel model = controller.getModel();
        if (!model.getGameState().equals(Constants.GameStates.END)
                && model.getGameState().equals(Constants.GameStates.COLLISION)) {
            model.setClicks(model.getClicks() + 1);
            model.setGameState(Constants.GameStates.CLICK);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
