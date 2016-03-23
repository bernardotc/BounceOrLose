package com.example.BounceOrLose;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by bernardot on 2/14/16.
 */
public class InstructionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText(getResources().getString(R.string.instructions));
        setContentView(textView);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                // Assumming the parent activity is on the back of the stack.
                // The alternate way is commented out.
                // this.startActivity(upIntent);
                // NavUtils.navigateUpTo(this, upIntent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
