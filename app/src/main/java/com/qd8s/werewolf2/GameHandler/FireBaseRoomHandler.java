package com.qd8s.werewolf2.GameHandler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/22/2017.
 * Description: FireBaseRoomHandler is a class for handeling the Client's local version of a Room,
 * and updating it whenever there is a change in Firebase. Additionaly, it will at some point
 * contain logic for updating the Firebase's version any time the local one changes. For now,
 * it allows a Client to join the Room in firebase upon FireBaseRoomHandler initialization if the
 * Client isn't the Host. If they are the host, then this Handler will go ahead and create a new
 * Room in Firebase for us and others to join.Then it will have the Client join it.
 */

public class FireBaseRoomHandler {

    // A reference to the room in Firebase
    private DatabaseReference mRef;

    // The local version of the room.
    private Room mRoom;

    // The client associated with this handler.
    private Client mClient;

    // A boolean to determine if the client should be added to the room.
    //  If false, when the next update occurs, the client will join.
    // Then it should be set to false.
    private boolean mJoinRoom;

    // Max players in a room.
    private final int MAXPLAYERS = 12;

    // Tag for debugging.
    private static final String TAG = "FireBaseHandler";

    // Name of the Master list that all things get published to.
    private static final String MASTERLIST = "MasterList";

    /**
     * @return A copy of the List of Clients in the Room.
     */
    public List<Client> getClients() { return new ArrayList<Client>(mRoom.getClients());}

    /**
     * A Firebase handler containing a Room.
     * @param roomID ID of the room.
     */
    public FireBaseRoomHandler(Client client, String roomID) {
        mJoinRoom = false;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference(roomID);
        mClient = client;

        // If client is the host, Implement host logic!
        if(client.is_host()) {
            Log.v(TAG, "Client is hosting the game! " + client.get_ID());
            hostRoom(client, roomID); // Adds a new room to firebase.
            mJoinRoom = true;
        }
        // If they aren't the host, allow the room to get the instance from Firebase!
        else {
            addEventListener(mRef);
        }
    }

    /**
     * Add a room to FireBase.
     * @param roomID Name of the new Room.
     */
    private void hostRoom(Client client, String roomID) {

        // Create a new local Room.
        mRoom = new Room(MAXPLAYERS, roomID);
        mRoom.addClient(client);

        // Serialize it.
        Gson gson = new Gson();
        String dataToFirebase = gson.toJson(mRoom);

        // Send the data to firebase.
        mRef.setValue(dataToFirebase);

        // Add a listener to the reference, to detect changes.
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

                // Get the serialized data from Firebase
                String roomJson = dataSnapshot.getValue(String.class);
                Gson gson = new Gson();
                mRoom = gson.fromJson(roomJson, Room.class);

                // If the Client isn't in the room, have them join it!
                if(!mJoinRoom)
                {
                    mJoinRoom = true;
                    addClientToRoom(mClient);
                }

                // Log for allowing us to know that the room updated.
                Log.v(TAG, "Updating Room! Clients: " + mRoom.getClients().toString());

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadRoom:onCancelled", databaseError.toException());
                // ...
            }
        };

        // Attach the event listener to the reference!
        ref.addValueEventListener(roomListener);

    }

    /**
     * Add a client to the Room when joining it.
     * @param client Client to be added.
     */
    private void addClientToRoom(Client client) {

        // Change our local version first.
        mRoom.addClient(client);

        // Serialize it, and send it up to Firebase.
        Gson gson = new Gson();
        String dataToFirebase = gson.toJson(mRoom);
        mRef.setValue(dataToFirebase);

    }


}
