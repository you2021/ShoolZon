package com.juj27.shoolzon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
               startActivity(intent);
               finish();
           }
       },2000);
    }
}