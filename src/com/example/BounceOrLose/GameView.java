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

        Paint infoPaint = new Paint();
        infoPaint.setStyle(Paint.Style.FILL);
        infoPaint.setColor(Color.CYAN);
        infoPaint.setAntiAlias(true);
        infoPaint.setTextSize(GameModel.getScreenWidth() / 10);

        Ball b = controller.getModel().getBall();
        List<Wall> walls = controller.getModel().getWalls();
        b.draw(canvas);
        for (Wall w : walls) {
            w.draw(canvas);
        }

        Rect bounds = new Rect();
        scorePaint.getTextBounds(String.valueOf(controller.getModel().getClicks()),0,String.valueOf(controller.getModel().getClicks()).length(),bounds);
        int width = bounds.width();
        canvas.drawText(String.valueOf(controller.getModel().getClicks()), GameModel.getScreenWidth() / 2 - width / 2, GameModel.getScreenHeight() / 16, scorePaint);
        if (controller.getModel().getGameState().equals(Constants.GameStates.PAUSED)) {
            infoPaint.getTextBounds("Game Paused", 0, "Game Paused".length(), bounds);
            width = bounds.width();
            canvas.drawText("Game Paused", GameModel.getScreenWidth() / 2 - width / 2, GameModel.getScreenHeight() / 3 * 0.6f, infoPaint);
            infoPaint.getTextBounds("Click to resume", 0, "Click to resume".length(), bounds);
            width = bounds.width();
            canvas.drawText("Click to resume", GameModel.getScreenWidth() / 2 - width / 2, GameModel.getScreenHeight() / 3, infoPaint);
        } else if (controller.getModel().getGameState().equals(Constants.GameStates.END)) {
            infoPaint.getTextBounds("Game end", 0, "Game end".length(), bounds);
            width = bounds.width();
            canvas.drawText("Game end", GameModel.getScreenWidth() / 2 - width / 2, GameModel.getScreenHeight() / 3 * 0.6f, infoPaint);
            infoPaint.getTextBounds("Click to start", 0, "Click to start".length(), bounds);
            width = bounds.width();
            canvas.drawText("Click to start", GameModel.getScreenWidth() / 2 - width / 2, GameModel.getScreenHeight() / 3, infoPaint);
        }
    }

    @Override
    public void onClick(View v) {
        GameModel model = controller.getModel();
        if (!model.getGameState().equals(Constants.GameStates.END)
                && model.getGameState().equals(Constants.GameStates.COLLISION)) {
            model.setClicks(model.getClicks() + 1);
            model.setGameState(Constants.GameStates.CLICK);
        } else if (model.getGameState().equals(Constants.GameStates.PAUSED)) {
            model.setGameState(Constants.GameStates.MOVING);
        } else if (model.getGameState().equals(Constants.GameStates.END)) {
            model.setGameState(Constants.GameStates.MOVING);
            model.gameInit();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
