package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qd8s.werewolf2.GameHandler.Client;

public class GameMenu extends AppCompatActivity {

    Client client;
    TextView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Log.i("i am here", "Game Menu");
        Intent i = getIntent();

        client = i.getExtras().getParcelable("Client_Data");
        //Log.i(client.getPlayer().getName(), "is my player name");

        welcomeView = (TextView)findViewById(R.id.welcomeView);
        welcomeView.setText("Welcome, " + client.get_ID());

    }

    public void startHostGame(View view) {
        // Set the client as the host!
        client.set_host(true);

        Intent intent = new Intent(this, HostGame.class);
        intent.putExtra("Client_Data", client);
        startActivity(intent);
    }

    public void startJoinGame(View view) {
        // Don't let the client be the host!
        client.set_host(false);

        Intent intent = new Intent(this, JoinGame.class);
        intent.putExtra("Client_Data", client);
        startActivity(intent);
    }


}

