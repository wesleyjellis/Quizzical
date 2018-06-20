package com.example.wes.quizzical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ANSWER = "user_answer";
    private static final String QUESTION_ANSWERED = "question_answered";

    private Button trueButton;
    private Button falseButton;
    private TextView answerTextView;

    private boolean userAnswer; // What button the user clicked last (true or false)
    private boolean questionAnswered = false; // Has the question been answered?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        answerTextView = findViewById(R.id.answer_text);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HI", "true button clicked");
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HI", "false button clicked");
                checkAnswer(false);
            }
        });

        if (savedInstanceState != null) { //no savedInstanceState when activity is first launched
            questionAnswered = savedInstanceState.getBoolean(QUESTION_ANSWERED, false);
            userAnswer = savedInstanceState.getBoolean(USER_ANSWER);z
        }

        if (questionAnswered) {
            checkAnswer(userAnswer);
        }
    }

    private void checkAnswer(boolean answerToCheck) {
        questionAnswered = true;
        userAnswer = answerToCheck;

        if(answerToCheck == false) {
            answerTextView.setText("Correct!");
        } else {
            answerTextView.setText("Nope, WRONG 👎");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(USER_ANSWER, userAnswer);
        outState.putBoolean(QUESTION_ANSWERED, questionAnswered);
    }
}
