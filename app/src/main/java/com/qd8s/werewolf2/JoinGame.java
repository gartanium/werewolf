package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.MasterListAdapter;

public class JoinGame extends AppCompatActivity {

    RoomAdapter mRoom;
    EditText roomName;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the mUser data from the last activity.
        mUser = getIntent().getExtras().getParcelable("Client_Data");
        setContentView(R.layout.activity_join_game);

        mRoom = new RoomAdapter();
    }

    public void downloadLobby(View view) {
        roomName = (EditText) findViewById(R.id.roomNameInput);
        mRoom.connectToRoom(roomName.getText().toString());
        if (mRoom.isConnectedToFirebase()) {
            Toast.makeText(JoinGame.this, "Connected to " + roomName.getText().toString() + ".",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(JoinGame.this, "Connection to " + roomName.getText().toString() + " failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void startGameLobby(View view) {
        if(mRoom.isConnectedToFirebase()){
            // Initialize the Intent.
            Intent intent = new Intent(this, GameLobby.class);


           mRoom.joinRoom(mUser); // Seriously, please remember to change this later!

            // Store the User data into the intent, to send it to the next activity.
            intent.putExtra("Client_Data", mUser);
            intent.putExtra("Room_Data", mRoom);
            startActivity(intent);
        }
    }
}
