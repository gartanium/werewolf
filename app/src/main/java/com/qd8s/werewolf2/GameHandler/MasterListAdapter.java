package com.qd8s.werewolf2.GameHandler;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.qd8s.werewolf2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/9/2017.
 * This class contains a real time reference to every key for every Room, and
 * the player count of every single Room.
 */

public class MasterListAdapter {

    /**
     * Our local version of the Master List.
     */
    private MasterList mMasterList;

    /**
     *
     * @return Returns a copy of the local version of the Master List.
     */
    public MasterList getMasterList() { return mMasterList; }


    /**
     * The permanent ID of the "Master List".
     */
    private final String mID = "MASTERLIST";

    /**
     * Tag for any errors related to this class.
     */
    private final String TAG = "MasterListAdapter";

    /**
     * Reference to the database.
     */
    private DatabaseReference mRef;

    /**
     * A class containing a reference to the IDS of all the Rooms.
     */
    public MasterListAdapter() {
        mRef = FirebaseDatabase.getInstance().getReference(mID);
    }

    /**
     * Add an event listener to a room reference.
     *
     * @param ref Reference to add the listener to.
     */
    private void addChildEventListener(final DatabaseReference ref) {

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "UpdateMasterList:onCancelled", databaseError.toException());
                /*Toast.makeText(mContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();*/
            }
        };
        ref.addChildEventListener(childEventListener);
    }
}