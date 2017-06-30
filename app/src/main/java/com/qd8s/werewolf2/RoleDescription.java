package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoleDescription extends AppCompatActivity {

    private User user;
    private int numPlayers;
    private boolean doc;
    private int numWolfs;
    private Random randomNum;
    private int numAssignedRoles;

    /**
     * Use the RoomAdapter class to get all your logic for the Room.
     * The room will contain a list of all clients and their associated users.
     */
    RoomAdapter room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);

        user = getIntent().getExtras().getParcelable("Client_Data");
        user.set_host(true);

        if (user.isHost()) {
            List<User> players = new ArrayList<>();
            List<String> roles = new ArrayList<>();

            //assign players from firebase goes here

            //
            for (int i = 0; i < 10; i++)
            {
                User player = new User();
                players.add(player);
            }

            numPlayers = players.size();
            //doc = true;
            numWolfs = numPlayers / 4;
            //randomNum = new Random();
            numAssignedRoles = 0;
            //int count = 0;

            //adds wolf role
            for (int i = 0; i < numWolfs; i++) {
                roles.add("wolf");
                numAssignedRoles++;
            }
            //add doc role
            roles.add("doc");
            numAssignedRoles++;

            //adds villagers role
            for (int i = numAssignedRoles; i < numPlayers; i++) {
                roles.add("villager");
                numAssignedRoles++;
            }

            //randomize
            Collections.shuffle(roles);

            //assign
            for (int i = 0; i < numAssignedRoles; i++) {
                players.get(i).setRole(roles.get(i));
            }

            for (int i = 0; i < numPlayers; i++)
            {
               Log.v("Player", players.get(i).getRole() + " " + (i + 1));
            }




        //code that update the firebase goes here.

        }


    }

    public void startDayNight(View view) {
        Intent intent = new Intent(this, DayNight.class);
        startActivity(intent);
    }
}

