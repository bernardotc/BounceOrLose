package com.example.VirusAttack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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

    boolean otherActivity = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Constants.backgroundMusic = MediaPlayer.create(getApplicationContext(), R.raw.background_sound);
        Constants.backgroundMusic.setLooping(true);

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
                otherActivity = true;
                Intent myIntent = new Intent(MenuActivity.this, VirusActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

        intructionsButton.setOnTouchListener(new ButtonWithTouch(imageIntructionsButton));

        intructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherActivity = true;
                Intent myIntent = new Intent(MenuActivity.this, InstructionsActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        otherActivity = false;
        if (!Constants.backgroundMusic.isPlaying()) {
            try {
                Constants.backgroundMusic.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Constants.backgroundMusic.start();
                    }
                });
                Constants.backgroundMusic.prepareAsync();
            } catch (Exception e) {
                Constants.backgroundMusic.start();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!otherActivity) {
            Constants.backgroundMusic.pause();
        }
    }
}
