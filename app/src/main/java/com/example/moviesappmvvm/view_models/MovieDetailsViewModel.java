package com.example.moviesappmvvm.view_models;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.models.MovieDetailsModel;

public class MovieDetailsViewModel extends ViewModel {
    private MutableLiveData<Movie> movieMutableLiveData = new MutableLiveData<>();
    private MovieDetailsModel moviesListModel = new MovieDetailsModel();

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();

    public MutableLiveData<Movie> getData(int movieID) {
        isLoadingLiveData.setValue(View.VISIBLE);
        moviesListModel.getMovieDetails(new MovieDetailsModel.OnFinishedListener() {
            @Override
            public void onFinished(Movie movie) {
                isLoadingLiveData.setValue(View.INVISIBLE);
                if (movie != null)
                    movieMutableLiveData.setValue(movie);
            }

            @Override
            public void onFailure(Throwable t) {
                isLoadingLiveData.setValue(View.INVISIBLE);
            }
        }, movieID);

        return movieMutableLiveData;
    }
}
