package com.qd8s.werewolf2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Status Team 4 on 7/5/2017.
 */
public class NightTest {
    @Test
    public void get_target() throws Exception {
        User testObject = new User("testing");
        User target = new User("target");
        testObject.setTarget(target);
        Night nightTestObject = new Night();
        nightTestObject.get_target();

    }

    @Test
    public void set_target() throws Exception {

    }

    @Test
    public void get_roleDoer() throws Exception {

    }

    @Test
    public void set_roleDoer() throws Exception {

    }

    @Test
    public void doRole() throws Exception {

    }

    @Test
    public void wolfVoteCounter() throws Exception {

    }

    @Test
    public void wolfVote() throws Exception {

    }

    @Test
    public void wolfNom() throws Exception {

    }

}