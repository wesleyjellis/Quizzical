package com.example.wes.quizzical;

import android.content.Context;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Okio;

public class QuizRepository {

    private final Context context;

    public QuizRepository(Context context) {
        this.context = context;
    }

    public Quiz getQuiz() {
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

    public void getRemoteQuiz(int id, final QuizCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://oolong.tahnok.me/cdn/quizzes/" + id + ".json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure();
                }

                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<Quiz> jsonAdapter = moshi.adapter(Quiz.class);
                Quiz quiz = jsonAdapter.fromJson(response.body().source());

                callback.onSuccess(quiz);
            }
        });

    }

    public void getRemoteQuizzes(final QuizzesCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://oolong.tahnok.me/cdn/quizzes.json ")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure();
                }

                Moshi moshi = new Moshi.Builder().build();
                Type type = Types.newParameterizedType(List.class, Quiz.class);
                JsonAdapter<List<Quiz>> jsonAdapter = moshi.adapter(type);
                List<Quiz> quizzes = jsonAdapter.fromJson(response.body().source());

                callback.onSuccess(quizzes);
            }
        });

    }

    public interface QuizCallback {
        void onFailure();
        void onSuccess(Quiz quiz);
    }

    public interface QuizzesCallback {
        void onFailure();
        void onSuccess(List<Quiz> quizzes);
    }


}
