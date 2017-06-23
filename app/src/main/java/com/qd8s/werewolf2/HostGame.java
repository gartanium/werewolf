package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.qd8s.werewolf2.GameHandler.Client;
import com.qd8s.werewolf2.GameHandler.FireBaseRoomHandler;
import com.qd8s.werewolf2.GameHandler.Room;

public class HostGame extends AppCompatActivity {

    private Client client;
    /**
     * Publishes the Lobby to firebase.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = getIntent().getExtras().getParcelable("Client_Data");
        setContentView(R.layout.activity_host_game);
    }

    /**
     * Start the GameLobby activity, and publish to firebase!
     * @param view
     */
    public void startGameLobby(View view) {

        EditText text = (EditText)findViewById(R.id.gameName);
        String newRoomName = text.getText().toString();

        Intent intent = new Intent(this, GameLobby.class);

        client.is_Host();

        FireBaseRoomHandler roomHandler = new FireBaseRoomHandler(client, newRoomName);

        startActivity(intent);
    }


}
