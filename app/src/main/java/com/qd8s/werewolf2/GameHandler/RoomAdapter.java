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
 * Description: RoomAdapter is a class for handling the Client's local version of a Room,
 * and updating it whenever there is a change in Firebase. Additionally, it will at some point
 * contain logic for updating the Firebase's version any time the local one changes. For now,
 * it allows a Client to join the Room in firebase upon RoomAdapter initialization if the
 * Client isn't the Host. If they are the host, then this Handler will go ahead and create a new
 * Room in Firebase for us and others to join.Then it will have the Client join it.
 */

public class RoomAdapter {

    // A reference to the room in Firebase
    private DatabaseReference mRef;

    // The local version of the room.
    private Room mRoom;

    /**
     * Returns a local copy of the Room.
     * @return
     */
    public Room getRoom() { return mRoom; }

    /**
     * Returns the player count
     * @return
     */
    public int getPlayerCount() { return mRoom.get_Player_Count();}

    // The client associated with this handler.
    private Client mClient;

    // A boolean to determine if the client should be added to the room.
    //  If false, when the next update occurs, the client will join.
    // Then it should be set to false.
    private boolean mRoomIsInitialized;

    // Max players in a room.
    private final int MAXPLAYERS = 12;

    // Tag for debugging.
    private static final String TAG = "FireBaseHandler";

    // Name of the Master list that all things get published to.
    private static final String MASTERLIST = "MasterList";

    // Error message when a Client attempts to illegaly host a game.
    private static final String BADHOSTATTEMPT = "ERROR: BAD HOST ATTEMPT!";

    // Error message when methods are illegaly called, and mRoom is null.
    private static final String BADACCESSATEMPT =
            "ERROR: Client has not joined a room! Did you forget to call the join/hostRoom funcs?";

    /**
     * @return A copy of the List of Clients in the Room. Returns null when in error.
     */
    public List<Client> getClients() {

        // Validate that the Client has joined a room!
        try{
            if(mRoomIsInitialized)
                return new ArrayList<Client>(mRoom.getClients());
            else
                throw new NullPointerException(BADACCESSATEMPT);
        }
        catch(NullPointerException e){
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     *
     * @return The number of clients in the Room. Returns -1 for an error state.
     */
    public int getRoomSize() {

        // Validate that the Client has joined a room!
        try{
            if(mRoomIsInitialized)
                return mRoom.get_Player_Count();
            else
                throw new NullPointerException(BADACCESSATEMPT);
        }
        catch(NullPointerException e){
            Log.e(TAG, e.getMessage(), e);
            return -1;
        }
    }

    /**
     * A Firebase handler containing a Room.
     */
    public RoomAdapter(Client client) {
        mClient = client;
        mRoomIsInitialized = false;
        mRoom = null;
        mRef = null;
    }

    /**
     * Attempt to host a game!
     */
    public void hostRoom(String roomID) {

        // Don't let a Client host a room if they are already apart of one!
        try {
            if(mRoom == null) {
                // Create the firebase Room Reference.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mRef = database.getReference(MASTERLIST).child(roomID);

                // This is used for determining if our local version of the Room needs initializing.
                mRoomIsInitialized = true;

                // Create a new local Room.
                mRoom = new Room(MAXPLAYERS, roomID);
                mRoom.addClient(mClient);

                // Serialize it.
                Gson gson = new Gson();
                String dataToFirebase = gson.toJson(mRoom);

                // Send the data to firebase.
                mRef.setValue(dataToFirebase);

                // Add a listener to the reference, to detect changes.
                addEventListener(mRef);
                Log.v(TAG, "Local Client is hosting a room! Client:" + mClient.get_ID());
            }
            else
                throw new IllegalStateException("Unkonwn Error, a room already exists!");
        }
        catch(IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    /**
     * Makes the Client to join a room.
     * @param roomID Firebase ID for the room to join.
     */
    public void joinRoom(String roomID) {

        // Don't allow a client to join a room if they are already apart of one!
        try {
            if (mRoom == null) {
                // Initialize our reference, and set it equal to the firebase version of the Room.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mRef = database.getReference(MASTERLIST).child(roomID);

                // Add the event listener, so our local version of the Room will update whenever FB does.
                addEventListener(mRef);

                // Log for being nice and tidy :-)!
                Log.v(TAG, "Local Client is joining a room! Client:" + mClient.get_ID());
            }
            else
                throw new IllegalStateException("Client is already in a Room! RoomID: " + mRoom.getID());
            }
        catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * Removes the Client from the firebase room.
     */
    public void leaveRoom() {

        // Make sure that the Room isn't nUll!.
        try {
            if (mRoom != null && mRoom.getClients().contains(mClient)) {
                // Remove the Client from the local Room.
                mRoom.removeClient(mClient);

                // Serialize it, and send it up to Firebase.
                Gson gson = new Gson();
                String dataToFirebase = gson.toJson(mRoom);
                mRef.setValue(dataToFirebase);
            } else
                throw new IllegalStateException("Client is not in the room, or the Room is Null!");
        }
        catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }
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
                if(!mRoomIsInitialized)
                {
                    mRoomIsInitialized = true;
                    addClientToRoom();
                }

                // Log for allowing us to know that the room updated.
                Log.v(TAG, "Updating Room! Clients: " + mRoom.getClients().toString());

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting update failed, log a message
                Log.w(TAG, "loadRoom:onCancelled", databaseError.toException());
                // ...
            }
        };

        // Attach the event listener to the reference!
        ref.addValueEventListener(roomListener);

    }

    /**
     * Add a client to the Room when joining it.
     */
    private void addClientToRoom() {

        // Change our local version first.
        mRoom.addClient(mClient);

        // Serialize it, and send it up to Firebase.
        Gson gson = new Gson();
        String dataToFirebase = gson.toJson(mRoom);
        mRef.setValue(dataToFirebase);

    }


}
