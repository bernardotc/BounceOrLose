package com.example.BounceOrLose;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bernardot on 3/23/16.
 */
public class MenuActivity extends Activity {

    RelativeLayout startButton;
    ImageView imageStartButton;
    TextView textStartView;
    TextView titleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startButton = (RelativeLayout) findViewById(R.id.button_start);
        imageStartButton = (ImageView) findViewById(R.id.img_button);
        textStartView = (TextView) findViewById(R.id.text_start);
        titleView = (TextView) findViewById(R.id.title_game);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "Acidic.TTF");
        textStartView.setTypeface(customFont);
        titleView.setTypeface(customFont);

        startButton.setOnTouchListener(new ButtonWithTouch(imageStartButton));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call GameActivity to start the game
                Intent myIntent = new Intent(MenuActivity.this, BounceOrLoseActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
