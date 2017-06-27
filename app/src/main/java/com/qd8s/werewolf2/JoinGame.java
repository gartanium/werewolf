package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.Client;
import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.MasterListAdapter;

public class JoinGame extends AppCompatActivity {

    MasterListAdapter masterListAdapter;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the client data from the last activity.
        client = getIntent().getExtras().getParcelable("Client_Data");
        setContentView(R.layout.activity_join_game);
    }

    public void startGameLobby(View view) {
        Intent intent = new Intent(this, GameLobby.class);

        client.set_host(false);
        RoomAdapter roomHandler = new RoomAdapter(client);
        roomHandler.joinRoom("foo"); // Seriously, please remember to change this later!

        startActivity(intent);
    }



}
