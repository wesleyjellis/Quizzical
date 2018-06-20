package com.example.wes.quizzical;

import android.content.Context;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.InputStream;

import okio.Okio;

public class QuizRepository {

    private final Context context;

    public QuizRepository(Context context) {
        this.context = context;
    }

    public Quiz getQuiz () {
        InputStream assetInputStream;
        try {
            assetInputStream = context.getAssets().open("quiz.json");
        } catch (IOException e) {
            Log.e("QuizRepo", "Could not open quiz json", e);
            return null;
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Quiz> jsonAdapter = moshi.adapter(Quiz.class);

        try {
            Quiz quiz = jsonAdapter.fromJson(Okio.buffer(Okio.source(assetInputStream)));
            return quiz;
        } catch (IOException e) {
            Log.e("QuizRepo", "Could not parse json", e);
            return null;
        }

    }

}
