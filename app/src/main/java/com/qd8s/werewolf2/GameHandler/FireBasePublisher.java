package com.qd8s.werewolf2.GameHandler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 6/22/2017.
 */

public class FireBasePublisher {

    private Map<DatabaseReference, Room> mRoomRefs;

    private static final String TAG = "FireBasePublisher";

    public FireBasePublisher() {
        mRoomRefs = new HashMap<DatabaseReference, Room>();

    }

    /**
     * Add a room to FireBase.
     * @param room Room to add.
     */
    public void addRoom(final Room room) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nextRef = database.getReference(room.getID());
        mRoomRefs.put(nextRef, room);
        addEventListener(nextRef);

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
                Room newRoom = dataSnapshot.getValue(Room.class);
                mRoomRefs.get(ref).updateRoom(newRoom.getClients());
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
