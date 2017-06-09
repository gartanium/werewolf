package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startHostGame(View view) {
        Intent intent = new Intent(this, HostGame.class);
        startActivity(intent);
    }

    public void startJoinGame(View view) {
        Intent intent = new Intent(this, JoinGame.class);
        startActivity(intent);
    }
}

