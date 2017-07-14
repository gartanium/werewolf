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
import java.util.Map;

public class DaySecond extends AppCompatActivity {
    private List<User> userList;
    private User currentPlayer;
    private RoomAdapter mRoom;
    private User target;
    private Map<User, Integer> topMap;
    private final String TAG = "DaySecond";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_second);

        userList = new ArrayList<User>();

        //Intent intent = getIntent();

        currentPlayer = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");
        topMap = getIntent().getExtras().getParcelable("Map");

        //getMap put it into an array list
        userList = mRoom.getUsers();

        final ArrayList<User> aliveUsers = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).isAlive())
            {
                aliveUsers.add(userList.get(i));
            }
        }
// get the mUser data from FireBase!
        ListView listview = (ListView) findViewById(R.id.listView3);
        UserListAdapter adapter = new UserListAdapter(this, aliveUsers);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                target = aliveUsers.get(position);
                currentPlayer.setTarget(target);
                Log.d("Listener", currentPlayer.getTarget().getName());
                for (int i = 0; i < aliveUsers.size(); i++) {
                    if (currentPlayer.getName().equals(aliveUsers.get(i).getName()) && currentPlayer.is_voteReady() == false && currentPlayer.isAlive() == true) {
                        aliveUsers.get(i).setTarget(currentPlayer.getTarget());
                        aliveUsers.get(i).set_voteReady(true);
                    }
                }

                mRoom.updateUsers(aliveUsers);
            }
        });
    }

    public void goToNight(View view) {
        Log.v(TAG, "In go to Night");
        if (currentPlayer.getRole().equals("wolf")) {
            Intent intent = new Intent(this, NightWolf.class);
            intent.putExtra("Client_Data", currentPlayer);
            intent.putExtra("Room_Data", mRoom);
            Log.v(TAG, "Starting night for wolf!");
            startActivity(intent);
        }
        if (currentPlayer.getRole().equals("doc")) {
            Intent intent = new Intent(this, NightDoc.class);
            intent.putExtra("Client_Data", currentPlayer);
            intent.putExtra("Room_Data", mRoom);
            Log.v(TAG, "Starting night for doc!");
            startActivity(intent);
        }
        if (currentPlayer.getRole().equals("villager")) {
            Intent intent = new Intent(this, NightVillager.class);
            intent.putExtra("Client_Data", currentPlayer);
            intent.putExtra("Room_Data", mRoom);
            Log.v(TAG, "Starting night for villager!");
            startActivity(intent);
        }
    }
}
