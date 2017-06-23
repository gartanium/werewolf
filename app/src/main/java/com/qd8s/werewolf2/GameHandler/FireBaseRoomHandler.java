package com.qd8s.werewolf2.GameHandler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 6/22/2017.
 */

public class FireBaseRoomHandler {

    DatabaseReference mRef;
    Room mRoom;

    // Max players in a room.
    private final int maxPlayers = 12;
    private static final String TAG = "FireBaseHandler";

    /**
     * A Firebase handler containing a Room.
     * @param roomID ID of the room.
     */
    public FireBaseRoomHandler(Client client, String roomID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference(roomID);

        // Does the room exist?
            // No?
                // Create it, set the player as host!
            // Yes?
                // Join it.

        // For now, this code will implement the above until I can figure it out.
        if(client.is_host()) {
            Log.v(TAG, "Client is hosting the game! " + client.get_ID());
            addRoom(client, roomID); // Adds a new room to firebase.
        }
        else {
            initializeJoinGameRoom(client);  // Sets this room's reference to Firebase's version on update.
            addClientToRoom(client);
        }
    }

    /**
     * Add a room to FireBase.
     * @param roomID Name of the new Room.
     */
    private void addRoom(Client client, String roomID) {

        mRoom = new Room(maxPlayers, roomID);
        mRoom.addClient(client);

        Gson gson = new Gson();
        String dataToFirebase = gson.toJson(mRoom);
        mRef.setValue(dataToFirebase);

        addEventListener(mRef);
    }

    /**
     * Add an event listener to a room reference.
     * @param ref Reference to add the listener to.
     */
    private void addEventListener(final DatabaseReference ref) {

        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                String roomJson = dataSnapshot.getValue(String.class);

                Gson gson = new Gson();
                mRoom = gson.fromJson(roomJson, Room.class);

                Log.v(TAG, "Updating Room! Client Name: " + mRoom.getClients().get(0).get_ID());
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        // Attach the event listener!
        ref.addValueEventListener(roomListener);

    }

    private void initializeJoinGameRoom(Client client) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Room room. Get First time use.
                        String roomJson = dataSnapshot.getValue(String.class);

                        Gson gson = new Gson();
                        mRoom = gson.fromJson(roomJson, Room.class);

                        Log.v(TAG, "Updating Room! Client Name: " + mRoom.getClients().get(0).get_ID());
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addClientToRoom(Client client) {
        mRoom.addClient(client);
        mRef.setValue(mRoom);
    }


}
