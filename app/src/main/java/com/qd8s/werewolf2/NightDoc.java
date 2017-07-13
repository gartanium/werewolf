package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qd8s.werewolf2.GameHandler.NightFinishedListener;
import com.qd8s.werewolf2.GameHandler.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

public class NightDoc extends AppCompatActivity {
    private List<User> userList;
    private User mUser;
    private RoomAdapter mRoom;
    private User target;
    private final String TAG = "NightDoc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_doc);
        Intent intent = getIntent();

        mUser = intent.getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        userList = mRoom.getUsers();
        final ArrayList<User> aliveUsers = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).isAlive())
            {
                aliveUsers.add(userList.get(i));
                Log.v("Checking alive users", aliveUsers.get(i).getName());
            }
        }
// get the mUser data from FireBase!
        ListView listview = (ListView) findViewById(R.id.listView);
        UserListAdapter adapter = new UserListAdapter(this, aliveUsers);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                target = aliveUsers.get(position);
                mUser.setTarget(target);
                Log.d("Listener", mUser.getTarget().getName());
                for (int i = 0; i < aliveUsers.size(); i++) {
                    if (mUser.getName() == aliveUsers.get(i).getName() && mUser.is_voteReady() == false && mUser.isAlive() == true) {
                        aliveUsers.get(i).setTarget(mUser.getTarget());
                        aliveUsers.get(i).set_voteReady(true);
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


        /*mRoom.addListener(new NightFinishedListener() {
            @Override
            public void onNightFinished() {
                Intent intent = new Intent(getBaseContext(), DayMain.class);
                intent.putExtra("Client_Data", mUser);
                intent.putExtra("Room_Data", mRoom);
                startActivity(intent);
            }
        });*/
    }
    //TODO:an onclick that sets the target of the given user to the user they click on


    // TODO: Put logic here for when the User hits the ready button.
    public void onReadyButton(View view) {
       // mUser.setState(User.UserState.DoneWithNight);

        // NOTE!!!!!!!!!!!!!!!!!!
        // When ever the User updates,
        // Because of our event up above,
        // A check goes to see if everyone is ready.
        // If everyone is ready, then it moves to the next Lobby!
        // I hope.
        mRoom.updateUser(mUser);
        Intent intent = new Intent(this, DayMain.class);
        intent.putExtra("Client_Data", mUser);
        intent.putExtra("Room_Data", mRoom);
        Log.v(TAG, "Starting day for the doctor ");
        startActivity(intent);

    }

}
