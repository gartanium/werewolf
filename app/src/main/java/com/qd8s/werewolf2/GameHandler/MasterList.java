package com.qd8s.werewolf2.GameHandler;

import android.util.Pair;

import java.util.List;

/**
 * Created by Matthew on 6/24/2017.
 * A class containing all the important Room Data for the Master List.
 * Also contains data for translaiting it into an Array Addapter.
 */

public class MasterList {

    /**
     * A list of room Ids and the Player count.
     */
    private List<RoomData> mRoomDataList;

    /**
     *
     * @return Returns a list of Room Data.
     */
    public List<RoomData> getRoomDataList() { return mRoomDataList;}

    /**
     *
     * @return The number of Rooms in the Master List.
     */
    public int getRoomCount(){ return mRoomDataList.size();}

    /**
     * Add a room to the Master List.
     * @param roomID      ID of the room.
     * @param playerCount Number of players in the Room.
     * @param maxPlayers Maximum number of players allowed in the Room.
     */
    public void addRoom(String roomID, int playerCount, int maxPlayers) {
        RoomData data = new RoomData(roomID, playerCount, maxPlayers);

    }

    /**
     * Removes data from the Room.
     * @param roomID
     * @param playerCount
     */
    public void removeRoom(String roomID, int playerCount) {

    }
}
