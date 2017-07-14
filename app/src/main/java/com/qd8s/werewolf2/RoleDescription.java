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

        user = mRoom.getUser(user);
        Log.v("RoleAssigner", "Your role is: " + user.getRole());

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
            des = "As the Doc you will have the chance every night to make" +
                    " one person immune from death for only that night." +
                    " During the day you will try to determine who the wolf is. " +
                    " Your goal is to find all the wolves.";

            descriptions = (TextView) findViewById(R.id.description);
            descriptions.setText(des);
        }
        else if (user.getRole().equals("wolf"))
        {
            des = "As a Wolf and any additional wolves will pick someone to kill" +
                    " every night. During the day you will try to avert suspicion so" +
                    " you won't be killed. Your goal is to have only the wolves survive.";

            descriptions = (TextView) findViewById(R.id.description);
            descriptions.setText(des);
        }
        else
        {
            des = "As a villager you will want to say your prayers at night, that" +
                    " is when the wolves come to attack. During the day will try to" +
                    " figure out who the wolf is and vote them to be killed off." +
                    " Your goal is to find all the wolves.";
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

