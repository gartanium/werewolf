package com.qd8s.werewolf2.GameHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/9/2017.
 * A Room is "Hosted" for a player.
 */
public class Room {


    // Contains a simple list of players.
    private List<Player> _players;

    public int get_maxPlayers() {
        return _maxPlayers;
    }

    private int _maxPlayers;

    /**
     * Returns a list of players in the room.
     * @return
     */
    public List<Player> getPlayers() {
        return new ArrayList<Player>(_players);
    }

    /**
     * Returns the number of players in the Lobby.
     * @return
     */
    public int get_Player_Count() {
        return _players.size();
    }

    /**
     * Default constructor. Initializes lobby size to 0.
     */
    public Room() {
        _maxPlayers = 0;
        _players = new ArrayList<Player>();
    }

    // Constructor that accepts a paramater for the maximum number of players.
    public Room(int maxPlayers) {
        _players = new ArrayList<Player>();
        _maxPlayers = maxPlayers;
    }

    /**
     * Adds a player to the lobby. Throws an exception if there are to many players in the Room.
     * @param player
     */
    public void addPlayer(Player player) throws IllegalArgumentException {

        if (_maxPlayers == _players.size()) {

            throw new IllegalArgumentException("To many players in the Game Room!");
        }
        else
            _players.add(player);
    }

    /**
     * Removes a player from the Lobby.
     * @param player Player to remove from the Room
     */
    public void removePlayer(Player player) {
        _players.remove(player);
    }

    /**
     * Removes a player from the Lobby.
     * @param index Index of player to remove.
     */
    public void removePlayer(int index) {

        if(index >= _players.size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index!");
        }
        else
            _players.remove(index);

    }



}
