package com.example.wes.quizzical;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private String title;
    private int id;
    private List<Question> questions = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}
