package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    private TextView role;

    /**
     * Use the RoomAdapter class to get all your logic for the Room.
     * The mRoom will contain a list of all clients and their associated users.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("RoleAssigner", "Created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);

        user = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        // Set the users state to be in RoleDescription.
        user.setActivityLocation(User.ActivityLocation.RoleDescription);
        user.setName("FOOBAR");
        mRoom.updateUser(user);
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
                Log.v("RoleAssigner", "User: " + players.get(i).getID() + " role set to: " + roles.get(i));
            }

            for (int i = 0; i < numPlayers; i++)
            {
               Log.v("RoleAssigner", players.get(i).getRole() + " " + (i + 1));
            }

            Log.v("RoleAssigner", "Attempting to update RoleDescriptions!");
            mRoom.updateUsers(players);
        }

        mRoom = getIntent().getExtras().getParcelable("Room_Data");
        user = mRoom.getUser(user);


        role = (TextView)findViewById(R.id.userRole);
        role.setText(user.getRole());

    }

    public void startDayNight(View view) {

        if (user.getRole() == "wolf") {
            Intent intent = new Intent(this, NightWolf.class);
            intent.putExtra("Client_Data", user);
            intent.putExtra("Room_data", mRoom);
            startActivity(intent);
        }
        if (user.getRole() == "doc") {
            Intent intent = new Intent(this, NightDoc.class);
            intent.putExtra("Client_Data", user);
            intent.putExtra("Room_data", mRoom);
            startActivity(intent);
        }
        if (user.getRole() == "villager") {
            Intent intent = new Intent(this, NightVillager.class);
            intent.putExtra("Client_Data", user);
            intent.putExtra("Room_data", mRoom);
            startActivity(intent);
        }
    }
}

