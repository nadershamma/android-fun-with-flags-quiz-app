package com.nadershamma.apps.androidfunwithflags;

import android.os.Bundle;
import android.app.Activity;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
