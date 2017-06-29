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

            throw new IllegalArgumentException("To many players in the Game Room!");
        }
        else
            mUsers.add(user);
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

    /**
     * Updates the room with a new list of clients!
     * @param users A list of clients.
     */
    public void updateRoom(List<User> users) {
        mUsers = users;
    }

    /**
     * Updates user with similar ID.
     * @param user
     * @throws this throws an Illegal argument exception is the user is not found in the room.
     */
    public void updateUser(User user) {
        for(int i = 0; i < mUsers.size(); i++) {
            if(mUsers.get(i).getName() == user.getName()) {
                mUsers.set(i, user);
                return;
            }
        }

        throw new IllegalArgumentException("User not foudn in the Room! User: " + user.getName());
    }

    /**
     * Updates a list of users to firebase.
     * @param users A list of users.
     */
    public void updateUsers(List<User> users) {
        for (User u : users) {
            for(int i = 0; i < mUsers.size(); i++) {
                if(mUsers.get(i).getName() == users.get(i).getName()) {
                    mUsers.set(i, u);
                    continue;
                }

                if (i + 1 == mUsers.size())
                    throw new IllegalArgumentException("Invalid users list!");
            }

        }

    }
}
