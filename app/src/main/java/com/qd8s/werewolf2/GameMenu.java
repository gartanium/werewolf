package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameMenu extends AppCompatActivity {

    User user;
    TextView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Intent i = getIntent();

        user = i.getExtras().getParcelable("Client_Data");

        welcomeView = (TextView)findViewById(R.id.welcomeView);
        welcomeView.setText("Welcome, " + user.getName());

    }

    public void startHostGame(View view) {
        // Set the mUser as the host!
        user.set_host(true);

        Intent intent = new Intent(this, HostGame.class);
        intent.putExtra("Client_Data", user);
        startActivity(intent);
    }

    public void startJoinGame(View view) {
        // Don't let the mUser be the host!
        user.set_host(false);

        Intent intent = new Intent(this, JoinGame.class);
        intent.putExtra("Client_Data", user);
        startActivity(intent);
    }


}

