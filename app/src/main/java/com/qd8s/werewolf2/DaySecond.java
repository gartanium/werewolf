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

public class DaySecond extends AppCompatActivity {
    private List<User> userList;
    private User currentPlayer;
    private RoomAdapter mRoom;
    private User target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_second);

        userList = new ArrayList<User>();

        Intent intent = getIntent();

        currentPlayer = intent.getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        userList = mRoom.getUsers();
// get the user data from FireBase!
        ListView listview = (ListView) findViewById(R.id.listView1);
        UserListAdapter adapter = new UserListAdapter(this, userList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                target = userList.get(position);
                currentPlayer.setTarget(target);
                Log.d("Listener", currentPlayer.getTarget().getName());
                for (int i = 0; i < userList.size(); i++) {
                    if (currentPlayer.getName() == userList.get(i).getName() && currentPlayer.is_voteReady() == false && currentPlayer.isAlive() == true) {
                        userList.get(i).setTarget(currentPlayer.getTarget());
                        userList.get(i).set_voteReady(true);
                    }
                }
                mRoom.updateUsers(userList);
            }
        });
    }
}
