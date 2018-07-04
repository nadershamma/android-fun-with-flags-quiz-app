package com.nadershamma.apps.eventhandlers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.widget.Toast;

import com.nadershamma.apps.androidfunwithflags.MainActivity;
import com.nadershamma.apps.androidfunwithflags.MainActivityFragment;
import com.nadershamma.apps.androidfunwithflags.R;
import com.nadershamma.apps.lifecyclehelpers.QuizViewModel;

import java.util.Set;

public class PreferenceChangeListener implements OnSharedPreferenceChangeListener {
    private MainActivityFragment quizFragment;
    private QuizViewModel quizViewModel;
    private MainActivity mainActivity;

    public PreferenceChangeListener(MainActivity mainActivity,
                                    MainActivityFragment quizFragment,
                                    QuizViewModel quizViewModel) {
        this.mainActivity = mainActivity;
        this.quizFragment = quizFragment;
        this.quizViewModel = quizViewModel;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        this.mainActivity.setPreferencesChanged(true);

        if (key.equals(this.mainActivity.getREGIONS())) {
            this.quizViewModel.setGuessRows(sharedPreferences.getString(
                    MainActivity.CHOICES, null));
            this.quizFragment.resetQuiz();
        } else if (key.equals(this.mainActivity.getCHOICES())) {
            Set<String> regions = sharedPreferences.getStringSet(this.mainActivity.getREGIONS(),
                    null);
            if (regions != null && regions.size() > 0) {
                this.quizViewModel.setRegionsSet(sharedPreferences);
                this.quizFragment.resetQuiz();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                regions.add(this.mainActivity.getString(R.string.default_region));
                editor.putStringSet(this.mainActivity.getREGIONS(), regions);
                editor.apply();
                Toast.makeText(this.mainActivity, R.string.default_region_message,
                        Toast.LENGTH_LONG).show();
            }
        }

        Toast.makeText(this.mainActivity, R.string.restarting_quiz,
                Toast.LENGTH_SHORT).show();
    }
}
