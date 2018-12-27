package com.academy.fundamentals.moviesapp;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */

public class ScreenSlideMoviesFragment extends Fragment {
    MovieModel movieModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_details, container, false);
        this.movieModel = getArguments().getParcelable("movie");
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        Picasso.get().load("https://image.tmdb.org/t/p/w780/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg")
                .into((ImageView) view.findViewById(R.id.topImageView));
        Picasso.get().load(movieModel.getImageResourceUrl())
                .into((ImageView) view.findViewById(R.id.centerImageView));
        ((TextView) view.findViewById(R.id.movie_description)).setText(movieModel.getOverview());
        ((TextView) view.findViewById(R.id.movie_title)).setText(movieModel.getName());
        ((TextView) view.findViewById(R.id.release_date)).setText(movieModel.getReleaseDate());
    }

    public void movieTrailerOnClick(View view) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieModel.getTrailerUrl()));
        startActivity(appIntent);
    }

}
