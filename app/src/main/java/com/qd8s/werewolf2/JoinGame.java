package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qd8s.werewolf2.NoIdeaWhatToCallThisPackage.MasterList;

public class JoinGame extends AppCompatActivity {

    MasterList masterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }

    public void startGameLobby(View view) {
        Intent intent = new Intent(this, GameLobby.class);
        startActivity(intent);
    }

    public void updateMasterList(MasterList newList) {
        masterList = newList;
    }

}
