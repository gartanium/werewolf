package com.qd8s.werewolf2.GameHandler;

import android.os.Parcel;
import android.os.Parcelable;

import com.qd8s.werewolf2.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/9/2017.
 * A Room is "Hosted" for a player.
 */
public class Room implements Parcelable {

    private String mID;                 // ID to ID the Room.
    private int mMaxPlayers;            // Maximum players allowed in the Room.
    private List<User> mUsers;          // List of users in the Room.
    private boolean mReadyToStart;      // Bool for deciding when to start the game.

    private final String JOIN_ERROR_TOMANY_USERS = "ERROR: To many users in the room!";
    private final String GET_USER_ERROR_USER_NOT_IN_ROOM = "ERROR: User not in the room!";
    private final String ERROR_USER_ALREADY_IN_ROOM = "ERROR: User already in the room!";
    private final String ERROR_USER_ISNT_HOST = "ERROR: Only the host can start the room!";

    /**
     * Returns a copy of the players data in the Room.
     * @return
     */
    public List<User> getUsers() { return new ArrayList<User>(mUsers); }

    /**
     * Returns the number of players in the Lobby.
     * @return
     */
    public int get_Player_Count() {
        return mUsers.size();
    }

    /**
     * Name of the Room.
     * @return
     */
    public String getID() { return mID; }

    /**
     * Maximum number of players in the Lobby.
     * @return
     */
    public int getMaxPlayers() {
        return mMaxPlayers;
    }


    /**
     * Returns a user by index.
     * @param index Index o a user
     * @throws throws IndexOutOfBoundsException
     * @return
     */
    private User getUser(int index) throws IndexOutOfBoundsException {
        try {
            return mUsers.get(index);
        }
        catch(IndexOutOfBoundsException e) {
            throw e;
        }
    }

    /**
     * getUser
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
    public User getUser(String id) throws IllegalArgumentException {

        String usersInRoom = "";
        // foreach user, see if their ID matches!
        for (User u: mUsers) {
            if (u.getID().equals(id)) {
                return u;
            }
            else
                usersInRoom += u.getID() + " ";
        }

        // If the Id is not found, throw this exception!
        throw new IllegalArgumentException(GET_USER_ERROR_USER_NOT_IN_ROOM + " IDS: " + usersInRoom);
    }

    // Set to true when the Room is ready to start the game!
    public void startRoom(User user) {
        if(user.isHost()) {
            mReadyToStart = true;
        }
        else
            throw new IllegalArgumentException(ERROR_USER_ISNT_HOST);
    }

    protected Room(Parcel in) {
        mUsers = in.createTypedArrayList(User.CREATOR);
        mID = in.readString();
        mMaxPlayers = in.readInt();
        mReadyToStart = in.readByte() != 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    /**
     * Default constructor. Initializes lobby size to 0.
     * Sets a default name.
     */
    public Room() {
        mMaxPlayers = 0;
        mUsers = new ArrayList<User>();
        mID = "default_name";
        mReadyToStart = false;
    }

    /**
     * Constructor that accepts a paramater for the maximum number of players.
     * @param maxPlayers Maximum players permitted in the Room.
     * @param ID ID (Name) Of the Room.
     */
    public Room(int maxPlayers, String ID) {
        mUsers = new ArrayList<User>();
        mMaxPlayers = maxPlayers;
        mID = ID;
        mReadyToStart = false;
    }

    /**
     * Check to see if the room is ready to start.
     * @return whether or not the room is ready to start.
     */
    public boolean isReadyToStart() { return mReadyToStart; }

    /**
     *
     * @return Returns true if the room is full or above capacity.
     */
    public boolean isFull() { return mUsers.size() >= mMaxPlayers; }

    /**
     * Check to see if each user is ready to switch to the next activity.
     * @return Returns true if each user is ready to switch activities.
     */
    public boolean isReadyToTransition() {
        for (User u: mUsers) {
            if(u.getState() != User.UserState.JoinNextActivity)
                return false;
        }

        return true;
    }

    /**
     * Checks to see if each user is done with the Night.
     * @return Returns true if each user is done with the Night.
     */
    public boolean isDoneWithNight() {
        for (User u: mUsers) {
            if (u.getState() != User.UserState.DoneWithNight)
                return false;

        }
        return true;
    }

    /**
     * Checks to see if each user is Idle
     * @return Returns true if each user is Idle in the Room.
     */
    public boolean isUsersIdle() {
        for (User u: mUsers) {
            if (u.getState() != User.UserState.Idle)
                return false;

        }
        return true;
    }

    /**
     * Sets every user to the Idle state in the Room.
     */
    public void setUsersIdle() {
        for (User u: mUsers){
            u.setState(User.UserState.Idle);
        }
    }

    /**
     * Sets each user's state to state.
     * @param state
     */
    public void setUsersState(User.UserState state) {
        for(User u: mUsers) {
            u.setState(state);
        }
    }

    /**
     * Set the state of a specific user.
     * @param user
     * @param state
     */
    public void setState(User user, User.UserState state) {
        getUser(user.getID()).setState(state);
    }

    /**
     * Adds a client to the lobby.
     * @param user client to add.
     * @throws IllegalArgumentException if there are to many users or user already is in the room.
     */
    public void addUser(User user) throws IllegalArgumentException {

        if (mMaxPlayers == mUsers.size()) {

            throw new IllegalArgumentException(JOIN_ERROR_TOMANY_USERS);
        }
        else if (containsUser(user)) {
            throw new IllegalArgumentException(ERROR_USER_ALREADY_IN_ROOM);
        }
        else {
            mUsers.add(user);
        }
    }

    /**
     * Removes a client from the Lobby.
     * @param user User to remove from the Room
     */
    public void removeUser(User user) {

        mUsers.remove(user);
    }

    /**
     * Removes a player from the Lobby.
     * @param index Index of player to remove.
     */
    public void removeUser(int index) throws IndexOutOfBoundsException {

        if(index >= mUsers.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        else
            mUsers.remove(index);

    }

    /**
     * Updates a user in the Room.
     * @param user
     */
    public void updateUser(User user) {
        mUsers.set(findIndex(user.getID()), user);
    }

    private int findIndex(String ID) {
        if(mUsers != null) {
            for (int i = 0; i < mUsers.size(); i++) {
                if (mUsers.get(i).getID().equals(ID)) {
                    return i;
                }
            }
        }
        else
            throw new NullPointerException("There are no Users in the Room!");

        throw new IllegalArgumentException("User is not in the Room!");
    }

    /**
     * Updates all the users in the Room.
     * @param users
     */
    public void updateUsers(List<User> users) {
        if(users.size() == mUsers.size()) {
            mUsers = users;
        }
    }


    /**
     * Returns true if the room contains a given user. (Checks by ID)
     * @param user
     * @return
     */
    public boolean containsUser(User user) {
        for (User u: mUsers) {
            if(u.getID() == user.getID()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mUsers);
        dest.writeString(mID);
        dest.writeInt(mMaxPlayers);
        dest.writeByte((byte) (mReadyToStart ? 1 : 0));
    }

    // Cycles through each user and returns true if they are all ready to transition states.
}
