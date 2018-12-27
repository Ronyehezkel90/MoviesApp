package com.academy.fundamentals.moviesapp.AsyncAndThreads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.academy.fundamentals.moviesapp.R;

public class AsyncTaskActivity extends AppCompatActivity {

    CounterAsyncTask counterAsyncTask;
    IAsyncTaskEvents iAsyncTaskEvents;
    TextView counterTextView;
    static final String LAST_STATE = "lastState";
    private int state;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(LAST_STATE, state);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_and_thread);
        if (savedInstanceState != null) {
            state = savedInstanceState.getInt(LAST_STATE);
        } else {
            state = -1;
        }

        iAsyncTaskEvents = new IAsyncTaskEvents() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute() {
                counterTextView.setText("Finish");
                counterAsyncTask = new CounterAsyncTask(iAsyncTaskEvents);
            }

            @Override
            public void onProgressUpdate(Integer number) {
                state = number;
                counterTextView.setText(String.valueOf(number));
            }

            @Override
            public void onCancelled() {
                counterTextView.setText("Ready");
                counterAsyncTask = new CounterAsyncTask(iAsyncTaskEvents);
            }
        };
        initActivity();
        if (state != -1) {
            counterAsyncTask = new CounterAsyncTask(iAsyncTaskEvents);
            startCounting();
        }
    }

    private void initActivity() {
        counterTextView = ((TextView) findViewById(R.id.activity_type));
        if (state == -1) {
            counterTextView.setText("This is AsyncTask Activity");
        }
        (findViewById(R.id.createButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterAsyncTask = new CounterAsyncTask(iAsyncTaskEvents);
            }
        });
        (findViewById(R.id.cancelButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterAsyncTask.cancel(true);
            }
        });
        (findViewById(R.id.startButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCounting();
            }
        });
    }

    private void startCounting() {
        if (null == counterAsyncTask) {
            counterTextView.setText("Please create asyncTask before start");
        } else {
            Integer[] sequence = createSequnceToCount(state);
            counterAsyncTask.execute(sequence);
        }
    }

    private Integer[] createSequnceToCount(int number) {
        if (number == -1) {
            number = 10;
        }
        Integer[] sequnce = new Integer[number];
        for (int i = 0; i < number; i++) {
            sequnce[i] = number - i;
        }
        return sequnce;
    }


    public class CounterAsyncTask extends AsyncTask<Integer[], Integer, String> {
        IAsyncTaskEvents iAsyncTaskEvents;

        public CounterAsyncTask(IAsyncTaskEvents iAsyncTaskEvents) {
            this.iAsyncTaskEvents = iAsyncTaskEvents;
        }

        @Override
        protected String doInBackground(Integer[]... numbers) {
            for (Integer number : numbers[0]) {
                if (!isCancelled()) {
                    publishProgress(number);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            return "Finish";
        }

        @Override
        public void onPreExecute() {
            iAsyncTaskEvents.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... number) {
            iAsyncTaskEvents.onProgressUpdate(number[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            iAsyncTaskEvents.onPostExecute();
        }

        @Override
        protected void onCancelled() {
            iAsyncTaskEvents.onCancelled();
        }
    }


}
