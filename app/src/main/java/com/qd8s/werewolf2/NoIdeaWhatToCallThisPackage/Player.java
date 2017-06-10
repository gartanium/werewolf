package com.qd8s.werewolf2.NoIdeaWhatToCallThisPackage;

/**
 * Created by Matthew on 6/9/2017.
 * A player contains data for their ID and privelage level.
 */

public class Player {

    private enum PrivilegeLevel { standard, admin, host };
    private String id;
    private PrivilegeLevel privilegeLevel;
    private boolean inGame;

}
