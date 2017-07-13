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

public class NightWolf extends AppCompatActivity {
    private List<User> userList;
    private User mUser;
    private RoomAdapter mRoom;
    private User target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_wolf);

        userList = new ArrayList<User>();

        Intent intent = getIntent();

        mUser = intent.getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        userList = mRoom.getUsers();

        ListView listview = (ListView) findViewById(R.id.listView2);
        UserListAdapter adapter = new UserListAdapter(this, userList);
        listview.setAdapter(adapter);

        /**
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                target = userList.get(position);
                mUser.setTarget(target);
                Log.d("Listener", mUser.getTarget().getName());
                for (int i = 0; i < userList.size(); i++) {
                    if (mUser.getName() == userList.get(i).getName() && mUser.is_voteReady() == false && mUser.isAlive() == true) {
                        userList.get(i).setTarget(mUser.getTarget());
                        userList.get(i).set_voteReady(true);
                    }
                }
                mRoom.updateUsers(userList);
            }
        });
         **/

        /**
        mRoom.addListener(new NightFinishedListener() {
            @Override
            public void onNightFinished() {
                Intent intent = new Intent(getBaseContext(), DayMain.class);
                intent.putExtra("Client_Data", mUser);
                intent.putExtra("Room_Data", mRoom);
                startActivity(intent);
            }
        });**/
    }
    //TODO:an onclick that sets the target of the given user to the user they click on

    // TODO: Put logic here for when the User hits the ready button.
    public void onReadyButton(View view) {
        mUser.setState(User.UserState.DoneWithNight);

        // NOTE!!!!!!!!!!!!!!!!!!
        // When ever the User updates,
        // Because of our event up above,
        // A check goes to see if everyone is ready.
        // If everyone is ready, then it moves to the next Lobby!
        // I hope.
        mRoom.updateUser(mUser);

    }
}
