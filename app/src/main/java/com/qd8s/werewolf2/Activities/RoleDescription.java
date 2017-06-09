package com.qd8s.werewolf2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.Activities.DayNight;
import com.qd8s.werewolf2.R;

public class RoleDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);
    }

    public void startDayNight(View view) {
        Intent intent = new Intent(this, DayNight.class);
        startActivity(intent);
    }
}
