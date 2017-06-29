package com.qd8s.werewolf2.GameHandler;

import com.qd8s.werewolf2.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/9/2017.
 * A Room is "Hosted" for a player.
 */
public class Room {


    /**
     * Returns a copy of the players data in the Room.
     * @return
     */
    public List<User> getUsers() { return new ArrayList<User>(mUser); }
    private List<User> mUser;

    /**
     * Returns the number of players in the Lobby.
     * @return
     */
    public int get_Player_Count() {
        return mUser.size();
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
     * Default constructor. Initializes lobby size to 0.
     * Sets a default name.
     */
    public Room() {
        mMaxPlayers = 0;
        mUser = new ArrayList<User>();
        mID = "default_name";
    }

    /**
     * Constructor that accepts a paramater for the maximum number of players.
     * @param maxPlayers Maximum players permitted in the Room.
     * @param ID ID (Name) Of the Room.
     */
    public Room(int maxPlayers, String ID) {
        mUser = new ArrayList<User>();
        mMaxPlayers = maxPlayers;
        mID = ID;
    }

    /**
     * Adds a client to the lobby. Throws an exception if there are to many players in the Room.
     * @param user client to add.
     */
    public void addUser(User user) throws IllegalArgumentException {

        if (mMaxPlayers == mUser.size()) {

            throw new IllegalArgumentException("To many players in the Game Room!");
        }
        else
            mUser.add(user);
    }

    /**
     * Removes a client from the Lobby.
     * @param user User to remove from the Room
     */
    public void removeUser(User user) {
        mUser.remove(user);
    }

    /**
     * Removes a player from the Lobby.
     * @param index Index of player to remove.
     */
    public void removeUser(int index) {

        if(index >= mUser.size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index!");
        }
        else
            mUser.remove(index);

    }

    /**
     * Updates the room with a new list of clients!
     * @param users A list of clients.
     */
    public void updateRoom(List<User> users) {
        mUser = users;
    }

    /**
     * Updates user with similar ID.
     * @param user
     */
    public void updateUser(User user) {
        for(int i = 0; i < mUser.size(); i++) {
            if(mUser.get(i).getName() == user.getName()) {
                mUser.set(i, user);
            }
        }
    }
}
