package com.example.moviesappmvvm.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesappmvvm.MovieDetailsActivity;
import com.example.moviesappmvvm.adapters.MoviesAdapter;
import com.example.moviesappmvvm.R;
import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.view_models.MoviesListViewModel;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingMoviesFragment extends ParentFragment {
    private static final String TAG = "MovieListActivity";
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private RecyclerView rvMovieList;
    private List<Movie> moviesList;
    private MoviesAdapter moviesAdapter;
    private ProgressBar pbLoading;
    private TextView tvEmptyView;
    private int pageNo = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private GridLayoutManager mLayoutManager;

    private MoviesListViewModel moviesListViewModel;

    public NowPlayingMoviesFragment(MoviesListViewModel moviesListViewModel) {
        this.moviesListViewModel = moviesListViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_list_layout, container, false);

        buildUI(view);
        moviesListViewModel.isLoadingLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aBoolean) {
                pbLoading.setVisibility(aBoolean);
            }
        });
        moviesListViewModel.getNowPlayingMoviesData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesList.addAll(movies);
                moviesAdapter.notifyDataSetChanged();

                pageNo++;
            }
        });

        return view;
    }


    private void buildUI(View view) {
        rvMovieList = view.findViewById(R.id.rv_movie_list);
        pbLoading = view.findViewById(R.id.pb_loading);

        moviesList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, moviesList);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMovieList.setLayoutManager(mLayoutManager);
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(moviesAdapter);


        tvEmptyView = view.findViewById(R.id.tv_empty_view);

        rvMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvMovieList.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    moviesListViewModel.getMoreNowPlayingMovies(pageNo);
                    loading = true;
                }
            }
        });
    }

    public void showEmptyView() {
        rvMovieList.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);

    }

    public void hideEmptyView() {
        rvMovieList.setVisibility(View.VISIBLE);
        tvEmptyView.setVisibility(View.GONE);
    }

    public void onMovieItemClick(int position) {
        if (position == -1) {
            return;
        }
        Intent detailIntent = new Intent(getContext(), MovieDetailsActivity.class);
        detailIntent.putExtra("movie_id", moviesList.get(position).getId());
        startActivity(detailIntent);
    }
}
