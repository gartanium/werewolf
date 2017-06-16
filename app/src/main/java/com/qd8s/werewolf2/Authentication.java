package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.Client;

/**
 * The main activity for the game.
 */
public class Authentication extends AppCompatActivity {

    /**
     * The client contains all the data for the user.
     */
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        client = new Client();
    }

    // Put authentication code here.
    public void authenticate(View view) {

    }

    public void startGameMenu(View view) {
        Intent intent = new Intent(this, GameMenu.class);
        intent.putExtra("Client_Data", client);
        startActivity(intent);
    }

}
