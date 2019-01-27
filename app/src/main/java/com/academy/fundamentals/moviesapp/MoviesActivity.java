package com.academy.fundamentals.moviesapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.academy.fundamentals.moviesapp.AsyncAndThreads.AsyncTaskActivity;
import com.academy.fundamentals.moviesapp.AsyncAndThreads.ThreadHandlerActivity;
import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MovieItem;
import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MoviesListResponse;
import com.academy.fundamentals.moviesapp.Networking.RestClient;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements MyMoviesClickable {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<MovieItem> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initRecyclerView();
        loadMovies();
        initItemTouchHelper();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.async_task_item: {
                Intent intent = new Intent(this, AsyncTaskActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.thread_handler_item: {
                Intent intent = new Intent(this, ThreadHandlerActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void initItemTouchHelper() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(movies, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(movies, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                System.out.print(direction);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(callback);
        ith.attachToRecyclerView(recyclerView);
    }

    private void loadMovies() {
        Call<MoviesListResponse> call = RestClient.moviesService.getPopularMovies();
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                if (response.isSuccessful()) {
                    movies = response.body().getResults();
                    adapter = new MoviesViewAdapter(MoviesActivity.this, movies);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                //todo: show an error to the user

            }
        });
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
//        Toast.makeText(this, movies.get(position).getOverview(), Toast.LENGTH_LONG).show();
    }
}
