package com.qd8s.werewolf2.GameHandler;

import com.qd8s.werewolf2.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Matthew on 6/15/2017.
 */
public class RoomTest {



    @Before
    public void initialize() {

    }

    @Test
    public void addPlayer() throws Exception {

        int maxLobbySize = 5;
        Room testObj = new Room(maxLobbySize, "foo");

        for(int i = 0; i < maxLobbySize; i++)
        {
            testObj.addUser(new User(new Integer(i).toString(), "Bob"));

            int expected = i + 1;
            int actual = testObj.get_Player_Count();

            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void removePlayer() throws Exception {

        int maxLobbySize = 5;
        Room testObj = new Room(maxLobbySize, "foo");

        for(int i = 0; i < maxLobbySize; i++) {
            testObj.addUser(new User(new Integer(i).toString(), "bob"));
        }

        for(int i = maxLobbySize - 1; i > 0; i--) {

            // First get our player that is going to be removed.
            List<User> results = testObj.getUsers();
            User UsertoRemove = results.get(i);

            testObj.removeUser(i);

            results = testObj.getUsers();

            Assert.assertFalse(results.contains(UsertoRemove));
        }

    }

    @Test
    public void removePlayerException() throws Exception {
        Room testObj = new Room();

        try {
            testObj.removeUser(0);
        }
        catch (IndexOutOfBoundsException exception)
        {
            return;
        }

        Assert.fail("Expected an IndexOutOfBoundsException!");
    }

    @Test
    public void removePlayerExceptionTwo() throws Exception {
        Room testObj = new Room(5, "foo");
        testObj.addUser(new User());

        try {
            testObj.removeUser(1);
        }
        catch (IndexOutOfBoundsException exception)
        {
            return;
        }

        Assert.fail("Expected an IndexOutOfBoundsException!");
    }

    /**
     * Attempts to add a player in a full lobby.
     * @throws Exception
     */
    @Test
    public void addPlayerException() throws Exception {

        int maxLobbySize = 1;
        Room testObj = new Room(maxLobbySize, "foo");
        testObj.addUser(new User());

        try {

            testObj.addUser(new User());
        }
        catch (IllegalArgumentException exception) {
            return;
        }

        Assert.fail("Expected an Illegal Argument Exception!");

    }

    @Test
    public void addPlayerExceptionAlreadyInRoom() throws Exception {
        Room testObj = new Room(12, "foo");
        User testUser = new User("ID_3082", "Bob");
        testObj.addUser(testUser);

        User duplicateTestUser = new User("ID_3082", "Bob");

        try {
            testObj.addUser(duplicateTestUser);
        }
        catch (IllegalArgumentException exception) {
            return;
        }

        Assert.fail("Expected an Illegal Argument Exception!");
    }

    @Test
    public void updateUser() throws Exception {
        Room testObj = new Room(12, "foo");
        User testUser = new User("foo", "bob");

        testObj.addUser(testUser);

        testUser.setName("john");

        Assert.assertEquals("john", testObj.getUser(0).getName());
    }

    @Test
    public void getUser() throws Exception {
        Room testObj = new Room(12, "foo");
        User testUser = new User("foo", "bob");

        testObj.addUser(testUser);

        testUser.setName("Jim");

        User actual = testObj.getUser("foo");

        String expected = "Jim";

        Assert.assertEquals(expected, actual.getName());
    }

    @Test
    public void containsUser() throws Exception {

        try {
            Room testObj = new Room(12, "foo");
            User testOne = new User("1", "John");
            User testTwo = new User("2", "Bob");
            User testThree = new User("3", "Bill");

            testObj.addUser(testOne);
            testObj.addUser(testTwo);

            Assert.assertTrue(testObj.containsUser(testOne));
            Assert.assertFalse(testObj.containsUser(testThree));
        }
        catch(IllegalArgumentException e) {
            Assert.fail(e.getMessage());
        }
    }
}