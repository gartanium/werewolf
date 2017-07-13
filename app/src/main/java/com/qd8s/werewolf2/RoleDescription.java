package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RoleDescription extends AppCompatActivity {

    private User user;
    private RoomAdapter mRoom;
    private TextView role;
    private TextView descriptions;
    private String des;

    private final String TAG = "RoleDescription";
    /**
     * Use the RoomAdapter class to get all your logic for the Room.
     * The mRoom will contain a list of all clients and their associated users.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("RoleAssigner", "Created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_description);

        user = getIntent().getExtras().getParcelable("Client_Data");
        mRoom = getIntent().getExtras().getParcelable("Room_Data");

        // Set the users state to be in RoleDescription.
        user.setActivityLocation(User.ActivityLocation.RoleDescription);
        //user.setName("FOOBAR");
        //mRoom.updateUser(user);
        //mUser.set_host(true);

        User player = mRoom.getUser(user);
        user = player;
        role = (TextView) findViewById(R.id.userRole);
        role.setText(user.getRole());

        if (user.getRole().equals("doc")){
            des = "As Doc you will have the chance during each night to make" +
                    " one person immune from death during the night." +
                    " During the day you will trying to vote for and kill the wolfs" +
                    " befeore wolfs win.";

            descriptions = (TextView) findViewById(R.id.description);
            descriptions.setText(des);
        }
        else if (user.getRole().equals("wolf"))
        {
            des = "As a Wolf you and if there are other wolfs you will pick someone to kill" +
                    " every night. During the day you will vote to trying to get them to kill" +
                    " more villagers and not a wolf.";

            descriptions = (TextView) findViewById(R.id.description);
            descriptions.setText(des);
        }
        else
        {
            des = "As a villager during the night you will just click the button that" +
                    " says you a villager. During the day will particapate in voting to try" +
                    " to kill a wolf";
            descriptions = (TextView) findViewById(R.id.description);
            descriptions.setText(des);
        }


    }

    public void startDayNight(View view) {


        if (user.getRole().equals("wolf")) {
            Intent intent = new Intent(this, NightWolf.class);
            intent.putExtra("Client_Data", user);
            intent.putExtra("Room_Data", mRoom);
            Log.v(TAG, "Starting night for wolf!");
            startActivity(intent);
        }
        if (user.getRole().equals("doc")) {
            Intent intent = new Intent(this, NightDoc.class);
            intent.putExtra("Client_Data", user);
            intent.putExtra("Room_Data", mRoom);
            Log.v(TAG, "Starting night for doc!");
            startActivity(intent);
        }
        if (user.getRole().equals("villager")) {
            Intent intent = new Intent(this, NightVillager.class);
            intent.putExtra("Client_Data", user);
            intent.putExtra("Room_Data", mRoom);
            Log.v(TAG, "Starting night for villager!");
            startActivity(intent);
        }
    }
}

