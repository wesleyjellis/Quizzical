package com.example.wes.quizzical;

public class Question {
    private boolean answer;
    private String statement;

    public Question(String statement, boolean answer) {
        this.statement = statement;
        this.answer = answer;

    }

    public boolean getAnswer() {
        return answer;
    }

    public String getStatement() {
        return statement;
    }
}
