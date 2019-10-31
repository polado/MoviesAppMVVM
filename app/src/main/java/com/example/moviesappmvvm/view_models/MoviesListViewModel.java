package com.example.moviesappmvvm.view_models;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesappmvvm.database.MovieDao;
import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.models.MoviesListModel;

import java.util.List;

public class MoviesListViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> popularMoviesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> nowPlayingMoviesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> favouriteMoviesMutableLiveData = new MutableLiveData<>();
    private MoviesListModel moviesListModel = new MoviesListModel();

    public MutableLiveData<Integer> isLoadingLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Movie>> isFavouriteLiveData = new MutableLiveData<>();

    public MovieDao movieDao;

    public void MoviesListViewModel() {
        moviesListModel = new MoviesListModel();
        popularMoviesMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getFavouriteMoviesData() {

        SetFavouritesTask t = new SetFavouritesTask();
        t.execute();

        return isFavouriteLiveData;
    }

    public MutableLiveData<List<Movie>> getPopularMoviesData() {
        isLoadingLiveData.setValue(View.VISIBLE);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Movie> list = movieDao
                        .getAll();

                moviesListModel.getPopularMoviesList(new MoviesListModel.OnFinishedListener() {
                    @Override
                    public void onFinished(List<Movie> movieArrayList) {
                        for (Movie m : movieArrayList) {
                            for (Movie l : list) {
                                if (l.getId() == m.getId()) {
                                    Log.e("MovieListPresenter", "db contains" + m.toString());
                                    m.isFavourite = true;
                                }
                            }
                        }

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
            }
        });

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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Movie> list = movieDao
                        .getAll();

                moviesListModel.getNowPlayingMoviesList(new MoviesListModel.OnFinishedListener() {
                    @Override
                    public void onFinished(List<Movie> movieArrayList) {
                        for (Movie m : movieArrayList) {
                            for (Movie l : list) {
                                if (l.getId() == m.getId()) {
                                    Log.e("MovieListPresenter", "db contains" + m.toString());
                                    m.isFavourite = true;
                                }
                            }
                        }
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
            }
        });



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

    public void toggleFavourite(final Movie movie, final boolean isFavourite) {
        movie.isFavourite = isFavourite;
        ToggleFavouriteTask t = new ToggleFavouriteTask();
        t.execute(movie);
    }

    private class ToggleFavouriteTask extends AsyncTask<Movie, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Movie... movies) {
            if (movies[0].isFavourite) movieDao
                    .insert(movies[0]);
            else movieDao
                    .delete(movies[0]);

            List<Movie> list = movieDao
                    .getAll();
            Log.e("MovieListPresenter", "toggle Movies in db " + list.size());
            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            isFavouriteLiveData.setValue(movies);
            super.onPostExecute(movies);
        }
    }

    private class SetFavouritesTask extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            isLoadingLiveData.setValue(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            List<Movie> list = movieDao
                    .getAll();
            Log.e("MovieListPresenter", "toggle Movies in db " + list.size());
            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            isLoadingLiveData.setValue(View.INVISIBLE);
            isFavouriteLiveData.setValue(movies);
            super.onPostExecute(movies);
        }
    }
}
