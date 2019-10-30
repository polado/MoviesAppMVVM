package com.example.moviesappmvvm.view_models;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.models.MoviesListModel;

import java.util.List;

public class MoviesListViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> popularMoviesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> nowPlayingMoviesMutableLiveData = new MutableLiveData<>();
    private MoviesListModel moviesListModel = new MoviesListModel();

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();

    public void MoviesListViewModel() {
        moviesListModel = new MoviesListModel();
        popularMoviesMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getPopularMoviesData() {
        isLoadingLiveData.setValue(View.VISIBLE);
        moviesListModel.getPopularMoviesList(new MoviesListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                isLoadingLiveData.setValue(View.INVISIBLE);
                if (movieArrayList != null) {
                    popularMoviesMutableLiveData.setValue(movieArrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isLoadingLiveData.setValue(View.INVISIBLE);

            }
        }, 1);

        return popularMoviesMutableLiveData;
    }

    public void getMorePopularMovies(int page) {
        isLoadingLiveData.setValue(View.VISIBLE);
        moviesListModel.getPopularMoviesList(new MoviesListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                isLoadingLiveData.setValue(View.INVISIBLE);
                if (movieArrayList != null) {
                    popularMoviesMutableLiveData.setValue(movieArrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isLoadingLiveData.setValue(View.INVISIBLE);

            }
        }, page);
    }

    public MutableLiveData<List<Movie>> getNowPlayingMoviesData() {
        isLoadingLiveData.setValue(View.VISIBLE);
        moviesListModel.getNowPlayingMoviesList(new MoviesListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                isLoadingLiveData.setValue(View.INVISIBLE);
                if (movieArrayList != null) {
                    nowPlayingMoviesMutableLiveData.setValue(movieArrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isLoadingLiveData.setValue(View.INVISIBLE);

            }
        }, 1);

        return nowPlayingMoviesMutableLiveData;
    }

    public void getMoreNowPlayingMovies(int page) {
        isLoadingLiveData.setValue(View.VISIBLE);
        moviesListModel.getNowPlayingMoviesList(new MoviesListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                isLoadingLiveData.setValue(View.INVISIBLE);
                if (movieArrayList != null) {
                    nowPlayingMoviesMutableLiveData.setValue(movieArrayList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isLoadingLiveData.setValue(View.INVISIBLE);

            }
        }, page);
    }
}
