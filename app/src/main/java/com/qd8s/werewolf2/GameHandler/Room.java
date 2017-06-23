package com.qd8s.werewolf2.GameHandler;

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
    public List<Client> getClients() { return new ArrayList<Client>(mClients); }
    private List<Client> mClients;

    /**
     * Returns the number of players in the Lobby.
     * @return
     */
    public int get_Player_Count() {
        return mClients.size();
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
        mClients = new ArrayList<Client>();
        mID = "default_name";
    }

    /**
     * Constructor that accepts a paramater for the maximum number of players.
     * @param maxPlayers Maximum players permitted in the Room.
     * @param ID ID (Name) Of the Room.
     */
    public Room(int maxPlayers, String ID) {
        mClients = new ArrayList<Client>();
        mMaxPlayers = maxPlayers;
        mID = ID;
    }

    /**
     * Adds a client to the lobby. Throws an exception if there are to many players in the Room.
     * @param client client to add.
     */
    public void addClient(Client client) throws IllegalArgumentException {

        if (mMaxPlayers == mClients.size()) {

            throw new IllegalArgumentException("To many players in the Game Room!");
        }
        else
            mClients.add(client);
    }

    /**
     * Removes a client from the Lobby.
     * @param client Client to remove from the Room
     */
    public void removePlayer(Client client) {
        mClients.remove(client);
    }

    /**
     * Removes a player from the Lobby.
     * @param index Index of player to remove.
     */
    public void removePlayer(int index) {

        if(index >= mClients.size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index!");
        }
        else
            mClients.remove(index);

    }

    /**
     * Updates the room with a new list of clients!
     * @param clients A list of clients.
     */
    public void updateRoom(List<Client> clients) {
        mClients = clients;
    }
}
