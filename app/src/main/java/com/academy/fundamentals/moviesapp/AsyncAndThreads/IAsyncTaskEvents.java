package com.academy.fundamentals.moviesapp.AsyncAndThreads;

/**
 * Created by ron on 22/12/18.
 */

public interface IAsyncTaskEvents {
    void onPreExecute();

    void onPostExecute();

    void onProgressUpdate(Integer integer);

    void onCancelled();
}
