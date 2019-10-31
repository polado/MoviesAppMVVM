package com.example.moviesappmvvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.moviesappmvvm.adapters.TabAdapter;
import com.example.moviesappmvvm.database.AppDatabase;
import com.example.moviesappmvvm.database.DatabaseClient;
import com.example.moviesappmvvm.tabs.FavouriteMoviesFragment;
import com.example.moviesappmvvm.tabs.NowPlayingMoviesFragment;
import com.example.moviesappmvvm.tabs.PopularMoviesFragment;
import com.example.moviesappmvvm.view_models.MoviesListViewModel;
import com.google.android.material.tabs.TabLayout;

public class MoviesListActivity extends AppCompatActivity {
    private MoviesListViewModel moviesListViewModel;

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        moviesListViewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        moviesListViewModel.movieDao = db.movieDao();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(new PopularMoviesFragment(moviesListViewModel), "Popular");
        adapter.addFragment(new NowPlayingMoviesFragment(moviesListViewModel), "Now Playing");
        adapter.addFragment(new FavouriteMoviesFragment(moviesListViewModel), "Favourites");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
