package com.academy.fundamentals.moviesapp.AsyncAndThreads;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.academy.fundamentals.moviesapp.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ThreadHandlerActivity extends AppCompatActivity {

    MySimpleAsyncTask mySimpleAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_and_thread);
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.counter_fragment, new CounterFragment()).commit();
        initActivity();
    }

    public void execute() {
        onPreExecute();//UI Thread
        doInBackground();//BG Thread
        onPostExecute();//UI Thread
    }

    private void onPreExecute() {
        mySimpleAsyncTask = new MySimpleAsyncTask();
    }

    private void onPostExecute() {
    }

    private void doInBackground() {
        mySimpleAsyncTask.run();
    }

    private void initActivity() {
        ((TextView) findViewById(R.id.activity_type)).setText("This is Thread Handler Activity");
        (findViewById(R.id.createButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.cancelButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        (findViewById(R.id.startButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        onPreExecute();
        new Handler(Looper.getMainLooper()).post(mySimpleAsyncTask);
        HandlerThread handlerThread = new HandlerThread("worker");
        handlerThread.start();

        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
    }

    public class MySimpleAsyncTask implements Runnable {
        boolean canceled = false;
        int countTo = -1;

        boolean isCancelled() {
            return canceled;
        }

        @Override
        public void run() {
            for (int i = 0; i < countTo; i++) {
                if (!isCancelled()) {
//                    publishProgress(countTo - i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }

}
