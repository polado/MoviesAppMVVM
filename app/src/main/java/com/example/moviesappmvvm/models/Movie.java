package com.example.moviesappmvvm.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Movie implements Serializable {
    @Ignore
    public boolean isFavourite;

    private String title;
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private float rating;

    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String thumbPath;

    @SerializedName("runtime")
    private String runTime;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("homepage")
    private String homepage;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    public Movie(int id, String title, String releaseDate, float rating, String thumbPath, String overview, String backdropPath,
                 String runTime, String tagline, String homepage) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.thumbPath = thumbPath;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.runTime = runTime;
        this.tagline = tagline;
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating=" + rating +
                ", thumbPath='" + thumbPath + '\'' +
                ", overview='" + overview + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", runTime='" + runTime + '\'' +
                ", tagline='" + tagline + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}