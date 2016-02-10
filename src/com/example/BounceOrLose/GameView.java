package com.example.BounceOrLose;

import android.content.Context;
import android.graphics.Canvas;
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
        Ball b = controller.getModel().getBall();
        List<Wall> walls = controller.getModel().getWalls();
        b.draw(canvas);
        for (Wall w : walls) {
            w.draw(canvas);
        }
    }

    @Override
    public void onClick(View v) {
        if (!controller.getModel().getGameState().equals(Constants.GameStates.END))
            controller.getModel().setGameState(Constants.GameStates.CLICK);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
