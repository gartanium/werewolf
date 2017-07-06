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


    protected Room(Parcel in) {
        mUsers = in.createTypedArrayList(User.CREATOR);
        mID = in.readString();
        mMaxPlayers = in.readInt();
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
     * Returns a copy of the players data in the Room.
     * @return
     */
    public List<User> getUsers() { return new ArrayList<User>(mUsers); }
    private List<User> mUsers;

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
    private String mID;

    /**
     * Maximum number of players in the Lobby.
     * @return
     */
    public int getMaxPlayers() {
        return mMaxPlayers;
    }
    private int mMaxPlayers;

    /**
     *
     * @return Returns true if the room is full or above capacity.
     */
    public boolean isFull() { return mUsers.size() >= mMaxPlayers; }

    /**
     * Default constructor. Initializes lobby size to 0.
     * Sets a default name.
     */
    public Room() {
        mMaxPlayers = 0;
        mUsers = new ArrayList<User>();
        mID = "default_name";
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
    }

    /**
     * Adds a client to the lobby. Throws an exception if there are to many players in the Room.
     * @param user client to add.
     */
    public void addUser(User user) throws IllegalArgumentException {

        if (mMaxPlayers == mUsers.size()) {

            throw new IllegalArgumentException("To many players in the Room!");
        }
        else
            mUsers.add(user);
    }

    /**
     * Returns a user by index.
     * @param index Index o a user
     * @throws throws IndexOutOfBoundsException
     * @return
     */
    public User getUser(int index) throws IndexOutOfBoundsException {
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

        // foreach user, see if their ID matches!
        for (User u: mUsers) {
            if(u.getID() == id){
                return u;
            }
        }

        // If the Id is not found, throw this exception!
        throw new IllegalArgumentException("ID: " + id + " is not in the Room!");
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
    public void removeUser(int index) {

        if(index >= mUsers.size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index!");
        }
        else
            mUsers.remove(index);

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
    }
}
