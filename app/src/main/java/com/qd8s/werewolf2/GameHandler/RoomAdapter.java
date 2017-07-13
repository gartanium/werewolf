package com.qd8s.werewolf2.GameHandler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.qd8s.werewolf2.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/22/2017.
 * Description: RoomAdapter is a class for handling the Client's local version of a Room,
 * and updating it whenever there is a change in Firebase. Additionally, it will at some point
 * contain logic for updating the Firebase's version any time the local one changes. For now,
 * it allows a User to join the Room in firebase upon RoomAdapter initialization if the
 * User isn't the Host. If they are the host, then this Handler will go ahead and create a new
 * Room in Firebase for us and others to join.Then it will have the User join it.
 */

public class RoomAdapter implements Parcelable{

    // A list of ReadyListners that get a function called whenever update
    // Updates.
    private List<RoomStartListener> mRoomStartListeners = new ArrayList<RoomStartListener>();

    /**
     * A list of listeners for whenever every user is done with the Night.
     */
    private List<NightFinishedListener> mNightFinishedListeners = new ArrayList<>();

    private List<UserJoinedListener> mUserJoinedListener = new ArrayList<>();

    /**
     * Add a listener to the RoomAdapater!
     */
    public void addListener(RoomStartListener listener) {
        mRoomStartListeners.add(listener);
    }

    /**
     * Add a night finished listener that is called whenever everyone is done with the Night.
     * @param listener
     */
    public void addListener(NightFinishedListener listener) { mNightFinishedListeners.add(listener);}

    /**
     * Add a listener for when a User joins a room.
     * @param listener
     */
    public void addListener(UserJoinedListener listener) { mUserJoinedListener.add(listener);}

    // A reference to the room in Firebase
    private DatabaseReference mRef;

    // The local version of the room.
    private Room mRoom;

    /**
     * Returns the player count
     * @return
     */
    public int getPlayerCount() { return mRoom.get_Player_Count();}

    // Max players in a room.
    private final int MAXPLAYERS = 12;

    // Tag for debugging.
    private static final String TAG = "FireBaseHandler";

    // Name of the Master list that all things get published to.
    private static final String MASTERLIST = "MasterList";

    // Error message when a User attempts to illegaly host a game.
    private static final String BADHOSTATTEMPT = "ERROR: BAD HOST ATTEMPT!";

    // Error message when methods are illegaly called, and mRoom is null.
    private static final String BADACCESSATEMPT =
            "ERROR: Client has not joined a room! Did you forget to call the join/hostRoom funcs?";

    /**
     * @return Returns if the local client is connected to Firebase.
     */
    public boolean isConnectedToFirebase() { return mRoom != null; }

    /**
     * Returns the ID of the inner room.
     * @return Id of the room.
     * @throws NullPointerException
     */
    public String getID() throws NullPointerException {
        if(mRoom != null)
            return mRoom.getID();
        else
            throw new NullPointerException("mRoom is not yet initialized! (Did you forget to join the Room?");
    }

    /**
     * @return A copy of the List of Users in the Room. Returns null when in error.
     */
    public List<User> getUsers() {

        // Validate that the User has joined a room!
        try{
            if(mRoom != null)
                return new ArrayList<User>(mRoom.getUsers());
            else
                throw new NullPointerException(BADACCESSATEMPT);
        }
        catch(NullPointerException e){
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Returns the updated version of the User.
     * @param user
     * @return
     */
    public User getUser(User user) {
        return mRoom.getUser(user.getID());
    }

    /**
     *
     * @return The number of clients in the Room. Returns -1 for an error state.
     */
    public int getRoomSize() {

        // Validate that the User has joined a room!
        try{
            if(mRoom != null)
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
    public RoomAdapter() {
        mRoom = null;
        mRef = null;
     }

     /**
     * Attempt to host a game!
     */
    public void hostRoom(String roomID, User user) {

        // Don't let a User host a room if they are already apart of one!
        try {
            if(mRoom == null) {
                // Create the firebase Room Reference.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mRef = database.getReference(MASTERLIST).child(roomID);

                // Create a new local Room.
                mRoom = new Room(MAXPLAYERS, roomID);
                mRoom.addUser(user);

                user.set_host(true);

                // Serialize it.
                Gson gson = new Gson();
                String dataToFirebase = gson.toJson(mRoom);

                // Send the data to firebase.
                mRef.setValue(dataToFirebase);

                // Add a listener to the reference, to detect changes.
                addEventListener(mRef);
                logUserUpdateMsg("hosting room!", user);
            }
            else
                throw new IllegalStateException("Unknown Error, a room already exists!");
        }
        catch(IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    /**
     * Makes the User to join a room.
     * @param user user to join the room.
     */
    public void joinRoom(User user) {
        addUserToRoom(user);

        // Fire all the events for all the Users!
        for ( UserJoinedListener listener: mUserJoinedListener) {
            listener.onUserJoined();
        }
    }

    /**
     * @param user user to leave the room.
     */
    public void leaveRoom(User user) {

        // Make sure that the Room isn't nUll!.
        try {
            if (mRoom != null && mRoom.getUsers().contains(user)) {
                // Remove the User from the local Room.
                mRoom.removeUser(user);

                // Serialize it, and send it up to Firebase.
                Gson gson = new Gson();
                String dataToFirebase = gson.toJson(mRoom);
                mRef.setValue(dataToFirebase);

                logUserUpdateMsg("leaving room", user);

            } else
                throw new IllegalStateException("Client is not in the room, or the Room is Null!");
        }
        catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * Connect to a room in firebase
     * @param roomID The id of the firebase room.
     */
    public void connectToRoom(String roomID) {

        // Initialize our reference, and set it equal to the firebase version of the Room.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference(MASTERLIST).child(roomID);
        // Add the event listener, so our local version of the Room will update whenever FB does.
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

                // See if any events need to be fired.
                checkForEvents();
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
     * Checks to see if we need to fire any Events.
     */
    private void checkForEvents() {
        // If room is ready to start, fire the event!
        if(mRoom.isReadyToStart())
        {
            for (RoomStartListener obj: mRoomStartListeners) {
                obj.onRoomStart();
            }
        }

        // If everyone in the Room is finished with Night, fire the event!
        /*if(mRoom.isDoneWithNight())
        {
            for (NightFinishedListener listener: mNightFinishedListeners) {
                listener.onNightFinished();
            }
        }*/
    }

    /**
     * Add a client to the Room when joining it.
     */
    private void addUserToRoom(User user) {

        try {
            if (isConnectedToFirebase()) {

                if(!mRoom.isFull()) {
                    // Change our local version first.
                    mRoom.addUser(user);

                    // Serialize it, and send it up to Firebase.
                    Gson gson = new Gson();
                    String dataToFirebase = gson.toJson(mRoom);
                    mRef.setValue(dataToFirebase);

                    // Log for allowing us to know that the room updated.
                    logUserUpdateMsg("joining room!", user);
                }
                else
                    throw new IllegalStateException("Unable to join room, it's full!");
            }
            else
                throw new IllegalStateException("Unable to join room, not connected to FireBase!");
        }
        catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }


    }

    /**
     * Updated version of the user. This function can become very time consuming!
     * @param user user to be updated
     */
    public void updateUser(User user) {

        try {

            logUserUpdateMsg("Attempting to update single User!", user);
            mRoom.updateUser(user);
            Gson gson = new Gson();
            String dataToFirebase = gson.toJson(mRoom);
            mRef.setValue(dataToFirebase);

        }
        catch (IllegalArgumentException e) {
            Log.e(TAG, "Invalid User: " + user.getID() + "! (Is the ID incorrect?)", e);
        }

    }

    /**
     * Updates a list of users.
     * @param users A list of users.
     */
    public void updateUsers(List<User> users) {
        for(int i = 0; i < users.size(); i++) {
            logUserUpdateMsg("Attempting to update all Users!", users.get(i));
            mRoom.updateUser(users.get(i));
        }

        // Send the updated version to Firebase!
        Gson gson = new Gson();
        String dataToFirebase = gson.toJson(mRoom);
        mRef.setValue(dataToFirebase);
    }

    /**
     * Attempts to start the Room if the user is the host.
     * @param user
     */
    public void startRoom(User user) {
        try {
            mRoom.startRoom(user);
            Gson gson = new Gson();
            String dataToFirebase = gson.toJson(mRoom);
            mRef.setValue(dataToFirebase);
        }
        catch (IllegalArgumentException e) {
            Log.v(TAG, e.getMessage(), e);
        }
    }

    /**
     * Check to see if all the players are ready to transition to the next activity!
     * @return
     */
    public boolean isRoomReadyToTransition() {
        if(mRoom.isReadyToTransition())
            return true;
        else
            return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mRoom, flags);
    }

    protected RoomAdapter(Parcel in) {
        mRoom = in.readParcelable(Room.class.getClassLoader());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference(MASTERLIST).child(mRoom.getID());
        addEventListener(mRef);

    }

    public static final Creator<RoomAdapter> CREATOR = new Creator<RoomAdapter>() {
        @Override
        public RoomAdapter createFromParcel(Parcel in) {
            return new RoomAdapter(in);
        }

        @Override
        public RoomAdapter[] newArray(int size) {
            return new RoomAdapter[size];
        }
    };

    /**
     * Logs an update message.
     * @param msg Update message to display.
     * @param user User affected
     */
    private void logUserUpdateMsg(String msg, User user) {
        Log.v(TAG, "Room: " + mRoom.getID() + " User: " + user.getID() + " " + msg);
    }
}
