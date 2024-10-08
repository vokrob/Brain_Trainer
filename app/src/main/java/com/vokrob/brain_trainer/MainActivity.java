package com.vokrob.brain_trainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private final String save_key = "save_key";
    private TextView tvMain, tvRes;
    private Toolbar toolbar;
    private int number_1;
    private int number_2;
    private int number_false;
    private int number_i;
    private int number_res;
    private int max = 20;
    private int min = 1;
    private int max1 = 40;
    private int min1 = 10;
    private long startTime = 0;
    private long currentTime = 0;
    private float time_result = 0.0f;
    private int true_answer = 0;
    private int max_true_answer = 10;
    private boolean is_true_answer = false;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Time: 0.0");
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
        init();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init() {
        startTime = System.currentTimeMillis();
        pref = getSharedPreferences("Test", MODE_PRIVATE);
        tvMain = findViewById(R.id.tvMain);
        tvRes = findViewById(R.id.tvRes);
        tvRes.setText(String.valueOf(true_answer));
        numbers();
        startTimer();
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                currentTime = System.currentTimeMillis();
                time_result = (float) (currentTime - startTime) / 1000;
                if (toolbar != null) {
                    toolbar.setTitle(String.format("Time: %.1f", time_result));
                }
                timerHandler.postDelayed(this, 100);
            }
        };
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void numbers() {
        number_1 = (int) (Math.random() * (max - min));
        number_2 = (int) (Math.random() * (max - min));
        number_false = (int) (Math.random() * (max1 - min1));
        number_i = (int) (Math.random() * (5 - 1));
        number_res = number_1 + number_2;
        String text;
        if (number_i == 3 || number_i == 1) {
            text = number_1 + "+" + number_2 + "=" + number_res;
            is_true_answer = true;
        } else {
            text = number_1 + "+" + number_2 + "=" + number_false;
            is_true_answer = false;
        }
        tvMain.setText(String.valueOf(text));
        if (true_answer >= max_true_answer) {
            timerHandler.removeCallbacks(timerRunnable);
            saveBestResult(time_result);
            Intent i = new Intent(MainActivity.this, FinalActivity.class);
            i.putExtra("result", time_result);
            i.putExtra("bestResult", getBestResult());
            startActivity(i);
        }
    }

    public void onClickTrue(View view) {
        if (is_true_answer) {
            true_answer++;
            numbers();
        } else {
            numbers();
        }
        tvRes.setText(String.valueOf(true_answer));
    }

    public void onClickFalse(View view) {
        if (!is_true_answer) {
            true_answer++;
            numbers();
        } else {
            numbers();
        }
        tvRes.setText(String.valueOf(true_answer));
    }

    private void saveBestResult(float result) {
        float currentBest = pref.getFloat("best_result", Float.MAX_VALUE);
        if (result < currentBest) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putFloat("best_result", result);
            editor.apply();
        }
    }

    private float getBestResult() {
        return pref.getFloat("best_result", Float.MAX_VALUE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }
}




















