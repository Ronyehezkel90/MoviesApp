package com.academy.fundamentals.moviesapp.Networking;

import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MoviesListResponse;
import com.academy.fundamentals.moviesapp.Networking.DAO.Video.VideosListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesService {
    //https://api.themoviedb.org/3/movie/popular?api_key=06a669102f85c31366fc3d09b3dba7fc
    @GET("movie/popular")
    Call<MoviesListResponse> getPopularMovies();


    //https://api.themoviedb.org/3/movie/424783/videos?api_key=06a669102f85c31366fc3d09b3dba7fc&language=en-US
    @GET("movie/{movie_id}/videos")
    Call<VideosListResponse> getMovieVideos(@Path("movie_id") String movieId);
}

