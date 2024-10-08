package com.vokrob.brain_trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class FinalActivity extends Activity {
    private TextView tvTitle, tvResult, tvBestResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.white));        setContentView(R.layout.final_layout);
        tvTitle = findViewById(R.id.tvTitle);
        tvResult = findViewById(R.id.tvResult);
        tvBestResult = findViewById(R.id.tvBestResult);
        setResult();
    }

    public void onClickFinish(View view){
        Intent i = new Intent(FinalActivity.this, StartActivity.class);
        startActivity(i);
        finish();
    }

    private void setResult(){
        Intent intent = getIntent();
        float result = intent.getFloatExtra("result", 0f);
        float bestResult = intent.getFloatExtra("bestResult", Float.MAX_VALUE);
        tvResult.setText(String.format("Текущий результат: %.1f сек.", result));
        tvBestResult.setText(String.format("Лучший результат: %.1f сек.", bestResult));
    }
}
















