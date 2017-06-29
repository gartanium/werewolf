package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                System.out.println(players.get(i).getRole() + " " + (i + 1));
            }


        //if the top randomize does not work
        /*
        while (numPlayers > numAssignedRoles) {
            //numWolfs greater 0 and doc is true assign all roles
            if (numWolfs > 0 && doc) {
                switch (randomNum.nextInt(3)) {
                    case 1:
                        //wolf
                        players.get(count).setRole("wolf");
                        numWolfs--;
                        numAssignedRoles++;
                        count++;
                        break;
                    case 2:
                        //doc
                        players.get(count).setRole("doc");
                        numAssignedRoles++;
                        count++;
                        doc = false;
                        break;
                    case 3:
                        //villager
                        players.get(count).setRole("villager");
                        numAssignedRoles++;
                        count++;
                        break;
                }
            }
            //numWolfs assigned 0 and doc is true. Assign doc and villagers
            if (numWolfs == 0 && doc) {
                switch (randomNum.nextInt(2)) {
                    case 1:
                        //villager
                        players.get(count).setRole("villager");
                        numAssignedRoles++;
                        count++;
                        break;
                    case 2:
                        players.get(count).setRole("doc");
                        numAssignedRoles++;
                        count++;
                        doc = false;
                        break;

                }
            }

            //numWolfs is greater than 0 and doc is false. Assign wolf and villagers
            if (numWolfs > 0 && !doc) {
                switch (randomNum.nextInt(2)) {
                    case 1:
                        //villager
                        players.get(count).setRole("villager");
                        numAssignedRoles++;
                        count++;
                        break;
                    case 2:
                        //wolf
                        players.get(count).setRole("wolf");
                        numWolfs--;
                        numAssignedRoles++;
                        count++;
                        break;
                }
            }
            //villagers are left. Assign villagers
            else
            {
                players.get(count).setRole("villager");
                numAssignedRoles++;
                count++;
            }
        }*/


        //code that update the firebase goes here.

        }


    }

    public void startDayNight(View view) {
        Intent intent = new Intent(this, DayNight.class);
        startActivity(intent);
    }
}

