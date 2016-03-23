package com.example.BounceOrLose;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by bernardot on 3/23/16.
 */
public class ButtonWithTouch implements View.OnTouchListener {
    ImageView imageButton;

    public ButtonWithTouch(ImageView imageButton) {
        this.imageButton = imageButton;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageButton.setImageResource(R.drawable.button_hover);
                break;
            case MotionEvent.ACTION_UP:
                imageButton.setImageResource(R.drawable.button);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                imageButton.setImageResource(R.drawable.button);
                break;
            default:

                break;
        }
        return false;
    }
}
