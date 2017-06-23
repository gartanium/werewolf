package com.qd8s.werewolf2.GameHandler;

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
        Room testObj = new Room(maxLobbySize);

        for(int i = 0; i < maxLobbySize; i++)
        {
            testObj.addClient(new Client());

            int expected = i + 1;
            int actual = testObj.get_Player_Count();

            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void removePlayer() throws Exception {

        int maxLobbySize = 5;
        Room testObj = new Room(maxLobbySize);

        for(int i = 0; i < maxLobbySize; i++) {
            testObj.addClient(new Client());
        }

        for(int i = maxLobbySize - 1; i > 0; i--) {

            // First get our player that is going to be removed.
            List<Client> results = testObj.getClients();
            Client toRemove = results.get(i);

            testObj.removeClient(i);

            results = testObj.getClients();

            Assert.assertFalse(results.contains(toRemove));
        }

    }

    @Test
    public void removePlayerException() throws Exception {
        Room testObj = new Room();

        try {
            testObj.removeClient(0);
        }
        catch (IndexOutOfBoundsException exception)
        {
            return;
        }

        Assert.fail("Expected an IndexOutOfBoundsException!");
    }

    @Test
    public void removePlayerExceptionTwo() throws Exception {
        Room testObj = new Room(5);
        testObj.addClient(new Client());

        try {
            testObj.removeClient(1);
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
        Room testObj = new Room(maxLobbySize);
        testObj.addClient(new Client());

        try {

            testObj.addClient(new Client());
        }
        catch (IllegalArgumentException exception)
        {
            return;
        }

        Assert.fail("Expected an Illegal Argument Exception!");

    }
}