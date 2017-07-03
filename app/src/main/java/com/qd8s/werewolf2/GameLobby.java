package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.Room;
import com.qd8s.werewolf2.GameHandler.RoomAdapter;

public class GameLobby extends AppCompatActivity {

    /**
     * _room contains all of the data for the players.
     */
    User user;
    RoomAdapter room;

    private static final String TAG = "GameLobby";

    /**
     * When this activity is created, the user is added to the lobby.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        user = getIntent().getExtras().getParcelable("Client_Data");
        room = getIntent().getExtras().getParcelable("Room_Data");
        String msg = room.getID();
        Log.v(TAG, "Loaded data froom Room: " + msg);

    }


    public void startRoleDescription(View view) {

        Intent intent = new Intent(this, RoleDescription.class);
        intent.putExtra("Client_Data", user);
        startActivity(intent);
    }

}
