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

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by bernardot on 3/23/16.
 */
public class MenuActivity extends Activity {

    RelativeLayout startButton;
    ImageView imageStartButton;
    TextView textStartView;
    TextView titleView;
    TextView vitaminsView;

    RelativeLayout instructionsButton;
    ImageView imageInstructionsButton;
    TextView textInstructionsView;

    boolean otherActivity = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Constants.backgroundMusic = MediaPlayer.create(getApplicationContext(), R.raw.background_sound);
        Constants.backgroundMusic.setLooping(true);
        Constants.vitaminsSavedFile = getResources().getString(R.string.saveVitaminsFile);

        startButton = (RelativeLayout) findViewById(R.id.button_start);
        imageStartButton = (ImageView) findViewById(R.id.img_button);
        textStartView = (TextView) findViewById(R.id.text_start);
        titleView = (TextView) findViewById(R.id.title_game);

        instructionsButton = (RelativeLayout) findViewById(R.id.button_instructions);
        imageInstructionsButton = (ImageView) findViewById(R.id.img_buttonInstructions);
        textInstructionsView = (TextView) findViewById(R.id.text_instructions);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "Acidic.TTF");
        textStartView.setTypeface(customFont);
        titleView.setTypeface(customFont);
        textInstructionsView.setTypeface(customFont);

        vitaminsView = (TextView) findViewById(R.id.text_vitamin);
        int vitamins = loadVitamins();
        vitaminsView.setText(String.valueOf(vitamins));

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

        instructionsButton.setOnTouchListener(new ButtonWithTouch(imageInstructionsButton));

        instructionsButton.setOnClickListener(new View.OnClickListener() {
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

    public int loadVitamins() {
        int vitamins = 0;
        try {
            FileInputStream in = openFileInput(Constants.vitaminsSavedFile);
            ObjectInputStream obj = new ObjectInputStream(in);
            vitamins = obj.readInt();
            obj.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vitamins;
    }
}
