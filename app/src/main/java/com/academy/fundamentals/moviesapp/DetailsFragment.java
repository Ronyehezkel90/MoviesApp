package com.academy.fundamentals.moviesapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MovieItem;
import com.academy.fundamentals.moviesapp.Networking.DAO.Video.VideoItem;
import com.academy.fundamentals.moviesapp.Networking.DAO.Video.VideosListResponse;
import com.academy.fundamentals.moviesapp.Networking.RestClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.academy.fundamentals.moviesapp.Constants.backdropSize;
import static com.academy.fundamentals.moviesapp.Constants.baseImageUrl;
import static com.academy.fundamentals.moviesapp.Constants.bigPosterSize;

/**
 * A simple {@link Fragment} subclass.
 */

public class DetailsFragment extends Fragment {
    @BindView(R.id.movie_description)
    TextView movieDescriptionTextView;
    @BindView(R.id.movie_title)
    TextView movieTitleTextView;
    @BindView(R.id.release_date)
    TextView releaseDateTextView;
    @BindView(R.id.back_drop_image_view)
    ImageView backDropImageView;
    @BindView(R.id.poster_image_view)
    ImageView posterImageView;


    MovieItem movieItem;
    VideoItem videoItem;
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_details, container, false);
        this.movieItem = getArguments().getParcelable("movie");
        ButterKnife.bind(this, rootView);
        initView();
        loadVideos();
        return rootView;
    }

    private void initView() {
        String backDropImageUrl = String.format(baseImageUrl, backdropSize, movieItem.getBackdropPath());
        Picasso.get().load(backDropImageUrl).into(backDropImageView);
        String posterImageUrl = String.format(baseImageUrl, bigPosterSize, movieItem.getPosterPath());
        Picasso.get().load(posterImageUrl).into(posterImageView);
        movieDescriptionTextView.setText(movieItem.getOverview());
        movieTitleTextView.setText(movieItem.getTitle());
        releaseDateTextView.setText(movieItem.getReleaseDate());
    }

    public void movieTrailerOnClick() {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoItem.getKey()));
        startActivity(appIntent);
    }

    private void loadVideos() {
        String movieId = String.valueOf(movieItem.getId());
        Call<VideosListResponse> call = RestClient.moviesService.getMovieVideos(movieId);
        final Button trailerButton = rootView.findViewById(R.id.trailer_button);
        call.enqueue(new Callback<VideosListResponse>() {
            @Override
            public void onResponse(Call<VideosListResponse> call, Response<VideosListResponse> response) {
                if (response.isSuccessful()) {
                    videoItem = response.body().getResults().get(0);
                    trailerButton.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<VideosListResponse> call, Throwable t) {
                trailerButton.setText("No Trailer");
            }
        });
    }

}
