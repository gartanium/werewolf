package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.Client;
import com.qd8s.werewolf2.GameHandler.RoomAdapter;

public class RoleDescription extends AppCompatActivity {

    Client client;

    /**
     * Use the RoomAdapter class to get all your logic for the Room.
     * The room will contain a list of all clients and their associated users.
     */
    RoomAdapter room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);
        //jhjhjhjh
        client = getIntent().getExtras().getParcelable("Client_Data");


    }

    public void startDayNight(View view) {
        Intent intent = new Intent(this, DayNight.class);
        startActivity(intent);
    }
}

