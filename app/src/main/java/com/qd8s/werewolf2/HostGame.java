package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HostGame extends AppCompatActivity {

    /**
     * Publishes the Lobby to firebase.
     */
    protected void publishToFirebase() {
        // Just freaking add the thing.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_game);
    }

    public void startGameLobby(View view) {
        Intent intent = new Intent(this, GameLobby.class);
        startActivity(intent);
    }


}
