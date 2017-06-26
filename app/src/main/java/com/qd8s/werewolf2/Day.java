package com.qd8s.werewolf2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 6/5/17.
 */
//
public class Day {
    public Integer voteCounter(Integer votes) {
        return votes + 1;
    }

    public Map<Integer, User> getTopNominees(Map<Integer, User> nominees) {
        Map<Integer, User> topNominees = new HashMap<>();
        Integer topFirst = 0;
        Integer topSecond = 0;
        User topUser = new User();
        User secondUser = new User();
        for(Map.Entry<Integer, User> entry : nominees.entrySet()) {
            if( entry.getKey() >= topFirst) {
                topSecond = topFirst;
                topFirst = entry.getKey();
                secondUser = topUser;
                topUser = entry.getValue();
            } else if(entry.getKey() < topFirst & entry.getKey() > topSecond) {
                topSecond = entry.getKey();
                secondUser = entry.getValue();
            }
        }
        topNominees.put(topFirst, topUser);
        topNominees.put(topSecond, secondUser);
        return topNominees;
    }

    public User executeVerdict(Map<Integer, User> nominees) {
        Integer topVotes = 0;
        Integer secondVotes = 0;
        User guiltyPerson = new User();
        for(Map.Entry<Integer, User> entry : nominees.entrySet()) {
            if( entry.getKey() >= topVotes) {
                secondVotes = topVotes;
                topVotes = entry.getKey();
            }

            if(topVotes == secondVotes) {
                guiltyPerson = null;
            } else {
                guiltyPerson = entry.getValue();
            }
        }
        return guiltyPerson;
    }
 }
