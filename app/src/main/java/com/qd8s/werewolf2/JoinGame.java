package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.MasterListAdapter;

public class JoinGame extends AppCompatActivity {

    RoomAdapter mRoom;
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
        mRoom.connectToRoom("matt");
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
