package com.academy.fundamentals.moviesapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.academy.fundamentals.moviesapp.Constants.smallPosterSize;

/**
 * Created by ron on 21/12/18.
 */

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MovieItem> dataSource;
    private MyMoviesClickable myMoviesClickable;

    public MoviesViewAdapter(Context context, List<MovieItem> items) {
        dataSource = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMoviesClickable = (MoviesActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindViewHolder(position, dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageRow;
        public final TextView titleRow;
        public final TextView overviewRow;
        public final View view;

        public ViewHolder(View view) {
            super(view);
            imageRow = view.findViewById(R.id.image_row);
            titleRow = view.findViewById(R.id.title_row);
            overviewRow = view.findViewById(R.id.overview_row);
            this.view = view;
        }

        public void onBindViewHolder(final int position, MovieItem movieItem) {
            Picasso.get().load(String.format(Constants.baseImageUrl, smallPosterSize, movieItem.getPosterPath())).into(imageRow);
            titleRow.setText(movieItem.getTitle());
            overviewRow.setText(movieItem.getOverview());

            view.setOnHoverListener(new View.OnHoverListener() {
                @Override
                public boolean onHover(View v, MotionEvent event) {
                    return false;
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myMoviesClickable.onLongClick(position);
                    return false;
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myMoviesClickable.onClick(position);
                }
            });
        }
    }


}