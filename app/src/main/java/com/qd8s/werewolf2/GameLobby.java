package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.RoomStartListener;

public class GameLobby extends AppCompatActivity {

    /**
     * _room contains all of the data for the players.
     */
    User mUser;
    RoomAdapter mRoom;

    private static final String TAG = "GameLobby";

    /**
     * When this activity is created, the mUser is added to the lobby.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        mUser = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");
        String msg = mRoom.getID();
        Log.v(TAG, "Loaded data froom Room: " + msg);

        mRoom.addListener(
                new RoomStartListener() {
                    @Override
                    public void onRoomStart() {
                        Intent intent = new Intent(getBaseContext(), RoleDescription.class);
                        intent.putExtra("Client_Data", mUser);
                        intent.putExtra("Room_Data", mRoom);
                        startActivity(intent);
                    }
                }
        );

        ListView listview = (ListView) findViewById(R.id.GameLobbyListView);
        UserListAdapter adapter = new UserListAdapter(this, mRoom.getUsers());
        listview.setAdapter(adapter);
    }


    public void startRoleDescription(View view) {
        mRoom.startRoom(mUser);
    }

}
