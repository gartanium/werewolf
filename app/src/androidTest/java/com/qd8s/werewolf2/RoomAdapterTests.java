package com.qd8s.werewolf2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.qd8s.werewolf2.GameHandler.RoomAdapter;
import com.qd8s.werewolf2.GameHandler.RoomStartListener;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RoomAdapterTests {

   @Test
    public void HostRoomTest() {
       RoomAdapter testObj = new RoomAdapter();
       User host = new User("Test_ID", "Bob");

       Assert.assertFalse(host.isHost());

       testObj.hostRoom("TestRoom", host);

       Assert.assertTrue(host.isHost());
   }

}
