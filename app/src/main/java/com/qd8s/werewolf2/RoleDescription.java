package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.Client;

public class RoleDescription extends AppCompatActivity {

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);
        client = getIntent().getExtras().getParcelable("Client_Data");
    }

    public void startDayNight(View view) {
        Intent intent = new Intent(this, DayNight.class);
        startActivity(intent);
    }
}

