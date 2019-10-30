package com.example.moviesappmvvm;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.moviesappmvvm.api.ApiClient;
import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.view_models.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity {
    private ImageView ivBackdrop;
    private ProgressBar pbLoadBackdrop;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieRatings;
    private TextView tvOverview;
    private TextView tvHomepageValue;
    private TextView tvTaglineValue;
    private TextView tvRuntimeValue;

    private MovieDetailsViewModel movieDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        buildUI();

        Intent mIntent = getIntent();
        int movieID = mIntent.getIntExtra("movie_id", 0);

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.isLoadingLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aBoolean) {
                pbLoadBackdrop.setVisibility(aBoolean);
            }
        });

        movieDetailsViewModel.getData(movieID).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                setDataToViews(movie);
            }
        });
    }

    private void buildUI() {
        ivBackdrop = findViewById(R.id.iv_backdrop);
        pbLoadBackdrop = findViewById(R.id.pb_load_backdrop);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_release_date);
        tvMovieRatings = findViewById(R.id.tv_movie_ratings);
        tvOverview = findViewById(R.id.tv_movie_overview);

        tvHomepageValue = findViewById(R.id.tv_homepage_value);
        tvTaglineValue = findViewById(R.id.tv_tagline_value);
        tvRuntimeValue = findViewById(R.id.tv_runtime_value);
    }

    public void setDataToViews(Movie movie) {
        if (movie != null) {
            Log.d("MovieDetailsActivity", "Movie data received: " + movie.toString());
//            movieName = movie.getTitle();
            tvMovieTitle.setText(movie.getTitle());
            tvMovieReleaseDate.setText(movie.getReleaseDate());
            tvMovieRatings.setText(String.valueOf(movie.getRating()));
            tvOverview.setText(movie.getOverview());

            Glide.with(this)
                    .load(ApiClient.BACKDROP_BASE_URL + movie.getBackdropPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                    .into(ivBackdrop);

            tvTaglineValue.setText(movie.getTagline() != null ? movie.getTagline() : "N/A");
            tvHomepageValue.setText(movie.getHomepage() != null ? movie.getHomepage() : "N/A");
            tvRuntimeValue.setText(movie.getRunTime() != null ? movie.getRunTime() : "N/A");
        }
    }
}
