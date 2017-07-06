package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DayNight extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
        user = getIntent().getExtras().getParcelable("Client_Data");
        //TODO:get players here
        List<User> players = new ArrayList();
        startGame((ArrayList<User>) players, user);
    }



    public void startGame(ArrayList<User> players, User user)
    {
        //the main loop of the game, while the gameOver is false, the game continues
        boolean gameOver = false;
        while(!gameOver)
        {
            //do the night stuff
            goToNight(players, user);
            //do the day stuff
            //goToDay(players);
            //check if the game is over
            for(int i = 0; i < players.size(); i++) {
                int goodWinning = 0;
                if(players.get(i).getRole() == "villager" || players.get(i).getRole() == "doc") {
                    goodWinning++;
                }
                if(players.get(i).getRole() == "wolf") {
                    goodWinning--;
                }
                //if there are less good than bad, the game ends, or if there are no more bad, the game ends
                if(goodWinning < 0 || goodWinning >= players.size()-1) {
                    gameOver = true;
                }
            }
        }
    }
    public void goToNight(ArrayList<User> Players, User you) {
        //TODO:bring up the night interface, I don't know how to do that
        //the night isn't over
        boolean nightOver = false;
        Night tonight = new Night();
        tonight.set_roleDoer(you);
        tonight.doRole();
        //loop until everyone is done with the night
        while(nightOver = false) {
            nightOver = true;
            for (int i = 0; i < Players.size(); i++) {
                if (!Players.get(i).is_vote1()) {
                    nightOver = false;
                }
            }
        }
        //the host will perform everyone's roles
        //update the firebase based on the actions here
    }

    public void goToDay(ArrayList<User> Players, User you) {
        //open the day interface
        boolean dayOver = false;
    }

    public void StartMainActivty(View view) {
        Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }


}
