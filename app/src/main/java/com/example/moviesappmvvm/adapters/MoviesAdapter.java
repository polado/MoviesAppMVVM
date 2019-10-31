package com.example.moviesappmvvm.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.moviesappmvvm.R;
import com.example.moviesappmvvm.api.ApiClient;
import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.tabs.ParentFragment;
import com.example.moviesappmvvm.view_models.MoviesListViewModel;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ParentFragment movieListActivity;
    private List<Movie> movieList;
    private MoviesListViewModel moviesListViewModel;
    private ParentFragment fragment;

    public MoviesAdapter(ParentFragment movieListActivity, List<Movie> movieList, MoviesListViewModel moviesListViewModel, ParentFragment fragment) {
        this.movieListActivity = movieListActivity;
        this.movieList = movieList;
        this.moviesListViewModel = moviesListViewModel;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Movie movie = movieList.get(position);

        holder.tvMovieTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());

        Glide.with(movieListActivity)
                .load(ApiClient.IMAGE_BASE_URL + movie.getThumbPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pbLoadImage.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pbLoadImage.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(holder.ivMovieThumb);

        moviesListViewModel.isFavouriteLiveData.observe(fragment, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.e("MoviesAdapter", movies.size() + " changed " + movie.getTitle());
                int counter = 0;
                for (Movie m : movies) {
                    if (movie.getId() == m.getId()) {
                        Log.e("MoviesAdapter", "db contains" + m.getTitle());
                        counter++;
                    }
                }
                movie.isFavourite = counter > 0;

                if (movie.isFavourite) holder.favourite.setImageResource(R.drawable.ic_favorite);
                else holder.favourite.setImageResource(R.drawable.ic_favorite_border);
            }
        });


        if (movie.isFavourite) holder.favourite.setImageResource(R.drawable.ic_favorite);
        else holder.favourite.setImageResource(R.drawable.ic_favorite_border);

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MoviesAdapter", "onclick " + movie.isFavourite);
                movie.isFavourite = !movie.isFavourite;
                if (movie.isFavourite)
                    holder.favourite.setImageResource(R.drawable.ic_favorite);
                else holder.favourite.setImageResource(R.drawable.ic_favorite_border);
                moviesListViewModel.toggleFavourite(movie, movie.isFavourite);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieListActivity.onMovieItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle;

        TextView tvReleaseDate;

        ImageView ivMovieThumb;

        ProgressBar pbLoadImage;

        ImageButton favourite;

        MyViewHolder(View itemView) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            ivMovieThumb = itemView.findViewById(R.id.iv_movie_thumb);
            pbLoadImage = itemView.findViewById(R.id.pb_load_image);
            favourite = itemView.findViewById(R.id.ib_favourite);
        }
    }
}