package com.qd8s.werewolf2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testUserIsAlive() {
        User testObj = new User();

        testObj.setAlive(true);

        assertEquals(testObj.isAlive(), true);
    }

    @Test
    public void testTargetObj() {
        User testObj = new User();
        User targetObj = new User();

        assertEquals(true, targetObj.isAlive());

        testObj.setRole("Wolf");

        testObj.setTarget(targetObj);
        testObj.performRole();

        assertEquals(false, targetObj.isAlive());

    }

    @Test
    public void testSetImmune() {
        User testObj = new User();
        testObj.setRole("Doc");

        User target = new User();

        testObj.setTarget(target);
        testObj.performRole();

        assertEquals(true, target.isImmune());

    }


}