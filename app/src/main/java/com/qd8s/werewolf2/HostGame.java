package com.qd8s.werewolf2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.qd8s.werewolf2.GameHandler.Room;

public class HostGame extends AppCompatActivity {

    /**
     * Publishes the Lobby to firebase.
     */
    protected void publishToFirebase() {
        // Generate a Lobby, and add it to Firebase!
        Integer maxRoomSize = R.integer.max_room_size;

        Room newRoom = new Room(maxRoomSize);
        Gson gson = new Gson();

        // Gson converts it into a Json string.
        String dataToFirebase =  gson.toJson(newRoom);

       // String dataToFirebase = "Cruel world!";

        // Now send the data up to Firebase!!
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("The Only Lobby That Matters");
        myRef.setValue(dataToFirebase);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_game);
    }

    /**
     * Start the GameLobby activity, and publish to firebase!
     * @param view
     */
    public void startGameLobby(View view) {

        Intent intent = new Intent(this, GameLobby.class);
        publishToFirebase();
        startActivity(intent);
    }


}
