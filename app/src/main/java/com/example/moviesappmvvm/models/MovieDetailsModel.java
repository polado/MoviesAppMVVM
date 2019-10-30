package com.example.moviesappmvvm.models;

import android.util.Log;


import com.example.moviesappmvvm.api.ApiClient;
import com.example.moviesappmvvm.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsModel {
    private final String TAG = "MovieDetailsModel";

    private String apiKey = "c6141ba11d0bb71fbe3ec33b08c8125a";
    private ApiInterface apiInterface;

    public MovieDetailsModel() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getMovieDetails(final OnFinishedListener onFinishedListener, int movieId) {
        Call<Movie> call = apiInterface.getMovieDetails(movieId, apiKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Log.d(TAG, "Movie data received: " + movie.toString());
                onFinishedListener.onFinished(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });

    }

    public interface OnFinishedListener {
        void onFinished(Movie movie);

        void onFailure(Throwable t);
    }
}
