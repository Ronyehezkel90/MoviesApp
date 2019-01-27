package com.academy.fundamentals.moviesapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.academy.fundamentals.moviesapp.Networking.DAO.Movie.MovieModel;
import com.academy.fundamentals.moviesapp.Networking.DAO.Video.VideoModel;

@Database(entities = {MovieModel.class, VideoModel.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "movies.db";
    private static AppDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public abstract VideoDao videoDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


}

