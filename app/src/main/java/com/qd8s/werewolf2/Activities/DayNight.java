package com.qd8s.werewolf2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.R;

public class DayNight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
    }

    public void StartMainActivty(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}