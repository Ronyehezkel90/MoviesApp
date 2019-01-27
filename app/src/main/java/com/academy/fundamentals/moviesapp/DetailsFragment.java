package com.academy.fundamentals.moviesapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MovieModel;
import com.academy.fundamentals.moviesapp.Networking.DAO.Video.VideoModel;
import com.academy.fundamentals.moviesapp.Networking.DAO.Video.VideosListResponse;
import com.academy.fundamentals.moviesapp.Networking.RestClient;
import com.academy.fundamentals.moviesapp.db.AppDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

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


    MovieModel movieItem;
    VideoModel videoItem;
    ViewGroup rootView;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_details, container, false);
        this.movieItem = getArguments().getParcelable("movie");
        ButterKnife.bind(this, rootView);
        initView();
        setContext();
        loadVideos();
        return rootView;
    }

    private void setContext() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        final Context context = activity.getApplicationContext();
        if (context == null) {
            return;
        }
        this.context = context;
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
        restoreData();
        String movieId = String.valueOf(movieItem.getId());
        Call<VideosListResponse> call = RestClient.moviesService.getMovieVideos(movieId);
        final Button trailerButton = rootView.findViewById(R.id.trailer_button);
        call.enqueue(new Callback<VideosListResponse>() {
            @Override
            public void onResponse(Call<VideosListResponse> call, Response<VideosListResponse> response) {
                if (response.isSuccessful()) {
                    videoItem = response.body().getResults().get(0);
                    saveData(videoItem);
                    //todo: solve it for load from cache as well.
                    trailerButton.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<VideosListResponse> call, Throwable t) {
                trailerButton.setText("No Trailer");
            }
        });
    }

    private void saveData(VideoModel videoItem) {
        VideoModel videoModel = new VideoModel();
        videoModel.setMovieId(movieItem.getId());
        videoModel.setId(videoItem.getId());
        videoModel.setKey(videoItem.getKey());
        AppDatabase.getInstance(context).videoDao().insert(videoModel);
    }

    private void restoreData() {
        final VideoModel videoModel = AppDatabase.getInstance(context).videoDao().getVideo(movieItem.getId());
        if (videoModel != null) {
            videoItem = videoModel;
            return;
        }

    }

}
