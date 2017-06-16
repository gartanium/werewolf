package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Intent i = getIntent();

        client = i.getExtras().getParcelable("Client_Data");

        welcomeView = (TextView)findViewById(R.id.welcomeView);
        welcomeView.setText("Welcome, " + client.get_ID());

    }

    public void startHostGame(View view) {
        Intent intent = new Intent(this, HostGame.class);
        startActivity(intent);
    }

    public void startJoinGame(View view) {
        Intent intent = new Intent(this, JoinGame.class);
        startActivity(intent);
    }


}

