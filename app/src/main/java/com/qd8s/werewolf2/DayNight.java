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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
        //get players here
        List<User> players = new ArrayList();
        startGame((ArrayList<User>) players);
    }



    public void startGame(ArrayList<User> players)
    {
        boolean gameOver = false;
        while(!gameOver)
        {
            //goToNight(players);
            //goToDay(players);
            for(int i = 0; i < players.size(); i++) {
                int goodWinning = 0;
                if(players.get(i).getRole() == "villager" || players.get(i).getRole() == "doc") {
                    goodWinning++;
                }
                if(players.get(i).getRole() == "wolf") {
                    goodWinning--;
                }
                if(goodWinning < 0 || goodWinning == players.size()-1) {
                    gameOver = true;
                }
            }
        }
    }
    public void goToNight(ArrayList<User> Players, User you) {
        //bring up the night interface, I don't know how to do that
        boolean nightOver = false;
        Night tonight = new Night();
        tonight.set_roleDoer(you);
        tonight.doRole();
        while(nightOver = false) {
            nightOver = true;
            for (int i = 0; i < Players.size(); i++) {
                if (!Players.get(i).isActDone()) {
                    nightOver = false;
                }
            }
        }
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
