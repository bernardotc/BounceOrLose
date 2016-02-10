package com.example.BounceOrLose;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class BounceOrLoseActivity extends Activity {

    GameModel model;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get screen resolution
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Create model object
        model = new GameModel(metrics.widthPixels, metrics.heightPixels);

        setContentView(R.layout.main);
    }
}
