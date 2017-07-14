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

public class NightVillager extends AppCompatActivity {
    private List<User> userList;
    private User mUser;
    private RoomAdapter mRoom;
    private User target;
    private final String TAG = "NightVillager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_villager);

        userList = new ArrayList<User>();

        //Intent intent = getIntent();

        mUser = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        mRoom.addListener(new NightFinishedListener() {
            @Override
            public void onNightFinished() {
                Intent intent = new Intent(getBaseContext(), DayMain.class);
                intent.putExtra("Client_Data", mUser);
                intent.putExtra("Room_Data", mRoom);
                startActivity(intent);
            }
        });
    }

    public void onReadyButton(View view) {

        mUser.setState(User.UserState.DoneWithNight);
        mRoom.updateUser(mUser);
        mRoom.finishNight(mUser);

    }
}
