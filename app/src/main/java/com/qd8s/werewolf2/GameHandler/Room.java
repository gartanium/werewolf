package com.qd8s.werewolf2.GameHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/9/2017.
 * A Room is "Hosted" for a player.
 */
public class Room {


    // Contains a simple list of players.
    private List<Client> _clients;

    public int get_maxPlayers() {
        return _maxPlayers;
    }

    private int _maxPlayers;

    /**
     * Returns a list of players in the room.
     * @return
     */
    public List<Client> getPlayers() {
        return new ArrayList<Client>(_clients);
    }

    /**
     * Returns the number of players in the Lobby.
     * @return
     */
    public int get_Player_Count() {
        return _clients.size();
    }

    /**
     * Default constructor. Initializes lobby size to 0.
     */
    public Room() {
        _maxPlayers = 0;
        _clients = new ArrayList<Client>();
    }

    // Constructor that accepts a paramater for the maximum number of players.
    public Room(int maxPlayers) {
        _clients = new ArrayList<Client>();
        _maxPlayers = maxPlayers;
    }

    /**
     * Adds a client to the lobby. Throws an exception if there are to many players in the Room.
     * @param client
     */
    public void addPlayer(Client client) throws IllegalArgumentException {

        if (_maxPlayers == _clients.size()) {

            throw new IllegalArgumentException("To many players in the Game Room!");
        }
        else
            _clients.add(client);
    }

    /**
     * Removes a client from the Lobby.
     * @param client Client to remove from the Room
     */
    public void removePlayer(Client client) {
        _clients.remove(client);
    }

    /**
     * Removes a player from the Lobby.
     * @param index Index of player to remove.
     */
    public void removePlayer(int index) {

        if(index >= _clients.size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index!");
        }
        else
            _clients.remove(index);

    }



}
