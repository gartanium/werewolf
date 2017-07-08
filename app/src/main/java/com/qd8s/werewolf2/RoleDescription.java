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


public class RoleDescription extends AppCompatActivity {

    private User user;
    private int numPlayers;
    private int numWolfs;
    private int numAssignedRoles;
    private RoomAdapter mRoom;

    /**
     * Use the RoomAdapter class to get all your logic for the Room.
     * The mRoom will contain a list of all clients and their associated users.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);

        user = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        // Set the users state to be in RoleDescription.
        user.setActivityLocation(User.ActivityLocation.RoleDescription);
        //mUser.set_host(true);

        if (user.isHost()) {
            List<User> players = new ArrayList<>();
            List<String> roles = new ArrayList<>();

            players = mRoom.getUsers();

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

            //updates firebase
            mRoom.updateUsers(players);
        }


    }

    public void startDayNight(View view) {
        Intent intent = new Intent(this, DayNight.class);
        intent.putExtra("Client_Data", user);
        intent.putExtra("Room_data", mRoom);
        startActivity(intent);
    }
}

