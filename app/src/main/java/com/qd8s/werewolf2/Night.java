package com.qd8s.werewolf2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 6/5/17.
 */

public class Night {

    public User get_target() {
        return _target;
    }

    public void set_target(User _target) {
        this._target = _target;
    }

    public User get_roleDoer() {
        return _roleDoer;
    }

    public void set_roleDoer(User _roleDoer) {
        this._roleDoer = _roleDoer;
    }

    private User _target;
    private User _roleDoer;

    public User doRole()
    {
        //wolf
        if(_roleDoer.getRole() == "Werewolf") {
            _target.setAlive(false);
        }
        //doc
        if(_roleDoer.getRole() == "Doctor") {
            _target.setAlive(true);
            _target.setImmune(true);
        }
        //villager
        if(_roleDoer.getRole() == "villager") {
            return null;
        }

        return _target;
    }

    public Integer wolfVoteCounter(Integer vote) {
        if(_roleDoer.getAlpha() == true) {
            return vote + 2;
        } else {
            return vote + 1;
        }
    }

    public User wolfVote(Map< Integer, User> users)
    {
        Integer topVotes = 0;
        Integer secondVotes = 0;
        User guiltyPerson = new User();
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
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

    public Map< Integer, User> wolfNom(Map< Integer, User> users)
    {
        Map<Integer, User> topNominees = new HashMap<>();
        Integer topFirst = 0;
        Integer topSecond = 0;
        User topUser = new User();
        User secondUser = new User();
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
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



}
