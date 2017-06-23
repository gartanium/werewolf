package com.qd8s.werewolf2.GameHandler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 6/22/2017.
 */

public class FireBaseRoomHandler {

    DatabaseReference mRef;
    Room mRoom;

    private static final String TAG = "FireBaseHandler";

    public FireBaseRoomHandler() {
        // Does the room exist?
            // No?
                // Create it, set the player as host!
            // Yes?
                // Join it.

    }

    /**
     * Add a room to FireBase.
     * @param room Room to add.
     */
    private void addRoom(final Room room, Client client) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nextRef = database.getReference(room.getID());

        Gson gson = new Gson();
        String dataToFirebase = gson.toJson(room);
        nextRef.setValue(dataToFirebase);

        //mRoomRefs.put(nextRef, room);
        addEventListener(nextRef);
    }

    /**
     * Add a client to FireBase
     * @param client Client to add.
     */
    private void addClient(final Client client) {

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
                Room newRoom = gson.fromJson(roomJson, Room.class);

                mRoom.updateRoom(newRoom.getClients());

                Log.v(TAG, "Updating Reference!");
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        ref.addValueEventListener(roomListener);
    }

}
