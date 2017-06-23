package com.qd8s.werewolf2.GameHandler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.qd8s.werewolf2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 6/9/2017.
 * Contains a refrence to every active game.
 */

public class MasterList {

    private List<Room> roomList;

    /**
     * Returns a list of all the rooms.
     * @return
     */
    public List<Room> getList() {
        return new ArrayList<Room> (roomList);
    }

    /**
     * Default constructor for the arrayList.
     */
    public MasterList() {
        roomList = new ArrayList<Room>();
    }

    /**
     * Add a new room to the master list.
     * @param room
     */
    void addRoom(Room room) {
        roomList.add(room);
    }

    /**
     * Remove a room from the master list.
     * @param room
     */
    void removeRoom(Room room) {
        roomList.remove(room);
    }

   // @Override
  //  public void publish() {
        // Publishes the lobby data to firebase.
        // Generate a Lobby, and add it to Firebase!

        //Gson gson = new Gson();

        // Gson converts it into a Json string.
       // String dataToFirebase =  gson.toJson(this);

        // Now send the data up to Firebase!!
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
       // DatabaseReference myRef = database.getReference(String.valueOf(R.string.Master_List_Name));
      //  myRef.setValue(dataToFirebase);

    //}




}
