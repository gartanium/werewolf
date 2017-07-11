package com.qd8s.werewolf2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 6/5/17.
 */
//
public class Day {
    public Map<User, Integer> voteCounter(ArrayList<User> userList) {
        Map<User, Integer> userVotes = new HashMap<>();
        User nominee = new User();
        for (int i = 0; i < userList.size(); i++) {
            Integer votes = 0;
            nominee = userList.get(i);
            for (int j = 0; j < userList.size(); j++) {
                if (nominee == userList.get(j).getTarget() && userList.get(j).getTarget() != null) {
                    votes++;
                    userList.get(j).setTarget(null);
                }
            }
            userVotes.put(nominee, votes);
        }
        return userVotes;
    }

    public Map<User, Integer> getTopNominees(Map<User, Integer> nominees) {
        Map<User, Integer> topNominees = new HashMap<>();
        Integer topFirst = 0;
        Integer topSecond = 0;
        User topUser = new User();
        User secondUser = new User();
        for (Map.Entry<User, Integer> entry : nominees.entrySet()) {
            if (entry.getValue() >= topFirst) {
                topSecond = topFirst;
                topFirst = entry.getValue();
                secondUser = topUser;
                topUser = entry.getKey();
            } else if (entry.getValue() < topFirst & entry.getValue() > topSecond) {
                topSecond = entry.getValue();
                secondUser = entry.getKey();
            }
        }
        topNominees.put(topUser, topFirst);
        topNominees.put(secondUser, topSecond);
        return topNominees;
    }

    public User executeVerdict(Map<User, Integer> nominees) {
        Integer topVotes = 0;
        Integer secondVotes = 0;
        User guiltyPerson = new User();
        for (Map.Entry<User, Integer> entry : nominees.entrySet()) {
            if (entry.getValue() >= topVotes) {
                secondVotes = topVotes;
                topVotes = entry.getValue();
            }

            if(topVotes == secondVotes) {
                guiltyPerson = null;
            } else {
                guiltyPerson = entry.getKey();
            }
        }
        return guiltyPerson;
    }
 }
