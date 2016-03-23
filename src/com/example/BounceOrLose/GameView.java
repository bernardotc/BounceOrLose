package com.example.BounceOrLose;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

/**
 * Created by bernardot on 2/10/16.
 */
public class GameView extends SurfaceView implements View.OnClickListener, View.OnTouchListener {
    private BounceOrLoseActivity controller;
    private Bitmap virus = BitmapFactory.decodeResource(getResources(), R.drawable.virus);
    private Bitmap virusReduce = BitmapFactory.decodeResource(getResources(), R.drawable.virus_reduce);

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

    public void draw() {
        SurfaceHolder holder = getHolder();
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                onDraw(canvas);
            }
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        Ball b = controller.getModel().getBall();
        List<Wall> walls = controller.getModel().getWalls();

        if (GameModel.getPowerUp().getType().equals(Constants.PowerUps.REDUCE_SIZE)) {
            b.draw(canvas, virusReduce);
        } else {
            b.draw(canvas, virus);
        }
        for (Wall w : walls) {
            w.draw(canvas);
        }

        Rect bounds = new Rect();
        Constants.scorePaint.getTextBounds(String.valueOf(controller.getModel().getScore()),0,String.valueOf(controller.getModel().getScore()).length(),bounds);
        int width = bounds.width();
        canvas.drawText(String.valueOf(controller.getModel().getScore()), GameModel.getScreenWidthStatic() / 2 - width / 2, GameModel.getScreenHeightStatic() / 16, Constants.scorePaint);
        if (controller.getModel().getGameState().equals(Constants.GameStates.PAUSED)) {
            drawMessage(Constants.pauseMessage, Constants.infoPaint, Constants.resumeMessage, Constants.infoPaint, canvas);
        } else if (controller.getModel().getGameState().equals(Constants.GameStates.END)) {
            drawMessage(Constants.endMessage, Constants.infoPaint, Constants.restartMessage, Constants.infoPaint, canvas);
        } else if (controller.getModel().getGameState().equals(Constants.GameStates.START)) {
            drawMessage(Constants.titleMessage, Constants.infoPaint, Constants.startMessage, Constants.infoPaint, canvas);
        }
    }

    public void drawMessage(String textUp, Paint up, String textDown, Paint down, Canvas canvas) {
        Rect bounds = new Rect();
        int width;
        up.getTextBounds(textUp, 0, textUp.length(), bounds);
        width = bounds.width();
        canvas.drawText(textUp, GameModel.getScreenWidthStatic() / 2 - width / 2, GameModel.getScreenHeightStatic() / 3 * 0.6f, up);
        down.getTextBounds(textDown, 0, textDown.length(), bounds);
        width = bounds.width();
        canvas.drawText(textDown, GameModel.getScreenWidthStatic() / 2 - width / 2, GameModel.getScreenHeightStatic() / 3, down);
    }

    @Override
    public void onClick(View v) {
        GameModel model = controller.getModel();
        if (!model.getGameState().equals(Constants.GameStates.END)
                && model.getGameState().equals(Constants.GameStates.COLLISION)) {
            if (model.getPowerUp().getType().equals(Constants.PowerUps.DOUBLE_POINTS)) {
                model.setScore(model.getScore() + 2);
            } else {
                model.setScore(model.getScore() + 1);
            }
            model.setGoodClicks(model.getGoodClicks() + 1);
            model.setGameState(Constants.GameStates.CLICK);
        } else if (model.getGameState().equals(Constants.GameStates.PAUSED)) {
            model.setGameState(Constants.GameStates.MOVING);
        } else if (model.getGameState().equals(Constants.GameStates.END)) {
            model.setGameState(Constants.GameStates.START);
            model.gameInit();
        } else if (model.getGameState().equals(Constants.GameStates.START)) {
            model.setGameState(Constants.GameStates.MOVING);
        } else if (model.getGameState().equals(Constants.GameStates.MOVING)) {
            model.setBadClicks(model.getBadClicks() + 1);
            model.getBall().increaseRadius();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
