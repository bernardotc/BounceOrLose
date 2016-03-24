package com.example.VirusAttack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

    RelativeLayout intructionsButton;
    ImageView imageIntructionsButton;
    TextView textIntructionsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startButton = (RelativeLayout) findViewById(R.id.button_start);
        imageStartButton = (ImageView) findViewById(R.id.img_button);
        textStartView = (TextView) findViewById(R.id.text_start);
        titleView = (TextView) findViewById(R.id.title_game);

        intructionsButton = (RelativeLayout) findViewById(R.id.button_instructions);
        imageIntructionsButton = (ImageView) findViewById(R.id.img_buttonInstructions);
        textIntructionsView = (TextView) findViewById(R.id.text_instructions);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "Acidic.TTF");
        textStartView.setTypeface(customFont);
        titleView.setTypeface(customFont);
        textIntructionsView.setTypeface(customFont);

        startButton.setOnTouchListener(new ButtonWithTouch(imageStartButton));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MenuActivity.this, VirusActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(myIntent);
            }
        });

        intructionsButton.setOnTouchListener(new ButtonWithTouch(imageIntructionsButton));

        intructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MenuActivity.this, InstructionsActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(myIntent);
            }
        });
    }
}