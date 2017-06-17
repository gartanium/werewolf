package com.qd8s.werewolf2;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by admin on 6/5/17.
 */
//
public class Day {
    public Map<Integer, User> nominate(){
        Map<Integer, User> nominees = null;
        return nominees;
    }

    public Boolean vote(Map<Integer, User> nominees) {
        Boolean verdict = false;
        // Guilty = true
        // Innocent = false
        return verdict;
    }

    public void executeVerdict(User target) {
        target.setAlive(false);
    }
 }
