package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.NoIdeaWhatToCallThisPackage.Lobby;

public class GameLobby extends AppCompatActivity {

    private Lobby lobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
    }

    public void startRoleDescription(View view) {

        Intent intent = new Intent(this, RoleDescription.class);
        startActivity(intent);
    }

    /**
     * Updates the player's version of the lobby.
     * The player is able to kick others if they are the host.
     */
    public void updateLobby(Lobby lobby) {

    }
}
