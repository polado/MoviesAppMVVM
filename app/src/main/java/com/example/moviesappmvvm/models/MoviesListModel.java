package com.example.moviesappmvvm.models;

import android.util.Log;

import com.example.moviesappmvvm.api.ApiClient;
import com.example.moviesappmvvm.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListModel {
    private final String TAG = "MoviesListModel";

    private String apiKey = "c6141ba11d0bb71fbe3ec33b08c8125a";
    private ApiInterface apiInterface;

    public MoviesListModel() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPopularMoviesList(final OnFinishedListener onFinishedListener, int pageNo) {
        Call<MoviesList> call = apiInterface.getPopularMovies(apiKey, pageNo);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Log.e(TAG, "Error " + t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

    public void getNowPlayingMoviesList(final OnFinishedListener onFinishedListener, int pageNo) {
        Call<MoviesList> call = apiInterface.getNowPlayingMovies(apiKey, pageNo);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Log.e(TAG, "Error " + t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

    public interface OnFinishedListener {
        void onFinished(List<Movie> movieArrayList);

        void onFailure(Throwable t);
    }

}
