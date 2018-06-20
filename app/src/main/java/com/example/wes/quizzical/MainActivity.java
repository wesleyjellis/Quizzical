package com.example.wes.quizzical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements QuizRepository.QuizCallback {

    private static final String USER_ANSWER = "user_answer";
    private static final String QUESTION_ANSWERED = "question_answered";
    private static final String CURRENT_QUESTION_INDEX = "current_question_index";
    private static final String SCORE = "score";

    private Button trueButton;
    private Button falseButton;
    private TextView answerTextView;
    private Button nextButton;
    private TextView questionTextView;

    private boolean userAnswer; // What button the user clicked last (true or false)
    private boolean questionAnswered = false; // Has the question been answered?
    private Quiz quiz;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        answerTextView = findViewById(R.id.answer_text);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question);

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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        if (savedInstanceState != null) { //no savedInstanceState when activity is first launched
            questionAnswered = savedInstanceState.getBoolean(QUESTION_ANSWERED, false);
            userAnswer = savedInstanceState.getBoolean(USER_ANSWER);
            currentQuestionIndex = savedInstanceState.getInt(CURRENT_QUESTION_INDEX, 0);
            score = savedInstanceState.getInt(SCORE, 0);
        }


        new QuizRepository(this).getRemoteQuiz(this);

    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= quiz.getQuestions().size()) {
            Intent resultActivityIntent = new Intent(this, ResultActivity.class);
            resultActivityIntent.putExtra(ResultActivity.KEY_SCORE, 0);
            resultActivityIntent.putExtra(ResultActivity.KEY_TOTAL_QUESTIONS, quiz.getQuestions().size());
            startActivity(resultActivityIntent);
        } else {
            questionAnswered = false;
            showQuestion();
        }
    }

    private void showQuestion() {
        Question question = quiz.getQuestions().get(currentQuestionIndex);
        questionTextView.setText(question.getStatement());
        answerTextView.setText("");
        nextButton.setEnabled(false);
    }

    private void checkAnswer(boolean answerToCheck) {
        questionAnswered = true;
        userAnswer = answerToCheck;

        if(answerToCheck == quiz.getQuestions().get(currentQuestionIndex).getAnswer()) {
            answerTextView.setText("Correct!");
            score++;
        } else {
            answerTextView.setText("Nope, WRONG 👎");
        }

        nextButton.setEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(USER_ANSWER, userAnswer);
        outState.putBoolean(QUESTION_ANSWERED, questionAnswered);
        outState.putInt(CURRENT_QUESTION_INDEX, currentQuestionIndex);
        outState.putInt(SCORE, score);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Unable to retrieve quiz", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(Quiz quiz) {
        this.quiz = quiz;
        showQuestion();

        if (questionAnswered) {
            checkAnswer(userAnswer);
        }
    }
}
