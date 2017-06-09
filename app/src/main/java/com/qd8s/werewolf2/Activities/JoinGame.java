package com.qd8s.werewolf2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.Activities.GameLobby;
import com.qd8s.werewolf2.R;

public class JoinGame extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }

    public void startGameLobby(View view) {
        Intent intent = new Intent(this, GameLobby.class);
        startActivity(intent);
    }
}
