package com.example.moviesappmvvm.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviesappmvvm.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE id = (:movieID)")
    List<Movie> getMovie(int movieID);

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Update
    void update(Movie movie);
}
