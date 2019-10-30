package com.example.moviesappmvvm.api;

import com.example.moviesappmvvm.models.Movie;
import com.example.moviesappmvvm.models.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/popular")
    Call<MoviesList> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("movie/now_playing")
    Call<MoviesList> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}