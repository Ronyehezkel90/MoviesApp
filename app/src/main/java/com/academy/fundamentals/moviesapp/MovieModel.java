package com.academy.fundamentals.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ron on 21/12/18.
 */
public class MovieModel implements Parcelable {

    private String name;
    private String imageResourceUrl;
    private String overview;
    private String trailerUrl;
    private String releaseDate;

    public MovieModel(String name, String overview, String imageResourceUrl, String trailerUrl, String releaseDate) {
        this.name = name;
        this.overview = overview;
        this.imageResourceUrl = imageResourceUrl;
        this.trailerUrl = trailerUrl;
        this.releaseDate = releaseDate;

    }

    protected MovieModel(Parcel in) {
        name = in.readString();
        imageResourceUrl = in.readString();
        overview = in.readString();
        trailerUrl = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageResourceUrl);
        dest.writeString(overview);
        dest.writeString(trailerUrl);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResourceUrl() {
        return imageResourceUrl;
    }

    public void setImageResourceUrl(String imageResourceUrl) {
        this.imageResourceUrl = imageResourceUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
