package com.vokrob.brain_trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.white));
        setContentView(R.layout.start_layout);
    }

    public void onClickStart(View view){
        Intent i = new Intent(StartActivity.this, MainActivity.class);
        startActivity(i);
    }
}






















