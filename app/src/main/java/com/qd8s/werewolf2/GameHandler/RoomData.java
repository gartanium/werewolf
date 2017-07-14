package com.qd8s.werewolf2.GameHandler;

/**
 * Created by Matthew on 6/24/2017.
 * Simple class for storing meta data about a Room.
 */

public class RoomData {
    public String ID;
    public int PlayerCount;
    public int MaxSize;

    /**
     * Non-default Constructor
     * @param roomID Firebase ID of the Room
     * @param playerCount Number of Players in the Room.
     * @param maxSize Maximum Size of the Room.
     */
    public RoomData(String roomID, int playerCount, int maxSize) {
        ID = roomID;
        PlayerCount = playerCount;
        MaxSize = maxSize;
    }
}
