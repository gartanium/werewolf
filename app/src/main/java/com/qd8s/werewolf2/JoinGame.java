package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.MasterListAdapter;

public class JoinGame extends AppCompatActivity {

    MasterListAdapter masterListAdapter;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the user data from the last activity.
        mUser = getIntent().getExtras().getParcelable("Client_Data");
        setContentView(R.layout.activity_join_game);
    }

    public void startGameLobby(View view) {

        // Initialize the Intent.
        Intent intent = new Intent(this, GameLobby.class);

        // set the status of the User.
        RoomAdapter roomHandler = new RoomAdapter();
        roomHandler.joinRoom("foo", mUser); // Seriously, please remember to change this later!

        // Store the User data into the intent, to send it to the next activity.
        intent.putExtra("Client_Data", mUser);
        intent.putExtra("Room_Data", roomHandler);
        startActivity(intent);
    }

}
