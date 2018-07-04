package com.nadershamma.apps.androidfunwithflags;

import android.app.Fragment;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nadershamma.apps.lifecyclehelpers.QuizViewModel;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String CHOICES = "pref_numberOfChoices";
    public static final String REGIONS = "pref_regionsToInclude";
    private boolean deviceIsPhone = true;
    private boolean preferencesChanged = true;
    private MainActivityFragment quizFragment;
    private QuizViewModel quizViewModel;
    private OnSharedPreferenceChangeListener preferencesChangeListener;

    private void setSharedPreferences() {
        // set default values in the app's SharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Register a listener for shared preferences changes
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(preferencesChangeListener);
    }

    private void screenSetUp() {
        if (getScreenSize() == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                getScreenSize() == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            deviceIsPhone = false;
        }
        if (deviceIsPhone) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private class PreferencesChangeListener implements OnSharedPreferenceChangeListener {
        private MainActivityFragment quizFragment;
        private QuizViewModel quizViewModel;

        public PreferencesChangeListener(MainActivityFragment quizFragment, QuizViewModel quizViewModel) {
            this.quizFragment = quizFragment;
            this.quizViewModel = quizViewModel;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            preferencesChanged = true;

            if (key.equals(REGIONS)) {
                this.quizViewModel.setGuessRows(sharedPreferences.getString(
                        MainActivity.CHOICES, null));
                this.quizFragment.resetQuiz();
            } else if (key.equals(CHOICES)) {
                Set<String> regions = sharedPreferences.getStringSet(REGIONS, null);
                if (regions != null && regions.size() > 0) {
                    this.quizViewModel.setRegionsSet(sharedPreferences);
                    this.quizFragment.resetQuiz();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    regions.add(getString(R.string.default_region));
                    editor.putStringSet(REGIONS, regions);
                    editor.apply();
                    Toast.makeText(MainActivity.this, R.string.default_region_message,
                            Toast.LENGTH_LONG).show();
                }
            }

            Toast.makeText(MainActivity.this, R.string.restarting_quiz,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        this.quizFragment = (MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.quizFragment);

        this.preferencesChangeListener = new PreferencesChangeListener(
                this.quizFragment, this.quizViewModel);

        this.setContentView(R.layout.activity_main);
        this.setSupportActionBar(toolbar);
        this.setSharedPreferences();
        this.screenSetUp();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged) {
            // TODO call MainActivityFragment Methods
            // Page 143
            preferencesChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
        return super.onOptionsItemSelected(item);
    }

    public int getScreenSize() {
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize;
    }

    public MainActivityFragment getQuizFragment() {
        return this.quizFragment;
    }
}
