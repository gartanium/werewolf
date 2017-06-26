package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.Client;
import com.qd8s.werewolf2.GameHandler.Room;

public class GameLobby extends AppCompatActivity {

    /**
     * _room contains all of the data for the players.
     */
    private Room _room;
    Client client;

    /**
     * When this activity is created, the user is added to the lobby.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        client = getIntent().getExtras().getParcelable("Client_Data");
    }


    public void startRoleDescription(View view) {

        Intent intent = new Intent(this, RoleDescription.class);
        intent.putExtra("Client_Data", client);
        startActivity(intent);
    }

    /**
     * Updates the player's version of the room.
     * The player is able to kick others if they are the host.
     */
    public void updateLobby() {
        // Load the Lobby from Firebase, into this activity.

        // _room = room_from_firebase

    }
}
