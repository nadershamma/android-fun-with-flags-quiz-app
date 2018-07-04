package com.nadershamma.apps.eventhandlers;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nadershamma.apps.androidfunwithflags.MainActivityFragment;
import com.nadershamma.apps.androidfunwithflags.R;
import com.nadershamma.apps.androidfunwithflags.ResultsDialogFragment;
import com.nadershamma.apps.lifecyclehelpers.QuizViewModel;

public class GuessButtonListener implements OnClickListener {
    private QuizViewModel quizViewModel;
    private TextView answerTextView;
    private MainActivityFragment mainActivityFragment;

    public GuessButtonListener(MainActivityFragment mainActivityFragment,
                               QuizViewModel quizViewModel,
                               TextView answerTextView) {
        this.quizViewModel = quizViewModel;
        this.mainActivityFragment = mainActivityFragment;
        this.answerTextView = answerTextView;
    }

    @Override
    public void onClick(View v) {
        Button guessButton = ((Button) v);
        String guess = guessButton.getText().toString();
        String answer = quizViewModel.getCorrectCountryName();
        this.quizViewModel.setTotalGuesses(1);

        if (guess.equals(answer)) {
            this.quizViewModel.setCorrectAnswers(1);
            this.answerTextView.setText(answer + "!");
            this.answerTextView.setTextColor(
                    this.mainActivityFragment.getResources().getColor(R.color.correct_answer));

            // TODO
            // this.mainActivityFragment.disableButtons();

            ResultsDialogFragment quizResults = new ResultsDialogFragment();
            try{
                quizResults.show(this.mainActivityFragment.getFragmentManager(), "Quiz Results");
            } catch (NullPointerException e){
                Log.e(QuizViewModel.getTag(),
                        "GuessButtonListener: this.mainActivityFragment.getFragmentManager() " +
                                "returned null",
                        e);
            }

        }
    }
}
