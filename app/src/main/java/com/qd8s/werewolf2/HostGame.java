package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;

public class HostGame extends AppCompatActivity {

    private User user;
    /**
     * Publishes the Lobby to firebase.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getExtras().getParcelable("Client_Data");
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

        RoomAdapter roomHandler = new RoomAdapter(user);
        roomHandler.hostRoom(newRoomName);

        intent.putExtra("Client_Data", user);

        startActivity(intent);
    }


}
