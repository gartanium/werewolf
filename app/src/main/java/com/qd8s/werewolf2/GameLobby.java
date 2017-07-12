package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.RoomStartListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLobby extends AppCompatActivity {

    /**
     * _room contains all of the data for the players.
     */
    User mUser;
    RoomAdapter mRoom;
    private User user;
    private int numPlayers;
    private int numWolfs;
    private int numAssignedRoles;

    private static final String TAG = "GameLobby";

    /**
     * When this activity is created, the mUser is added to the lobby.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        mUser = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");
        String msg = mRoom.getID();
        Log.v(TAG, "Loaded data froom Room: " + msg);

        mRoom.addListener(
                new RoomStartListener() {
                    @Override
                    public void onRoomStart() {
                        assignRoles();
                        Intent intent = new Intent(getBaseContext(), RoleDescription.class);
                        intent.putExtra("Client_Data", mUser);
                        intent.putExtra("Room_Data", mRoom);
                        startActivity(intent);
                    }
                }
        );

        ListView listview = (ListView) findViewById(R.id.GameLobbyListView);
        UserListAdapter adapter = new UserListAdapter(this, mRoom.getUsers());
        listview.setAdapter(adapter);
    }


    public void startRoleDescription(View view) {
        mRoom.startRoom(mUser);
    }

    public void assignRoles()
    {
            List<User> players;
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
    //}

}
