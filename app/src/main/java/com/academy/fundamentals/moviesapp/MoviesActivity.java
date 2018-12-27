package com.academy.fundamentals.moviesapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.academy.fundamentals.moviesapp.AsyncAndThreads.AsyncTaskActivity;
import com.academy.fundamentals.moviesapp.AsyncAndThreads.ThreadHandlerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MoviesActivity extends AppCompatActivity implements MyMoviesClickable {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ArrayList<MovieModel> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initMoviesList();
        initRecyclerView();
        initItenTouchHelper();
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

    private void initItenTouchHelper() {
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

    private void initMoviesList() {
        Constants cons = new Constants();
        movies = new ArrayList<MovieModel>();
        for (int movieIdx = 0; movieIdx < cons.moviesNames.length; movieIdx++) {
            movies.add(new MovieModel(
                    cons.moviesNames[movieIdx],
                    cons.overviews[movieIdx],
                    cons.moviesImages[movieIdx],
                    cons.trailerUrls[movieIdx],
                    cons.releaseDates[movieIdx]));
        }
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MoviesViewAdapter(this, movies);
        recyclerView.setAdapter(adapter);
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
