package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

public class DayMain extends AppCompatActivity {
    private List<User> userList;
    private User currentPlayer;
    private RoomAdapter mRoom;
    private User target;
    private final String TAG = "DayMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_main);

        userList = new ArrayList<User>();

        //Intent intent = getIntent();

        currentPlayer = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        userList = mRoom.getUsers();
// get the mUser data from FireBase!
        final ArrayList<User> aliveUsers = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).isAlive())
            {
                aliveUsers.add(userList.get(i));
                Log.v("Checking alive users", aliveUsers.get(i).getName());
            }
        }
        ListView listview = (ListView) findViewById(R.id.listView1);
        UserListAdapter adapter = new UserListAdapter(this, aliveUsers);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                target = aliveUsers.get(position);
                currentPlayer.setTarget(target);
                Log.d("Listener", currentPlayer.getTarget().getName());
                for (int i = 0; i < aliveUsers.size(); i++) {
                    Log.v("Testing inside forloop", aliveUsers.get(i).getName());
                    Log.v("Testing vote Ready", String.valueOf(currentPlayer.is_voteReady()));
                    Log.v("Testing Is Alive", String.valueOf(currentPlayer.isAlive()));
                    if (currentPlayer.getName().equals(aliveUsers.get(i).getName()) && currentPlayer.is_voteReady() == false && currentPlayer.isAlive() == true) {
                        Log.v("Testing inside if", aliveUsers.get(i).getName());
                        aliveUsers.get(i).setTarget(currentPlayer.getTarget());
                        aliveUsers.get(i).set_voteReady(true);
                        Log.v("Testing User", currentPlayer.getName());
                        Log.v("Testing Target", currentPlayer.getTarget().getName());
                    }
                }
                for (int i = 0; i < userList.size(); i++){
                    for (int j = 0; j < aliveUsers.size(); j++){
                        if (userList.get(i).getName().equals(aliveUsers.get(j).getName())) {
                            userList.set(i, aliveUsers.get(j));
                            break;
                        }
                    }
                }
                mRoom.updateUsers(userList);
            }
        });

    }

    public void onReadyDaySecond(View view) {
        //currentPlayer.setState(User.UserState.DoneWithNight);
        Log.v(TAG, "Entering onReadyDaySecond");
        // NOTE!!!!!!!!!!!!!!!!!!
        // When ever the User updates,
        // Because of our event up above,
        // A check goes to see if everyone is ready.
        // If everyone is ready, then it moves to the next Lobby!
        // I hope.
        mRoom.updateUser(currentPlayer);
        Intent intent = new Intent(this, DaySecond.class);
        intent.putExtra("Client_Data", currentPlayer);
        intent.putExtra("Room_Data", mRoom);
        Log.v(TAG, "Starting day second");
        startActivity(intent);

    }
}
