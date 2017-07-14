package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.FirebaseConnectedListener;
import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.MasterListAdapter;

public class JoinGame extends AppCompatActivity {

    RoomAdapter mRoom;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("JoinGameActivity", "In Activity Join Game");
        // Get the mUser data from the last activity.
        mUser = getIntent().getExtras().getParcelable("Client_Data");
        setContentView(R.layout.activity_join_game);

        mRoom = new RoomAdapter();


        mRoom.addListener(new FirebaseConnectedListener() {
            @Override
            public void onConnected() {
                Log.v("JoinGameActivity", "Firing connected to firebase!");

                    // Initialize the Intent.
                    Log.v("JoinGameActivity", "Firing connected to firebase!");
                    Intent intent = new Intent(getBaseContext(), GameLobby.class);


                    mRoom.joinRoom(mUser); // Seriously, please remember to change this later!

                    // Store the User data into the intent, to send it to the next activity.
                    intent.putExtra("Client_Data", mUser);
                    intent.putExtra("Room_Data", mRoom);
                    startActivity(intent);

            }
        });
    }

    public void startGameLobby(View view) {
        Log.v("JoinGameActivity", "Joining Lobby!");
        mRoom.connectToRoom("matt");
    }
}
