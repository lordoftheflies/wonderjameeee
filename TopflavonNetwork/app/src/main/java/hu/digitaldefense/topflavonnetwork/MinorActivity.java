package hu.digitaldefense.topflavonnetwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MinorActivity extends Activity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minor);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
