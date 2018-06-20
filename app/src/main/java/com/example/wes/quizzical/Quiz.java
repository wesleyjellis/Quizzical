package com.example.wes.quizzical;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    private static Quiz quiz;

    public static Quiz getInstance() {
        if (quiz == null) {
            quiz = new Quiz();
            quiz.addQuestion(new Question("The moon is made of cheese", false));
            quiz.addQuestion(new Question("The sum of the internal angles of a triangle is 180", true));
        }
        return quiz;
    }
}
